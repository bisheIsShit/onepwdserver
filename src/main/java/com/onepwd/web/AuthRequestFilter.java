package com.onepwd.web;

import com.onepwd.dao.AccountDao;
import com.onepwd.dao.BaseDao;
import com.onepwd.dao.TokenDao;
import com.onepwd.entity.StringPK;
import com.onepwd.entity.UserToken;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.*;
import java.io.IOException;

import java.util.*;

/**
 * Created by fanngyuan on 5/23/14.
 */
public class AuthRequestFilter implements ContainerRequestFilter{

//    private static final Logger logger = Logger.getLogger(AuthRequestFilter.class);

    @Autowired
    private TokenDao tokenStorage;

    public TokenDao getTokenStorage() {
        return tokenStorage;
    }

    public void setTokenStorage(TokenDao tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    private final static String passSelectors[];

    static {
        passSelectors = new String[] {
                "register",
                "login",
                "alive"
        };
    }


    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        UriInfo uriInfo=containerRequestContext.getUriInfo();
        List<PathSegment> lists = uriInfo.getPathSegments();
        String selector = lists.get(lists.size() - 1).getPath();
        if (Arrays.asList(passSelectors).contains(selector)) {
            return;
        }

        String token =containerRequestContext.getHeaderString("access_token");
        if(token==null){
            MultivaluedMap<String,String> pathParams=containerRequestContext.getUriInfo().getPathParameters();
            token=pathParams.getFirst("access_token");
        }
        StringPK tokenPK = new StringPK();
        if (token == null) {
            token = "";
        }
        tokenPK.setKey(token);
        UserToken userToken = tokenStorage.get(tokenPK);

        if (userToken == null) {
            containerRequestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(WebUtils.createAccessDeniedJSON())
                    .build());
            return;
        }

//        logger.info("access_token is "+token+" user_id is "+userToken.getUserId());
        containerRequestContext.getHeaders().add("uid", String.valueOf(userToken.getUserId()));
    }
}
