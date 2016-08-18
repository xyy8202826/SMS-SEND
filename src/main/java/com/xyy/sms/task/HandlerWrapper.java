package com.xyy.sms.task;

import com.xyy.sms.model.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 任务执行线程管理器
 * User: wen Date: 2015/09/28 ProductName: tradecenter Version: 1.0
 */
@Slf4j
@AllArgsConstructor
public class HandlerWrapper implements Runnable {

    private String key;

    /** 处理命令对象 */
    private MessageDTO message;

    /** 具体命令处理类 */
    private MessageHandler messageHandler;

    @Override
    public void run() {
        try {
            messageHandler.execute(key,message);
        } catch (Exception t) {
            log.error(t.getMessage() +"MessageHandler 异常:{}", t);
        }
    }

}
