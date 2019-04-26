package com.github.wanjinzhong.easycronplugincenter.service;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public interface MailService {

    JavaMailSenderImpl getInstance();

    void flush();

    boolean sendNewUserEmail(String email, String pwd, String userName);

    boolean sendChangePwdValCodeEmail(String email, String userName, String valCode);

    boolean sendRegisterValCodeEmail(String email, String userName, String valCode);

    boolean testConnection();
}
