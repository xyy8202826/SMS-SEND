package com.xyy.sms.task.syndata;

import com.xyy.Constant;
import com.xyy.JsonUtil;
import com.xyy.sms.dao.SmsDao;
import com.xyy.sms.model.MessageDTO;
import com.xyy.sms.model.Sms;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 * User: xuyuanye Date: 2016/8/18 Project: sms-send
 */
@AllArgsConstructor
public class SynFailThread implements Runnable{
    private static  final Logger log= LoggerFactory.getLogger(SynSuccessThread.class);

    private StringRedisTemplate template;

    private SmsDao smsDao;
     /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true) {
            log.info(" befor KEY_RESP_FAIL_LIST");
            String messageKey = template.boundListOps(Constant.KEY_RESP_FAIL_LIST).rightPop(0, TimeUnit.SECONDS);
            log.info("KEY_RESP_FAIL_LIST key{}",messageKey);
            log.info("after KEY_RESP_FAIL_LIST ");
            String strMess=template.boundValueOps(Constant.KEY_SMS_PREFIX + messageKey).get();
            MessageDTO message= JsonUtil.parse(strMess, MessageDTO.class);
            String result=(String)template.boundHashOps(Constant.KEY_RETRY_HASH).get(messageKey);
            String [] errorMessage = result.split(",");
            Sms sms= smsDao.findByMessKey(messageKey);
            if(sms == null){
                sms = new Sms();
                sms.setMessKey(messageKey);
                sms.setCode(message.getCode());
                sms.setPhone(message.getPhone());
                sms.setParameter(message.getParameter());
                sms.setErrorMess(errorMessage[1]);
                smsDao.save(sms);
            }
            template.boundHashOps(Constant.KEY_RETRY_HASH).delete(messageKey);
            template.delete(Constant.KEY_SMS_PREFIX +messageKey );
        }

    }
}
