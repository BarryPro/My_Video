package com.belong.controller;

import com.belong.config.ConstantConfig;
import com.belong.service.IOrderService;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.jms.ConnectionFactory;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/weChat")
public class WebChatController {
    private static final Logger logger = LoggerFactory.getLogger(WebChatController.class);
    @Autowired
    private WeChatListenerServiceImpl weChatListenerService;
    @Autowired
    private IOrderService orderService;


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
    public String getPayMessage(@RequestParam("pay_total") String pay_total,
                                @RequestParam("user_id") String user_id,
                                @RequestParam("order_id") String order_id,
            Map map,HttpServletResponse response){
        if (textMessage != null) {
            Pattern pattern = Pattern.compile("二维码收款：(.+)元");
            Matcher matcher = pattern.matcher(textMessage);
            map.put("user_id",Long.parseLong(user_id));
            map.put("order_id",Long.parseLong(order_id));
            if(matcher.find()) {
                // 判断是否是当前用户支付的当前的订单
                if (orderService.getOrderByOrderIdAndUserId(map).getOrder_id()!=null) {
                   String mq_pay_total = matcher.group(1);
                   // 检测用户支付的金额与订单的金额是否一致
                   if (Double.parseDouble(mq_pay_total) == Double.parseDouble(pay_total)){
                       map.put("payMsg",mq_pay_total);
                       textMessage = null;
                   } else {
                       map.put("payMsg",null);
                   }
                } else {
                    map.put("payMsg",null);
                }
            } else {
                map.put("payMsg",null);
            }
        } else {
            map.put("payMsg",null);
        }
        videoController.json(map,response);
        return ConstantConfig.HOME;
    }
    @JmsListener(destination = "my_play.pay_mq.queue")
    private void setTextMessage(String textMessage){
        this.textMessage = textMessage;
        logger.info("my_play.pay_mq.queue consumer:{}",textMessage);
    }
}
