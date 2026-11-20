package org.example.blog.article;

import org.example.blog.article.dto.ArticleResponse;

import java.util.Optional;

public class ArticleService {
    ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public Optional<ArticleResponse> findById(long id) {
        return repository.findById(id);
    }
}
