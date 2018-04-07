package com.belong.controller;

import com.alibaba.fastjson.JSONObject;
import com.belong.config.ConstantConfig;
import com.belong.model.Order_Video;
import com.belong.service.IOrderService;
import com.belong.service.IPayOrderService;
import com.belong.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping(value = "/my_order")
@SessionAttributes(value = {"order","orderId"})
public class OrderController {
    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService service;
    @Autowired
    private IPayOrderService payOrderService;

    @RequestMapping(value = "/preview")
    public String insertOrder(@RequestParam("vip_type") String vip_type,
                        @RequestParam("vip_time") String vip_time,
                        @RequestParam("user_id") String user_id,
                        Map map){
        logger.info("OrderController insertOrder [vip_type:{} vip_time:{} user_id:{}]",vip_type,vip_time,user_id);
        if (!StringUtils.isEmpty(vip_time) && !StringUtils.isEmpty(vip_type)) {
            Order_Video order = new Order_Video();
            order.setOrder_id(genOrderId(user_id,vip_type,vip_time));
            order.setOrder_name(genOrderName(vip_type,vip_time));
            order.setOrder_type(vip_type);
            order.setUser_id(Integer.parseInt(user_id));
            order.setOrder_status(0);
            String timeStamp = TimeUtils.getFormatTime("yyyy-MM-dd HH:mm:ss",new Date());
            order.setTrade_time(timeStamp);
            double payTotal = 0.0;
            payTotal = Integer.parseInt(vip_time) * ConstantConfig.VIP_CASH.get(vip_type);
            order.setPay_total(payTotal);
            map.put("order",order);
            if (service.insertOrder(map)){
                map.put(ConstantConfig.MSG,"生成订单成功！");
                map.put(ConstantConfig.ORDER_SWITCH,"1");
                logger.info("OrderController order {}", JSONObject.toJSONString(order));
                // 把订单类型转换成文件
                order.setOrder_type(ConstantConfig.VIP_TYPE.get(order.getOrder_type()));
            } else {
                map.put(ConstantConfig.MSG,"生成订单失败！");
                map.put(ConstantConfig.ORDER_SWITCH,"0");
            }
        } else {
            map.put(ConstantConfig.MSG,"生成订单失败！");
            map.put(ConstantConfig.ORDER_SWITCH,"0");
        }
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/paySubmit")
    public String insertPayOrder(@RequestParam("order_id") String order_id,
                        @RequestParam("pay_total") String pay_total,
                        Map map) {
        logger.info("OrderController insertPayOrder [vip_type:{} vip_time:{} user_id:{}]", order_id, pay_total);

        return ConstantConfig.HOME;
    }

    private String genOrderName (String vip_type, String vip_time){
        return "MyPlay" + vip_time + "个月的" + ConstantConfig.VIP_TYPE.get(vip_type) + "会员";
    }

    public static void main (String[]args){
        System.out.println(new OrderController().genOrderId("1", "1", "3"));
    }
    private Long genOrderId (String user_id, String vip_type, String vip_time){
        return Long.parseLong(TimeUtils.getFormatTime("yyyyMMddHHmmss", new Date()) + user_id + vip_type + vip_time);
    }
}
