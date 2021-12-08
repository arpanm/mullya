package com.mullya.app.web.rest;

import com.mullya.app.repository.CancellationDetailsRepository;
import com.mullya.app.service.CancellationDetailsService;
import com.mullya.app.service.dto.CancellationDetailsDTO;
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
 * REST controller for managing {@link com.mullya.app.domain.CancellationDetails}.
 */
@RestController
@RequestMapping("/api")
public class CancellationDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CancellationDetailsResource.class);

    private static final String ENTITY_NAME = "cancellationDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CancellationDetailsService cancellationDetailsService;

    private final CancellationDetailsRepository cancellationDetailsRepository;

    public CancellationDetailsResource(
        CancellationDetailsService cancellationDetailsService,
        CancellationDetailsRepository cancellationDetailsRepository
    ) {
        this.cancellationDetailsService = cancellationDetailsService;
        this.cancellationDetailsRepository = cancellationDetailsRepository;
    }

    /**
     * {@code POST  /cancellation-details} : Create a new cancellationDetails.
     *
     * @param cancellationDetailsDTO the cancellationDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cancellationDetailsDTO, or with status {@code 400 (Bad Request)} if the cancellationDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cancellation-details")
    public ResponseEntity<CancellationDetailsDTO> createCancellationDetails(@RequestBody CancellationDetailsDTO cancellationDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save CancellationDetails : {}", cancellationDetailsDTO);
        if (cancellationDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new cancellationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CancellationDetailsDTO result = cancellationDetailsService.save(cancellationDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/cancellation-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cancellation-details/:id} : Updates an existing cancellationDetails.
     *
     * @param id the id of the cancellationDetailsDTO to save.
     * @param cancellationDetailsDTO the cancellationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cancellationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the cancellationDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cancellationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cancellation-details/{id}")
    public ResponseEntity<CancellationDetailsDTO> updateCancellationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CancellationDetailsDTO cancellationDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CancellationDetails : {}, {}", id, cancellationDetailsDTO);
        if (cancellationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cancellationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cancellationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CancellationDetailsDTO result = cancellationDetailsService.save(cancellationDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cancellationDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cancellation-details/:id} : Partial updates given fields of an existing cancellationDetails, field will ignore if it is null
     *
     * @param id the id of the cancellationDetailsDTO to save.
     * @param cancellationDetailsDTO the cancellationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cancellationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the cancellationDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cancellationDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cancellationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cancellation-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CancellationDetailsDTO> partialUpdateCancellationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CancellationDetailsDTO cancellationDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CancellationDetails partially : {}, {}", id, cancellationDetailsDTO);
        if (cancellationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cancellationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cancellationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CancellationDetailsDTO> result = cancellationDetailsService.partialUpdate(cancellationDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cancellationDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cancellation-details} : get all the cancellationDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cancellationDetails in body.
     */
    @GetMapping("/cancellation-details")
    public ResponseEntity<List<CancellationDetailsDTO>> getAllCancellationDetails(Pageable pageable) {
        log.debug("REST request to get a page of CancellationDetails");
        Page<CancellationDetailsDTO> page = cancellationDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cancellation-details/:id} : get the "id" cancellationDetails.
     *
     * @param id the id of the cancellationDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cancellationDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cancellation-details/{id}")
    public ResponseEntity<CancellationDetailsDTO> getCancellationDetails(@PathVariable Long id) {
        log.debug("REST request to get CancellationDetails : {}", id);
        Optional<CancellationDetailsDTO> cancellationDetailsDTO = cancellationDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cancellationDetailsDTO);
    }

    /**
     * {@code DELETE  /cancellation-details/:id} : delete the "id" cancellationDetails.
     *
     * @param id the id of the cancellationDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cancellation-details/{id}")
    public ResponseEntity<Void> deleteCancellationDetails(@PathVariable Long id) {
        log.debug("REST request to delete CancellationDetails : {}", id);
        cancellationDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
