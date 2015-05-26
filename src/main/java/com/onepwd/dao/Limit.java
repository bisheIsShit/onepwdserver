package com.onepwd.dao;

import java.util.List;

/**
 * Created by fanngyuan on 2014/11/16.
 */
class Limit {

    private String limitSQL;

    private List<Object> params;

    public String getLimitSQL() {
        return limitSQL;
    }

    public void setLimitSQL(String limitSQL) {
        this.limitSQL = limitSQL;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
}
