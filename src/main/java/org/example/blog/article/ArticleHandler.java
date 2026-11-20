package org.example.blog.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.blog.article.dto.ArticleResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class ArticleHandler implements HttpHandler {
    ArticleService service;
    ObjectMapper mapper;

    public ArticleHandler(ArticleService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        try {
            String method = ex.getRequestMethod();
            String[] parts = ex.getRequestURI().getPath().split("/");

            if (!method.equals("GET")) {
                ex.getResponseHeaders().add("Allow", "GET");
                send(ex, 405, "{\"error\":\"method not allowed\"}");
                return;
            }
            if (parts.length != 3) {
                send(ex, 404, "{\"error\":\"not found\"}");
                return;
            }

            long id;
            try {
                id = Long.parseLong(parts[2]);
            } catch (NumberFormatException e) {
                send(ex, 400, "{\"error\":\"id must be a number\"}");
                return;
            }

            Optional<ArticleResponse> found = service.findById(id);
            if (found.isEmpty()) {
                send(ex, 404, "{\"error\":\"no such article\"}");
            } else {
                send(ex, 200, mapper.writeValueAsString(found.get()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            send(ex, 500, "{\"error\":\"internal error\"}");
        } finally {
            ex.close();
        }
    }

    private void send(HttpExchange ex, int status, String json) throws IOException {
        byte[] body = json.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().add("Content-Type", "application/json");
        ex.sendResponseHeaders(status, body.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(body);
        }
    }
}
