package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.HibernateConfiguration;
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class EngineRepositoryTest {

    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    @BeforeAll
    public static void init() {
        sf = new HibernateConfiguration().sf();
    }

    @AfterAll
    public static void close() {
        sf.close();
    }

    @AfterEach
    public void deleteTable() {
        for (Engine e : engineRepository.findAll()) {
            engineRepository.delete(e.getId());
        }
    }

    @Test
    void saveAndFindById() {
        Engine engine = new Engine();
        engine.setName("test");
        int id = engineRepository.save(engine).getId();
        assertThat(engineRepository.findById(id).isPresent(), is(true));
        assertThat(engineRepository.findById(id).get(), is(engine));
    }

    @Test
    void findIsEmpty() {
        assertThat(engineRepository.findAll(), is(List.of()));
    }
}