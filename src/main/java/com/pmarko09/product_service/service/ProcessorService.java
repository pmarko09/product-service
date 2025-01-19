package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.ProcessorMapper;
import com.pmarko09.product_service.model.dto.ProcessorDto;
import com.pmarko09.product_service.model.entity.Processor;
import com.pmarko09.product_service.repository.ProcessorRepository;
import com.pmarko09.product_service.validation.ProcessorValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessorService {

    private final ProcessorRepository processorRepository;
    private final ProcessorMapper processorMapper;

    public List<ProcessorDto> getAllProcessors(Pageable pageable) {
        log.info("ProcessorService: fetching all processors");
        return processorRepository.findAllProcessors(pageable).stream()
                .map(processorMapper::toDto)
                .toList();
    }

    @Transactional
    public ProcessorDto addProcessor(Processor processor) {
        log.info("ProcessorService: adding processor with details: {}", processor);
        ProcessorValidation.processorNameCheck(processorRepository, processor.getName());
        return processorMapper.toDto(processorRepository.save(processor));
    }

    @Transactional
    public ProcessorDto editProcessor(Long id, Processor newProcessor) {
        log.info("ProcessorService: editing processor with ID: {} with details: {}", id, newProcessor);
        Processor processor = ProcessorValidation.existCheck(processorRepository, id);
        Processor.update(processor, newProcessor);
        return processorMapper.toDto(processorRepository.save(processor));
    }

    @Transactional
    public void deleteProcessor(Long id) {
        log.info("ProcessorService: deleting processor with ID: {}", id);
        Processor processor = ProcessorValidation.existCheck(processorRepository, id);
        processorRepository.delete(processor);
    }
}