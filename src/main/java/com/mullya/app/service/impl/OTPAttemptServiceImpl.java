package com.mullya.app.service.impl;

import com.mullya.app.domain.OTPAttempt;
import com.mullya.app.repository.OTPAttemptRepository;
import com.mullya.app.service.OTPAttemptService;
import com.mullya.app.service.dto.OTPAttemptDTO;
import com.mullya.app.service.mapper.OTPAttemptMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OTPAttempt}.
 */
@Service
@Transactional
public class OTPAttemptServiceImpl implements OTPAttemptService {

    private final Logger log = LoggerFactory.getLogger(OTPAttemptServiceImpl.class);

    private final OTPAttemptRepository oTPAttemptRepository;

    private final OTPAttemptMapper oTPAttemptMapper;

    public OTPAttemptServiceImpl(OTPAttemptRepository oTPAttemptRepository, OTPAttemptMapper oTPAttemptMapper) {
        this.oTPAttemptRepository = oTPAttemptRepository;
        this.oTPAttemptMapper = oTPAttemptMapper;
    }

    @Override
    public OTPAttemptDTO save(OTPAttemptDTO oTPAttemptDTO) {
        log.debug("Request to save OTPAttempt : {}", oTPAttemptDTO);
        OTPAttempt oTPAttempt = oTPAttemptMapper.toEntity(oTPAttemptDTO);
        oTPAttempt = oTPAttemptRepository.save(oTPAttempt);
        return oTPAttemptMapper.toDto(oTPAttempt);
    }

    @Override
    public Optional<OTPAttemptDTO> partialUpdate(OTPAttemptDTO oTPAttemptDTO) {
        log.debug("Request to partially update OTPAttempt : {}", oTPAttemptDTO);

        return oTPAttemptRepository
            .findById(oTPAttemptDTO.getId())
            .map(existingOTPAttempt -> {
                oTPAttemptMapper.partialUpdate(existingOTPAttempt, oTPAttemptDTO);

                return existingOTPAttempt;
            })
            .map(oTPAttemptRepository::save)
            .map(oTPAttemptMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OTPAttemptDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OTPAttempts");
        return oTPAttemptRepository.findAll(pageable).map(oTPAttemptMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OTPAttemptDTO> findOne(Long id) {
        log.debug("Request to get OTPAttempt : {}", id);
        return oTPAttemptRepository.findById(id).map(oTPAttemptMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OTPAttempt : {}", id);
        oTPAttemptRepository.deleteById(id);
    }
}
