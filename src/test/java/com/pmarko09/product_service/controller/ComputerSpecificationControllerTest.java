package com.pmarko09.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.product_service.model.dto.ComputerSpecificationDto;
import com.pmarko09.product_service.model.entity.ComputerSpecification;
import com.pmarko09.product_service.model.entity.Processor;
import com.pmarko09.product_service.model.entity.Ram;
import com.pmarko09.product_service.service.ComputerSpecificationService;
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
public class ComputerSpecificationControllerTest {

    @MockBean
    ComputerSpecificationService computerSpecificationService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllComputerSpec_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: getAllComputerSpec_DataCorrect_ReturnStatus200");
        ComputerSpecificationDto compSpec1 = new ComputerSpecificationDto();
        compSpec1.setId(1L);
        compSpec1.setProductId(11L);
        compSpec1.setRamSize(99);
        compSpec1.setProcessorName("ABC");
        ComputerSpecificationDto compSpec2 = new ComputerSpecificationDto();
        compSpec2.setId(2L);
        compSpec2.setProductId(12L);
        compSpec2.setRamSize(991);
        compSpec2.setProcessorName("ABCD");
        List<ComputerSpecificationDto> compSpecList = List.of(compSpec1, compSpec2);

        when(computerSpecificationService.getAllComputerSpecializations()).thenReturn(compSpecList);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/specialization/computer"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].productId").value(11L))
                .andExpect(jsonPath("$[0].ramSize").value(99))
                .andExpect(jsonPath("$[0].processorName").value("ABC"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].productId").value(12L))
                .andExpect(jsonPath("$[1].ramSize").value(991))
                .andExpect(jsonPath("$[1].processorName").value("ABCD"));

        verify(computerSpecificationService).getAllComputerSpecializations();
        log.info("Finish test: getAllComputerSpec_DataCorrect_ReturnStatus200");
    }

    @Test
    void getById_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: getById_DataCorrect_ReturnStatus200");
        ComputerSpecificationDto compSpec1 = new ComputerSpecificationDto();
        compSpec1.setId(1L);
        compSpec1.setProductId(11L);
        compSpec1.setRamSize(99);
        compSpec1.setProcessorName("ABC");

        when(computerSpecificationService.getById(1L)).thenReturn(compSpec1);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/specialization/computer/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productId").value(11L))
                .andExpect(jsonPath("$.ramSize").value(99))
                .andExpect(jsonPath("$.processorName").value("ABC"));

        verify(computerSpecificationService).getById(1L);
        log.info("Finish test: getById_DataCorrect_ReturnStatus200");
    }

    @Test
    void addComputerSpecification_DataCorrect_ReturnStatus201() throws Exception {
        log.info("Start test: addComputerSpecification_DataCorrect_ReturnStatus201");
        ComputerSpecificationDto compSpecDto1 = new ComputerSpecificationDto();
        compSpecDto1.setId(1L);
        compSpecDto1.setProductId(11L);
        compSpecDto1.setRamSize(99);
        compSpecDto1.setProcessorName("ABC");
        Ram ram = new Ram(11L, "512");
        Processor processor = new Processor(5L, "XX");
        ComputerSpecification comSpec = new ComputerSpecification();
        comSpec.setProcessor(processor);
        comSpec.setMemorySize(ram);
        comSpec.setId(1L);

        when(computerSpecificationService.addComputerSpecification(comSpec)).thenReturn(compSpecDto1);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/specialization/computer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(compSpecDto1)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productId").value(11L))
                .andExpect(jsonPath("$.ramSize").value(99))
                .andExpect(jsonPath("$.processorName").value("ABC"));

        verify(computerSpecificationService).addComputerSpecification(comSpec);
        log.info("Finish test: addComputerSpecification_DataCorrect_ReturnStatus201");
    }

    @Test
    void assignProcessor_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: assignProcessor_DataCorrect_ReturnStatus200");
        ComputerSpecificationDto compSpecDto1 = new ComputerSpecificationDto();
        compSpecDto1.setId(1L);
        compSpecDto1.setProductId(11L);
        compSpecDto1.setRamSize(99);
        compSpecDto1.setProcessorName("ABC");

        when(computerSpecificationService.assignProcessor(5L, 1L)).thenReturn(compSpecDto1);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/specialization/computer/1/processor/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productId").value(11L))
                .andExpect(jsonPath("$.ramSize").value(99))
                .andExpect(jsonPath("$.processorName").value("ABC"));

        verify(computerSpecificationService).assignProcessor(5L, 1L);
        log.info("Finish test: assignProcessor_DataCorrect_ReturnStatus200");
    }

    @Test
    void assignRam_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: assignRam_DataCorrect_ReturnStatus200");
        ComputerSpecificationDto compSpecDto1 = new ComputerSpecificationDto();
        compSpecDto1.setId(1L);
        compSpecDto1.setProductId(11L);
        compSpecDto1.setRamSize(99);
        compSpecDto1.setProcessorName("ABC");

        when(computerSpecificationService.assignRam(5L, 1L)).thenReturn(compSpecDto1);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/specialization/computer/1/ram/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productId").value(11L))
                .andExpect(jsonPath("$.ramSize").value(99))
                .andExpect(jsonPath("$.processorName").value("ABC"));

        verify(computerSpecificationService).assignRam(5L, 1L);
        log.info("Finish test: assignRam_DataCorrect_ReturnStatus200");
    }

    @Test
    void deleteComputerSpecialization_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: deleteComputerSpecialization_DataCorrect_ReturnStatus200");
        doNothing().when(computerSpecificationService).deleteComputerSpecification(1L);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/specialization/computer/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(computerSpecificationService).deleteComputerSpecification(1L);
        log.info("Finish test: deleteComputerSpecialization_DataCorrect_ReturnStatus200");
    }
}