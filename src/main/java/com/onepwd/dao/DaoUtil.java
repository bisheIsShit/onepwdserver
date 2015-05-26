package com.onepwd.dao;

import com.onepwd.entity.IntPK;
import com.onepwd.entity.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fanngyuan on 2014/11/16.
 */
public class DaoUtil {

    public static String genUpdateStmt(String tableName, Map<String, String> params, List<String> paramsList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("update ");
        stringBuilder.append(tableName);
        stringBuilder.append(" set ");
        int size = params.size();
        Set<Map.Entry<String, String>> paramEntrySet = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : paramEntrySet) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=?");
            if (i < size - 1) {
                stringBuilder.append(",");
            }
            i++;
            paramsList.add(entry.getValue());
        }
        return stringBuilder.toString();
    }

    public static String getInStmt(List<? extends Key> pkList) {
        if (pkList.size() == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < pkList.size(); i++) {
            if(pkList.get(i)==null)
                continue;
            stringBuilder.append(pkList.get(i).getInKey());
            if (i < pkList.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(")");
        if(stringBuilder.toString().length()==2){
            return null;
        }
        return stringBuilder.toString();
    }

    public static Limit getLimitStatement(IntPK since, IntPK max, int count, String keyword, Object... keys) {
        Limit limit = new Limit();
        List<Object> objects = new ArrayList<>();
        for (Object object : keys)
            objects.add(object);
        StringBuilder sb = new StringBuilder();
        if (since != null && since.getId() != 0) {
            sb.append(" and ");
            sb.append(" ");
            sb.append(keyword);
            sb.append(">?");
            objects.add(since.getId());
        }
        if (max != null && max.getId() != 0) {
            sb.append(" and ");
            sb.append(" ");
            sb.append(keyword);
            sb.append("<?");
            objects.add(max.getId());
        }
        sb.append(" order by ");
        sb.append(keyword);
        sb.append(" desc limit ?");
        objects.add(count);
        limit.setLimitSQL(sb.toString());
        limit.setParams(objects);
        return limit;
    }

    public static Limit getLimitWithNoAnd(IntPK since, IntPK max, int count, String keyword, Object... keys) {
        Limit limit = new Limit();
        List<Object> objects = new ArrayList<>();
        for (Object object : keys)
            objects.add(object);
        StringBuilder sb = new StringBuilder();
        if (since != null && since.getId() != 0) {
            sb.append(" ");
            sb.append(keyword);
            sb.append(">?");
            objects.add(since.getId());
        }
        if (since != null && since.getId() != 0 && max != null && max.getId() != 0) {
            sb.append(" and ");
        }
        if (max != null && max.getId() != 0) {
            sb.append(" ");
            sb.append(keyword);
            sb.append("<?");
            objects.add(max.getId());
        }
        sb.append(" order by ");
        sb.append(keyword);
        sb.append(" desc limit ?");
        objects.add(count);
        limit.setLimitSQL(sb.toString());
        limit.setParams(objects);
        return limit;
    }
}
