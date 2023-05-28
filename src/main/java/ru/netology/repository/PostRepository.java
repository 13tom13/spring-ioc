package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

    private final Map<Long, Post> repository = new ConcurrentHashMap<>();

    private static final AtomicLong COUNTER = new AtomicLong(1);

    public PostRepository() {

    }

    public List<Post> all() {
        return new ArrayList<>(repository.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.of(repository.get(id));
    }

    public Post save(Post post) {
        System.out.println(post.getId());
        if (post.getId() == 0) {
            post.setId(COUNTER.getAndIncrement());
            repository.put(post.getId(), post);
        } else if (repository.containsKey(post.getId())) {
            repository.replace(post.getId(), post);
        } else {
            post.setContent("Post with id " + post.getId() + " not found");
        }
        return post;
    }

    public void removeById(long id) {
      if (repository.containsKey(id)){
        repository.remove(id);
      } else {
        throw new NotFoundException("Post with id " + id + " not found");
      }
    }
}
