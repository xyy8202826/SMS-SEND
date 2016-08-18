package com.xyy.sms.task;

import com.xyy.Constant;
import com.xyy.sms.model.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 * User: xuyuanye Date: 2016/8/18 Project: sms-send
 */
@Service("messageHandler")
public class MessageHandlerImpl implements  MessageHandler {
    @Autowired
    private StringRedisTemplate template;
    @Override
    public void execute(String key,MessageDTO message) {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random random=new Random();
        int rand=random.nextInt(10);
        if(rand==5){//失败
            template.boundHashOps(Constant.KEY_RETRY_HASH).put(key,"1,失败原因");
            template.boundListOps(Constant.KEY_RETRY_LIST).leftPush(key);
        }else{//成功
            template.boundListOps(Constant.KEY_RESP_SUCC_LIST).leftPush(key);
        }
    }
}
