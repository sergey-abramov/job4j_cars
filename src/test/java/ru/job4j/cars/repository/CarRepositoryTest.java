package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.job4j.cars.configuration.HibernateConfiguration;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CarRepositoryTest {

    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    private final OwnerRepository ownerRepository = new OwnerRepository(crudRepository);
    private final CarRepository carRepository = new CarRepository(crudRepository);

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
        for (Car c : carRepository.findAll()) {
            carRepository.delete(c.getId());
        }
        for (Engine e : engineRepository.findAll()) {
            engineRepository.delete(e.getId());
        }
        for (Owner o : ownerRepository.findAll()) {
            ownerRepository.delete(o.getId());
        }
    }

    @Test
    void saveAndFindById() {
        var engine = new Engine();
        var owner = new Owner();
        engineRepository.save(engine);
        ownerRepository.save(owner);
        Car car = new Car();
        car.setOwner(owner);
        car.setEngine(engine);
        int id = carRepository.save(car).getId();
        assertThat(carRepository.findById(id).isPresent(), is(true));
        assertThat(carRepository.findById(id).get(), is(car));
    }

    @Test
    void findAll() {
        assertThat(carRepository.findAll().isEmpty(), is(true));
    }

    @Test
    void update() {
        var engine = new Engine();
        var owner = new Owner();
        engineRepository.save(engine);
        ownerRepository.save(owner);
        Car car = new Car();
        car.setOwner(owner);
        car.setEngine(engine);
        int id = carRepository.save(car).getId();
        var owner1 = new Owner();
        owner1.setName("test");
        ownerRepository.save(owner1);
        car.setOwner(owner1);
        carRepository.update(car);
        assertThat(carRepository.findById(id).isPresent(), is(true));
        assertThat(carRepository.findById(id).get().getOwner().getName(), is("test"));
    }
}