package com.onepwd.web;

import com.onepwd.dao.AccountDao;
import com.onepwd.dao.InfoDao;
import com.onepwd.dao.TokenDao;
import com.onepwd.entity.IntPK;
import com.onepwd.entity.StringPK;
import com.onepwd.entity.UserToken;
import com.onepwd.entity.Account;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import java.util.Base64;
import java.util.HashMap;

/**
 * Created by CheerS17 on 5/19/15.
 */

@Path("v1/account")
@Component
@Service("accountWebService")
public class AccountWebService {
    private static final Logger logger = Logger.getLogger(AccountWebService.class);
    public static final String UserKey = "user";
    public static final String TokenKey = "token";

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TokenDao tokenStorage;

    @Autowired
    private InfoDao infoDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public TokenDao getTokenStorage() {
        return tokenStorage;
    }

    public void setTokenStorage(TokenDao tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    public InfoDao getInfoDao() {
        return infoDao;
    }

    public void setInfoDao(InfoDao infoDao) {
        this.infoDao = infoDao;
    }

    @GET
    @Path("alive")
    public String alive() {
        return WebUtils.createErrorJSON("lalalalal");
    }


    private UserToken generateToken(long userId) {
        UserToken token = new UserToken();
        token.setUserId(userId);
        token.setKey(Base64.getUrlEncoder().encodeToString(TEA.encryptByTea(String.valueOf(userId))));
        return token;
    }

    private HashMap<String, Object> getLoginToken(Account account) {
        UserToken userToken = generateToken(account.getId());
        Account user = accountDao.get(new IntPK(account.getId()));
        if (userToken != null) {
            StringPK stringPK = new StringPK();
            stringPK.setKey(userToken.getKey());
            tokenStorage.add(userToken);
        } else {
            userToken = new UserToken();
            userToken.setUserId(account.getId());
            userToken.setKey(String.valueOf(account.getId()));
            tokenStorage.add(userToken);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put(UserKey, user);
        map.put(TokenKey, userToken.getKey());
        HashMap<String, Object> result = new HashMap<>();
        result.put(UserKey, map);
        return result;
    }

    @POST
    @Path("register")
    public String registerAccount(@FormParam("id") final String name, @FormParam("password") final String password) {
        Account account = new Account(0);
        account.setUserName(name);
        account.setPassword(password);
        try {
            Account ret = accountDao.add(account);
            if (null != ret) {
                HashMap<String, Object> map = getLoginToken(ret);
                return WebUtils.createCommonResultJSON(map);
            }
        } catch (NullPointerException e) {
            return WebUtils.createErrorJSON("already registered");
        }
        return WebUtils.createErrorJSON("Unknown error");
    }

    @POST
    @Path("login")
    public String loginAccount(@FormParam("id") final String name, @FormParam("password") final String password) {
        try {
            logger.warn("name: " + name + ", password: " + password);
            Account ret = accountDao.getWithPassword(name, password);
            if (null != ret) {
                HashMap<String, Object> map = getLoginToken(ret);
                return WebUtils.createCommonResultJSON(map);
            }
            logger.warn("ret is null");
            return WebUtils.createErrorJSON("name or password error, or not register");
        } catch (NullPointerException e) {
            return WebUtils.createErrorJSON("not register");
        }
    }

    @POST
    @Path("set_cipher")
    public String setInfo(@HeaderParam("uid") long uid, @FormParam("value") String value) {

        return WebUtils.createSuccessJSON();
    }

    @GET
    @Path("get_cipher")
    public String getInfo(@HeaderParam("uid") long uid) {

        return WebUtils.createSuccessJSON();
    }

}
