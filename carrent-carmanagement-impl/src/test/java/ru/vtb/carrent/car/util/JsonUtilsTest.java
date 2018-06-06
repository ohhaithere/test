package ru.vtb.carrent.car.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.domain.entity.Car;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Documentation template
 *
 * @author Tsimafei_Dynikau
 */
public class JsonUtilsTest {

    private final Car testCar = new Car().setId(1L);
    private final String testCarJson = JsonUtils.beanToJsonSilent(testCar, "Error");

    @Test
    public void testJsonToBean() throws IOException {
        Car car = JsonUtils.jsonToBean(testCarJson, Car.class);
        assertEquals(testCar, car);
    }

    @Test
    public void testJsonToBean1() throws IOException {
        Car car = JsonUtils.jsonToBean(testCarJson, new TypeReference<Car>(){});
        assertEquals(testCar, car);
    }

    @Test
    public void testJsonToInstance() throws IOException {
        Car car = JsonUtils.jsonToInstance(testCarJson, new Car());
        assertEquals(testCar, car);
    }

    @Test
    public void testJsonToMap() throws IOException {
        Map map = JsonUtils.jsonToMap(testCarJson);
        System.out.println();
        assertEquals(testCar.getId().intValue(), map.get("id"));
    }

    @Test
    public void testBeanToJson() throws IOException {
        String actualCarJson = JsonUtils.beanToJson(testCar);
        assertEquals(testCarJson, actualCarJson);
    }
}