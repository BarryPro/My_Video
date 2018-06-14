package com.belong.mq;

import lombok.Getter;
import lombok.Setter;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author barry
 * @since 2018/6/14 13:27
 */
@Setter
@Getter
@Service
public class ActiveMq {
    private static final Logger logger = LoggerFactory.getLogger(ActiveMq.class);
    private Destination destination;
    private Connection connection = null;
    private Session session;
    private MessageProducer producer;
    private MessageConsumer consumer;

    private Session initMQ() {
        // ConnectionFactory ：连接工厂，JMS 用它创建连接
        ConnectionFactory connectionFactory; // Connection ：JMS 客户端到JMS
        // Provider 的连接
        // 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destination = session.createQueue("my_play.pay_mq.queue");
        } catch (Exception e) {
            logger.error("ActiveMq initMQ JMSException");
        }
        return session;
    }

    public void sendMessage(String text) {
        Session session = initMQ();
        TextMessage message = null;
        try {
            producer = session.createProducer(destination);
            message = session.createTextMessage(text);
            // 发送消息
            producer.send(message);
            session.commit();
        } catch (JMSException e) {
            logger.error("ActiveMq sendMessage JMSException");
        }
    }

    public String receiverMessage() {
        Session session = initMQ();
        String message = null;
        try {
            consumer = session.createConsumer(destination);
            message = ((TextMessage) consumer.receive()).getText();
        } catch (JMSException e) {
            logger.error("ActiveMq receiverMessage JMSException");
        }
        return message;
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("二维码收款：(.+)元");
        Matcher matcher = pattern.matcher("二维码收款：6.12元,备注：无");
        if(matcher.find()) {
            String mq_pay_total = matcher.group(1);
            System.out.println(mq_pay_total);
        }
    }
}
