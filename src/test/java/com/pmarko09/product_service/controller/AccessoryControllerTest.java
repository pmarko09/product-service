package com.pmarko09.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.product_service.model.dto.AccessoryDto;
import com.pmarko09.product_service.model.entity.Accessory;
import com.pmarko09.product_service.service.AccessoryService;
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
public class AccessoryControllerTest {

    @MockBean
    AccessoryService accessoryService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAll_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: getAll_DataCorrect_ReturnStatus200");
        AccessoryDto accessoryDto1 = new AccessoryDto();
        accessoryDto1.setId(1L);
        accessoryDto1.setName("A");
        AccessoryDto accessoryDto2 = new AccessoryDto();
        accessoryDto2.setId(2L);
        accessoryDto2.setName("B");
        List<AccessoryDto> accessoryDtoList = List.of(accessoryDto1, accessoryDto2);
        Pageable pageable = PageRequest.of(0, 2);

        when(accessoryService.getAllAccessories(pageable)).thenReturn(accessoryDtoList);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/accessories?page=0&size=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("B"));

        verify(accessoryService).getAllAccessories(pageable);
        log.info("Finish test: getAll_DataCorrect_ReturnStatus200");
    }

    @Test
    void findById_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: findById_DataCorrect_ReturnStatus200");
        AccessoryDto accessoryDto1 = new AccessoryDto();
        accessoryDto1.setId(1L);
        accessoryDto1.setName("A");

        when(accessoryService.findAccessoryById(1L)).thenReturn(accessoryDto1);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/accessories/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("A"));

        verify(accessoryService).findAccessoryById(1L);
        log.info("Finish test: findById_DataCorrect_ReturnStatus200");
    }

    @Test
    void add_DataCorrect_ReturnStatus201() throws Exception {
        log.info("Start test: add_DataCorrect_ReturnStatus201");
        Accessory accessory = new Accessory();
        accessory.setId(1L);
        accessory.setName("A");
        AccessoryDto accessoryDto1 = new AccessoryDto();
        accessoryDto1.setId(1L);
        accessoryDto1.setName("A");

        when(accessoryService.addAccessory(accessory)).thenReturn(accessoryDto1);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/accessories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(accessory)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("A"));

        verify(accessoryService).addAccessory(accessory);
        log.info("Finish test: add_DataCorrect_ReturnStatus201");
    }

    @Test
    void edit_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: edit_DataCorrect_ReturnStatus200");
        Accessory editedAccessory = new Accessory();
        editedAccessory.setId(1L);
        editedAccessory.setName("AX");
        AccessoryDto accessoryDto1 = new AccessoryDto();
        accessoryDto1.setId(1L);
        accessoryDto1.setName("AX");

        when(accessoryService.editAccessory(1L, editedAccessory)).thenReturn(accessoryDto1);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/accessories/edit/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(editedAccessory)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("AX"));

        verify(accessoryService).editAccessory(1L, editedAccessory);
        log.info("Finish test: edit_DataCorrect_ReturnStatus200");
    }

    @Test
    void delete_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: delete_DataCorrect_ReturnStatus200");
        doNothing().when(accessoryService).deleteAccessory(1L);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/accessories/delete/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(accessoryService).deleteAccessory(1L);
        log.info("Finish test: delete_DataCorrect_ReturnStatus200");
    }
}