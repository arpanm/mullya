package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.Requirement;
import com.mullya.app.domain.enumeration.RequirementStatus;
import com.mullya.app.repository.RequirementRepository;
import com.mullya.app.service.dto.RequirementDTO;
import com.mullya.app.service.mapper.RequirementMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RequirementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequirementResourceIT {

    private static final Float DEFAULT_MIN_PRICE = 1F;
    private static final Float UPDATED_MIN_PRICE = 2F;

    private static final Float DEFAULT_MAX_PRICE = 1F;
    private static final Float UPDATED_MAX_PRICE = 2F;

    private static final Float DEFAULT_QUANTITY_KG = 1F;
    private static final Float UPDATED_QUANTITY_KG = 2F;

    private static final String DEFAULT_NEEDED_BY = "AAAAAAAAAA";
    private static final String UPDATED_NEEDED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final RequirementStatus DEFAULT_STATUS = RequirementStatus.New;
    private static final RequirementStatus UPDATED_STATUS = RequirementStatus.Accepted;

    private static final String ENTITY_API_URL = "/api/requirements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private RequirementMapper requirementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequirementMockMvc;

    private Requirement requirement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requirement createEntity(EntityManager em) {
        Requirement requirement = new Requirement()
            .minPrice(DEFAULT_MIN_PRICE)
            .maxPrice(DEFAULT_MAX_PRICE)
            .quantityKg(DEFAULT_QUANTITY_KG)
            .neededBy(DEFAULT_NEEDED_BY)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return requirement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requirement createUpdatedEntity(EntityManager em) {
        Requirement requirement = new Requirement()
            .minPrice(UPDATED_MIN_PRICE)
            .maxPrice(UPDATED_MAX_PRICE)
            .quantityKg(UPDATED_QUANTITY_KG)
            .neededBy(UPDATED_NEEDED_BY)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        return requirement;
    }

    @BeforeEach
    public void initTest() {
        requirement = createEntity(em);
    }

    @Test
    @Transactional
    void createRequirement() throws Exception {
        int databaseSizeBeforeCreate = requirementRepository.findAll().size();
        // Create the Requirement
        RequirementDTO requirementDTO = requirementMapper.toDto(requirement);
        restRequirementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requirementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Requirement in the database
        List<Requirement> requirementList = requirementRepository.findAll();
        assertThat(requirementList).hasSize(databaseSizeBeforeCreate + 1);
        Requirement testRequirement = requirementList.get(requirementList.size() - 1);
        assertThat(testRequirement.getMinPrice()).isEqualTo(DEFAULT_MIN_PRICE);
        assertThat(testRequirement.getMaxPrice()).isEqualTo(DEFAULT_MAX_PRICE);
        assertThat(testRequirement.getQuantityKg()).isEqualTo(DEFAULT_QUANTITY_KG);
        assertThat(testRequirement.getNeededBy()).isEqualTo(DEFAULT_NEEDED_BY);
        assertThat(testRequirement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRequirement.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createRequirementWithExistingId() throws Exception {
        // Create the Requirement with an existing ID
        requirement.setId(1L);
        RequirementDTO requirementDTO = requirementMapper.toDto(requirement);

        int databaseSizeBeforeCreate = requirementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequirementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requirementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requirement in the database
        List<Requirement> requirementList = requirementRepository.findAll();
        assertThat(requirementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRequirements() throws Exception {
        // Initialize the database
        requirementRepository.saveAndFlush(requirement);

        // Get all the requirementList
        restRequirementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requirement.getId().intValue())))
            .andExpect(jsonPath("$.[*].minPrice").value(hasItem(DEFAULT_MIN_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxPrice").value(hasItem(DEFAULT_MAX_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantityKg").value(hasItem(DEFAULT_QUANTITY_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].neededBy").value(hasItem(DEFAULT_NEEDED_BY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getRequirement() throws Exception {
        // Initialize the database
        requirementRepository.saveAndFlush(requirement);

        // Get the requirement
        restRequirementMockMvc
            .perform(get(ENTITY_API_URL_ID, requirement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requirement.getId().intValue()))
            .andExpect(jsonPath("$.minPrice").value(DEFAULT_MIN_PRICE.doubleValue()))
            .andExpect(jsonPath("$.maxPrice").value(DEFAULT_MAX_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantityKg").value(DEFAULT_QUANTITY_KG.doubleValue()))
            .andExpect(jsonPath("$.neededBy").value(DEFAULT_NEEDED_BY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRequirement() throws Exception {
        // Get the requirement
        restRequirementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRequirement() throws Exception {
        // Initialize the database
        requirementRepository.saveAndFlush(requirement);

        int databaseSizeBeforeUpdate = requirementRepository.findAll().size();

        // Update the requirement
        Requirement updatedRequirement = requirementRepository.findById(requirement.getId()).get();
        // Disconnect from session so that the updates on updatedRequirement are not directly saved in db
        em.detach(updatedRequirement);
        updatedRequirement
            .minPrice(UPDATED_MIN_PRICE)
            .maxPrice(UPDATED_MAX_PRICE)
            .quantityKg(UPDATED_QUANTITY_KG)
            .neededBy(UPDATED_NEEDED_BY)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        RequirementDTO requirementDTO = requirementMapper.toDto(updatedRequirement);

        restRequirementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requirementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requirementDTO))
            )
            .andExpect(status().isOk());

        // Validate the Requirement in the database
        List<Requirement> requirementList = requirementRepository.findAll();
        assertThat(requirementList).hasSize(databaseSizeBeforeUpdate);
        Requirement testRequirement = requirementList.get(requirementList.size() - 1);
        assertThat(testRequirement.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testRequirement.getMaxPrice()).isEqualTo(UPDATED_MAX_PRICE);
        assertThat(testRequirement.getQuantityKg()).isEqualTo(UPDATED_QUANTITY_KG);
        assertThat(testRequirement.getNeededBy()).isEqualTo(UPDATED_NEEDED_BY);
        assertThat(testRequirement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequirement.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingRequirement() throws Exception {
        int databaseSizeBeforeUpdate = requirementRepository.findAll().size();
        requirement.setId(count.incrementAndGet());

        // Create the Requirement
        RequirementDTO requirementDTO = requirementMapper.toDto(requirement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequirementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requirementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requirementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requirement in the database
        List<Requirement> requirementList = requirementRepository.findAll();
        assertThat(requirementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequirement() throws Exception {
        int databaseSizeBeforeUpdate = requirementRepository.findAll().size();
        requirement.setId(count.incrementAndGet());

        // Create the Requirement
        RequirementDTO requirementDTO = requirementMapper.toDto(requirement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequirementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requirementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requirement in the database
        List<Requirement> requirementList = requirementRepository.findAll();
        assertThat(requirementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequirement() throws Exception {
        int databaseSizeBeforeUpdate = requirementRepository.findAll().size();
        requirement.setId(count.incrementAndGet());

        // Create the Requirement
        RequirementDTO requirementDTO = requirementMapper.toDto(requirement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequirementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requirementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Requirement in the database
        List<Requirement> requirementList = requirementRepository.findAll();
        assertThat(requirementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequirementWithPatch() throws Exception {
        // Initialize the database
        requirementRepository.saveAndFlush(requirement);

        int databaseSizeBeforeUpdate = requirementRepository.findAll().size();

        // Update the requirement using partial update
        Requirement partialUpdatedRequirement = new Requirement();
        partialUpdatedRequirement.setId(requirement.getId());

        partialUpdatedRequirement.quantityKg(UPDATED_QUANTITY_KG).description(UPDATED_DESCRIPTION);

        restRequirementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequirement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequirement))
            )
            .andExpect(status().isOk());

        // Validate the Requirement in the database
        List<Requirement> requirementList = requirementRepository.findAll();
        assertThat(requirementList).hasSize(databaseSizeBeforeUpdate);
        Requirement testRequirement = requirementList.get(requirementList.size() - 1);
        assertThat(testRequirement.getMinPrice()).isEqualTo(DEFAULT_MIN_PRICE);
        assertThat(testRequirement.getMaxPrice()).isEqualTo(DEFAULT_MAX_PRICE);
        assertThat(testRequirement.getQuantityKg()).isEqualTo(UPDATED_QUANTITY_KG);
        assertThat(testRequirement.getNeededBy()).isEqualTo(DEFAULT_NEEDED_BY);
        assertThat(testRequirement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequirement.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateRequirementWithPatch() throws Exception {
        // Initialize the database
        requirementRepository.saveAndFlush(requirement);

        int databaseSizeBeforeUpdate = requirementRepository.findAll().size();

        // Update the requirement using partial update
        Requirement partialUpdatedRequirement = new Requirement();
        partialUpdatedRequirement.setId(requirement.getId());

        partialUpdatedRequirement
            .minPrice(UPDATED_MIN_PRICE)
            .maxPrice(UPDATED_MAX_PRICE)
            .quantityKg(UPDATED_QUANTITY_KG)
            .neededBy(UPDATED_NEEDED_BY)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);

        restRequirementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequirement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequirement))
            )
            .andExpect(status().isOk());

        // Validate the Requirement in the database
        List<Requirement> requirementList = requirementRepository.findAll();
        assertThat(requirementList).hasSize(databaseSizeBeforeUpdate);
        Requirement testRequirement = requirementList.get(requirementList.size() - 1);
        assertThat(testRequirement.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testRequirement.getMaxPrice()).isEqualTo(UPDATED_MAX_PRICE);
        assertThat(testRequirement.getQuantityKg()).isEqualTo(UPDATED_QUANTITY_KG);
        assertThat(testRequirement.getNeededBy()).isEqualTo(UPDATED_NEEDED_BY);
        assertThat(testRequirement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequirement.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingRequirement() throws Exception {
        int databaseSizeBeforeUpdate = requirementRepository.findAll().size();
        requirement.setId(count.incrementAndGet());

        // Create the Requirement
        RequirementDTO requirementDTO = requirementMapper.toDto(requirement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequirementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requirementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requirementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requirement in the database
        List<Requirement> requirementList = requirementRepository.findAll();
        assertThat(requirementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequirement() throws Exception {
        int databaseSizeBeforeUpdate = requirementRepository.findAll().size();
        requirement.setId(count.incrementAndGet());

        // Create the Requirement
        RequirementDTO requirementDTO = requirementMapper.toDto(requirement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequirementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requirementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requirement in the database
        List<Requirement> requirementList = requirementRepository.findAll();
        assertThat(requirementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequirement() throws Exception {
        int databaseSizeBeforeUpdate = requirementRepository.findAll().size();
        requirement.setId(count.incrementAndGet());

        // Create the Requirement
        RequirementDTO requirementDTO = requirementMapper.toDto(requirement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequirementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(requirementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Requirement in the database
        List<Requirement> requirementList = requirementRepository.findAll();
        assertThat(requirementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequirement() throws Exception {
        // Initialize the database
        requirementRepository.saveAndFlush(requirement);

        int databaseSizeBeforeDelete = requirementRepository.findAll().size();

        // Delete the requirement
        restRequirementMockMvc
            .perform(delete(ENTITY_API_URL_ID, requirement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Requirement> requirementList = requirementRepository.findAll();
        assertThat(requirementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
