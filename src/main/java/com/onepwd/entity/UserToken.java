package com.onepwd.entity;

/**
 * Created by CheerS17 on 5/21/15.
 */
public class UserToken extends StringPK {

    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public UserToken(long userId) {
        this.userId = userId;
    }

    public UserToken() {
        super();
    }
}