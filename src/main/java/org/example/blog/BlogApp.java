package org.example.blog;

import com.sun.net.httpserver.HttpServer;
import org.example.blog.article.ArticleHandler;
import org.example.blog.user.UserHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class BlogApp {
    static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/articles", new ArticleHandler());
        server.createContext("/user", new UserHandler());
        server.start();
        System.out.println("Listening on http://localhost:8080");
    }
}
