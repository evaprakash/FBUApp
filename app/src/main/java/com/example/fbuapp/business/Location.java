package com.example.fbuapp.business;

import org.parceler.Parcel;

import java.util.List;

@Parcel(analyze = Location.class)
public class Location {
    List<String> display_address;

    public List<String> getDisplayAddress() {
        return display_address;
    }
}
