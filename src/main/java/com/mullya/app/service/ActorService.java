package com.mullya.app.service;

import com.mullya.app.service.dto.ActorDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mullya.app.domain.Actor}.
 */
public interface ActorService {
    /**
     * Save a actor.
     *
     * @param actorDTO the entity to save.
     * @return the persisted entity.
     */
    ActorDTO save(ActorDTO actorDTO);

    /**
     * Partially updates a actor.
     *
     * @param actorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ActorDTO> partialUpdate(ActorDTO actorDTO);

    /**
     * Get all the actors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" actor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActorDTO> findOne(Long id);

    /**
     * Delete the "id" actor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
