package com.belong.plugins.webchat;

import com.belong.plugins.util.MtUtil;

import javax.mail.Address;
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

        Session session = Session.getInstance(props);
        Message msg = new MimeMessage(session);
        msg.setSubject(title);
        msg.setText(content);
        msg.setFrom(new InternetAddress("binmtplus@163.com"));
        Transport transport = session.getTransport();
        transport.connect("binmtplus", MtUtil.EMAIL_PASSWORD);
        transport.sendMessage(msg, new Address[]{new InternetAddress(recever)});
        transport.close();
        return true;
    }
}
