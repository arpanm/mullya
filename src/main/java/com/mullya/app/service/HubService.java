package com.mullya.app.service;

import com.mullya.app.service.dto.HubDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mullya.app.domain.Hub}.
 */
public interface HubService {
    /**
     * Save a hub.
     *
     * @param hubDTO the entity to save.
     * @return the persisted entity.
     */
    HubDTO save(HubDTO hubDTO);

    /**
     * Partially updates a hub.
     *
     * @param hubDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HubDTO> partialUpdate(HubDTO hubDTO);

    /**
     * Get all the hubs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HubDTO> findAll(Pageable pageable);

    /**
     * Get the "id" hub.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HubDTO> findOne(Long id);

    /**
     * Delete the "id" hub.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
