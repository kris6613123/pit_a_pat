package com.pitapat.pitapatPoint.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerVO {
    private Integer customer;
    private String name;
    private String tel;
    private Integer point;
    private LocalDateTime regDttm;
    private String memo;


    //none db
    private String type;
    public CustomerVO() {}

    public CustomerVO( Integer customer ) {
        this.customer = customer;
    }


}
