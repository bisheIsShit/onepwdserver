package com.onepwd.dao;

import com.onepwd.entity.Account;
import com.onepwd.entity.IntPK;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import org.apache.log4j.Logger;

import java.security.PublicKey;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by closure on 5/25/15.
 */
public class AccountDao extends BaseDao<Account, IntPK> {
    private static final Logger logger = Logger.getLogger(AccountDao.class);
    private static final String add = "insert into account (name, password) values(?, ?)";
    private static final String get = "select uid, name from account where uid = ?";
    private static final String multiGet = "select uid, name from account where uid in ";
    private static final String delete = "delete from account where uid = ?";
    private static final String getWithMail = "select uid, name from account where name = ?";
    private static final String update = "update account set name=?, password=? where uid=?";
    private static final String getWithPassword = "select uid, name, password from account where uid = ?, password = ?";

    @Override
    public Account add(final Account account) {
        try {
            this.getTemplate().execute("SET NAMES utf8mb4");
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getTemplate().update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement ps = con.prepareStatement(add, Statement.RETURN_GENERATED_KEYS);
                            ps.setString(1, account.getUserName());
                            ps.setString(2, account.getPassword());
                            return ps;
                        }
                    }, keyHolder
            );
            account.setId(keyHolder.getKey().intValue());
            return account;
        } catch (Exception e) {
            logger.warn("add account failed",e);
            return null;
        }
    }

    @Override
    public void delete(IntPK key) {

    }

    @Override
    public void update(Account oldT, Account newT) {

    }

    @Override
    public void update(Account account) {

    }

    @Override
    public Map<IntPK, Account> multiGet(List<IntPK> ids) {
        String inStmt = DaoUtil.getInStmt(ids);
        if(inStmt == null) return null;

        Map<IntPK, Account> result = new HashMap<>();
        List<Account> accountList = this.getTemplate().query(multiGet + inStmt, new AccountInfoRowMapper());
        for(Account account:accountList){
            result.put(new IntPK(account.getId()),account);
        }
        return result;
    }

    @Override
    public Account get(IntPK t) {
        try {
            return getTemplate().queryForObject(get, new AccountInfoRowMapper(), t.getId());
        } catch (EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    public Account getWithPassword(Account account) {
        try {
            return getTemplate().queryForObject(getWithPassword, new AccountInfoWithPasswordRowMapper(), account.getId(), account.getPassword());
        } catch (EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }
}


class AccountInfoRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Account account = new Account(0);
        account.setId(resultSet.getInt("uid"));
        account.setUserName(resultSet.getString("name"));
        return account;
    }
}

class AccountInfoWithPasswordRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Account account = new Account(0);
        account.setId(resultSet.getInt("uid"));
        account.setUserName(resultSet.getString("name"));
        account.setPassword(resultSet.getString("password"));
        return null;
    }
}
