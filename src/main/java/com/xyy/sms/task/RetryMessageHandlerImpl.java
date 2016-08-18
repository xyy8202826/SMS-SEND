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
@Service("retryMessageHandler")
public class RetryMessageHandlerImpl implements  MessageHandler {
    @Autowired
    private StringRedisTemplate template;
    @Override
    public void execute(String key,MessageDTO message) {
        Random random=new Random();
        int rand=random.nextInt(10);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(rand!=5){//失败
            String result=(String)template.boundHashOps(Constant.KEY_RETRY_HASH).get(key);
            String [] str = result.split(",");
            int count=Integer.valueOf(str[0]);
                count++;
            template.boundHashOps(Constant.KEY_RETRY_HASH).put(key,count+","+str[1]);
            if(count==6){
                template.boundListOps(Constant.KEY_RESP_FAIL_LIST).leftPush(key);
            }else{
                template.boundListOps(Constant.KEY_RETRY_LIST).leftPush(key);
            }
        }else{//成功
            template.boundHashOps(Constant.KEY_RETRY_HASH).delete(key);
            template.boundListOps(Constant.KEY_RESP_SUCC_LIST).leftPush(key);
        }
    }
}
