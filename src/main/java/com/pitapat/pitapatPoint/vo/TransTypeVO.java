package com.pitapat.pitapatPoint.vo;

import lombok.Data;

@Data
public class TransTypeVO {
    private String type;
    private Double rate;
    private String reserved;

    //none db
    private Integer num = 0; //이용자 수
}
