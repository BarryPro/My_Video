package com.belong.service.impl;

import com.belong.dao.PayOrderMapper;
import com.belong.service.IPayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class PayOrderServiceImpl implements IPayOrderService {
    @Autowired
    private PayOrderMapper dao;
    @Override
    public boolean insertPayOrder(Map map) {
        return dao.insertPayOrder(map);
    }
}
