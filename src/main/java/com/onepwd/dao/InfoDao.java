package com.onepwd.dao;

import com.onepwd.entity.Info;
import com.onepwd.entity.IntPK;

import java.util.List;
import java.util.Map;

/**
 * Created by closure on 5/26/15.
 */
public class InfoDao extends BaseDao<Info, IntPK> {
    @Override
    public Info add(Info info) {
        return null;
    }

    @Override
    public Info get(IntPK t) {
        return null;
    }

    @Override
    public Map<IntPK, Info> multiGet(List<IntPK> ids) {
        return null;
    }

    @Override
    public void update(Info oldT, Info newT) {

    }

    @Override
    public void update(Info info) {

    }

    @Override
    public void delete(IntPK key) {

    }
}
