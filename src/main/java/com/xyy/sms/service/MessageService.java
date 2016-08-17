package com.xyy.sms.service;

import com.xyy.sms.model.MessageDTO;

/**
 * Created by Administrator on 2016/8/17.
 */
public interface MessageService {
    /**
     * 发送消息
     * @param message
     * @return
     */
    boolean sendMessage(MessageDTO message);
}
