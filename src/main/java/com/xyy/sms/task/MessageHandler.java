package com.xyy.sms.task;

import com.xyy.sms.model.MessageDTO;

/**
 * <p>
 * </p>
 * User: xuyuanye Date: 2016/8/18 Project: sms-send
 */
public interface MessageHandler {
     void execute(String key,MessageDTO message);
}
