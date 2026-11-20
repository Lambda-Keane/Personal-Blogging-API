package org.example.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import org.example.blog.article.ArticleHandler;
import org.example.blog.article.ArticleRepository;
import org.example.blog.article.ArticleService;
import org.example.blog.config.BlogDatabase;
import org.example.blog.user.UserHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class BlogApp {
    static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        String url  = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5431/blog");
        String user = System.getenv().getOrDefault("DB_USER", "postgres");
        String pass = System.getenv("DB_PASSWORD");

        server.createContext("/articles",
                new ArticleHandler(
                        new ArticleService(
                                new ArticleRepository(
                                        new BlogDatabase(url, user, pass))), new ObjectMapper()));
        server.createContext("/users", new UserHandler());
        server.start();
        System.out.println("Listening on http://localhost:8080");
    }
}
