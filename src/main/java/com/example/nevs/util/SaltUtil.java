package com.example.nevs.util;

import java.util.Random;

public class SaltUtil {
    public static String getSalt(int n){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz12345667890!@#$%^&*()_+-~.,/|".toCharArray();
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            stringBuilder.append(aChar);
        }
        return stringBuilder.toString();

    }
}
