package com.DreamerGp2024.model;

import java.io.Serializable;

public class Post implements Serializable {

    private int postID;
    private int author;
    private Long time;
    private String header;
    private String body;



    public Post(int postID, int author, Long time, String header, String body) {
        this.postID = postID;
        this.author = author;
        this.time = time;
        this.header = header;
        this.body = body;
    }

    public Post() {
        super();
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postID=" + postID +
                ", author=" + author +
                ", time=" + time +
                ", header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
