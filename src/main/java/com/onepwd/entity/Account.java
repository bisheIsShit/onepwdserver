package com.onepwd.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by closure on 5/25/15.
 */
public class Account extends IntPK {
    private String userName;

    private String password;

    public Account(long key) {
        super(key);
    }

    @JSONField(name = "user_name")
    public String getUserName() {
        return userName;
    }

    @JSONField(name = "user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JSONField(name="password")
    public String getPassword() { return password; }

    @JSONField(name="password")
    public void setPassword(String password) {
        this.password = password;
    }
}

