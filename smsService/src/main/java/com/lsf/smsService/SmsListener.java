package com.lsf.smsService;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 消息监听器
 */
@Component
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;


    /**
     *  发送短信
     *  从 smsCode-queue 队列中取消息后，再发送短信
     */
    //@JmsListener(destination = "smsCode-queue")
    @JmsListener(destination = "sms-queue")
    public void sendMessage(Map<String, String> map) {
        try {
            smsUtil.sendSms(map.get("mobile"),
                    map.get("signName"),
                    map.get("templateCode"),
                    map.get("param")
            );
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
