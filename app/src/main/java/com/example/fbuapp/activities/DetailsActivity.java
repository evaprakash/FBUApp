package com.example.fbuapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fbuapp.comments.Comment;
import com.example.fbuapp.comments.CommentAdapter;
import com.example.fbuapp.R;
import com.example.fbuapp.posts.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import www.sanju.motiontoast.MotionToast;

public class DetailsActivity extends AppCompatActivity {

    private final String TAG = "DetailsActivity";
    private Post post;
    private TextView username;
    private ImageView imagePost;
    private TextView caption;
    private TextView createdAt;
    private EditText writeComment;
    private Button btnComment;
    private RecyclerView rvComments;
    protected CommentAdapter adapter;
    protected List<Comment> allComments;
    private SwipeRefreshLayout swipeContainerComments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        username = findViewById(R.id.username);
        imagePost = findViewById(R.id.imagePost);
        caption = findViewById(R.id.caption);
        createdAt = findViewById(R.id.createdAt);
        writeComment = findViewById(R.id.writeComment);
        btnComment = findViewById(R.id.btnComment);
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));
        rvComments = findViewById(R.id.rvComments);
        allComments= new ArrayList<>();
        adapter = new CommentAdapter(getApplicationContext(), allComments);
        rvComments.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvComments.setLayoutManager(linearLayoutManager);
        queryComments(post);
        username.setText(post.getUser().getUsername());
        caption.setText(post.getDescription());

        if (post.getImage() != null) {
            Glide.with(DetailsActivity.this).load(post.getImage().getUrl()).transform(new RoundedCornersTransformation(50, 10)).into(imagePost);
        } else {
            imagePost.setVisibility(View.GONE);
        }

        createdAt.setText(calculateTimeAgo(post.getCreatedAt()));

        swipeContainerComments = (SwipeRefreshLayout) findViewById(R.id.swipeContainerComments);
        swipeContainerComments.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0, post);
            }
        });

        swipeContainerComments.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment();
                comment.setPostID(post.getObjectId());
                comment.setUser(ParseUser.getCurrentUser());
                comment.setContent(writeComment.getText().toString());
                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException exception) {
                        if (exception != null) {
                            Log.e(TAG, "Error while saving", exception);
                            MotionToast.Companion.createColorToast(DetailsActivity.this,
                                    "Error",
                                    "Error while saving comment.",
                                    MotionToast.TOAST_ERROR,
                                    MotionToast.GRAVITY_BOTTOM,
                                    MotionToast.SHORT_DURATION,
                                    ResourcesCompat.getFont(DetailsActivity.this,R.font.helvetica_regular));
                            return;
                        } else {
                            writeComment.setText("");
                            MotionToast.Companion.createColorToast(DetailsActivity.this,
                                    "Success",
                                    "Posted!",
                                    MotionToast.TOAST_SUCCESS,
                                    MotionToast.GRAVITY_BOTTOM,
                                    MotionToast.SHORT_DURATION,
                                    ResourcesCompat.getFont(DetailsActivity.this,R.font.helvetica_regular));
                        }
                    }
                });
            }
        });


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

    protected void queryComments(Post post) {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.whereEqualTo(Comment.KEY_POST_ID, post.getObjectId());
        query.include(Comment.KEY_USER);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException exception) {
                if (exception != null) {
                    Log.e(TAG, "Issue with getting comments", exception);
                    MotionToast.Companion.createColorToast(DetailsActivity.this,
                            "Error",
                            "Error with loading comments.",
                            MotionToast.TOAST_ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.SHORT_DURATION,
                            ResourcesCompat.getFont(DetailsActivity.this,R.font.helvetica_regular));
                    return;
                }
                for (Comment comment : comments) {
                    Log.i(TAG, "Comment: " + comment.getUser().getUsername());
                }
                allComments.addAll(comments);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void fetchTimelineAsync(int page, Post post) {
        adapter.clear();
        queryComments(post);
        swipeContainerComments.setRefreshing(false);
    }
}