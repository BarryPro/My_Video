package com.belong;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@SpringBootApplication
@EnableJms
public class MyVideoApplication {

	@Bean
	public Queue queue() {
		// 设置支付消息队列的topic
		return new ActiveMQQueue("my_play.pay_mq.queue");
	}

	public static void main(String[] args) {
		SpringApplication.run(MyVideoApplication.class, args);
	}
}
