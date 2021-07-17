package com.example.fbuapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDescription;
        TextView tvName;
        ImageView ivImage;
        ImageView postLike;
        ImageButton heart;
        TextView numLikes;
        AnimatedVectorDrawableCompat avd;
        AnimatedVectorDrawable avd2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvName = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            heart = itemView.findViewById(R.id.heart);
            numLikes = itemView.findViewById(R.id.numLikes);
            postLike = itemView.findViewById(R.id.postLike);

        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvName.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            numLikes.setText(String.valueOf(post.getNumLikes()));

            final Drawable drawable = postLike.getDrawable();

            heart.setOnTouchListener(new View.OnTouchListener() {
                GestureDetector gestureDetector = new GestureDetector(context.getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        if (post.hasLiked(ParseUser.getCurrentUser()) == false) {
                            post.like(ParseUser.getCurrentUser());

                            postLike.setAlpha(0.70f);
                            if (drawable instanceof AnimatedVectorDrawableCompat) {
                                avd = (AnimatedVectorDrawableCompat) drawable;
                                avd.start();
                            } else if (drawable instanceof AnimatedVectorDrawable) {
                                avd2 = (AnimatedVectorDrawable) drawable;
                                avd2.start();
                            }

                            heart.setImageResource(R.drawable.ic_baseline_favorite_24);
                            numLikes.setText(String.valueOf(post.getNumLikes()));
                        }
                        return super.onDoubleTap(e);
                    }
                });
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gestureDetector.onTouchEvent(event);
                    return false;
                }
            });
            /*
            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (post.hasLiked(ParseUser.getCurrentUser()) == false) {
                        post.like(ParseUser.getCurrentUser());
                        heart.setImageResource(R.drawable.ic_baseline_favorite_24);
                        numLikes.setText(String.valueOf(post.getNumLikes()));
                    } else {
                        post.unlike(ParseUser.getCurrentUser());
                        heart.setImageResource(R.drawable.ic_outline_favorite_border_24);
                        numLikes.setText(String.valueOf(post.getNumLikes()));
                    }
                }
            });
            */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("post", Parcels.wrap(post));
                    context.startActivity(intent);
                }
            });


        }
    }

}
