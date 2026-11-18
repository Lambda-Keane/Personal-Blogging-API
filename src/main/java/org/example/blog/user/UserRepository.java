package org.example.blog.user;

import org.example.blog.config.BlogDatabase;
import org.example.blog.article.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository {
    private final BlogDatabase db;

    public UserRepository(BlogDatabase db) { this.db = db; }

    public Optional<User> findUserById(long user_id) {
        String sql = "SELECT user_id, username FROM users WHERE user_id = ?";

        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new User(rs.getLong("user_id"),
                        rs.getString("username")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("findUserById failed()", e);
        }
    }
}
