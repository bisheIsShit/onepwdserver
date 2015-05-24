package com.onepwd.web;

import com.onepwd.entity.UserToken;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.onepwd.web.WebUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Base64;

/**
 * Created by CheerS17 on 5/19/15.
 */

@Path("v1/account")
@Component
@Service("accountWebService")
public class AccountWebService {
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
}
