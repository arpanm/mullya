package com.mullya.app.service;

import com.mullya.app.domain.OTP;
import com.mullya.app.service.dto.OTPDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mullya.app.domain.OTP}.
 */
public interface OTPService {
    /**
     * Save a oTP.
     *
     * @param oTPDTO the entity to save.
     * @return the persisted entity.
     */
    OTPDTO save(OTPDTO oTPDTO);

    /**
     * Expire a oTP.
     *
     * @param oTP the entity to save.
     * @return the persisted entity.
     */
    OTPDTO expire(OTP oTP);

    /**
     * Partially updates a oTP.
     *
     * @param oTPDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OTPDTO> partialUpdate(OTPDTO oTPDTO);

    /**
     * Get all the oTPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OTPDTO> findAll(Pageable pageable);

    /**
     * Get the "id" oTP.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OTPDTO> findOne(Long id);

    /**
     * Delete the "id" oTP.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
