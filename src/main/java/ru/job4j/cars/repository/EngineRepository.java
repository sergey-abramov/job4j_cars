package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EngineRepository {

    private final CrudRepository repository;

    public void save(Engine engine) {
        repository.run(session -> session.persist(engine));
    }

    public Optional<Engine> findById(int id) {
        return repository.optional("from Engine where id = :eId", Engine.class, Map.of("eId", id));
    }

    public List<Engine> findAll() {
        return repository.query("from Engine", Engine.class);
    }
}
