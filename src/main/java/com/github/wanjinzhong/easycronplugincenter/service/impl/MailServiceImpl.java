package com.github.wanjinzhong.easycronplugincenter.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.github.wanjinzhong.easycronplugincenter.bo.SettingBo;
import com.github.wanjinzhong.easycronplugincenter.service.MailService;
import com.github.wanjinzhong.easycronplugincenter.service.SettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Transactional
public class MailServiceImpl implements MailService {


    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private SettingService settingService;

    private Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    private JavaMailSenderImpl javaMailSender;

    private MailServiceImpl() {

    }

    @Override
    public JavaMailSenderImpl getInstance() {
        if (javaMailSender != null) {
            return javaMailSender;
        }
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        SettingBo settingBo = settingService.getSettings();
        javaMailSender.setHost(settingBo.getEmailHost());
        int port;
        try {
            port = Integer.valueOf(settingBo.getEmailPort());
        } catch (NumberFormatException e) {
            port = 0;
        }
        javaMailSender.setPort(port);

        javaMailSender.setUsername(settingBo.getEmail());
        javaMailSender.setPassword(settingBo.getEmailPwd());
        javaMailSender.setDefaultEncoding("UTF-8");
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.starttls.required", "true");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.connectiontimeout", "5000");
        properties.setProperty("mail.smtp.timeout", "5000");
        javaMailSender.setJavaMailProperties(properties);
        this.javaMailSender = javaMailSender;
        return javaMailSender;
    }

    @Override
    public void flush() {
        javaMailSender = null;
    }

    private boolean sendEmail(String from, String to, String subject, String template, Map variables) {
        try {
            MimeMessage mimeMessage;
            try {
                mimeMessage = getInstance().createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                // 设置发件人邮箱
                helper.setFrom(from);
                // 设置收件人邮箱
                helper.setTo(to);
                // 设置邮件标题
                helper.setSubject(subject);

                // 添加正文（使用thymeleaf模板）
                Context context = new Context();
                context.setVariables(variables);
                String content = this.templateEngine.process(template, context);
                helper.setText(content, true);

                // 发送邮件
                javaMailSender.send(mimeMessage);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } catch (MailSendException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }


    @Override
    public boolean sendNewUserEmail(String email, String pwd, String userName) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("TITLE", "用户注册成功");
        dataMap.put("USERNAME", userName);
        dataMap.put("PASSWORD", pwd);
        dataMap.put("EMAIL", email);
        String from = settingService.getSettings().getEmail();
        return sendEmail(from, email, "用户注册成功", "email_register", dataMap);
    }

    @Override
    public boolean sendChangePwdValCodeEmail(String email, String userName, String valCode) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("TITLE", "修改密码");
        dataMap.put("ACTION", "您正在请求修改密码");
        dataMap.put("USERNAME", userName);
        dataMap.put("VAL_CODE", valCode);
        String from = settingService.getSettings().getEmail();
        return sendEmail(from, email, "修改密码", "email_valcode", dataMap);
    }

    @Override
    public boolean sendRegisterValCodeEmail(String email, String userName, String valCode) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("TITLE", "新用户注册");
        dataMap.put("USERNAME", userName);
        dataMap.put("ACTION", "您正在注册账号");
        dataMap.put("VAL_CODE", valCode);
        String from = settingService.getSettings().getEmail();
        return sendEmail(from, email, "新用户注册", "email_valcode", dataMap);
    }

    @Override
    public boolean testConnection() {
        try {
            getInstance().testConnection();
        } catch (MessagingException e) {
            logger.error("邮件配置错误", e);
            return false;
        }
        return true;
    }
}
