package com.belong.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Order_Video implements Serializable {
    private Long order_id;
    private String order_name;
    private String order_type;
    private String trade_time;
    private String update_time;
    private Integer user_id;
    private Integer order_status;
    private Double pay_total;
    private String extra;
}
