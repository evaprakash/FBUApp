package com.example.fbuapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context context;
    private List<Comment> allComments;
    private final String TAG = "CommentAdapter";

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.allComments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = allComments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return allComments.size();
    }

    public void clear() {
        allComments.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Comment> list) {
        allComments.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView commentText;
        TextView commentUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.commentText);
            commentUser = itemView.findViewById(R.id.commentUser);
        }

        public void bind(Comment comment) {
            commentText.setText(comment.getContent());
            commentUser.setText(comment.getUser().getUsername());
        }
    }

}
