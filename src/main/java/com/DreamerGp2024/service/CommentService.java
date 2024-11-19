package com.DreamerGp2024.service;

import com.DreamerGp2024.model.Comment;
import com.DreamerGp2024.model.Post;
import com.DreamerGp2024.model.StoreException;

import java.util.List;

public interface CommentService {
    public String addComment(Comment comment) throws StoreException;

    public String deleteCommentById(String commentID) throws StoreException;

    public List<Comment> getAllComments() throws StoreException;
}
