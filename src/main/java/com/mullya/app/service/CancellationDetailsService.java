package com.mullya.app.service;

import com.mullya.app.service.dto.CancellationDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mullya.app.domain.CancellationDetails}.
 */
public interface CancellationDetailsService {
    /**
     * Save a cancellationDetails.
     *
     * @param cancellationDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    CancellationDetailsDTO save(CancellationDetailsDTO cancellationDetailsDTO);

    /**
     * Partially updates a cancellationDetails.
     *
     * @param cancellationDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CancellationDetailsDTO> partialUpdate(CancellationDetailsDTO cancellationDetailsDTO);

    /**
     * Get all the cancellationDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CancellationDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cancellationDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CancellationDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" cancellationDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
