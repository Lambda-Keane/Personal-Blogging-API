package org.example.blog.article;

import org.example.blog.article.dto.ArticleResponse;
import org.example.blog.article.dto.CreateArticleRequest;
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

    public Optional<ArticleResponse> findById(long article_id) {
        String sql = "SELECT article_id, publisher_id, title, content FROM articles WHERE article_id = ?;";

        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, article_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new ArticleResponse(rs.getLong("article_id"),
                        rs.getLong("publisher_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getObject("created_at", LocalDateTime.class),
                        rs.getObject("updated_at", LocalDateTime.class)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("findArticleById() failed", e);
        }
    }

    public void insertArticle(long article_id, CreateArticleRequest newArticle) {
        String sql = "INSERT INTO articles (publisher_id, title, content) VALUES (?, ?, ?);";

        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, newArticle.publisher_id());
            ps.setString(2, newArticle.title());
            ps.setString(3, newArticle.content());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("findArticleById() failed", e);
        }
    }
}
