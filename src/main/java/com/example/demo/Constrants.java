package com.example.demo;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

public class Constrants {

    public static final String API_SECRET_KEY = "expenserackerapikeyhytdshjgfxrejku";
    public static final long TOKEN_VALIDITY = 2 * 68*68*1000;

    public static SecretKey generateSecreteKey(){
        try {
            return new SecretKeySpec(API_SECRET_KEY.getBytes("UTF-8"), "HmacSHA256");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


    }

}