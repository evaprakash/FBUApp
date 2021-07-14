package com.example.fbuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

    private Post post;
    private TextView username;
    private ImageView imagePost;
    private TextView caption;
    private TextView createdAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        username = findViewById(R.id.username);
        imagePost = findViewById(R.id.imagePost);
        caption = findViewById(R.id.caption);
        createdAt = findViewById(R.id.createdAt);

        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));

        username.setText(post.getUser().getUsername());
        caption.setText(post.getDescription());
        Glide.with(DetailsActivity.this).load(post.getImage().getUrl()).into(imagePost);
        createdAt.setText(calculateTimeAgo(post.getCreatedAt()));

    }

    public static String calculateTimeAgo(Date createTime) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createTime.getTime();
            long time = createTime.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }

        return "";
    }
}