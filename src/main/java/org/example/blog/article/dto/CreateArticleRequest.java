package org.example.blog.article.dto;

public record CreateArticleRequest(long publisher_id,
                                   String title,
                                   String content) {
}
