package org.example.blog.article;

import java.time.LocalDateTime;

public record Article(long article_id,
                      long publisher_id,
                      String title,
                      String content,
                      LocalDateTime created_at,
                      LocalDateTime updated_at) {
}
