package com.mullya.app.web.rest;

import com.mullya.app.repository.RequirementRepository;
import com.mullya.app.service.RequirementService;
import com.mullya.app.service.dto.RequirementDTO;
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
 * REST controller for managing {@link com.mullya.app.domain.Requirement}.
 */
@RestController
@RequestMapping("/api")
public class RequirementResource {

    private final Logger log = LoggerFactory.getLogger(RequirementResource.class);

    private static final String ENTITY_NAME = "requirement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequirementService requirementService;

    private final RequirementRepository requirementRepository;

    public RequirementResource(RequirementService requirementService, RequirementRepository requirementRepository) {
        this.requirementService = requirementService;
        this.requirementRepository = requirementRepository;
    }

    /**
     * {@code POST  /requirements} : Create a new requirement.
     *
     * @param requirementDTO the requirementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requirementDTO, or with status {@code 400 (Bad Request)} if the requirement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requirements")
    public ResponseEntity<RequirementDTO> createRequirement(@RequestBody RequirementDTO requirementDTO) throws URISyntaxException {
        log.debug("REST request to save Requirement : {}", requirementDTO);
        if (requirementDTO.getId() != null) {
            throw new BadRequestAlertException("A new requirement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequirementDTO result = requirementService.save(requirementDTO);
        return ResponseEntity
            .created(new URI("/api/requirements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requirements/:id} : Updates an existing requirement.
     *
     * @param id the id of the requirementDTO to save.
     * @param requirementDTO the requirementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requirementDTO,
     * or with status {@code 400 (Bad Request)} if the requirementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requirementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requirements/{id}")
    public ResponseEntity<RequirementDTO> updateRequirement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequirementDTO requirementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Requirement : {}, {}", id, requirementDTO);
        if (requirementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requirementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requirementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequirementDTO result = requirementService.save(requirementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requirementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /requirements/:id} : Partial updates given fields of an existing requirement, field will ignore if it is null
     *
     * @param id the id of the requirementDTO to save.
     * @param requirementDTO the requirementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requirementDTO,
     * or with status {@code 400 (Bad Request)} if the requirementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requirementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requirementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/requirements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequirementDTO> partialUpdateRequirement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequirementDTO requirementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Requirement partially : {}, {}", id, requirementDTO);
        if (requirementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requirementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requirementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequirementDTO> result = requirementService.partialUpdate(requirementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requirementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /requirements} : get all the requirements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requirements in body.
     */
    @GetMapping("/requirements")
    public ResponseEntity<List<RequirementDTO>> getAllRequirements(Pageable pageable) {
        log.debug("REST request to get a page of Requirements");
        Page<RequirementDTO> page = requirementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /requirements/:id} : get the "id" requirement.
     *
     * @param id the id of the requirementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requirementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requirements/{id}")
    public ResponseEntity<RequirementDTO> getRequirement(@PathVariable Long id) {
        log.debug("REST request to get Requirement : {}", id);
        Optional<RequirementDTO> requirementDTO = requirementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requirementDTO);
    }

    /**
     * {@code DELETE  /requirements/:id} : delete the "id" requirement.
     *
     * @param id the id of the requirementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requirements/{id}")
    public ResponseEntity<Void> deleteRequirement(@PathVariable Long id) {
        log.debug("REST request to delete Requirement : {}", id);
        requirementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
