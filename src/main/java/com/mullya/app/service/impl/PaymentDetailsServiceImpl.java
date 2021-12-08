package com.mullya.app.service.impl;

import com.mullya.app.domain.PaymentDetails;
import com.mullya.app.repository.PaymentDetailsRepository;
import com.mullya.app.service.PaymentDetailsService;
import com.mullya.app.service.dto.PaymentDetailsDTO;
import com.mullya.app.service.mapper.PaymentDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PaymentDetails}.
 */
@Service
@Transactional
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    private final Logger log = LoggerFactory.getLogger(PaymentDetailsServiceImpl.class);

    private final PaymentDetailsRepository paymentDetailsRepository;

    private final PaymentDetailsMapper paymentDetailsMapper;

    public PaymentDetailsServiceImpl(PaymentDetailsRepository paymentDetailsRepository, PaymentDetailsMapper paymentDetailsMapper) {
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.paymentDetailsMapper = paymentDetailsMapper;
    }

    @Override
    public PaymentDetailsDTO save(PaymentDetailsDTO paymentDetailsDTO) {
        log.debug("Request to save PaymentDetails : {}", paymentDetailsDTO);
        PaymentDetails paymentDetails = paymentDetailsMapper.toEntity(paymentDetailsDTO);
        paymentDetails = paymentDetailsRepository.save(paymentDetails);
        return paymentDetailsMapper.toDto(paymentDetails);
    }

    @Override
    public Optional<PaymentDetailsDTO> partialUpdate(PaymentDetailsDTO paymentDetailsDTO) {
        log.debug("Request to partially update PaymentDetails : {}", paymentDetailsDTO);

        return paymentDetailsRepository
            .findById(paymentDetailsDTO.getId())
            .map(existingPaymentDetails -> {
                paymentDetailsMapper.partialUpdate(existingPaymentDetails, paymentDetailsDTO);

                return existingPaymentDetails;
            })
            .map(paymentDetailsRepository::save)
            .map(paymentDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentDetails");
        return paymentDetailsRepository.findAll(pageable).map(paymentDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentDetailsDTO> findOne(Long id) {
        log.debug("Request to get PaymentDetails : {}", id);
        return paymentDetailsRepository.findById(id).map(paymentDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentDetails : {}", id);
        paymentDetailsRepository.deleteById(id);
    }
}
