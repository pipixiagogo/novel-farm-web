package com.bs.farm.novelfarmweb.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User  {
    private String name;
    private String password;
    private Date createTime;
    private String phoneNum;
    private String token;
    //报废时间
    private Date expireTime;
    private Date loginTime;
    private int loginNum = 0;
    private boolean isBoss = false;
}
