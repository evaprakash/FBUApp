package com.example.fbuapp.business;

import org.parceler.Parcel;

import java.util.List;
@Parcel(analyze = Coordinates.class)
public class Coordinates {
    float latitude;
    float longitude;

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }


}
