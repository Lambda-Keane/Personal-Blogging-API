package org.example.blog.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BlogDatabase {
    private final String url  = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/blog");
    private final String user = System.getenv().getOrDefault("DB_USER", "postgres");
    private final String pass = System.getenv("DB_PASSWORD");

    public Connection connect() throws SQLException {
        if (pass == null) {
            throw new IllegalStateException("DB_PASSWORD is not set");
        }
        return DriverManager.getConnection(url, user, pass);
    }
}
