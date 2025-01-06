package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.ProcessorMapper;
import com.pmarko09.product_service.model.dto.ProcessorDto;
import com.pmarko09.product_service.model.entity.Processor;
import com.pmarko09.product_service.repository.ProcessorRepository;
import com.pmarko09.product_service.validation.ProcessorValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessorService {

    private final ProcessorRepository processorRepository;
    private final ProcessorMapper processorMapper;

    public List<ProcessorDto> getAllProcessors(Pageable pageable) {
        return processorRepository.findAllProcessors(pageable).stream()
                .map(processorMapper::toDto)
                .toList();
    }

    @Transactional
    public ProcessorDto addProcessor(Processor processor) {
        ProcessorValidation.processorNameCheck(processorRepository, processor.getName());
        return processorMapper.toDto(processorRepository.save(processor));
    }

    @Transactional
    public ProcessorDto editProcessor(Long id, Processor newProcessor) {
        Processor processor = ProcessorValidation.existCheck(processorRepository, id);
        Processor.update(processor, newProcessor);
        return processorMapper.toDto(processorRepository.save(processor));
    }

    @Transactional
    public void deleteProcessor(Long id) {
        Processor processor = ProcessorValidation.existCheck(processorRepository, id);
        processorRepository.delete(processor);
    }
}
