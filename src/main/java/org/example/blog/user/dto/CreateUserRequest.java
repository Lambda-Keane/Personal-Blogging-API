package org.example.blog.user.dto;

public record CreateUserRequest(long user_id, String username, String password_hash) {
}
