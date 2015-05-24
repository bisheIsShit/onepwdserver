package com.onepwd.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

/**
 * Created by fanngyuan on 6/14/14.
 */
public class WebUtils {

//    private static final Logger logger = Logger.getLogger(WebUtils.class);
    public static final int SUCCESS_CODE = 0;
    private static int ERROR_CODE = 1;
    static boolean isDev = true;

    private static final String authorizedDeniedReason = "token is invalid";
    private static int authorizedDeniedCode = ERROR_CODE;

    private static final String resultNullReason = "not found";
    public static final String successReason = "success";

    static final Object[] emptyArray = new Object[0];


    static{
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
    }

    public static final String ReasonKey = "reason";
    public static final String CodeKey = "code";
    public static final String PayloadKey = "result";

    public static final String createResponseJSON(final String reason, final int code, final Object payload) {
        HashMap<String, Object> jsonObject = new HashMap<String, Object>() {{
            if (null != reason) {
                put(ReasonKey, reason);
            }
            put(CodeKey, code);
            if (null != payload) {
                put(PayloadKey, payload);
            }
        }};
        String resultJSON = JSON.toJSONString(jsonObject);

        //logger.info((" -> " + resultJSON));
        return resultJSON;
    }

    // fast way to build error json
    public static final String createErrorJSON(String errorMessage, int code) {
        assertTrue(code != SUCCESS_CODE, "code should be non-zero to be an error json result!");
        String result = "{\"" + ReasonKey + "\": \"" + errorMessage + "\", \"" + CodeKey + "\":" + code + "}";
//        logger.info(result);
        return result;
    }

    public static final String createErrorJSON(String errorMessage) {
        return createErrorJSON(errorMessage, ERROR_CODE);
    }

    // token invalid json response
    public static final String createAccessDeniedJSON() {
        return createErrorJSON(authorizedDeniedReason, authorizedDeniedCode);
    }

    public static final String createNullResultJSON() {
        return createErrorJSON(resultNullReason);
    }

    public static final String createCommonResultJSON(Map<String, Object> result) {
        if (null != result) {
            return createResponseJSON(null, SUCCESS_CODE, result);
        }
        return createNullResultJSON();
    }

    public static final String createCommonResultJSON(final String key, final Object result) {
        if (null != result) {
            HashMap<String, Object> map = new HashMap<String, Object>(){{put(key, result);}};
            return createResponseJSON(null, SUCCESS_CODE, map);
        }
        return  createNullResultJSON();
    }


    public static final String createSuccessJSON() {
        return createResponseJSON(successReason, SUCCESS_CODE, null);
    }

}
