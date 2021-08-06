package com.example.fbuapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView businessDetailsPrice;
    private Business business;
    private Button btnViewMap;
    private Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);
        business = (Business) Parcels.unwrap(getIntent().getParcelableExtra("business"));

        businessDetailsName = findViewById(R.id.businessDetailsName);
        btnViewMap = findViewById(R.id.btnViewMap);
        businessDetailsLayout = findViewById(R.id.businessDetailsLayout);
        businessDetailsImage = findViewById(R.id.businessDetailsImage);
        businessDetailsOpen = findViewById(R.id.businessDetailsOpen);
        businessDetailsPrice = findViewById(R.id.businessDetailsPrice);
        businessDetailsName.setText(business.getName());
        btnCall = findViewById(R.id.btnCall);

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
            businessDetailsOpen.setTextColor(getResources().getColor(R.color.closed));
        } else {
            businessDetailsOpen.setText("OPEN");
            businessDetailsOpen.setTextColor(getResources().getColor(R.color.open));
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

        btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMap(business);
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + business.getPhone()));
                startActivity(intent);
            }
        });

    }

    public void goToMap(Business business) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("latitude", business.getLatitude());
        intent.putExtra("longitude", business.getLongitude());
        intent.putExtra("name", business.getName());
        this.startActivity(intent);
    }
}