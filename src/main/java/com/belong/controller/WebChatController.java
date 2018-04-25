package com.belong.controller;

import com.belong.config.ConstantConfig;
import com.belong.service.impl.WeChatListenerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jms.ConnectionFactory;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

@Controller
@RequestMapping(value = "/weChat")
public class WebChatController {
    private static final Logger logger = LoggerFactory.getLogger(WebChatController.class);
    @Autowired
    private WeChatListenerServiceImpl weChatListenerService;

    private String textMessage;

    @Autowired
    private VideoController videoController;

    @RequestMapping(value = "/loginEntry")
    public String loginEntry( HttpServletResponse response){
        // 获得登录二维码
        response.setContentType(ConstantConfig.IMAGE);
        try {
            byte[] buffer = weChatListenerService.loginEntry();
            OutputStream os = response.getOutputStream();
            os.write(buffer);
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.error("IOException UserController getPic",e);
        }
        return null;
    }

    @RequestMapping(value = "/loginCode")
    public String getLoginCode(Map map,HttpServletResponse response){
        int loginCode = weChatListenerService.getLoginCode();
        map.put("loginCode",loginCode);
        videoController.json(map,response);
        return ConstantConfig.HOME;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(activeMQConnectionFactory);
        return bean;
    }

    @RequestMapping(value = "/payMQ")
    public String getPayMessage(Map map,HttpServletResponse response){
        if (textMessage != null && textMessage.startsWith("二维码收款")) {
            map.put("payMsg",textMessage);
            textMessage = null;
        } else {
            map.put("payMsg","");
        }
        videoController.json(map,response);
        return ConstantConfig.HOME;
    }
    @JmsListener(destination = "my_play.pay_mq.topic", containerFactory="jmsListenerContainerTopic")
    private void setTextMessage(String textMessage){
        this.textMessage = textMessage;
        logger.info("my_play.pay_mq.topic consumer:{}",textMessage);
    }
}
