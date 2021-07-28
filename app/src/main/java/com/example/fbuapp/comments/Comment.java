package com.example.fbuapp.comments;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;
import org.parceler.Parcel;

@Parcel(analyze = Comment.class)
@ParseClassName("Comment")
public class Comment extends ParseObject {

    public static final String KEY_CONTENT = "content";
    public static final String KEY_POST_ID = "postObjectID";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";

    public String getContent() {
        return getString(KEY_CONTENT);
    }

    public void setContent(String content) {
        put(KEY_CONTENT, content);
    }

    public String getPostID() {
        return getString(KEY_POST_ID);
    }

    public void setPostID(String postID) {
        put(KEY_POST_ID, postID);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

}