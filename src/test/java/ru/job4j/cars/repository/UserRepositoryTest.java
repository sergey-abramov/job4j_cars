package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.HibernateConfiguration;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class UserRepositoryTest {

    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final UserRepository userRepository = new UserRepository(crudRepository);

    @BeforeAll
    public static void init() {
       sf = new HibernateConfiguration().sf();
    }

    @AfterAll
    public static void close() {
        sf.close();
    }

    @AfterEach
    public void wipeTable() {
        for (User u : userRepository.findAllOrderById()) {
            userRepository.delete(u.getId());
        }
    }

    @Test
    void createAndFindById() {
        User user = new User();
        user.setLogin("qwe");
        user.setPassword("123");
        int id = userRepository.create(user).getId();
        assertThat(userRepository.findById(id).isPresent(), is(true));
        assertThat(userRepository.findById(id).get(), is(user));
    }

    @Test
    void update() {
        User user = new User();
        user.setLogin("qwe");
        user.setPassword("123");
        int id = userRepository.create(user).getId();
        user.setLogin("test");
        userRepository.update(user);
        assertThat(userRepository.findById(id).isPresent(), is(true));
        assertThat(userRepository.findById(id).get(), is(user));
    }

    @Test
    void delete() {
        User user = new User();
        user.setLogin("qwe");
        user.setPassword("123");
        int id = userRepository.create(user).getId();
        userRepository.delete(id);
        assertThat(userRepository.findById(id).isEmpty(), is(true));
    }

    @Test
    void findAllOrderById() {
        User user = new User();
        user.setLogin("qwe");
        user.setPassword("123");
        User user1 = new User();
        user1.setLogin("test");
        user1.setPassword("123");
        userRepository.create(user);
        userRepository.create(user1);
        assertThat(userRepository.findAllOrderById(), is(List.of(user, user1)));
    }

    @Test
    void findByLikeLogin() {
        User user = new User();
        user.setLogin("qwe");
        user.setPassword("123");
        User user1 = new User();
        user1.setLogin("test");
        user1.setPassword("123");
        userRepository.create(user);
        userRepository.create(user1);
        assertThat(userRepository.findByLikeLogin("es"), is(List.of(user1)));
    }

    @Test
    void findByLogin() {
        User user = new User();
        user.setLogin("qwe");
        user.setPassword("123");
        User user1 = new User();
        user1.setLogin("test");
        user1.setPassword("123");
        userRepository.create(user);
        userRepository.create(user1);
        assertThat(userRepository.findByLogin("test").isPresent(), is(true));
        assertThat(userRepository.findByLogin("test").get(), is(user1));
    }
}