package com.example.fbuapp.yelp;

public class Business {
    String name;
    String image_url;
    boolean is_closed;
    int review_count;
    float rating;
    String price;
    String display_phone;
    float distance;

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return image_url;
    }

    public boolean getIsClosed() {
        return is_closed;
    }

    public int getReviewCount() {
        return review_count;
    }

    public float getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }

    public String getDisplayPhone() {
        return display_phone;
    }

    public float getDistance() {
        return distance;
    }
}
