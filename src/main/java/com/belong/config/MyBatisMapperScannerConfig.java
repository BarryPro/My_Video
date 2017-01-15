package com.belong.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * Created by Administrator on 2016/11/4.
 */

@Configuration
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureAfter(MyBatisConfig.class)//在MyBatisConfig类后面执行该类
public class MyBatisMapperScannerConfig {
    /*

     <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.weikun.dao" />
		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
	</bean>

     */
    //做mybait的通用mapper
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //设置mapper包名
        mapperScannerConfigurer.setBasePackage("com.belong.dao");
        Properties properties = new Properties();
        //设置基类mapper包名 设置通用map插件
//        properties.setProperty("mappers", "com.weikun.util.MyMapper");
//        properties.setProperty("notEmpty", "false");
//        properties.setProperty("IDENTITY", "MYSQL");
//        //这里使用的通用Mapper的MapperScannerConfigurer，所有有下面这个方法
//        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;

    }
}
