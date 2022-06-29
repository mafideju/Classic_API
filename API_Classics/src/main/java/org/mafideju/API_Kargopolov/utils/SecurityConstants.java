package org.mafideju.API_Kargopolov.utils;

import org.mafideju.API_Kargopolov.config.AppProperties;
import org.mafideju.API_Kargopolov.config.SpringApplicationContext;

import java.security.SecureRandom;
import java.util.Base64;

public final class SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000;
    public static final String SIGN_UP_URL = "/users";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static String getTokenSecret() {
        AppProperties properties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return properties.getTokenSecret();
    }
}
//    public static String generateSafeToken() {
//        SecureRandom random = new SecureRandom();
//        byte[] bytes = new byte[36]; // 36 bytes * 8 = 288 bits, a little bit more than
//        // the 256 required bits
//        random.nextBytes(bytes);
//        var encoder = Base64.getUrlEncoder().withoutPadding();
//        return encoder.encodeToString(bytes);
//    }