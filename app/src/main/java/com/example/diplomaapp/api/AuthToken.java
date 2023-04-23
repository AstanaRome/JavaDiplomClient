package com.example.diplomaapp.api;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class AuthToken {
    public static String createAuthToken(String email, String password){
        byte[] data = new byte[0];
        try{
            data = (email + ":" + password).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "Basic " +  android.util.Base64.encodeToString(data, Base64.NO_WRAP);
    }
}
