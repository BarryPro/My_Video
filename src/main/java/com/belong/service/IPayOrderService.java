package com.belong.service;

import com.belong.model.Pay_Order;

import java.util.Map;

public interface IPayOrderService {
    boolean insertPayOrder(Map map);
    Pay_Order getPayOrderByOrderId(Map map);
}
