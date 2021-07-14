package com.example.fbuapp;

import com.parse.Parse;
import android.app.Application;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("YN2hYQ86lkNzzMBKblzbrtIymW1XF2U53qgbnu9E")
                .clientKey("WJ7uWUbVc1gIO6MPAlRAnTDFNz9jRdjckuvZLOLf")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}