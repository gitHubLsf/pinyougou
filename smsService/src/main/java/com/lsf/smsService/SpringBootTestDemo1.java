//package com.lsf.smsService;//package com.lsf;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jms.core.JmsMessagingTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SpringBootTestDemo1 {
//
//    @Autowired
//    private JmsMessagingTemplate jmsMessagingTemplate;
//
//
//    @Test
//    public void testSendMessage() {
//        Map<String, String> map = new HashMap<>();
//        map.put("mobile", "15359453680");
//        map.put("signName", "品优购");
//        map.put("templateCode", "SMS_190282541");
//        map.put("param", "{\"code\":\"980125\"}");
//        jmsMessagingTemplate.convertAndSend("sms-queue", map);
//    }
//}
