package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.Requirement;
import com.mullya.app.domain.enumeration.DeliveryStatus;
import com.mullya.app.domain.enumeration.PaymentStatus;
import com.mullya.app.domain.enumeration.RequirementStatus;
import com.mullya.app.domain.enumeration.StockCategory;
import com.mullya.app.repository.RequirementRepository;
import com.mullya.app.service.dto.RequirementDTO;
import com.mullya.app.service.mapper.RequirementMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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

    private static final StockCategory DEFAULT_CATEGORY = StockCategory.Rice;
    private static final StockCategory UPDATED_CATEGORY = StockCategory.Wheat;

    private static final String DEFAULT_VARIANT = "AAAAAAAAAA";
    private static final String UPDATED_VARIANT = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_VARIANT = "AAAAAAAAAA";
    private static final String UPDATED_SUB_VARIANT = "BBBBBBBBBB";

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

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final RequirementStatus DEFAULT_STATUS = RequirementStatus.New;
    private static final RequirementStatus UPDATED_STATUS = RequirementStatus.Accepted;

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.Pending;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.Initiated;

    private static final DeliveryStatus DEFAULT_DELIVERY_STATUS = DeliveryStatus.PendingConfirmation;
    private static final DeliveryStatus UPDATED_DELIVERY_STATUS = DeliveryStatus.Accepted;

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
            .category(DEFAULT_CATEGORY)
            .variant(DEFAULT_VARIANT)
            .subVariant(DEFAULT_SUB_VARIANT)
            .minPrice(DEFAULT_MIN_PRICE)
            .maxPrice(DEFAULT_MAX_PRICE)
            .quantityKg(DEFAULT_QUANTITY_KG)
            .neededBy(DEFAULT_NEEDED_BY)
            .description(DEFAULT_DESCRIPTION)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .status(DEFAULT_STATUS)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .deliveryStatus(DEFAULT_DELIVERY_STATUS);
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
            .category(UPDATED_CATEGORY)
            .variant(UPDATED_VARIANT)
            .subVariant(UPDATED_SUB_VARIANT)
            .minPrice(UPDATED_MIN_PRICE)
            .maxPrice(UPDATED_MAX_PRICE)
            .quantityKg(UPDATED_QUANTITY_KG)
            .neededBy(UPDATED_NEEDED_BY)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .status(UPDATED_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .deliveryStatus(UPDATED_DELIVERY_STATUS);
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
        assertThat(testRequirement.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testRequirement.getVariant()).isEqualTo(DEFAULT_VARIANT);
        assertThat(testRequirement.getSubVariant()).isEqualTo(DEFAULT_SUB_VARIANT);
        assertThat(testRequirement.getMinPrice()).isEqualTo(DEFAULT_MIN_PRICE);
        assertThat(testRequirement.getMaxPrice()).isEqualTo(DEFAULT_MAX_PRICE);
        assertThat(testRequirement.getQuantityKg()).isEqualTo(DEFAULT_QUANTITY_KG);
        assertThat(testRequirement.getNeededBy()).isEqualTo(DEFAULT_NEEDED_BY);
        assertThat(testRequirement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRequirement.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRequirement.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRequirement.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testRequirement.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRequirement.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRequirement.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testRequirement.getDeliveryStatus()).isEqualTo(DEFAULT_DELIVERY_STATUS);
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
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].variant").value(hasItem(DEFAULT_VARIANT)))
            .andExpect(jsonPath("$.[*].subVariant").value(hasItem(DEFAULT_SUB_VARIANT)))
            .andExpect(jsonPath("$.[*].minPrice").value(hasItem(DEFAULT_MIN_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxPrice").value(hasItem(DEFAULT_MAX_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantityKg").value(hasItem(DEFAULT_QUANTITY_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].neededBy").value(hasItem(DEFAULT_NEEDED_BY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].deliveryStatus").value(hasItem(DEFAULT_DELIVERY_STATUS.toString())));
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
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.variant").value(DEFAULT_VARIANT))
            .andExpect(jsonPath("$.subVariant").value(DEFAULT_SUB_VARIANT))
            .andExpect(jsonPath("$.minPrice").value(DEFAULT_MIN_PRICE.doubleValue()))
            .andExpect(jsonPath("$.maxPrice").value(DEFAULT_MAX_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantityKg").value(DEFAULT_QUANTITY_KG.doubleValue()))
            .andExpect(jsonPath("$.neededBy").value(DEFAULT_NEEDED_BY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.deliveryStatus").value(DEFAULT_DELIVERY_STATUS.toString()));
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
            .category(UPDATED_CATEGORY)
            .variant(UPDATED_VARIANT)
            .subVariant(UPDATED_SUB_VARIANT)
            .minPrice(UPDATED_MIN_PRICE)
            .maxPrice(UPDATED_MAX_PRICE)
            .quantityKg(UPDATED_QUANTITY_KG)
            .neededBy(UPDATED_NEEDED_BY)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .status(UPDATED_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .deliveryStatus(UPDATED_DELIVERY_STATUS);
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
        assertThat(testRequirement.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testRequirement.getVariant()).isEqualTo(UPDATED_VARIANT);
        assertThat(testRequirement.getSubVariant()).isEqualTo(UPDATED_SUB_VARIANT);
        assertThat(testRequirement.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testRequirement.getMaxPrice()).isEqualTo(UPDATED_MAX_PRICE);
        assertThat(testRequirement.getQuantityKg()).isEqualTo(UPDATED_QUANTITY_KG);
        assertThat(testRequirement.getNeededBy()).isEqualTo(UPDATED_NEEDED_BY);
        assertThat(testRequirement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequirement.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRequirement.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRequirement.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testRequirement.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRequirement.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequirement.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testRequirement.getDeliveryStatus()).isEqualTo(UPDATED_DELIVERY_STATUS);
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

        partialUpdatedRequirement
            .subVariant(UPDATED_SUB_VARIANT)
            .maxPrice(UPDATED_MAX_PRICE)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .status(UPDATED_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .deliveryStatus(UPDATED_DELIVERY_STATUS);

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
        assertThat(testRequirement.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testRequirement.getVariant()).isEqualTo(DEFAULT_VARIANT);
        assertThat(testRequirement.getSubVariant()).isEqualTo(UPDATED_SUB_VARIANT);
        assertThat(testRequirement.getMinPrice()).isEqualTo(DEFAULT_MIN_PRICE);
        assertThat(testRequirement.getMaxPrice()).isEqualTo(UPDATED_MAX_PRICE);
        assertThat(testRequirement.getQuantityKg()).isEqualTo(DEFAULT_QUANTITY_KG);
        assertThat(testRequirement.getNeededBy()).isEqualTo(DEFAULT_NEEDED_BY);
        assertThat(testRequirement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequirement.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRequirement.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRequirement.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testRequirement.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRequirement.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequirement.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testRequirement.getDeliveryStatus()).isEqualTo(UPDATED_DELIVERY_STATUS);
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
            .category(UPDATED_CATEGORY)
            .variant(UPDATED_VARIANT)
            .subVariant(UPDATED_SUB_VARIANT)
            .minPrice(UPDATED_MIN_PRICE)
            .maxPrice(UPDATED_MAX_PRICE)
            .quantityKg(UPDATED_QUANTITY_KG)
            .neededBy(UPDATED_NEEDED_BY)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .status(UPDATED_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .deliveryStatus(UPDATED_DELIVERY_STATUS);

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
        assertThat(testRequirement.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testRequirement.getVariant()).isEqualTo(UPDATED_VARIANT);
        assertThat(testRequirement.getSubVariant()).isEqualTo(UPDATED_SUB_VARIANT);
        assertThat(testRequirement.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testRequirement.getMaxPrice()).isEqualTo(UPDATED_MAX_PRICE);
        assertThat(testRequirement.getQuantityKg()).isEqualTo(UPDATED_QUANTITY_KG);
        assertThat(testRequirement.getNeededBy()).isEqualTo(UPDATED_NEEDED_BY);
        assertThat(testRequirement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequirement.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRequirement.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRequirement.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testRequirement.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRequirement.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequirement.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testRequirement.getDeliveryStatus()).isEqualTo(UPDATED_DELIVERY_STATUS);
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
