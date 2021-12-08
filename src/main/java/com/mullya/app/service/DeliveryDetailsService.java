package com.mullya.app.service;

import com.mullya.app.service.dto.DeliveryDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mullya.app.domain.DeliveryDetails}.
 */
public interface DeliveryDetailsService {
    /**
     * Save a deliveryDetails.
     *
     * @param deliveryDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    DeliveryDetailsDTO save(DeliveryDetailsDTO deliveryDetailsDTO);

    /**
     * Partially updates a deliveryDetails.
     *
     * @param deliveryDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DeliveryDetailsDTO> partialUpdate(DeliveryDetailsDTO deliveryDetailsDTO);

    /**
     * Get all the deliveryDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeliveryDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" deliveryDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeliveryDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" deliveryDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
