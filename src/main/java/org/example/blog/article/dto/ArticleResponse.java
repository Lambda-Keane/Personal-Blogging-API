package org.example.blog.article.dto;

import java.time.LocalDateTime;

public record ArticleResponse(long article_id,
                              long publisher_id,
                              String title,
                              String content,
                              LocalDateTime created_at,
                              LocalDateTime updated_at) {
}
