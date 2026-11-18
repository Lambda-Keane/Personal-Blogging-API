package org.example.blog.article;

import org.example.blog.config.BlogDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class ArticleRepository {
    private final BlogDatabase db;

    public ArticleRepository(BlogDatabase db) { this.db = db; }

    public Optional<Article> findArticleById(long article_id) {
        String sql = "SELECT article_id, publisher_id, title, content FROM articles WHERE article_id = ?";

        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, article_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new Article(rs.getLong("article_id"),
                        rs.getLong("publisher_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getObject("created_at", LocalDateTime.class),
                        rs.getObject("updated_at", LocalDateTime.class)));
            }
        } catch (SQLException e) {
            throw new RuntimeException("findArticleById() failed", e);
        }
    }
}
