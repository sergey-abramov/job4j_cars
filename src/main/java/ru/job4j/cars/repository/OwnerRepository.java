package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OwnerRepository {

    private final CrudRepository repository;

    public Owner save(Owner owner) {
        repository.run(session -> session.persist(owner));
        return owner;
    }

    public Optional<Owner> findById(int id) {
        return repository.optional("from Owner where id = :eId", Owner.class, Map.of("eId", id));
    }

    public List<Owner> findAll() {
        return repository.query("from Owner", Owner.class);
    }

    public boolean delete(int id) {
        return repository.booleanRun("delete from Owner where id = :eId", Map.of("eId", id));
    }
}
