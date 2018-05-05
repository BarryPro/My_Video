package com.belong.controller;

import com.alibaba.fastjson.JSONObject;
import com.belong.config.ConstantConfig;
import com.belong.model.Order_Video;
import com.belong.model.Pay_Order;
import com.belong.model.User;
import com.belong.service.IOrderService;
import com.belong.service.IPayOrderService;
import com.belong.service.IUserService;
import com.belong.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private IUserService userService;
    @Autowired
    private VideoController videoController;

    @RequestMapping(value="/order_query")
    public String orderQuery(@RequestParam("order_id") String order_id,
                             Map map,
                             HttpServletResponse response){
        logger.info("OrderController orderQuery [order_id:{}]",order_id);
        try{
            map.put("order_id",Long.parseLong(order_id));
            Order_Video orderVideo = service.getOrderByOrderId(map);
            if (orderVideo != null) {
                orderVideo.setExtra(orderVideo.getOrder_id().toString());
                map.put("user_id",orderVideo.getUser_id());
                User user = userService.getUserByUserId(map);
                Pay_Order payOrder = payOrderService.getPayOrderByOrderId(map);
                payOrder.setExtra(payOrder.getPay_id().toString());
                map.put("orderVideo",orderVideo);
                map.put("user",user);
                map.put("payOrder",payOrder);
                map.put(ConstantConfig.MSG,"订单查询成功！");
            } else {
                map.put(ConstantConfig.MSG,"订单查询失败！");
            }

        } catch (NumberFormatException e) {
            map.put(ConstantConfig.MSG,"订单查询失败，请确认订单格式！");
            logger.error("OrderController orderQuery NumberFormatException order_id {}",order_id,e);
        }

        videoController.json(map,response);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/preview")
    public String insertOrder(@RequestParam("vip_type") String vip_type,
                              @RequestParam("vip_time") String vip_time,
                              @RequestParam("user_id") String user_id,
                              Map map,
                              HttpServletResponse response){
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
            order.setExtra(order.getOrder_id()+"");
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
        videoController.json(map,response);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/paySubmit")
    public String insertPayOrder(@RequestParam("order_id") String order_id,
                        @RequestParam("pay_total") String pay_total,
                        @RequestParam("user_id") String user_id,
                        @RequestParam("pay_type") String pay_type,
                        Map map,HttpServletResponse response) {
        logger.info("OrderController insertPayOrder [order_id:{} ,user_id:{} ,pay_total:{} ,pay_type:{}]", order_id, user_id,pay_total,pay_type);
        Pay_Order payOrder = new Pay_Order();
        payOrder.setPay_id(genPayId(user_id,pay_type,pay_total));
        payOrder.setOrder_id(Long.parseLong(order_id));
        payOrder.setPay_status(1);
        payOrder.setPay_total(Double.parseDouble(pay_total));
        payOrder.setPay_type(Integer.parseInt(pay_type));
        payOrder.setUser_id(Integer.parseInt(user_id));
        map.put("payOrder",payOrder);
        boolean payStatus = payOrderService.insertPayOrder(map);
        // 更新订单状态
        if (payStatus) {
            map.put("order_id",order_id);
            // 支付成功
            map.put("order_status",1);
            if(service.updateOrderStatus(map)){
                // 更新用户身份
                map.put("user_id",user_id);
                map.put("vip",1);
                if(userService.updateUserVip(map)){
                    // 计算vip有效时间
                    String period = "";
                    int month = payOrder.getPay_total().intValue() / ConstantConfig.BASE_PRICE;
                    period = TimeUtils.getCurTimeMonthNumAfterTimestamp("yyyy-MM-dd HH:mm:ss",month);
                    map.put("period",period);
                    map.put("vipGrade",month);
                    userService.updatePeriodByUserid(map);
                    map.put("user_status","1");
                } else {
                    map.put("user_status","0");
                }
                map.put("pay_status","1");
            } else {
                map.put("pay_status","0");
            }
        }
         videoController.json(map,response);
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

    private Long genPayId (String user_id,String pay_type, String pay_total){
        return Long.parseLong(TimeUtils.getFormatTime("yyyyMMddHHmmss", new Date()) + user_id + pay_type + pay_total);
    }
}
