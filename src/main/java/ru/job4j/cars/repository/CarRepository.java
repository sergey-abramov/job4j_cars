package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarRepository {

    private final CrudRepository repository;

    public Car save(Car car) {
        repository.run(session -> session.persist(car));
        return car;
    }

    public Optional<Car> findById(int id) {
        return repository.optional("from Car where id = :eId", Car.class, Map.of("eId", id));
    }

    public List<Car> findAll() {
        return repository.query("from Car", Car.class);
    }

    public boolean update(Car car) {
        var car1 = findById(car.getId());
        return repository.booleanRun(session -> !car1.equals(session.merge(car)));
    }

    public boolean delete(int id) {
        return repository.booleanRun("delete from Car where id = :eId", Map.of("eId", id));
    }
}
