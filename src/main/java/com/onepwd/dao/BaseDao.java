package com.onepwd.dao;

import com.onepwd.entity.Key;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import com.onepwd.entity.Key;

/**
 * Created by fanngyuan on 4/15/14.
 */
public abstract class BaseDao<T extends Key,PK extends Key> {
    JdbcTemplate template;

    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public abstract T add(T t);

    public abstract T get(PK t);

    public abstract Map<PK,T> multiGet(List<PK> ids) ;

    public abstract void update(T t);

    public abstract void update(T oldT, T newT) ;

    public abstract void delete(PK key) ;

}
