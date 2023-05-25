package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PostRepository {

    private final CrudRepository repository;

    public void save(Post post) {
        repository.run(session -> session.persist(post));
    }

    public Optional<Post> findById(int id) {
        return repository.optional("from Post where id = :eId", Post.class, Map.of("eId", id));
    }

    public List<Post> findAll() {
        return repository.query("from Post", Post.class);
    }

    public List<Post> findByLastDay() {
        return repository.query("from Post p where p.created >= (select max(created) from Post)",
                Post.class);
    }

    public List<Post> findByCar(String carName) {
        return repository.query("from Post p WHERE p.car.name like :fName",
                Post.class, Map.of("fName", carName));
    }

    public List<Post> findAllByPhoto() {
        return repository.query("from Post p where p.files.size > 0", Post.class);
    }

    public boolean update(Post post) {
        var post1 = findById(post.getId());
        return repository.booleanRun(session -> !post1.equals(session.merge(post)));
    }

    public boolean delete(int id) {
        return repository.booleanRun("delete from Car where id = :eId", Map.of("eId", id));
    }
}
