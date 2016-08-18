package com.xyy.sms.task.syndata;

import com.xyy.sms.dao.SmsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>
 * </p>
 * User: xuyuanye Date: 2016/8/18 Project: sms-send
 */
@Component
public class SynDataTask {
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private SmsDao smsDao;

    @PostConstruct
    public void init(){
        new Thread( new SynSuccessThread(template,smsDao)).start();
        new Thread( new SynFailThread(template,smsDao)).start();
    }
}
