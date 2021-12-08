package com.mullya.app.web.rest;

import com.mullya.app.repository.BiddingDetailsRepository;
import com.mullya.app.service.BiddingDetailsService;
import com.mullya.app.service.dto.BiddingDetailsDTO;
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
 * REST controller for managing {@link com.mullya.app.domain.BiddingDetails}.
 */
@RestController
@RequestMapping("/api")
public class BiddingDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BiddingDetailsResource.class);

    private static final String ENTITY_NAME = "biddingDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BiddingDetailsService biddingDetailsService;

    private final BiddingDetailsRepository biddingDetailsRepository;

    public BiddingDetailsResource(BiddingDetailsService biddingDetailsService, BiddingDetailsRepository biddingDetailsRepository) {
        this.biddingDetailsService = biddingDetailsService;
        this.biddingDetailsRepository = biddingDetailsRepository;
    }

    /**
     * {@code POST  /bidding-details} : Create a new biddingDetails.
     *
     * @param biddingDetailsDTO the biddingDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new biddingDetailsDTO, or with status {@code 400 (Bad Request)} if the biddingDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bidding-details")
    public ResponseEntity<BiddingDetailsDTO> createBiddingDetails(@RequestBody BiddingDetailsDTO biddingDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save BiddingDetails : {}", biddingDetailsDTO);
        if (biddingDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new biddingDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BiddingDetailsDTO result = biddingDetailsService.save(biddingDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/bidding-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bidding-details/:id} : Updates an existing biddingDetails.
     *
     * @param id the id of the biddingDetailsDTO to save.
     * @param biddingDetailsDTO the biddingDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated biddingDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the biddingDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the biddingDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bidding-details/{id}")
    public ResponseEntity<BiddingDetailsDTO> updateBiddingDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BiddingDetailsDTO biddingDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BiddingDetails : {}, {}", id, biddingDetailsDTO);
        if (biddingDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, biddingDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!biddingDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BiddingDetailsDTO result = biddingDetailsService.save(biddingDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, biddingDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bidding-details/:id} : Partial updates given fields of an existing biddingDetails, field will ignore if it is null
     *
     * @param id the id of the biddingDetailsDTO to save.
     * @param biddingDetailsDTO the biddingDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated biddingDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the biddingDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the biddingDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the biddingDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bidding-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BiddingDetailsDTO> partialUpdateBiddingDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BiddingDetailsDTO biddingDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BiddingDetails partially : {}, {}", id, biddingDetailsDTO);
        if (biddingDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, biddingDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!biddingDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BiddingDetailsDTO> result = biddingDetailsService.partialUpdate(biddingDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, biddingDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bidding-details} : get all the biddingDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of biddingDetails in body.
     */
    @GetMapping("/bidding-details")
    public ResponseEntity<List<BiddingDetailsDTO>> getAllBiddingDetails(Pageable pageable) {
        log.debug("REST request to get a page of BiddingDetails");
        Page<BiddingDetailsDTO> page = biddingDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bidding-details/:id} : get the "id" biddingDetails.
     *
     * @param id the id of the biddingDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the biddingDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bidding-details/{id}")
    public ResponseEntity<BiddingDetailsDTO> getBiddingDetails(@PathVariable Long id) {
        log.debug("REST request to get BiddingDetails : {}", id);
        Optional<BiddingDetailsDTO> biddingDetailsDTO = biddingDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(biddingDetailsDTO);
    }

    /**
     * {@code DELETE  /bidding-details/:id} : delete the "id" biddingDetails.
     *
     * @param id the id of the biddingDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bidding-details/{id}")
    public ResponseEntity<Void> deleteBiddingDetails(@PathVariable Long id) {
        log.debug("REST request to delete BiddingDetails : {}", id);
        biddingDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
