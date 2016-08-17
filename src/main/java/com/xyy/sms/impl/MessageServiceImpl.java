package com.xyy.sms.impl;

import com.xyy.Constant;
import com.xyy.sms.model.MessageDTO;
import com.xyy.sms.service.MessageService;
import jodd.json.JsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/8/17.
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private StringRedisTemplate template;
    /**
     * 发送短信实现
     * @param message
     * @return
     */
    @Override
    public boolean sendMessage(MessageDTO message) {
        String key = template.boundValueOps(Constant.KEY_MT_ID).increment(1)+"";
        log.info("sendMessage key{}",key);
        String mess= new JsonSerializer().serialize(message);
        template.boundValueOps(Constant.KEY_SMS_PREFIX + key).set(mess);
        template.boundListOps(Constant.KEY_PENDING_LIST).leftPush(key);
        return true;
    }
}
