package com.example.fbuapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.fbuapp.posts.Post;
import com.example.fbuapp.posts.PostsAdapter;
import com.example.fbuapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import www.sanju.motiontoast.MotionToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {

    private final String TAG = "PostsFragment";
    public static final String KEY_CODE = "code";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    private SwipeRefreshLayout swipeContainer;
    private LinearLayout passwordProtection;
    private EditText passwordAttempt;
    private ImageButton btnSubmitUnlock;
    private ImageButton btnLock;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        passwordProtection = view.findViewById(R.id.passwordProtection);
        passwordAttempt = view.findViewById(R.id.passwordAttempt);
        btnSubmitUnlock = view.findViewById(R.id.btnSubmitUnlock);
        btnLock = view.findViewById(R.id.btnLock);
        rvPosts = view.findViewById(R.id.rvPosts);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLock.setVisibility(View.GONE);
                passwordProtection.setVisibility(View.VISIBLE);
            }
        });

        btnSubmitUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int passwordInput = Integer.valueOf(passwordAttempt.getText().toString());
                int password = ParseUser.getCurrentUser().getInt(KEY_CODE);
                if (password == passwordInput) {
                    btnSubmitUnlock.setImageResource(R.drawable.ic_baseline_lock_open_24);
                    passwordProtection.setVisibility(View.GONE);
                    swipeContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);
        rvPosts.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);
        queryPosts();

    }

    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    MotionToast.Companion.createColorToast(getActivity(),
                            "Error",
                            "Error while loading posts.",
                            MotionToast.TOAST_ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.SHORT_DURATION,
                            ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));
                    return;
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void fetchTimelineAsync(int page) {
        adapter.clear();
        queryPosts();
        swipeContainer.setRefreshing(false);
    }

}