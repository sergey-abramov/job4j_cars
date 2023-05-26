package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.HibernateConfiguration;
import ru.job4j.cars.model.Owner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class OwnerRepositoryTest {

    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final OwnerRepository ownerRepository = new OwnerRepository(crudRepository);

    @BeforeAll
    public static void init() {
        sf = new HibernateConfiguration().sf();
    }

    @AfterAll
    public static void close() {
        sf.close();
    }

    @AfterEach
    public void delete() {
        for (Owner o : ownerRepository.findAll()) {
            ownerRepository.delete(o.getId());
        }
    }

    @Test
    void saveAndFindById() {
        Owner owner = new Owner();
        owner.setName("test");
        int id = ownerRepository.save(owner).getId();
        assertThat(ownerRepository.findById(id).isPresent(), is(true));
        assertThat(ownerRepository.findById(id).get().getName(), is(owner.getName()));
    }

    @Test
    void findAllIsFalse() {
        assertThat(ownerRepository.findAll().isEmpty(), is(true));
    }
}