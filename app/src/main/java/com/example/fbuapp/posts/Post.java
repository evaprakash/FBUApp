package com.example.fbuapp.posts;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel(analyze = Post.class)
@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_LIKES= "likes";

    public List<ParseUser> getLikes() {
        List<ParseUser> likes = (List<ParseUser>) get(KEY_LIKES);
        if (likes == null) {
            return new ArrayList<>();
        } else {
            return likes;
        }
    }

    public void addLike(ParseUser user) {
        List<ParseUser> likes = getLikes();
        likes.add(user);
        put(KEY_LIKES, likes);
    }

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public int getNumLikes() {
        List<ParseUser> likes = getLikes();
        return likes.size();
    }

    public void removeLike(ParseUser user) {
        List<ParseUser> likes = getLikes();
        List<ParseUser> newLikes = new ArrayList<>();
        for (int i = 0; i < likes.size(); i++) {
            ParseUser p = likes.get(i);
            if (user.getObjectId().equals(p.getObjectId())) {
                continue;
            } else {
                newLikes.add(p);
            }
        }
        put(KEY_LIKES, newLikes);
    }

    public boolean hasLiked(ParseUser user) {
        List<ParseUser> likes = getLikes();
        for (int i = 0; i < likes.size(); i++) {
            ParseUser p = likes.get(i);
            if (user.getObjectId().equals(p.getObjectId())) {
                Log.e("Post", "True");
                return true;
            }
        }
        Log.e("Post", "False");
        return false;
    }

}
