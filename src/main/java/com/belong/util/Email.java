package com.belong.util;

import com.belong.config.HiddenConfig;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    // 发邮件的异常上抛，让上层感知进行处理，底层不做处理
    public static synchronized boolean sendEmail(String recever, String title, String content) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.host", "smtp.163.com");
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("username",HiddenConfig.EMAIL_163);
        props.setProperty("password",HiddenConfig.EMAIL_PASSWORD);
        props.setProperty("mail_to",HiddenConfig.EMAIL_QQ);

        Session session = Session.getInstance(props);
        Message msg = new MimeMessage(session);
        // 设置发件人
        msg.setFrom(new InternetAddress(props.getProperty("username")));
        // 添加抄送人 (抄送给自己)
        msg.addRecipients(Message.RecipientType.CC,InternetAddress.parse(props.getProperty("username")));
        // 添加收件人
        msg.addRecipients(Message.RecipientType.TO,InternetAddress.parse(props.getProperty("mail_to")));
        // 设置邮件主题
        msg.setSubject(title);
        // 设置邮件内容
        msg.setText(content);
        msg.saveChanges();
        Transport transport = session.getTransport();
        transport.connect(props.getProperty("username"),
                props.getProperty("password"));
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
        return true;
    }
}
