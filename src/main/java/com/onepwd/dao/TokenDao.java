package com.onepwd.dao;

import com.onepwd.entity.StringPK;
import com.onepwd.entity.UserToken;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by closure on 5/25/15.
 */
public class TokenDao extends BaseDao<UserToken, StringPK> {

    private static final Logger logger = Logger.getLogger(AccountDao.class);
    private static final String add = "insert into token (uid, token) values(?, ?)";
    private static final String get = "select uid, token from token where token = ?";

    @Override
    public UserToken get(StringPK t) {
        try {
            return getTemplate().queryForObject(get, new TokenRowMapper(), t.getKey());
        } catch (EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public UserToken add(UserToken userToken) {
        try {
            getTemplate().update(add, userToken.getUserId(), userToken.getKey());
            return userToken;
        } catch (Exception e) {
            logger.warn("add token failed",e);
            return null;
        }
    }

    @Override
    public Map<StringPK, UserToken> multiGet(List<StringPK> ids) {
        return null;
    }

    @Override
    public void update(UserToken oldT, UserToken newT) {

    }

    @Override
    public void update(UserToken userToken) {

    }

    @Override
    public void delete(StringPK key) {

    }
}

class TokenRowMapper implements RowMapper<UserToken> {
    @Override
    public UserToken mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        UserToken ut = new UserToken();
        ut.setUserId(resultSet.getLong("uid"));
        ut.setKey(resultSet.getString("token"));
        return ut;
    }
}