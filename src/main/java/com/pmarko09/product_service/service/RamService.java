package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.RamMapper;
import com.pmarko09.product_service.model.dto.RamDto;
import com.pmarko09.product_service.model.entity.Ram;
import com.pmarko09.product_service.repository.RamRepository;
import com.pmarko09.product_service.validation.RamValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RamService {

    private final RamRepository ramRepository;
    private final RamMapper ramMapper;

    public List<RamDto> getAllRams(Pageable pageable) {
        return ramRepository.findAllRams(pageable).stream()
                .map(ramMapper::toDto)
                .toList();
    }

    @Transactional
    public RamDto addRam(Ram newRam) {
        RamValidation.ramSizeCheck(ramRepository, newRam.getSize());
        return ramMapper.toDto(ramRepository.save(newRam));
    }

    @Transactional
    public RamDto editRam(Long id, Ram newRam) {
        Ram ram = RamValidation.existCheck(ramRepository, id);
        Ram.update(ram, newRam);
        return ramMapper.toDto(ramRepository.save(ram));
    }

    @Transactional
    public void deleteRam(Long id) {
        Ram ram = RamValidation.existCheck(ramRepository, id);
        ramRepository.delete(ram);
    }
}