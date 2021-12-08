package com.mullya.app.service.impl;

import com.mullya.app.domain.Hub;
import com.mullya.app.repository.HubRepository;
import com.mullya.app.service.HubService;
import com.mullya.app.service.dto.HubDTO;
import com.mullya.app.service.mapper.HubMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Hub}.
 */
@Service
@Transactional
public class HubServiceImpl implements HubService {

    private final Logger log = LoggerFactory.getLogger(HubServiceImpl.class);

    private final HubRepository hubRepository;

    private final HubMapper hubMapper;

    public HubServiceImpl(HubRepository hubRepository, HubMapper hubMapper) {
        this.hubRepository = hubRepository;
        this.hubMapper = hubMapper;
    }

    @Override
    public HubDTO save(HubDTO hubDTO) {
        log.debug("Request to save Hub : {}", hubDTO);
        Hub hub = hubMapper.toEntity(hubDTO);
        hub = hubRepository.save(hub);
        return hubMapper.toDto(hub);
    }

    @Override
    public Optional<HubDTO> partialUpdate(HubDTO hubDTO) {
        log.debug("Request to partially update Hub : {}", hubDTO);

        return hubRepository
            .findById(hubDTO.getId())
            .map(existingHub -> {
                hubMapper.partialUpdate(existingHub, hubDTO);

                return existingHub;
            })
            .map(hubRepository::save)
            .map(hubMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HubDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Hubs");
        return hubRepository.findAll(pageable).map(hubMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HubDTO> findOne(Long id) {
        log.debug("Request to get Hub : {}", id);
        return hubRepository.findById(id).map(hubMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hub : {}", id);
        hubRepository.deleteById(id);
    }
}
