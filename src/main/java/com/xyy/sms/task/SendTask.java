package com.xyy.sms.task;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>
 * </p>
 * User: xuyuanye Date: 2016/8/18 Project: sms-send
 */
@Component
public class SendTask {
    private static  final Logger log= LoggerFactory.getLogger(SendTask.class);
    @Autowired
    @Qualifier("messageDispatcher")
    MessageDispatcher messageDispatcher;
    @Autowired
    @Qualifier("retryMessageDispatcher")
    RetryMessageDispatcher retryMessageDispatcher;

    @PostConstruct
    public void init(){
        log.info("SendTask init");
        SimpleAsyncTaskExecutor cmdDispatchExecutor = new SimpleAsyncTaskExecutor();
        cmdDispatchExecutor.setDaemon(true);
        cmdDispatchExecutor.execute(messageDispatcher);
        cmdDispatchExecutor.execute(retryMessageDispatcher);
    }
}
