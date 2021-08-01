package com.example.fbuapp;

import com.example.fbuapp.business.Business;

import java.util.ArrayList;
import java.util.List;

public class Ranking {

    public static final int DISTANCE_WEIGHT = 8;
    public static final int PRICE_WEIGHT = 6;
    public static final int OPEN_WEIGHT = 4;
    public static final int RATING_WEIGHT = 4;
    public static final int MAX_WALKING_DISTANCE = 2000;
    public static final int MAX_BICYCLE_DISTANCE = 9000;
    public static final int MAX_TRANSIT_DISTANCE = 25000;
    public static final int MAX_AUTOMOBILE_DISTANCE = 35000;
    public static final int MAX_DISTANCE_PADDING = 1500;
    public static final int NUM_STARS = 5;

    List<Business> businesses;
    float[] rankings;
    String price;
    String transportation;

    public Ranking() {
        this.businesses = new ArrayList<Business>();
    }

    public Ranking(List<Business> businesses, String price, String transportation) {
        this.businesses = businesses;
        this.rankings = new float[this.businesses.size()];
        this.price = price;
        this.transportation = transportation;
    }

    public List<Business> rank() {
        int total = this.businesses.size();
        calculateDistanceScores();
        calculatePriceScores();
        calculateRatingScores();
        calculateOpenScores();
        int[] indices = getIndicesArray(total);
        int[] sortedIndices = mergeSort(indices, this.rankings);
        List<Business> rankedBusinesses = new ArrayList<>();
        float divideBy = 1;
        for (int i = total - 1; i >= 0; i--) {
            if (i == total - 1) {
                divideBy = this.rankings[sortedIndices[i]] / NUM_STARS;
            }
            Business business = this.businesses.get(sortedIndices[i]);
            business.setScore(this.rankings[sortedIndices[i]] / divideBy);
            rankedBusinesses.add(business);
        }
        this.businesses = rankedBusinesses;
        return this.businesses;
    }

    public void calculateOpenScores() {
        int total = this.businesses.size();
        for (int i = 0; i < total; i++) {
            boolean currentIsClosed = this.businesses.get(i).getIsClosed();
            if (!currentIsClosed) {
                this.rankings[i] += OPEN_WEIGHT;
            }
        }
    }

    public void calculateRatingScores() {
        int total = this.businesses.size();
        for (int i = 0; i < total; i++) {
            float currentRating = this.businesses.get(i).getRating();
            int currentReviewCount = this.businesses.get(i).getReviewCount();
            this.rankings[i] += RATING_WEIGHT * calculateRatingMetric(currentRating, currentReviewCount);
        }
    }

    public void calculatePriceScores() {
        int total = this.businesses.size();
        for (int i = 0; i < total; i++) {
            String currentPrice = this.businesses.get(i).getPrice();
            if (this.price.equals("2, 3")) {
                if (!"$$$$".equals(currentPrice)) {
                    this.rankings[i] += PRICE_WEIGHT;
                }
            } else if (this.price.equals("1")) {
                if ("$".equals(currentPrice)) {
                    this.rankings[i] += PRICE_WEIGHT;
                }
            } else {
                this.rankings[i] += PRICE_WEIGHT;
            }
        }
    }

    public int[] getIndicesArray(int N) {
        int[] indices = new int[N];
        for (int i = 0; i < N; i++) {
            indices[i] = i;
        }
        return indices;
    }

    public void calculateDistanceScores() {
        int total = this.businesses.size();
        for (int i = 0; i < total; i++) {
            float currentDistance = this.businesses.get(i).getDistance();
            if (this.transportation.equals("walking")) {
                if (currentDistance <= MAX_WALKING_DISTANCE + MAX_DISTANCE_PADDING) {
                    this.rankings[i] = DISTANCE_WEIGHT * 1/currentDistance;
                }
            } else if (this.transportation.equals("bicycle")) {
                if (currentDistance <= MAX_BICYCLE_DISTANCE + MAX_DISTANCE_PADDING) {
                    this.rankings[i] =  DISTANCE_WEIGHT * 1/currentDistance;
                }
            } else if (this.transportation.equals("transit")) {
                if (currentDistance <= MAX_TRANSIT_DISTANCE + MAX_DISTANCE_PADDING) {
                    this.rankings[i] =  DISTANCE_WEIGHT * 1/currentDistance;
                }
            } else {
                if (currentDistance <= MAX_AUTOMOBILE_DISTANCE + MAX_DISTANCE_PADDING) {
                    this.rankings[i] =  DISTANCE_WEIGHT * 1/currentDistance;
                }
            }
        }
    }

    public float calculateRatingMetric(float rating, int reviewCount) {
        return rating * reviewCount;
    }

    public int[] mergeSort(int[] indices, float[] sortBy) {
        int N = indices.length;

        if (N <= 1) {
            return indices;
        }

        int[] splitLeft = new int[N/2];
        int[] splitRight = new int[N - N/2];

        for (int i = 0; i < N/2; i++) {
            splitLeft[i] = indices[i];
        }

        for (int i = N/2; i < N; i++) {
            splitRight[i - N/2] = indices[i];
        }

        int[] left = mergeSort(splitLeft, sortBy);
        int[] right = mergeSort(splitRight, sortBy);
        return merge(left, right, sortBy);
    }

    public int[] merge(int[] left, int[] right, float[] sortBy) {

        int NLeft = left.length;
        int NRight = right.length;
        int N = NLeft + NRight;
        int[] merged = new int[N];
        int iLeft = 0;
        int iRight = 0;
        int iMerged = 0;

        while (iLeft < NLeft && iRight < NRight) {

            if (sortBy[left[iLeft]] < sortBy[right[iRight]]) {
                merged[iMerged] = left[iLeft];
                iLeft++;
            } else {
                merged[iMerged] = right[iRight];
                iRight++;
            }
            iMerged++;
        }

        while (iLeft < NLeft) {
            merged[iMerged] = left[iLeft];
            iLeft++;
            iMerged++;
        }

        while (iRight < NRight) {
            merged[iMerged] = right[iRight];
            iRight++;
            iMerged++;
        }

        return merged;
    }


}