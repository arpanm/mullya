package com.mullya.app.web.rest;

import com.mullya.app.repository.RemittanceDetailsRepository;
import com.mullya.app.service.RemittanceDetailsService;
import com.mullya.app.service.dto.RemittanceDetailsDTO;
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
 * REST controller for managing {@link com.mullya.app.domain.RemittanceDetails}.
 */
@RestController
@RequestMapping("/api")
public class RemittanceDetailsResource {

    private final Logger log = LoggerFactory.getLogger(RemittanceDetailsResource.class);

    private static final String ENTITY_NAME = "remittanceDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemittanceDetailsService remittanceDetailsService;

    private final RemittanceDetailsRepository remittanceDetailsRepository;

    public RemittanceDetailsResource(
        RemittanceDetailsService remittanceDetailsService,
        RemittanceDetailsRepository remittanceDetailsRepository
    ) {
        this.remittanceDetailsService = remittanceDetailsService;
        this.remittanceDetailsRepository = remittanceDetailsRepository;
    }

    /**
     * {@code POST  /remittance-details} : Create a new remittanceDetails.
     *
     * @param remittanceDetailsDTO the remittanceDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remittanceDetailsDTO, or with status {@code 400 (Bad Request)} if the remittanceDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/remittance-details")
    public ResponseEntity<RemittanceDetailsDTO> createRemittanceDetails(@RequestBody RemittanceDetailsDTO remittanceDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save RemittanceDetails : {}", remittanceDetailsDTO);
        if (remittanceDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new remittanceDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RemittanceDetailsDTO result = remittanceDetailsService.save(remittanceDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/remittance-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /remittance-details/:id} : Updates an existing remittanceDetails.
     *
     * @param id the id of the remittanceDetailsDTO to save.
     * @param remittanceDetailsDTO the remittanceDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remittanceDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the remittanceDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remittanceDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/remittance-details/{id}")
    public ResponseEntity<RemittanceDetailsDTO> updateRemittanceDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RemittanceDetailsDTO remittanceDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RemittanceDetails : {}, {}", id, remittanceDetailsDTO);
        if (remittanceDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remittanceDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remittanceDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RemittanceDetailsDTO result = remittanceDetailsService.save(remittanceDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remittanceDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /remittance-details/:id} : Partial updates given fields of an existing remittanceDetails, field will ignore if it is null
     *
     * @param id the id of the remittanceDetailsDTO to save.
     * @param remittanceDetailsDTO the remittanceDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remittanceDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the remittanceDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the remittanceDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the remittanceDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/remittance-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RemittanceDetailsDTO> partialUpdateRemittanceDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RemittanceDetailsDTO remittanceDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RemittanceDetails partially : {}, {}", id, remittanceDetailsDTO);
        if (remittanceDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remittanceDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remittanceDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RemittanceDetailsDTO> result = remittanceDetailsService.partialUpdate(remittanceDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remittanceDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /remittance-details} : get all the remittanceDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of remittanceDetails in body.
     */
    @GetMapping("/remittance-details")
    public ResponseEntity<List<RemittanceDetailsDTO>> getAllRemittanceDetails(Pageable pageable) {
        log.debug("REST request to get a page of RemittanceDetails");
        Page<RemittanceDetailsDTO> page = remittanceDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /remittance-details/:id} : get the "id" remittanceDetails.
     *
     * @param id the id of the remittanceDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remittanceDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/remittance-details/{id}")
    public ResponseEntity<RemittanceDetailsDTO> getRemittanceDetails(@PathVariable Long id) {
        log.debug("REST request to get RemittanceDetails : {}", id);
        Optional<RemittanceDetailsDTO> remittanceDetailsDTO = remittanceDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remittanceDetailsDTO);
    }

    /**
     * {@code DELETE  /remittance-details/:id} : delete the "id" remittanceDetails.
     *
     * @param id the id of the remittanceDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/remittance-details/{id}")
    public ResponseEntity<Void> deleteRemittanceDetails(@PathVariable Long id) {
        log.debug("REST request to delete RemittanceDetails : {}", id);
        remittanceDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
