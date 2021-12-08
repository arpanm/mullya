package com.mullya.app.service;

import com.mullya.app.service.dto.BiddingDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mullya.app.domain.BiddingDetails}.
 */
public interface BiddingDetailsService {
    /**
     * Save a biddingDetails.
     *
     * @param biddingDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    BiddingDetailsDTO save(BiddingDetailsDTO biddingDetailsDTO);

    /**
     * Partially updates a biddingDetails.
     *
     * @param biddingDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BiddingDetailsDTO> partialUpdate(BiddingDetailsDTO biddingDetailsDTO);

    /**
     * Get all the biddingDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BiddingDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" biddingDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BiddingDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" biddingDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
