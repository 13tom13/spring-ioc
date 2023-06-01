package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {

    private final Map<Long, Post> repository = new ConcurrentHashMap<>();

    private static final AtomicLong COUNTER = new AtomicLong(1);


    public List<Post> all() {
        return repository.values().stream()
                .filter(post -> !post.isRemoved())
                .collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(repository.get(id));
    }

    public Post save(Post post) {
        System.out.println(post.getId());
        if (post.getId() == 0) {
            post.setId(COUNTER.getAndIncrement());
            repository.put(post.getId(), post);
        } else if (repository.containsKey(post.getId())) {
            repository.replace(post.getId(), post);
        } else {
            throw new NotFoundException(post.getId());
        }
        return post;
    }

    public void removeById(long id) {
      if (repository.containsKey(id)){
        repository.get(id).setRemoved(true);
      } else {
        throw new NotFoundException(id);
      }
    }
}
