package com.boke.index.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
@TableName("user")
public class User implements Serializable {
    private String userId;
    private String username;
    private String password;
    private int age;
    private String sex;
    private String account;

}
