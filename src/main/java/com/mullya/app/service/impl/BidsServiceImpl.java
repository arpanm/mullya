package com.mullya.app.service.impl;

import com.mullya.app.domain.Bids;
import com.mullya.app.repository.BidsRepository;
import com.mullya.app.service.BidsService;
import com.mullya.app.service.dto.BidsDTO;
import com.mullya.app.service.mapper.BidsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bids}.
 */
@Service
@Transactional
public class BidsServiceImpl implements BidsService {

    private final Logger log = LoggerFactory.getLogger(BidsServiceImpl.class);

    private final BidsRepository bidsRepository;

    private final BidsMapper bidsMapper;

    public BidsServiceImpl(BidsRepository bidsRepository, BidsMapper bidsMapper) {
        this.bidsRepository = bidsRepository;
        this.bidsMapper = bidsMapper;
    }

    @Override
    public BidsDTO save(BidsDTO bidsDTO) {
        log.debug("Request to save Bids : {}", bidsDTO);
        Bids bids = bidsMapper.toEntity(bidsDTO);
        bids = bidsRepository.save(bids);
        return bidsMapper.toDto(bids);
    }

    @Override
    public Optional<BidsDTO> partialUpdate(BidsDTO bidsDTO) {
        log.debug("Request to partially update Bids : {}", bidsDTO);

        return bidsRepository
            .findById(bidsDTO.getId())
            .map(existingBids -> {
                bidsMapper.partialUpdate(existingBids, bidsDTO);

                return existingBids;
            })
            .map(bidsRepository::save)
            .map(bidsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BidsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bids");
        return bidsRepository.findAll(pageable).map(bidsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BidsDTO> findOne(Long id) {
        log.debug("Request to get Bids : {}", id);
        return bidsRepository.findById(id).map(bidsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bids : {}", id);
        bidsRepository.deleteById(id);
    }
}
