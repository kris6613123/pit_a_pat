package com.pitapat.pitapatPoint.vo;


import lombok.*;

@Data
public class UserVO {
    private Integer user;
    private String id;
    private String pwd;
    private String name;



    //none db


    public UserVO() {}
    public UserVO( Integer id ) {
        this.user = id;
    }

}
