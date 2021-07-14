package com.example.fbuapp;

import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("YN2hYQ86lkNzzMBKblzbrtIymW1XF2U53qgbnu9E")
                .clientKey("WJ7uWUbVc1gIO6MPAlRAnTDFNz9jRdjckuvZLOLf")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}