package com.lsf.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbUserDao;
import com.lsf.pinyougou.pojo.TbUser;
import com.lsf.pinyougou.service.interfaces.user.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import vo.PageResult;

import javax.jms.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户服务实现层
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 查询全部
     */
    @Override
    public List<TbUser> findAll() {
        return tbUserDao.queryAll(null);
    }



    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findPageLimit(TbUser user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbUser> list = tbUserDao.queryAll(user);
        PageInfo<TbUser> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 用户注册
     */
    @Override
    public void add(TbUser user) {
        // 用户注册时的时间
        user.setCreated(new Date());

        // 用户信息更新时的时间
        user.setUpdated(new Date());

        // 对密码进行 MD5 加密
        // 使用 apache common 包中的 DigesUtils 加密工具类
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));

        // 往数据库中添加用户
        tbUserDao.insert(user);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbUser user) {
        tbUserDao.update(user);
    }


    /**
     * 根据 ID 获取实体
     */
    @Override
    public TbUser findOne(long id) {
        return tbUserDao.queryById(id);
    }


    /**
     * 批量删除
     */
    @Override
    public void batchDelete(Long[] ids) {
        for (Long id : ids) {
            tbUserDao.deleteById(id);
        }
    }


    /**
     * 为手机号 phone 生成短信验证码
     */
    @Override
    public void createSmsCode(final String phone) {
        // 生成六位随机数，字符串形式保存
        StringBuilder strCode = new StringBuilder((long) (Math.random() * 1000000) + "");

        // 判断长度是否满足 6 位，不满足的话，在末位补 0，直到长度为 6
        int n = 0;
        if (strCode.length() < 6)
            n = 6 - strCode.length();
        for (int i = 0; i < n; i++)
			strCode.append("0");

        final String smsCode = strCode.toString();


        // 保存到 redis 缓存中
        redisTemplate.boundHashOps("smsCode").put(phone, smsCode);


        // 通过 activeMQ 通知短信微服务工程发送短信
        jmsTemplate.send(queueSmsCodeDestination, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("mobile", phone);
                mapMessage.setString("signName", signName);
                mapMessage.setString("templateCode", templateCode);
                Map<String, String> map = new HashMap<>();
                map.put("code", smsCode);
                mapMessage.setString("param", JSON.toJSONString(map));

                return mapMessage;
            }
        });
    }


    /**
     * 判断手机号 phone 对应的验证码是否正确
     */
    @Override
    public boolean checkSmsCode(String phone, String smsCode) {
        if (redisTemplate.boundHashOps("smsCode") == null)
            return false;

        // 从缓存中获取正确的验证码
        String trueSmsCode = (String) redisTemplate.boundHashOps("smsCode").get(phone);

        // 没有找到手机号对应的验证码
        if (trueSmsCode == null)
            return false;

        // 找到手机号对应的验证码，进行比较
        return trueSmsCode.equals(smsCode);
    }


    @Autowired
    private TbUserDao tbUserDao;


    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private JmsTemplate jmsTemplate;


    /**
     * activeMQ 上保存短信消息的队列
     */
    @Autowired
    private Destination queueSmsCodeDestination;


    /**
     * 短信签名
     */
    @Value("${sms.signName}")
    private String signName;


    /**
     * 短信模板-验证码
     */
    @Value("${sms.templateCode}")
    private String templateCode;
}
