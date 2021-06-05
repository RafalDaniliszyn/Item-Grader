package com.example.demo.user;

import java.util.Random;

public class Confirm {
    String key;

    public Confirm() {
    }

    public Confirm(String key) {
        this.key = key;
    }

    public static String generateConfirmationKey(){
        Random random = new Random();
        String key = String.valueOf(random.nextInt(10000)+1000);
        return key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
