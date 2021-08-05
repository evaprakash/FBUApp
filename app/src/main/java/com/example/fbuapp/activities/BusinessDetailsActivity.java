package com.example.fbuapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fbuapp.R;
import com.example.fbuapp.business.Business;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class BusinessDetailsActivity extends AppCompatActivity {

    private LinearLayout businessDetailsLayout;
    private TextView businessDetailsName;
    private ImageView businessDetailsImage;
    private TextView businessDetailsOpen;
    private TextView businessDetailsAddressLineOne;
    private TextView businessDetailsAddressLineTwo;
    private TextView businessDetailsPhoneNumber;
    private TextView businessDetailsPrice;
    private Business business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);
        business = (Business) Parcels.unwrap(getIntent().getParcelableExtra("business"));

        businessDetailsName = findViewById(R.id.businessDetailsName);
        businessDetailsLayout = findViewById(R.id.businessDetailsLayout);
        businessDetailsImage = findViewById(R.id.businessDetailsImage);
        businessDetailsOpen = findViewById(R.id.businessDetailsOpen);
        businessDetailsAddressLineOne = findViewById(R.id.businessDetailsAddressLineOne);
        businessDetailsAddressLineTwo= findViewById(R.id.businessDetailsAddressLineTwo);
        businessDetailsPhoneNumber = findViewById(R.id.businessDetailsPhoneNumber);
        businessDetailsPrice = findViewById(R.id.businessDetailsPrice);

        businessDetailsName.setText(business.getName());

        businessDetailsName.setText(business.getName());
        businessDetailsAddressLineOne.setText(business.getAddressLineOne());
        businessDetailsAddressLineTwo.setText(business.getAddressLineTwo());
        if (!business.getDisplayPhone().equals("")) {
            businessDetailsPhoneNumber.setText(business.getDisplayPhone());
        } else {
            businessDetailsPhoneNumber.setVisibility(View.GONE);
        }
        System.out.println(business.getDisplayPhone());

        if (business.getPrice() != null) {
            businessDetailsPrice.setText(business.getPrice());
        } else {
            businessDetailsPrice.setVisibility(View.GONE);
        }

        if (business.getImageUrl() != null) {
            Glide.with(BusinessDetailsActivity.this).load(business.getImageUrl()).transform(new RoundedCornersTransformation(50, 10)).into(businessDetailsImage);
        }

        if (business.getIsClosed()) {
            businessDetailsOpen.setText("CLOSED");
        } else {
            businessDetailsOpen.setText("OPEN");
        }

        float score = business.getScore();
        if (score >= 5.0) {
            businessDetailsLayout.setBackgroundResource(R.drawable.five);
        } else if (score >= 4.0) {
            businessDetailsLayout.setBackgroundResource(R.drawable.four);
        } else if (score >= 3.0) {
            businessDetailsLayout.setBackgroundResource(R.drawable.three);
        } else if (score >= 2.0) {
            businessDetailsLayout.setBackgroundResource(R.drawable.two);
        } else {
            businessDetailsLayout.setBackgroundResource(R.drawable.one);
        }

    }
}