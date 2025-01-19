package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.RamMapper;
import com.pmarko09.product_service.model.dto.RamDto;
import com.pmarko09.product_service.model.entity.Ram;
import com.pmarko09.product_service.repository.RamRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class RamServiceTest {

    RamRepository ramRepository;
    RamMapper ramMapper;
    RamService ramService;

    @BeforeEach
    void setup() {
        this.ramRepository = Mockito.mock(RamRepository.class);
        this.ramMapper = Mappers.getMapper(RamMapper.class);
        this.ramService = new RamService(ramRepository, ramMapper);
    }

    @Test
    void getAllRams_DataCorrect_RamDtosReturned() {
        log.info("Start test: getAllRams_DataCorrect_RamDtosReturned");
        Ram ram1 = new Ram(1L, "1");
        Ram ram2 = new Ram(2L, "2");
        Pageable pageable = PageRequest.of(0, 10);

        when(ramRepository.findAllRams(pageable)).thenReturn(List.of(ram1, ram2));

        List<RamDto> result = ramService.getAllRams(pageable);

        assertNotNull(result);
        assertEquals(1L, result.getFirst().getId());
        assertEquals("1", result.getFirst().getSize());
        assertEquals(2L, result.get(1).getId());
        assertEquals("2", result.get(1).getSize());

        verify(ramRepository).findAllRams(pageable);
        log.info("Finish test: getAllRams_DataCorrect_RamDtosReturned");
    }

    @Test
    void addRam_DataCorrect_RamDtoReturned() {
        log.info("Start test: addRam_DataCorrect_RamDtoReturned");
        Ram ram1 = new Ram(1L, "1");

        when(ramRepository.findById(1L)).thenReturn(Optional.of(ram1));
        when(ramRepository.save(any(Ram.class))).thenReturn(ram1);

        RamDto result = ramService.addRam(ram1);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("1", result.getSize());

        verify(ramRepository).save(ram1);
        log.info("Finish test: addRam_DataCorrect_RamDtoReturned");
    }

    @Test
    void editRam_DataCorrect_RamDtoReturned() {
        log.info("Start test: editRam_DataCorrect_RamDtoReturned");
        Ram ram1 = new Ram(1L, "1");
        Ram editedRam1 = new Ram(1L, "111");

        when(ramRepository.findById(1L)).thenReturn(Optional.of(ram1));
        when(ramRepository.save(ram1)).thenReturn(editedRam1);

        RamDto result = ramService.editRam(1L, editedRam1);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("111", result.getSize());

        verify(ramRepository).save(ram1);
        log.info("Finish test: editRam_DataCorrect_RamDtoReturned");
    }

    @Test
    void deleteRam_DataCorrect_RamDtoReturned() {
        log.info("Start test: deleteRam_DataCorrect_RamDtoReturned");
        Ram ram1 = new Ram(1L, "1");

        when(ramRepository.findById(1L)).thenReturn(Optional.of(ram1));
        ramService.deleteRam(1L);

        verify(ramRepository).delete(ram1);
        log.info("Finish test: deleteRam_DataCorrect_RamDtoReturned");
    }
}
