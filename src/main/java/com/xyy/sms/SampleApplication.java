package com.xyy.sms;

import com.xyy.sms.model.MessageDTO;
import com.xyy.sms.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/8/17.
 */
@Controller
@EnableAutoConfiguration
@ComponentScan("com.xyy.sms")
@EnableJpaRepositories(basePackages={"com.xyy.sms.dao"})
@EntityScan(basePackages="com.xyy.sms.model")
@Slf4j
public class SampleApplication {
    @Autowired
    MessageService messageService;

    @RequestMapping("/home")
    @ResponseBody
    String home() {
        MessageDTO message =new MessageDTO ();
        message.setPhone("123456787978");
        message.setCode("007");
        message.setParameter("短信内容");
        messageService.sendMessage(message);
        return "Hello World!";
    }
    public static void main(String[] args) {
        MDC.put("TRACE_LOG_ID","lakksjfkahsjfajsfgj");
        log.info("SampleApplication start");
        SpringApplication.run(SampleApplication.class, args);
    }
}
