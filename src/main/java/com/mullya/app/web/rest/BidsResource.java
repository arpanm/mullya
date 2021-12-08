package com.mullya.app.web.rest;

import com.mullya.app.repository.BidsRepository;
import com.mullya.app.service.BidsService;
import com.mullya.app.service.dto.BidsDTO;
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
 * REST controller for managing {@link com.mullya.app.domain.Bids}.
 */
@RestController
@RequestMapping("/api")
public class BidsResource {

    private final Logger log = LoggerFactory.getLogger(BidsResource.class);

    private static final String ENTITY_NAME = "bids";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BidsService bidsService;

    private final BidsRepository bidsRepository;

    public BidsResource(BidsService bidsService, BidsRepository bidsRepository) {
        this.bidsService = bidsService;
        this.bidsRepository = bidsRepository;
    }

    /**
     * {@code POST  /bids} : Create a new bids.
     *
     * @param bidsDTO the bidsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bidsDTO, or with status {@code 400 (Bad Request)} if the bids has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bids")
    public ResponseEntity<BidsDTO> createBids(@RequestBody BidsDTO bidsDTO) throws URISyntaxException {
        log.debug("REST request to save Bids : {}", bidsDTO);
        if (bidsDTO.getId() != null) {
            throw new BadRequestAlertException("A new bids cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BidsDTO result = bidsService.save(bidsDTO);
        return ResponseEntity
            .created(new URI("/api/bids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bids/:id} : Updates an existing bids.
     *
     * @param id the id of the bidsDTO to save.
     * @param bidsDTO the bidsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bidsDTO,
     * or with status {@code 400 (Bad Request)} if the bidsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bidsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bids/{id}")
    public ResponseEntity<BidsDTO> updateBids(@PathVariable(value = "id", required = false) final Long id, @RequestBody BidsDTO bidsDTO)
        throws URISyntaxException {
        log.debug("REST request to update Bids : {}, {}", id, bidsDTO);
        if (bidsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bidsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bidsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BidsDTO result = bidsService.save(bidsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bidsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bids/:id} : Partial updates given fields of an existing bids, field will ignore if it is null
     *
     * @param id the id of the bidsDTO to save.
     * @param bidsDTO the bidsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bidsDTO,
     * or with status {@code 400 (Bad Request)} if the bidsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bidsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bidsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bids/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BidsDTO> partialUpdateBids(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BidsDTO bidsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bids partially : {}, {}", id, bidsDTO);
        if (bidsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bidsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bidsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BidsDTO> result = bidsService.partialUpdate(bidsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bidsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bids} : get all the bids.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bids in body.
     */
    @GetMapping("/bids")
    public ResponseEntity<List<BidsDTO>> getAllBids(Pageable pageable) {
        log.debug("REST request to get a page of Bids");
        Page<BidsDTO> page = bidsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bids/:id} : get the "id" bids.
     *
     * @param id the id of the bidsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bidsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bids/{id}")
    public ResponseEntity<BidsDTO> getBids(@PathVariable Long id) {
        log.debug("REST request to get Bids : {}", id);
        Optional<BidsDTO> bidsDTO = bidsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bidsDTO);
    }

    /**
     * {@code DELETE  /bids/:id} : delete the "id" bids.
     *
     * @param id the id of the bidsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bids/{id}")
    public ResponseEntity<Void> deleteBids(@PathVariable Long id) {
        log.debug("REST request to delete Bids : {}", id);
        bidsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
