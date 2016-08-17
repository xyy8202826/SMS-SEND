package com.xyy.sms;

import com.xyy.sms.model.MessageDTO;
import com.xyy.sms.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/8/17.
 */
@Controller
@EnableAutoConfiguration
@ComponentScan("com.xyy.sms")
public class SampleApplication {
    @Autowired
    MessageService messageService;

    @RequestMapping("/home")
    @ResponseBody
    String home() {
        MessageDTO message =new MessageDTO ();
        message.setPhone("18721115160");
        message.setCode("007");
        message.setParameter("短信内容");
        messageService.sendMessage(message);
        return "Hello World!";
    }
    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
