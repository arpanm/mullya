package com.mullya.app.service;

import com.mullya.app.service.dto.RequirementDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mullya.app.domain.Requirement}.
 */
public interface RequirementService {
    /**
     * Save a requirement.
     *
     * @param requirementDTO the entity to save.
     * @return the persisted entity.
     */
    RequirementDTO save(RequirementDTO requirementDTO);

    /**
     * Partially updates a requirement.
     *
     * @param requirementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequirementDTO> partialUpdate(RequirementDTO requirementDTO);

    /**
     * Get all the requirements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequirementDTO> findAll(Pageable pageable);

    /**
     * Get the "id" requirement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequirementDTO> findOne(Long id);

    /**
     * Delete the "id" requirement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
