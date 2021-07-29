package com.example.fbuapp;

import com.example.fbuapp.yelp.Business;

import java.util.ArrayList;
import java.util.List;

public class Ranking {

    public static final int DISTANCE_WEIGHT = 8;
    public static final int PRICE_WEIGHT = 6;
    public static final int OPEN_WEIGHT = 4;
    public static final int RATING_WEIGHT = 4;

    List<Business> businesses;
    String price;
    String transportation;

    public Ranking() {
        this.businesses = new ArrayList<Business>();
    }

    public Ranking(List<Business> businesses, String price, String transportation) {
        this.businesses = new ArrayList<Business>();
        this.price = price;
        this.transportation = transportation;
    }

    public static int[] mergeSort(int[] arr) {
        int N = arr.length;

        if (N <= 1) {
            return arr;
        }

        int[] split_left = new int[N/2];
        int[] split_right = new int[N - N/2];

        for (int i = 0; i < N/2; i++) {
            split_left[i] = arr[i];
        }

        for (int i = N/2; i < N; i++) {
            split_right[i - N/2] = arr[i];
        }

        int[] left = mergeSort(split_left);
        int[] right = mergeSort(split_right);
        return merge(left, right);
    }

    public static int[] merge(int[] left, int[] right) {

        int N_left = left.length;
        int N_right = right.length;
        int N = N_left + N_right;
        int[] merged = new int[N];
        int i_left = 0;
        int i_right = 0;
        int i_merged = 0;

        while (i_left < N_left && i_right < N_right) {

            if (left[i_left] < right[i_right]) {
                merged[i_merged] = left[i_left];
                i_left++;
            } else {
                merged[i_merged] = right[i_right];
                i_right++;
            }
            i_merged++;
        }

        while (i_left < N_left) {
            merged[i_merged] = left[i_left];
            i_left++;
            i_merged++;
        }

        while (i_right < N_right) {
            merged[i_merged] = right[i_right];
            i_right++;
            i_merged++;
        }

        return merged;
    }


}