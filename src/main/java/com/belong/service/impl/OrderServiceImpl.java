package com.belong.service.impl;

import com.belong.dao.OrderMapper;
import com.belong.model.Order_Video;
import com.belong.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper dao;
    @Override
    public boolean insertOrder(Map map) {
        return dao.insertOrder(map);
    }

    @Override
    public boolean updateOrderStatus(Map map) {
        return dao.updateOrderStatus(map);
    }

    @Override
    public Order_Video getOrderByOrderId(Map map) {
        return dao.getOrderByOrderId(map);
    }
}
