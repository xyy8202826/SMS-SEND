package com.xyy.sms.task;

import com.xyy.Constant;
import com.xyy.JsonUtil;
import com.xyy.sms.model.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 * User: xuyuanye Date: 2016/8/18 Project: sms-send
 */
@Component("retryMessageDispatcher")
@Slf4j
public class RetryMessageDispatcher implements Runnable {
    //private static  final Logger log= LoggerFactory.getLogger(RetryMessageDispatcher.class);
    /**
     * 初始线程处理数
     */
    private int coreSize = 5;

    /**
     * 最大线程数
     */
    private int maxSize = 10;

    /**
     * 最大队列数
     */
    private int queueSize = 100;

    /**
     * 一天秒数
     */
    private static final long ONE_DAY_SEC = 24 * 60 * 60;

    /**
     * 空闲线程最大闲置时间
     */
    private long keepAliveTime = ONE_DAY_SEC;

    /**
     * 线程池接收新任务阀值
     */
    private int hungrySize = (int) (queueSize * 0.8);

    /**
     * 线程池
     */
    private WorkerPool pool;

    /**
     * 任务处理列
     */
    private BlockingQueue<Runnable> queue;

    /**
     * 命令处理Handler
     */
    @Autowired
    @Qualifier("retryMessageHandler")
    private MessageHandler retryMessageHandler;

    @Autowired
    private StringRedisTemplate template;

    /**
     * 线程任务执行方法
     */
    @Override
    public void run() {
        log.info("分发器[RetryMessageDispatcher]启动ing...");
        queue = new ArrayBlockingQueue(queueSize);
        pool = new WorkerPool(coreSize, maxSize, keepAliveTime, TimeUnit.SECONDS, queue);
        while (true) {
            if (queue.size() >= hungrySize) {
                continue;
            }
            log.info("befor KEY_PENDING_LIST");
            String messageKey = template.boundListOps(Constant.KEY_RETRY_LIST).rightPop(0,TimeUnit.SECONDS);
            log.info("KEY_PENDING_LIST key{}",messageKey);
            log.info("after KEY_PENDING_LIST ");
            String strMess=template.boundValueOps(Constant.KEY_SMS_PREFIX + messageKey).get();
            MessageDTO message=JsonUtil.parse(strMess, MessageDTO.class);
            try {
                pool.execute(new HandlerWrapper(messageKey,message,retryMessageHandler));
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * 中断线程
     */
    @PreDestroy
    public void destroy() {
        log.warn("收到分发器[MessageDispatcher]停止通知!!");
        pool.shutdown();
        Thread.interrupted();
    }

    /**
     * 任务处理线程池
     */
    private class WorkerPool extends ThreadPoolExecutor {
        public WorkerPool(int coreSize, int maxSize, long keepAlive, TimeUnit timeUnit, BlockingQueue<Runnable> queue) {
            super(coreSize, maxSize, keepAlive, timeUnit, queue);
        }

        @Override
        protected void afterExecute(Runnable runnable, Throwable throwable) {
            super.afterExecute(runnable, throwable);
        }
    }

}
