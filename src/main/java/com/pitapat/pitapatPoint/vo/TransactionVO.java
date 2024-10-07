package com.pitapat.pitapatPoint.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionVO {

    private String type;
    private char action;
    private Integer price;
    private Integer point;
    private Integer customer;
    private LocalDateTime regDttm;
    private String memo;


    //none db
    private String name;
    private String tel;

}
