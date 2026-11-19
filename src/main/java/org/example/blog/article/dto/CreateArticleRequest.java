package org.example.blog.article;

public record CreateArticleRequest(long article_id,
                                   long publisher_id,
                                   String title,
                                   String content) {
}
