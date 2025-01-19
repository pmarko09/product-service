package com.pmarko09.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.product_service.model.dto.SmartphoneSpecificationDto;
import com.pmarko09.product_service.model.entity.SmartphoneSpecification;
import com.pmarko09.product_service.service.SmartphoneSpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class SmartphoneSpecificationControllerTest {

    @MockBean
    SmartphoneSpecificationService service;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllSmartphoneSpec_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: getAllSmartphoneSpec_DataCorrect_ReturnStatus200");
        SmartphoneSpecificationDto smartphoneSpecificationDto = new SmartphoneSpecificationDto();
        smartphoneSpecificationDto.setId(1L);
        smartphoneSpecificationDto.setColor("red");
        smartphoneSpecificationDto.setProductId(3L);
        smartphoneSpecificationDto.setBatteryCapacity(1000);
        List<SmartphoneSpecificationDto> list = List.of(smartphoneSpecificationDto);

        when(service.getAllSmartphones()).thenReturn(list);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/specialization/smartphone"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].color").value("red"))
                .andExpect(jsonPath("$[0].productId").value(3L))
                .andExpect(jsonPath("$[0].batteryCapacity").value(1000));

        verify(service).getAllSmartphones();
        log.info("Finish test: getAllSmartphoneSpec_DataCorrect_ReturnStatus200");
    }

    @Test
    void getById_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: getById_DataCorrect_ReturnStatus200");
        SmartphoneSpecificationDto smartphoneSpecificationDto = new SmartphoneSpecificationDto();
        smartphoneSpecificationDto.setId(1L);
        smartphoneSpecificationDto.setColor("red");
        smartphoneSpecificationDto.setProductId(3L);
        smartphoneSpecificationDto.setBatteryCapacity(1000);

        when(service.getById(1L)).thenReturn(smartphoneSpecificationDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/specialization/smartphone/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.color").value("red"))
                .andExpect(jsonPath("$.productId").value(3L))
                .andExpect(jsonPath("$.batteryCapacity").value(1000));

        verify(service).getById(1L);
        log.info("Finish test: getById_DataCorrect_ReturnStatus200");
    }

    @Test
    void addSmartphoneSpecification_DataCorrect_ReturnStatus201() throws Exception {
        log.info("Start test: addSmartphoneSpecification_DataCorrect_ReturnStatus201");
        SmartphoneSpecificationDto smartphoneSpecificationDto = new SmartphoneSpecificationDto();
        smartphoneSpecificationDto.setId(1L);
        smartphoneSpecificationDto.setColor("red");
        smartphoneSpecificationDto.setBatteryCapacity(1000);

        SmartphoneSpecification smartphoneSpecification = new SmartphoneSpecification();
        smartphoneSpecification.setId(1L);
        smartphoneSpecification.setColor("red");
        smartphoneSpecification.setBatteryCapacity(1000);

        when(service.addSmartphoneSpecification(smartphoneSpecification)).thenReturn(smartphoneSpecificationDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/specialization/smartphone")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(smartphoneSpecificationDto)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.color").value("red"))
                .andExpect(jsonPath("$.batteryCapacity").value(1000));

        verify(service).addSmartphoneSpecification(smartphoneSpecification);
        log.info("Finish test: addSmartphoneSpecification_DataCorrect_ReturnStatus201");
    }

    @Test
    void assignAccessory_DataCorrect_ReturnStatus201() throws Exception {
        log.info("Start test: assignAccessory_DataCorrect_ReturnStatus201");
        SmartphoneSpecificationDto smartphoneSpecificationDto = new SmartphoneSpecificationDto();
        smartphoneSpecificationDto.setId(1L);
        smartphoneSpecificationDto.setColor("red");
        smartphoneSpecificationDto.setBatteryCapacity(1000);

        when(service.addAccessory(1L, 2L)).thenReturn(smartphoneSpecificationDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/specialization/smartphone/1/accessory/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.color").value("red"))
                .andExpect(jsonPath("$.batteryCapacity").value(1000));

        verify(service).addAccessory(1L, 2L);
        log.info("Finish test: assignAccessory_DataCorrect_ReturnStatus201");
    }

    @Test
    void deleteSmartphoneSpecialization_DataCorrect_ReturnStatus201() throws Exception {
        log.info("Start test: deleteSmartphoneSpecialization_DataCorrect_ReturnStatus201");
        doNothing().when(service).deleteSmartphoneSpecification(1L);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/specialization/smartphone/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service).deleteSmartphoneSpecification(1L);
        log.info("Finish test: deleteSmartphoneSpecialization_DataCorrect_ReturnStatus201");
    }
}