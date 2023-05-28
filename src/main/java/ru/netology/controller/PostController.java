package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    private final Gson gson = new Gson();

    public PostController(PostService service) {
        this.service = service;
    }

    private void JSONresponse(Object o, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        response.getWriter().print(gson.toJson(o));
    }

    public void all(HttpServletResponse response) throws IOException {
        final var data = service.all();
        JSONresponse(data, response);
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        final var data = service.getById(id);
        JSONresponse(data, response);
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        final var data = service.save(gson.fromJson(body, Post.class));
        JSONresponse(data, response);
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        service.removeById(id);
        JSONresponse("Post with id " + id + " remove", response);
    }
}
