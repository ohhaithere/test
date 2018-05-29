/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.config.resource.CarResourceImplTestConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.dto.CarDto;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.util.MockUtil;

/**
 * Car resource impl
 */
@ActiveProfiles("test")
@WebMvcTest(value = CarResourceImpl.class, secure = false)
@ContextConfiguration(classes = CarResourceImplTestConfig.class)
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
    public void testUpdateCar() throws Exception {
        when(carService.update(any(Car.class))).thenReturn(new Car());
        mockMvc.perform(put("/ui/car/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(MockUtil.validTemplateDto())))
                .andExpect(status().isOk());
        verify(carService).update(any(Car.class));
    }

    @Test
    public void testDeleteTemplate() throws Exception {
        doNothing().when(carService).delete(anyLong());
        mockMvc.perform(delete("/ui/car/123"))
                .andExpect(status().isNoContent());
        verify(carService).delete(anyLong());
    }

}