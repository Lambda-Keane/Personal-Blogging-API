package org.example.blog.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BlogDatabase {
    private final String url;
    private final String user;
    private final String pass;

    public BlogDatabase(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public Connection connect() throws SQLException, ClassNotFoundException {
        if (pass == null) {
            throw new SQLException("DB_PASSWORD is not set");
        }
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, user, pass);
    }
}
