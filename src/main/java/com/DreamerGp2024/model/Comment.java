package com.DreamerGp2024.model;

import java.io.Serializable;

public class Comment implements Serializable {

    private int commentID;
    private String book;
    private String author;
    private String header;
    private String body;
    private String secretBody;


    public Comment(int commentID, String book, String author, String header, String body, String secretBody) {
        this.commentID = commentID;
        this.book = book;
        this.author = author;
        this.header = header;
        this.body = body;
        this.secretBody = secretBody;
    }

    public Comment() {
        super();
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getAuthor() {return author;    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getSecretBody() {
        return secretBody;
    }

    public void setSecretBody(String secretBody) {
        this.secretBody = secretBody;
    }
}
