package com.mullya.app.service.impl;

import com.mullya.app.domain.OTP;
import com.mullya.app.domain.OTPAttempt;
import com.mullya.app.domain.enumeration.OtpType;
import com.mullya.app.repository.OTPAttemptRepository;
import com.mullya.app.security.InvalidOTPException;
import com.mullya.app.service.OTPAttemptService;
import com.mullya.app.service.OTPService;
import com.mullya.app.service.dto.OTPAttemptDTO;
import com.mullya.app.service.dto.OTPDTO;
import com.mullya.app.service.mapper.OTPAttemptMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import liquibase.util.StringUtil;
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

    private final OTPService oTPService;

    public OTPAttemptServiceImpl(OTPAttemptRepository oTPAttemptRepository, OTPAttemptMapper oTPAttemptMapper, OTPService oTPService) {
        this.oTPAttemptRepository = oTPAttemptRepository;
        this.oTPAttemptMapper = oTPAttemptMapper;
        this.oTPService = oTPService;
    }

    @Override
    public OTPAttemptDTO save(OTPAttemptDTO oTPAttemptDTO) {
        log.debug("Request to save OTPAttempt : {}", oTPAttemptDTO);
        OTPAttempt oTPAttempt = oTPAttemptMapper.toEntity(oTPAttemptDTO);
        oTPAttempt = oTPAttemptRepository.save(oTPAttempt);
        return oTPAttemptMapper.toDto(oTPAttempt);
    }

    @Override
    public OTPAttemptDTO saveAndValidate(OTPAttemptDTO oTPAttemptDTO) throws InvalidOTPException {
        log.debug("Request to save OTPAttempt : {}", oTPAttemptDTO);
        if (oTPAttemptDTO.getOtp() == null || oTPAttemptDTO.getOtp().getId() == null || oTPAttemptDTO.getOtp().getId() <= 0) {
            throw new InvalidOTPException(InvalidOTPException.IncorrectOtp);
        }
        Optional<OTPDTO> otp = oTPService.findOne(oTPAttemptDTO.getOtp().getId());
        if (!otp.isPresent()) {
            throw new InvalidOTPException(InvalidOTPException.IncorrectOtp);
        }
        log.debug("Retrive OTP : {}", otp.get());
        oTPAttemptDTO.setOtp(otp.get());
        OTPAttempt oTPAttempt = oTPAttemptMapper.toEntity(oTPAttemptDTO);
        oTPAttempt = oTPAttemptRepository.save(oTPAttempt);
        log.debug("oTPAttemptDTO: {}", oTPAttemptDTO);
        log.debug("oTPAttemptDTO.getOtp(): {}", oTPAttemptDTO.getOtp());
        log.debug("oTPAttemptDTO.getOtp().getIsActive(): {}", oTPAttemptDTO.getOtp().getIsActive());
        if (oTPAttemptDTO.getOtp() == null || !oTPAttemptDTO.getOtp().getIsActive()) {
            throw new InvalidOTPException(InvalidOTPException.Expired);
        } else if (oTPAttemptDTO.getOtp().getExpiryTime().isBefore(LocalDateTime.now())) {
            oTPService.expire(oTPAttempt.getOtp());
            throw new InvalidOTPException(InvalidOTPException.Expired);
        } else if (oTPAttemptDTO.getOtp().getType().equals(OtpType.Email)) {
            if (!oTPAttemptDTO.getOtp().getEmail().equals(oTPAttemptDTO.getEmail())) {
                throw new InvalidOTPException(InvalidOTPException.IncorrectEmail);
            }
        } else if (oTPAttemptDTO.getOtp().getType().equals(OtpType.Phone)) {
            if (!oTPAttemptDTO.getOtp().getPhone().equals(oTPAttemptDTO.getPhone())) {
                throw new InvalidOTPException(InvalidOTPException.IncorrectPhone);
            }
        } else {
            if (
                !oTPAttemptDTO.getOtp().getPhone().equals(oTPAttemptDTO.getPhone()) ||
                !oTPAttemptDTO.getOtp().getEmail().equals(oTPAttemptDTO.getEmail())
            ) {
                throw new InvalidOTPException(InvalidOTPException.IncorrectEmailOrPhone);
            }
        }

        if (!oTPAttemptDTO.getOtp().getOtpVal().equals(oTPAttemptDTO.getOtpVal().trim())) {
            log.error("entered {} needed {}", oTPAttemptDTO.getOtpVal(), oTPAttemptDTO.getOtp().getOtpVal());
            throw new InvalidOTPException(InvalidOTPException.IncorrectOtp);
        }
        oTPService.expire(oTPAttempt.getOtp());

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
