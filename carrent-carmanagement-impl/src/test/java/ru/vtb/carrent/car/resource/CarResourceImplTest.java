/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.hamcrest.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.config.resource.CarResourceImplTestConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.dto.CarDto;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.util.JsonUtils;
import ru.vtb.carrent.car.util.MockUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Car resource impl
 */
@ActiveProfiles("test")
@WebMvcTest(value = CarResourceImpl.class, secure = false)
@ContextConfiguration(classes = CarResourceImplTestConfig.class)
@EnableSpringDataWebSupport
public class CarResourceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Autowired
    private CarService carService;

    private ObjectMapper jsonObjectMapper = new ObjectMapper();

    @BeforeMethod
    public void resetMocks() {
        Mockito.reset(carService);
    }

    @Test
    public void testCreateTemplate() throws Exception {
        CarDto validRequestDto = MockUtil.validTemplateDto();
        when(carService.create(any(Car.class)))
                .then((InvocationOnMock invocationOnMock) -> invocationOnMock.getArguments()[0]);
        mockMvc.perform(post("/ui/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(validRequestDto)))
                .andExpect(status().isCreated());
        verify(carService).create(any(Car.class));
    }

    @Test
    public void testGetCar() throws Exception {
        mockMvc.perform(get("/ui/car/123"))
                .andExpect(status().isOk());
        verify(carService).find(anyLong());
    }

    @Test
    public void testGetPaginatedCars() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/ui/car")
                .param("page", "1")
                .param("size", "1")
                .accept(MediaType.APPLICATION_JSON);
        when(carService.findPaginated(any(Pageable.class))).thenReturn(new PageImpl<>(Arrays.asList(new Car())));
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)));
        verify(carService).findPaginated(any(Pageable.class));
    }

    @Test
    public void testUpdateCar() throws Exception {
        when(carService.update(any(Car.class))).thenReturn(new Car());
        mockMvc.perform(put("/ui/car/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(MockUtil.validTemplateDto())))
                .andExpect(status().isOk());
        verify(carService).update(any(Car.class));
    }

    @Test
    public void testInRentCar() throws Exception {
        mockMvc.perform(post("/ui/car/123/in-rent?endDate=2018-05-12T00:00:00.000+0000"))
                .andExpect(status().isOk());
        verify(carService).inRent(anyLong(), any(Date.class));
    }

    @Test
    public void testInStock() throws Exception {
        mockMvc.perform(post("/ui/car/123/in-stock"))
                .andExpect(status().isOk());
        verify(carService).inStock(anyLong());
    }

    @Test
    public void testOnMaintenance() throws Exception {
        mockMvc.perform(post("/ui/car/123/on-maintenance"))
                .andExpect(status().isOk());
        verify(carService).onMaintenance(anyLong());
    }

    @Test
    public void testDropOut() throws Exception {
        mockMvc.perform(post("/ui/car/123/drop-out"))
                .andExpect(status().isOk());
        verify(carService).dropOut(anyLong());
    }

    @Test
    public void testDeleteTemplate() throws Exception {
        doNothing().when(carService).delete(anyLong());
        mockMvc.perform(delete("/ui/car/123"))
                .andExpect(status().isNoContent());
        verify(carService).delete(anyLong());
    }

    @Test
    public void testGetCarsWithFilter() throws Exception {
        when(carService.getByFilter(any(List.class), any(Pageable.class))).thenReturn(new PageImpl<>(Arrays.asList(new Car())));
        List<KeyValuePair> filters = new ArrayList<>(3);
        filters.add(new KeyValuePair("model", "Jeep"));
        filters.add(new KeyValuePair("nextStatus", "IN_STOCK"));
        String filter = Base64.encodeBase64String(JsonUtils.beanToJson(filters.toArray(new KeyValuePair[filters.size()])).getBytes());
        mockMvc.perform(get("/ui/car?page=0&size=1&filter=" + filter)).andExpect(status().isOk());
        verify(carService).getByFilter(any(List.class), any(Pageable.class));
    }

    @Test
    public void testGetCarsWithRangeFilter() throws Exception {
        when(carService.getByFilter(any(List.class), any(Pageable.class))).thenReturn(new PageImpl<>(Arrays.asList(new Car())));
        List<KeyValuePair> filters = new ArrayList<>(3);
        filters.add(new KeyValuePair("dateOfManufacture", Arrays.asList("2010-01-01", "2011-01-01")));
        String filter = Base64.encodeBase64String(JsonUtils.beanToJson(filters.toArray(new KeyValuePair[filters.size()])).getBytes());
        mockMvc.perform(get("/ui/car?page=0&size=1&filter=" + filter)).andExpect(status().isOk());
        verify(carService).getByFilter(any(List.class), any(Pageable.class));
    }

}