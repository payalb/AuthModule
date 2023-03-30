package com.java.constants;


public class JWTConstants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 1*24*60; //1 day
    public static final long RFERESH_TOKEN_VALIDITY_SECONDS = 7*24*60; //7 days
    public static final String SIGNING_KEY = "helloworld";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
