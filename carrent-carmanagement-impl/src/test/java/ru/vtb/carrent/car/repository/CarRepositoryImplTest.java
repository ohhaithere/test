package ru.vtb.carrent.car.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.config.JpaTestConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.status.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class to test car repository
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = JpaTestConfig.class)
@DataJpaTest
@Slf4j
public class CarRepositoryImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CarRepository repository;

    @BeforeMethod
    public void persistBaseData() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 10);
        em.persist(makeCar(1, c.getTime(), "bmv", Status.IN_RENT.name()));
        c.set(Calendar.DAY_OF_MONTH, 13);
        em.persist(makeCar(2, c.getTime(), "audi", Status.IN_STOCK.name()));
        c.set(Calendar.DAY_OF_MONTH, 16);
        em.persist(makeCar(3, c.getTime(), "bmw", Status.IN_STOCK.name()));
    }

    @Test
    public void testFindByFilter() throws Exception {
        try {

            System.out.println(em.find(Car.class, 1l));
            System.out.println(em.find(Car.class, 2l));
            System.out.println(em.find(Car.class, 3l));

            List<KeyValuePair> filters = new ArrayList<>(3);
            filters.add(new KeyValuePair("model", "audi"));

            Page<Car> page = repository.findByFilter(filters, new PageRequest(0, 1));
            Assert.assertNotNull(page);
            Assert.assertNotNull(page.getContent());
            Assert.assertEquals(page.getContent().size(), 1);
            Assert.assertEquals(page.getContent().get(0).getModel(), "audi");
            Assert.assertEquals(page.getContent().get(0).getCurrentStatus(), Status.IN_STOCK.name());
            System.out.println(page.getContent().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByRangeDateFilter() throws Exception {
        try {

            System.out.println(em.find(Car.class, 1l));
            System.out.println(em.find(Car.class, 2l));
            System.out.println(em.find(Car.class, 3l));

            List<KeyValuePair> filters = new ArrayList<>(3);
            filters.add(new KeyValuePair("dateOfManufacture", Arrays.asList("2018-06-11T21:00:00.000Z", "2018-06-18T21:00:00.000Z")));
            Page<Car> page = repository.findByFilter(filters, new PageRequest(0, 10));
            page.getContent().forEach(System.out::println);
            Assert.assertNotNull(page);
            Assert.assertNotNull(page.getContent());
            Assert.assertEquals(page.getContent().size(), 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testFindByRangeDateWithNull() throws Exception {
        try {

            System.out.println(em.find(Car.class, 1l));
            System.out.println(em.find(Car.class, 2l));
            System.out.println(em.find(Car.class, 3l));

            List<KeyValuePair> filters = new ArrayList<>(3);
            filters.add(new KeyValuePair("dateOfManufacture", Arrays.asList(null, "2018-06-14T21:00:00.000Z")));
            Page<Car> page = repository.findByFilter(filters, new PageRequest(0, 10));
            page.getContent().forEach(System.out::println);
            Assert.assertNotNull(page);
            Assert.assertNotNull(page.getContent());
            Assert.assertEquals(page.getContent().size(), 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Car makeCar(long id, Date date, String model, String currentStatus) {
        Car car = new Car();
        car.setDateOfManufacture(date);
        car.setModel(model);
        car.setCurrentStatus(currentStatus);
        return car;
    }

}
