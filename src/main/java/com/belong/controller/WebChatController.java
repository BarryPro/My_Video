package com.belong.controller;

import com.belong.config.ConstantConfig;
import com.belong.service.impl.WeChatListenerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Controller
@RequestMapping(value = "/webChat")
public class WebChatController {
    private static final Logger logger = LoggerFactory.getLogger(WebChatController.class);
    @Autowired
    private WeChatListenerServiceImpl weChatListenerService;

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
}