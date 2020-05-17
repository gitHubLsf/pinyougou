package com.lsf.smsService;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * 短信服务工具类
 */
@Component
public class SmsUtil {

    @Autowired
    private Environment env;

    // 产品名称:云通信短信API产品,开发者无需替换
    private final String product = "Dysmsapi";

    // 产品域名,开发者无需替换
    private final String domain = "dysmsapi.aliyuncs.com";

    // 开发者自己的AK(在阿里云访问控制台寻找)
    private String accessKeyId;

    private String accessKeySecret;


    @PostConstruct
    private void init() {
        accessKeyId = env.getProperty("sms.accessKeyId");
        accessKeySecret = env.getProperty("sms.accessKeySecret");
    }


    /**
     * 发送短信，最常使用
     *
     * @param mobile        手机号
     * @param signName      短信签名
     * @param templateCode  短信模板
     * @param param         模板参数 json 字符串
     */
    public SendSmsResponse sendSms(String mobile,
                                   String signName,
                                   String templateCode,
                                   String param) throws ClientException {
        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();

        // 必填:待发送手机号
        request.setPhoneNumbers(mobile);

        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);

        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);

        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(param);

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }
}
