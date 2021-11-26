package com.mullya.app.service.impl;

import com.mullya.app.domain.OTP;
import com.mullya.app.repository.OTPRepository;
import com.mullya.app.service.OTPService;
import com.mullya.app.service.dto.OTPDTO;
import com.mullya.app.service.mapper.OTPMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OTP}.
 */
@Service
@Transactional
public class OTPServiceImpl implements OTPService {

    private final Logger log = LoggerFactory.getLogger(OTPServiceImpl.class);

    private final OTPRepository oTPRepository;

    private final OTPMapper oTPMapper;

    public OTPServiceImpl(OTPRepository oTPRepository, OTPMapper oTPMapper) {
        this.oTPRepository = oTPRepository;
        this.oTPMapper = oTPMapper;
    }

    @Override
    public OTPDTO save(OTPDTO oTPDTO) {
        log.debug("Request to save OTP : {}", oTPDTO);
        OTP oTP = oTPMapper.toEntity(oTPDTO);
        oTP = oTPRepository.save(oTP);
        return oTPMapper.toDto(oTP);
    }

    @Override
    public Optional<OTPDTO> partialUpdate(OTPDTO oTPDTO) {
        log.debug("Request to partially update OTP : {}", oTPDTO);

        return oTPRepository
            .findById(oTPDTO.getId())
            .map(existingOTP -> {
                oTPMapper.partialUpdate(existingOTP, oTPDTO);

                return existingOTP;
            })
            .map(oTPRepository::save)
            .map(oTPMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OTPDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OTPS");
        return oTPRepository.findAll(pageable).map(oTPMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OTPDTO> findOne(Long id) {
        log.debug("Request to get OTP : {}", id);
        return oTPRepository.findById(id).map(oTPMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OTP : {}", id);
        oTPRepository.deleteById(id);
    }
}
