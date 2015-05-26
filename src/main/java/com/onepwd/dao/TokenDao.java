package com.onepwd.dao;

import com.onepwd.entity.StringPK;
import com.onepwd.entity.UserToken;

import java.util.List;
import java.util.Map;

/**
 * Created by closure on 5/25/15.
 */
public class TokenDao extends BaseDao<UserToken, StringPK> {
    @Override
    public UserToken get(StringPK t) {
        return null;
    }

    @Override
    public UserToken add(UserToken userToken) {
        return null;
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
