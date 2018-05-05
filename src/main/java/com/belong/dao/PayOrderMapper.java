package com.belong.dao;

import com.belong.model.Pay_Order;

import java.util.Map;

public interface PayOrderMapper {
    boolean insertPayOrder(Map map);
    Pay_Order getPayOrderByOrderId(Map map);
}
