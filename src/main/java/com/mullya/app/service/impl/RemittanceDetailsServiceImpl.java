package com.mullya.app.service.impl;

import com.mullya.app.domain.RemittanceDetails;
import com.mullya.app.repository.RemittanceDetailsRepository;
import com.mullya.app.service.RemittanceDetailsService;
import com.mullya.app.service.dto.RemittanceDetailsDTO;
import com.mullya.app.service.mapper.RemittanceDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RemittanceDetails}.
 */
@Service
@Transactional
public class RemittanceDetailsServiceImpl implements RemittanceDetailsService {

    private final Logger log = LoggerFactory.getLogger(RemittanceDetailsServiceImpl.class);

    private final RemittanceDetailsRepository remittanceDetailsRepository;

    private final RemittanceDetailsMapper remittanceDetailsMapper;

    public RemittanceDetailsServiceImpl(
        RemittanceDetailsRepository remittanceDetailsRepository,
        RemittanceDetailsMapper remittanceDetailsMapper
    ) {
        this.remittanceDetailsRepository = remittanceDetailsRepository;
        this.remittanceDetailsMapper = remittanceDetailsMapper;
    }

    @Override
    public RemittanceDetailsDTO save(RemittanceDetailsDTO remittanceDetailsDTO) {
        log.debug("Request to save RemittanceDetails : {}", remittanceDetailsDTO);
        RemittanceDetails remittanceDetails = remittanceDetailsMapper.toEntity(remittanceDetailsDTO);
        remittanceDetails = remittanceDetailsRepository.save(remittanceDetails);
        return remittanceDetailsMapper.toDto(remittanceDetails);
    }

    @Override
    public Optional<RemittanceDetailsDTO> partialUpdate(RemittanceDetailsDTO remittanceDetailsDTO) {
        log.debug("Request to partially update RemittanceDetails : {}", remittanceDetailsDTO);

        return remittanceDetailsRepository
            .findById(remittanceDetailsDTO.getId())
            .map(existingRemittanceDetails -> {
                remittanceDetailsMapper.partialUpdate(existingRemittanceDetails, remittanceDetailsDTO);

                return existingRemittanceDetails;
            })
            .map(remittanceDetailsRepository::save)
            .map(remittanceDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RemittanceDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RemittanceDetails");
        return remittanceDetailsRepository.findAll(pageable).map(remittanceDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RemittanceDetailsDTO> findOne(Long id) {
        log.debug("Request to get RemittanceDetails : {}", id);
        return remittanceDetailsRepository.findById(id).map(remittanceDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RemittanceDetails : {}", id);
        remittanceDetailsRepository.deleteById(id);
    }
}
