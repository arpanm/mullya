package com.mullya.app.service.impl;

import com.mullya.app.domain.BiddingDetails;
import com.mullya.app.repository.BiddingDetailsRepository;
import com.mullya.app.service.BiddingDetailsService;
import com.mullya.app.service.dto.BiddingDetailsDTO;
import com.mullya.app.service.mapper.BiddingDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BiddingDetails}.
 */
@Service
@Transactional
public class BiddingDetailsServiceImpl implements BiddingDetailsService {

    private final Logger log = LoggerFactory.getLogger(BiddingDetailsServiceImpl.class);

    private final BiddingDetailsRepository biddingDetailsRepository;

    private final BiddingDetailsMapper biddingDetailsMapper;

    public BiddingDetailsServiceImpl(BiddingDetailsRepository biddingDetailsRepository, BiddingDetailsMapper biddingDetailsMapper) {
        this.biddingDetailsRepository = biddingDetailsRepository;
        this.biddingDetailsMapper = biddingDetailsMapper;
    }

    @Override
    public BiddingDetailsDTO save(BiddingDetailsDTO biddingDetailsDTO) {
        log.debug("Request to save BiddingDetails : {}", biddingDetailsDTO);
        BiddingDetails biddingDetails = biddingDetailsMapper.toEntity(biddingDetailsDTO);
        biddingDetails = biddingDetailsRepository.save(biddingDetails);
        return biddingDetailsMapper.toDto(biddingDetails);
    }

    @Override
    public Optional<BiddingDetailsDTO> partialUpdate(BiddingDetailsDTO biddingDetailsDTO) {
        log.debug("Request to partially update BiddingDetails : {}", biddingDetailsDTO);

        return biddingDetailsRepository
            .findById(biddingDetailsDTO.getId())
            .map(existingBiddingDetails -> {
                biddingDetailsMapper.partialUpdate(existingBiddingDetails, biddingDetailsDTO);

                return existingBiddingDetails;
            })
            .map(biddingDetailsRepository::save)
            .map(biddingDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BiddingDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BiddingDetails");
        return biddingDetailsRepository.findAll(pageable).map(biddingDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BiddingDetailsDTO> findOne(Long id) {
        log.debug("Request to get BiddingDetails : {}", id);
        return biddingDetailsRepository.findById(id).map(biddingDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BiddingDetails : {}", id);
        biddingDetailsRepository.deleteById(id);
    }
}
