package com.pitapat.pitapatPoint.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationVO {
    private Integer customer;
    private Integer pat;
    private String type;
    private LocalDateTime startDttm;
    private LocalDateTime endDttm;
    private LocalDateTime regDttm;
    private String memo;

    //none db
    private String status; // 예정 R 진행중 S 끝남 E
    private String customerName;
    private String patName;
    private String tel;
    private Integer num;
}
