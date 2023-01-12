package com.schedule.util;

import java.util.Base64;

public class CodeUitl {
    public static String encodeToString(String password){
        return Base64.getUrlEncoder().encodeToString(password.getBytes());
    }

    public static String decode(String password){
        byte[] decodedBytes = Base64.getUrlDecoder().decode(password);
        return new String(decodedBytes);
    }
}
