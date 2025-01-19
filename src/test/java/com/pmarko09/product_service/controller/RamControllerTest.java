package com.pmarko09.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.product_service.model.dto.RamDto;
import com.pmarko09.product_service.model.entity.Ram;
import com.pmarko09.product_service.service.RamService;
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
public class RamControllerTest {

    @MockBean
    RamService ramService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAll_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: getAll_DataCorrect_ReturnStatus200");
        RamDto ramDto = new RamDto(1L, "100");
        RamDto ramDto2 = new RamDto(2L, "200");
        List<RamDto> list = List.of(ramDto, ramDto2);
        Pageable pageable = PageRequest.of(0, 10);

        when(ramService.getAllRams(pageable)).thenReturn(list);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/rams?page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].size").value("100"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].size").value("200"));

        verify(ramService).getAllRams(pageable);
        log.info("Finish test: getAll_DataCorrect_ReturnStatus200");
    }

    @Test
    void addRam_DataCorrect_ReturnStatus201() throws Exception {
        log.info("Start test: addRam_DataCorrect_ReturnStatus201");
        RamDto ramDto = new RamDto(1L, "100");
        Ram ram = new Ram(1L, "100");

        when(ramService.addRam(ram)).thenReturn(ramDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/rams")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ramDto)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.size").value("100"));

        verify(ramService).addRam(ram);
        log.info("Finish test: addRam_DataCorrect_ReturnStatus201");
    }

    @Test
    void editRam_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: editRam_DataCorrect_ReturnStatus200");
        RamDto ramDto = new RamDto(1L, "100");
        Ram ram = new Ram(1L, "100");

        when(ramService.editRam(1L, ram)).thenReturn(ramDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/rams/edit/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ramDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.size").value("100"));

        verify(ramService).editRam(1L, ram);
        log.info("Finish test: editRam_DataCorrect_ReturnStatus200");
    }

    @Test
    void deleteRam_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: deleteRam_DataCorrect_ReturnStatus200");
        doNothing().when(ramService).deleteRam(1L);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/rams/delete/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(ramService).deleteRam(1L);
        log.info("Finish test: deleteRam_DataCorrect_ReturnStatus200");
    }
}
