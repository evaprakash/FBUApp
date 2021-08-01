package com.example.fbuapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fbuapp.R;
import com.example.fbuapp.business.Business;

import org.parceler.Parcels;

import java.util.List;

public class BusinessDetailsActivity extends AppCompatActivity {

    TextView businessDetailsName;
    ImageView businessDetailsImage;
    TextView businessDetailsOpen;
    TextView businessDetailsAddressLineOne;
    TextView businessDetailsAddressLineTwo;
    TextView businessDetailsPhoneNumber;
    TextView businessDetailsPrice;
    Business business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);
        business = (Business) Parcels.unwrap(getIntent().getParcelableExtra("business"));

        businessDetailsName = findViewById(R.id.businessDetailsName);
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
        businessDetailsPhoneNumber.setText(business.getDisplayPhone());
        businessDetailsPrice.setText(business.getPrice());

        if (business.getImageUrl() != null) {
            Glide.with(BusinessDetailsActivity.this).load(business.getImageUrl()).into(businessDetailsImage);
        }

        if (business.getIsClosed()) {
            businessDetailsOpen.setText("CLOSED");
        } else {
            businessDetailsOpen.setText("OPEN");
        }
    }
}