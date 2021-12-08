package com.mullya.app.web.rest;

import com.mullya.app.repository.HubRepository;
import com.mullya.app.service.HubService;
import com.mullya.app.service.dto.HubDTO;
import com.mullya.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mullya.app.domain.Hub}.
 */
@RestController
@RequestMapping("/api")
public class HubResource {

    private final Logger log = LoggerFactory.getLogger(HubResource.class);

    private static final String ENTITY_NAME = "hub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HubService hubService;

    private final HubRepository hubRepository;

    public HubResource(HubService hubService, HubRepository hubRepository) {
        this.hubService = hubService;
        this.hubRepository = hubRepository;
    }

    /**
     * {@code POST  /hubs} : Create a new hub.
     *
     * @param hubDTO the hubDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hubDTO, or with status {@code 400 (Bad Request)} if the hub has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hubs")
    public ResponseEntity<HubDTO> createHub(@RequestBody HubDTO hubDTO) throws URISyntaxException {
        log.debug("REST request to save Hub : {}", hubDTO);
        if (hubDTO.getId() != null) {
            throw new BadRequestAlertException("A new hub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HubDTO result = hubService.save(hubDTO);
        return ResponseEntity
            .created(new URI("/api/hubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hubs/:id} : Updates an existing hub.
     *
     * @param id the id of the hubDTO to save.
     * @param hubDTO the hubDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hubDTO,
     * or with status {@code 400 (Bad Request)} if the hubDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hubDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hubs/{id}")
    public ResponseEntity<HubDTO> updateHub(@PathVariable(value = "id", required = false) final Long id, @RequestBody HubDTO hubDTO)
        throws URISyntaxException {
        log.debug("REST request to update Hub : {}, {}", id, hubDTO);
        if (hubDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hubDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HubDTO result = hubService.save(hubDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hubDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hubs/:id} : Partial updates given fields of an existing hub, field will ignore if it is null
     *
     * @param id the id of the hubDTO to save.
     * @param hubDTO the hubDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hubDTO,
     * or with status {@code 400 (Bad Request)} if the hubDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hubDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hubDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hubs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HubDTO> partialUpdateHub(@PathVariable(value = "id", required = false) final Long id, @RequestBody HubDTO hubDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Hub partially : {}, {}", id, hubDTO);
        if (hubDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hubDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HubDTO> result = hubService.partialUpdate(hubDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hubDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hubs} : get all the hubs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hubs in body.
     */
    @GetMapping("/hubs")
    public ResponseEntity<List<HubDTO>> getAllHubs(Pageable pageable) {
        log.debug("REST request to get a page of Hubs");
        Page<HubDTO> page = hubService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hubs/:id} : get the "id" hub.
     *
     * @param id the id of the hubDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hubDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hubs/{id}")
    public ResponseEntity<HubDTO> getHub(@PathVariable Long id) {
        log.debug("REST request to get Hub : {}", id);
        Optional<HubDTO> hubDTO = hubService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hubDTO);
    }

    /**
     * {@code DELETE  /hubs/:id} : delete the "id" hub.
     *
     * @param id the id of the hubDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hubs/{id}")
    public ResponseEntity<Void> deleteHub(@PathVariable Long id) {
        log.debug("REST request to delete Hub : {}", id);
        hubService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
