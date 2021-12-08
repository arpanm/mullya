package com.mullya.app.service;

import com.mullya.app.service.dto.PaymentDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mullya.app.domain.PaymentDetails}.
 */
public interface PaymentDetailsService {
    /**
     * Save a paymentDetails.
     *
     * @param paymentDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentDetailsDTO save(PaymentDetailsDTO paymentDetailsDTO);

    /**
     * Partially updates a paymentDetails.
     *
     * @param paymentDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentDetailsDTO> partialUpdate(PaymentDetailsDTO paymentDetailsDTO);

    /**
     * Get all the paymentDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" paymentDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" paymentDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
