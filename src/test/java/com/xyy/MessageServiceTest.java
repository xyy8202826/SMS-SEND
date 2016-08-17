package com.xyy;

import com.xyy.sms.SampleApplication;
import com.xyy.sms.model.MessageDTO;
import com.xyy.sms.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2016/8/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={SampleApplication.class})
public class MessageServiceTest {
    @Autowired
    MessageService messageService;
    @Test
    public  void sendMessageTest(){
        MessageDTO message =new MessageDTO ();
        message.setPhone("18721115160");
        message.setCode("007");
        message.setParameter("短信内容");
        messageService.sendMessage(message);
    }
}
