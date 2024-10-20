package com.DreamerGp2024.service;

import com.DreamerGp2024.model.Post;
import com.DreamerGp2024.model.StoreException;

import java.util.List;

public interface PostService {
    public String addPost(Post post) throws StoreException;

    public String deletePostById(String postID) throws StoreException;

    public List<Post> getAllPosts() throws StoreException;
}
