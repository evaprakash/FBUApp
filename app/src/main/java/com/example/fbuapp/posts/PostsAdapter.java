package com.example.fbuapp.posts;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.bumptech.glide.Glide;
import com.example.fbuapp.activities.DetailsActivity;
import com.example.fbuapp.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;
    private final String TAG = "PostsAdapter";

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

        FrameLayout flImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvName = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            heart = itemView.findViewById(R.id.heart);
            numLikes = itemView.findViewById(R.id.numLikes);
            postLike = itemView.findViewById(R.id.postLike);
            flImage = itemView.findViewById(R.id.flImage);

        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvName.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image == null) {
                flImage.setVisibility(View.GONE);
            } else {
                Glide.with(context).load(image.getUrl()).transform(new RoundedCornersTransformation(50, 10)).into(ivImage);
            }
            numLikes.setText(String.valueOf(post.getNumLikes()));

            final Drawable drawable = postLike.getDrawable();


            if (post.hasLiked(ParseUser.getCurrentUser())) {
                heart.setImageResource(R.drawable.ic_baseline_favorite_24);
            }

            heart.setOnTouchListener(new View.OnTouchListener() {
                GestureDetector gestureDetector = new GestureDetector(context.getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        if (post.hasLiked(ParseUser.getCurrentUser()) == false) {
                            post.addLike(ParseUser.getCurrentUser());
                            post.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException exception) {
                                    if (exception != null) {
                                        Log.e(TAG, "Error while saving", exception);
                                        return;
                                    }
                                    heart.setImageResource(R.drawable.ic_baseline_favorite_24);
                                    numLikes.setText(String.valueOf(post.getNumLikes()));
                                    notifyDataSetChanged();
                                }
                            });

                            postLike.setAlpha(0.70f);
                            if (drawable instanceof AnimatedVectorDrawableCompat) {
                                avd = (AnimatedVectorDrawableCompat) drawable;
                                avd.start();
                            } else if (drawable instanceof AnimatedVectorDrawable) {
                                avd2 = (AnimatedVectorDrawable) drawable;
                                avd2.start();
                            }


                        } else {
                            post.removeLike(ParseUser.getCurrentUser());
                            post.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException exception) {
                                    if (exception != null) {
                                        Log.e(TAG, "Error while saving", exception);
                                        return;
                                    }
                                    heart.setImageResource(R.drawable.ic_outline_favorite_border_24);
                                    numLikes.setText(String.valueOf(post.getNumLikes()));
                                    notifyDataSetChanged();
                                }
                            });
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
