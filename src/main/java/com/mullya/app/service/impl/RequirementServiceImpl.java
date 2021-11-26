package com.mullya.app.service.impl;

import com.mullya.app.domain.Requirement;
import com.mullya.app.repository.RequirementRepository;
import com.mullya.app.service.RequirementService;
import com.mullya.app.service.dto.RequirementDTO;
import com.mullya.app.service.mapper.RequirementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Requirement}.
 */
@Service
@Transactional
public class RequirementServiceImpl implements RequirementService {

    private final Logger log = LoggerFactory.getLogger(RequirementServiceImpl.class);

    private final RequirementRepository requirementRepository;

    private final RequirementMapper requirementMapper;

    public RequirementServiceImpl(RequirementRepository requirementRepository, RequirementMapper requirementMapper) {
        this.requirementRepository = requirementRepository;
        this.requirementMapper = requirementMapper;
    }

    @Override
    public RequirementDTO save(RequirementDTO requirementDTO) {
        log.debug("Request to save Requirement : {}", requirementDTO);
        Requirement requirement = requirementMapper.toEntity(requirementDTO);
        requirement = requirementRepository.save(requirement);
        return requirementMapper.toDto(requirement);
    }

    @Override
    public Optional<RequirementDTO> partialUpdate(RequirementDTO requirementDTO) {
        log.debug("Request to partially update Requirement : {}", requirementDTO);

        return requirementRepository
            .findById(requirementDTO.getId())
            .map(existingRequirement -> {
                requirementMapper.partialUpdate(existingRequirement, requirementDTO);

                return existingRequirement;
            })
            .map(requirementRepository::save)
            .map(requirementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequirementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Requirements");
        return requirementRepository.findAll(pageable).map(requirementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequirementDTO> findOne(Long id) {
        log.debug("Request to get Requirement : {}", id);
        return requirementRepository.findById(id).map(requirementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Requirement : {}", id);
        requirementRepository.deleteById(id);
    }
}
