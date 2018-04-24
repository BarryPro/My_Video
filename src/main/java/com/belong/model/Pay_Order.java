package com.belong.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Pay_Order implements Serializable {
    private Long pay_id;
    private Long order_id;
    private String pay_time;
    private String update_time;
    private Integer user_id;
    private Integer pay_type;
    private Integer pay_status;
    private Double pay_total;
    private String extra;
}
