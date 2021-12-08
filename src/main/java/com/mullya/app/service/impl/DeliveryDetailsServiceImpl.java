package com.mullya.app.service.impl;

import com.mullya.app.domain.DeliveryDetails;
import com.mullya.app.repository.DeliveryDetailsRepository;
import com.mullya.app.service.DeliveryDetailsService;
import com.mullya.app.service.dto.DeliveryDetailsDTO;
import com.mullya.app.service.mapper.DeliveryDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DeliveryDetails}.
 */
@Service
@Transactional
public class DeliveryDetailsServiceImpl implements DeliveryDetailsService {

    private final Logger log = LoggerFactory.getLogger(DeliveryDetailsServiceImpl.class);

    private final DeliveryDetailsRepository deliveryDetailsRepository;

    private final DeliveryDetailsMapper deliveryDetailsMapper;

    public DeliveryDetailsServiceImpl(DeliveryDetailsRepository deliveryDetailsRepository, DeliveryDetailsMapper deliveryDetailsMapper) {
        this.deliveryDetailsRepository = deliveryDetailsRepository;
        this.deliveryDetailsMapper = deliveryDetailsMapper;
    }

    @Override
    public DeliveryDetailsDTO save(DeliveryDetailsDTO deliveryDetailsDTO) {
        log.debug("Request to save DeliveryDetails : {}", deliveryDetailsDTO);
        DeliveryDetails deliveryDetails = deliveryDetailsMapper.toEntity(deliveryDetailsDTO);
        deliveryDetails = deliveryDetailsRepository.save(deliveryDetails);
        return deliveryDetailsMapper.toDto(deliveryDetails);
    }

    @Override
    public Optional<DeliveryDetailsDTO> partialUpdate(DeliveryDetailsDTO deliveryDetailsDTO) {
        log.debug("Request to partially update DeliveryDetails : {}", deliveryDetailsDTO);

        return deliveryDetailsRepository
            .findById(deliveryDetailsDTO.getId())
            .map(existingDeliveryDetails -> {
                deliveryDetailsMapper.partialUpdate(existingDeliveryDetails, deliveryDetailsDTO);

                return existingDeliveryDetails;
            })
            .map(deliveryDetailsRepository::save)
            .map(deliveryDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeliveryDetails");
        return deliveryDetailsRepository.findAll(pageable).map(deliveryDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeliveryDetailsDTO> findOne(Long id) {
        log.debug("Request to get DeliveryDetails : {}", id);
        return deliveryDetailsRepository.findById(id).map(deliveryDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeliveryDetails : {}", id);
        deliveryDetailsRepository.deleteById(id);
    }
}
