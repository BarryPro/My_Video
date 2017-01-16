package com.belong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by belong on 2017/1/16.
 */
@Configuration
@EnableRedisHttpSession
public class RedisConfig {
    /**
     * EnableRedisHttpSession主解是允许redis来存储session的值一边全局共享数据
     * @return
     */
    @Bean
    public JedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory();
    }
}
