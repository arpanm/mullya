package com.mullya.app.service.impl;

import com.mullya.app.domain.CancellationDetails;
import com.mullya.app.repository.CancellationDetailsRepository;
import com.mullya.app.service.CancellationDetailsService;
import com.mullya.app.service.dto.CancellationDetailsDTO;
import com.mullya.app.service.mapper.CancellationDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CancellationDetails}.
 */
@Service
@Transactional
public class CancellationDetailsServiceImpl implements CancellationDetailsService {

    private final Logger log = LoggerFactory.getLogger(CancellationDetailsServiceImpl.class);

    private final CancellationDetailsRepository cancellationDetailsRepository;

    private final CancellationDetailsMapper cancellationDetailsMapper;

    public CancellationDetailsServiceImpl(
        CancellationDetailsRepository cancellationDetailsRepository,
        CancellationDetailsMapper cancellationDetailsMapper
    ) {
        this.cancellationDetailsRepository = cancellationDetailsRepository;
        this.cancellationDetailsMapper = cancellationDetailsMapper;
    }

    @Override
    public CancellationDetailsDTO save(CancellationDetailsDTO cancellationDetailsDTO) {
        log.debug("Request to save CancellationDetails : {}", cancellationDetailsDTO);
        CancellationDetails cancellationDetails = cancellationDetailsMapper.toEntity(cancellationDetailsDTO);
        cancellationDetails = cancellationDetailsRepository.save(cancellationDetails);
        return cancellationDetailsMapper.toDto(cancellationDetails);
    }

    @Override
    public Optional<CancellationDetailsDTO> partialUpdate(CancellationDetailsDTO cancellationDetailsDTO) {
        log.debug("Request to partially update CancellationDetails : {}", cancellationDetailsDTO);

        return cancellationDetailsRepository
            .findById(cancellationDetailsDTO.getId())
            .map(existingCancellationDetails -> {
                cancellationDetailsMapper.partialUpdate(existingCancellationDetails, cancellationDetailsDTO);

                return existingCancellationDetails;
            })
            .map(cancellationDetailsRepository::save)
            .map(cancellationDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CancellationDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CancellationDetails");
        return cancellationDetailsRepository.findAll(pageable).map(cancellationDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CancellationDetailsDTO> findOne(Long id) {
        log.debug("Request to get CancellationDetails : {}", id);
        return cancellationDetailsRepository.findById(id).map(cancellationDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CancellationDetails : {}", id);
        cancellationDetailsRepository.deleteById(id);
    }
}
