package com.belong.dao;

import com.belong.model.Order_Video;
import com.belong.model.Pay_Order;

import java.util.Map;

public interface OrderMapper {
    boolean insertOrder(Map map);
    boolean updateOrderStatus(Map map);
    Order_Video getOrderByOrderId(Map map);
    Pay_Order getPayOrderByOrderId(Map map);
}
