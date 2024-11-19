package com.DreamerGp2024.service.impl;

import com.DreamerGp2024.model.Comment;
import com.DreamerGp2024.model.Post;
import com.DreamerGp2024.model.StoreException;
import com.DreamerGp2024.service.CommentService;
import com.DreamerGp2024.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.DreamerGp2024.constant.db.CommentsDBConstants.*;

public class CommentServiceImpl implements CommentService {


    @Override
    public String addComment(Comment comment) throws StoreException {
        String result = null;
        Connection con = DBUtil.getConnection();
        String insertPostQuery = "INSERT INTO " + TABLE_COMMENTS + " (" +
                COLUMN_COMMENTID + ", " +
                COLUMN_BOOK + ", " +
                COLUMN_AUTHOR + ", " +
                COLUMN_HEADER + ", " +
                COLUMN_BODY + ", " +
                COLUMN_SECRETBODY + ") VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(insertPostQuery);
            ps.setInt(1, comment.getCommentID());
            ps.setString(2, comment.getBook());
            ps.setInt(3, comment.getAuthor());
            ps.setString(4, comment.getHeader());
            ps.setString(5, comment.getBody());
            ps.setString(6, comment.getSecretBody());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "Post added successfully";
            }
        } catch (SQLException ignored) {

        }
        return result;
    }

    @Override
    public String deleteCommentById(String commentID) throws StoreException {
        String result = null;
        Connection con = DBUtil.getConnection();
        String deletePostQuery = "DELETE FROM " + TABLE_COMMENTS + " WHERE " + COLUMN_COMMENTID + " = ?";
        try {
            PreparedStatement ps = con.prepareStatement(deletePostQuery);
            ps.setString(1, commentID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "Post deleted successfully";
            }
        } catch (SQLException ignored) {

        }
        return result;
    }

    @Override
    public List<Comment> getAllComments(String bookID) throws StoreException {
        List<Comment> comments = new ArrayList<>();
        Connection con = DBUtil.getConnection();
        String getAllPostsQuery = "SELECT * FROM " + TABLE_COMMENTS + " WHERE " + COLUMN_BOOK + " = ?";
        try {
            PreparedStatement ps = con.prepareStatement(getAllPostsQuery);
            ps.setString(1, bookID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment(
                        rs.getInt(COLUMN_COMMENTID),
                        rs.getString(COLUMN_BOOK),
                        rs.getInt(COLUMN_AUTHOR),
                        rs.getString(COLUMN_HEADER),
                        rs.getString(COLUMN_BODY),
                        rs.getString(COLUMN_SECRETBODY)
                );
                comments.add(comment);
            }
        } catch (SQLException ignored) {

        }
        return comments;
    }
}
