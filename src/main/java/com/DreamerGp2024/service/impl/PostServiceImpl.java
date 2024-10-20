package com.DreamerGp2024.service.impl;

import com.DreamerGp2024.model.Post;
import com.DreamerGp2024.model.StoreException;
import com.DreamerGp2024.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.DreamerGp2024.constant.db.PostsDBConstants.*;

public class PostServiceImpl {
    public String addPost(Post post) throws StoreException {
        String result = null;
        Connection con = DBUtil.getConnection();
        String insertPostQuery = "INSERT INTO " + TABLE_POSTS + " (" +
                COLUMN_POSTID + ", " +
                COLUMN_AUTHOR + ", " +
                COLUMN_TIME + ", " +
                COLUMN_HEADER + ", " +
                COLUMN_BODY + ") VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(insertPostQuery);
            ps.setInt(1, post.getPostID());
            ps.setInt(2, post.getAuthor());
            ps.setLong(3, post.getTime());
            ps.setString(4, post.getHeader());
            ps.setString(5, post.getBody());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "Post added successfully";
            }
        } catch (SQLException ignored) {

        }
        return result;
    }

    public String deletePostById(String postID) throws StoreException {
        String result = null;
        Connection con = DBUtil.getConnection();
        String deletePostQuery = "DELETE FROM " + TABLE_POSTS + " WHERE " + COLUMN_POSTID + " = ?";
        try {
            PreparedStatement ps = con.prepareStatement(deletePostQuery);
            ps.setString(1, postID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "Post deleted successfully";
            }
        } catch (SQLException ignored) {

        }
        return result;
    }

    public List<Post> getAllPosts() throws StoreException {
        List<Post> posts = new ArrayList<>();
        Connection con = DBUtil.getConnection();
        String getAllPostsQuery = "SELECT * FROM " + TABLE_POSTS;
        try {
            PreparedStatement ps = con.prepareStatement(getAllPostsQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post(
                        rs.getInt(COLUMN_POSTID),
                        rs.getInt(COLUMN_AUTHOR),
                        rs.getLong(COLUMN_TIME),
                        rs.getString(COLUMN_HEADER),
                        rs.getString(COLUMN_BODY)
                );
                posts.add(post);
            }
        } catch (SQLException ignored) {

        }
        return posts;
    }
}

