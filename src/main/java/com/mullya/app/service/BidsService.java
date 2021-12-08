package com.mullya.app.service;

import com.mullya.app.service.dto.BidsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mullya.app.domain.Bids}.
 */
public interface BidsService {
    /**
     * Save a bids.
     *
     * @param bidsDTO the entity to save.
     * @return the persisted entity.
     */
    BidsDTO save(BidsDTO bidsDTO);

    /**
     * Partially updates a bids.
     *
     * @param bidsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BidsDTO> partialUpdate(BidsDTO bidsDTO);

    /**
     * Get all the bids.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BidsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bids.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BidsDTO> findOne(Long id);

    /**
     * Delete the "id" bids.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
