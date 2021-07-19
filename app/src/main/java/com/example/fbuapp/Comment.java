package com.example.fbuapp;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import org.parceler.Parcel;

@Parcel(analyze = Comment.class)
@ParseClassName("Comment")
public class Comment extends ParseObject {

    public static final String KEY_CONTENT = "content";
    public static final String KEY_POST = "post";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";

    public String getContent() {
        return getString(KEY_CONTENT);
    }

    public void setContent(String content) {
        put(KEY_CONTENT, content);
    }

    //In Parse DB, Comment has a pointer to the corresponding Post. Not sure how to get that pointer?

    public void getPost() {
        return;
    }

    public void setPost() {
        return;
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

}