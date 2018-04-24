package com.belong;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Topic;

@SpringBootApplication
@EnableJms
public class MyVideoApplication {

	@Bean
	public Topic topic() {
		// 设置支付消息队列的topic
		return new ActiveMQTopic("my_play.pay_mq.topic");
	}

	public static void main(String[] args) {
		SpringApplication.run(MyVideoApplication.class, args);
	}
}
