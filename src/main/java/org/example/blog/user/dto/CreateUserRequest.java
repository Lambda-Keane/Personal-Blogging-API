package org.example.blog.user;

public record CreateUserRequest(long user_id, String username, String password_hash) {
}
