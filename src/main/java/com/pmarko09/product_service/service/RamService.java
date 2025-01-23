package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.RamMapper;
import com.pmarko09.product_service.model.dto.RamDto;
import com.pmarko09.product_service.model.entity.Ram;
import com.pmarko09.product_service.repository.RamRepository;
import com.pmarko09.product_service.validation.RamValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for RAM entities.
 * Handles operations related to RAM management, such as retrieving, adding, editing, and deleting RAM modules.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class RamService {

    private final RamRepository ramRepository;
    private final RamMapper ramMapper;

    /**
     * Retrieves all RAM modules with pagination.
     *
     * @param pageable Pagination information.
     * @return A list of RamDto objects.
     */
    public List<RamDto> getAllRams(Pageable pageable) {
        log.info("RamService: fetching all rams");
        return ramRepository.findAllRams(pageable).stream()
                .map(ramMapper::toDto)
                .toList();
    }

    /**
     * Adds a new RAM module.
     *
     * @param newRam The new RAM module to add.
     * @return A RamDto object of the added RAM module.
     */
    @Transactional
    public RamDto addRam(Ram newRam) {
        log.info("RamService: adding ram with details: {}", newRam);
        RamValidation.ramSizeCheck(ramRepository, newRam.getSize());
        return ramMapper.toDto(ramRepository.save(newRam));
    }

    /**
     * Edits an existing RAM module.
     *
     * @param id     The ID of the RAM module to edit.
     * @param newRam The updated RAM module data.
     * @return A RamDto object of the edited RAM module.
     */
    @Transactional
    public RamDto editRam(Long id, Ram newRam) {
        log.info("RamService: editing all ram with ID: {} and details: {}", id, newRam);
        Ram ram = RamValidation.existCheck(ramRepository, id);
        Ram.update(ram, newRam);
        return ramMapper.toDto(ramRepository.save(ram));
    }

    /**
     * Deletes a RAM module by its ID.
     *
     * @param id The ID of the RAM module to deleted.
     */
    @Transactional
    public void deleteRam(Long id) {
        log.info("RamService: deleting all ram with ID: {}", id);
        Ram ram = RamValidation.existCheck(ramRepository, id);
        ramRepository.delete(ram);
    }
}