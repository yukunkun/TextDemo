package com.yukun.textapplication.mvptest;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by yukun on 17-4-18.
 */
public class Status extends MultiItemEntity {
    private boolean isRetweet;
    private String text;
    private String userName;
    private String userAvatar;
    private String createdAt;
    public static final int TEXT = 1;
    public static final int IMG = 2;

    public boolean isRetweet() {
        return isRetweet;
    }

    public void setRetweet(boolean retweet) {
        isRetweet = retweet;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Status{" +
                "isRetweet=" + isRetweet +
                ", text='" + text + '\'' +
                ", userName='" + userName + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
