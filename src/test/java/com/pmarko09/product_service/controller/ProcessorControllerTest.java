package com.pmarko09.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.product_service.model.dto.ProcessorDto;
import com.pmarko09.product_service.model.entity.Processor;
import com.pmarko09.product_service.service.ProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class ProcessorControllerTest {

    @MockBean
    ProcessorService processorService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllProcessors_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: getAllProcessors_DataCorrect_ReturnStatus200");
        ProcessorDto processorDto1 = new ProcessorDto(1L, "A");
        ProcessorDto processorDto2 = new ProcessorDto(2L, "B");
        List<ProcessorDto> processorDtoList = List.of(processorDto1, processorDto2);
        Pageable pageable = PageRequest.of(0, 10);

        when(processorService.getAllProcessors(pageable)).thenReturn(processorDtoList);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/processors?page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("B"));

        verify(processorService).getAllProcessors(pageable);
        log.info("Finish test: getAllProcessors_DataCorrect_ReturnStatus200");
    }

    @Test
    void addProcessor_DataCorrect_ReturnStatus201() throws Exception {
        log.info("Start test: addProcessor_DataCorrect_ReturnStatus201");
        ProcessorDto processorDto1 = new ProcessorDto(1L, "A");
        Processor processor = new Processor(1L, "A");

        when(processorService.addProcessor(processor)).thenReturn(processorDto1);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/processors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(processor)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("A"));

        verify(processorService).addProcessor(processor);
        log.info("Finish test: addProcessor_DataCorrect_ReturnStatus201");
    }

    @Test
    void editProcessor_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: editProcessor_DataCorrect_ReturnStatus200");
        ProcessorDto processorDto1 = new ProcessorDto(1L, "A");
        Processor newProcessor = new Processor(1L, "A");

        when(processorService.editProcessor(1L, newProcessor)).thenReturn(processorDto1);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/processors/edit/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(processorDto1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("A"));

        verify(processorService).editProcessor(1L, newProcessor);
        log.info("Finish test: editProcessor_DataCorrect_ReturnStatus200");
    }

    @Test
    void deleteProcessor_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: deleteProcessor_DataCorrect_ReturnStatus200");
        doNothing().when(processorService).deleteProcessor(1L);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/processors/delete/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(processorService).deleteProcessor(1L);
        log.info("Finish test: deleteProcessor_DataCorrect_ReturnStatus200");
    }
}
