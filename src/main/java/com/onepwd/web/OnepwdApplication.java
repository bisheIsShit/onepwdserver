package com.onepwd.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 * Created by fanngyuan on 5/23/14.
 */
//@ApplicationPath("/")
public class OnepwdApplication extends ResourceConfig {

    public OnepwdApplication() {
        super(AccountWebService.class, AuthRequestFilter.class);
//        WebUtils.isDev = false;
        //Map<String, Object> properties = new HashMap<String, Object>();
        //properties.put(ServerProperties.MONITORING_STATISTICS_MBEANS_ENABLED, true);
        //setProperties(properties);
    }

}
