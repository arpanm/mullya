package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.BiddingDetails;
import com.mullya.app.domain.enumeration.BiddingStatus;
import com.mullya.app.repository.BiddingDetailsRepository;
import com.mullya.app.service.dto.BiddingDetailsDTO;
import com.mullya.app.service.mapper.BiddingDetailsMapper;
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
 * Integration tests for the {@link BiddingDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BiddingDetailsResourceIT {

    private static final String DEFAULT_START_DATE = "AAAAAAAAAA";
    private static final String UPDATED_START_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_END_DATE = "AAAAAAAAAA";
    private static final String UPDATED_END_DATE = "BBBBBBBBBB";

    private static final BiddingStatus DEFAULT_BIDDING_STATUS = BiddingStatus.New;
    private static final BiddingStatus UPDATED_BIDDING_STATUS = BiddingStatus.InProgress;

    private static final Float DEFAULT_MIN_PRICE = 1F;
    private static final Float UPDATED_MIN_PRICE = 2F;

    private static final Float DEFAULT_MAX_PRICE = 1F;
    private static final Float UPDATED_MAX_PRICE = 2F;

    private static final Float DEFAULT_MIN_QUANTITY_KG = 1F;
    private static final Float UPDATED_MIN_QUANTITY_KG = 2F;

    private static final Float DEFAULT_MAX_QUANTITY_KG = 1F;
    private static final Float UPDATED_MAX_QUANTITY_KG = 2F;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/bidding-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BiddingDetailsRepository biddingDetailsRepository;

    @Autowired
    private BiddingDetailsMapper biddingDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBiddingDetailsMockMvc;

    private BiddingDetails biddingDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BiddingDetails createEntity(EntityManager em) {
        BiddingDetails biddingDetails = new BiddingDetails()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .biddingStatus(DEFAULT_BIDDING_STATUS)
            .minPrice(DEFAULT_MIN_PRICE)
            .maxPrice(DEFAULT_MAX_PRICE)
            .minQuantityKg(DEFAULT_MIN_QUANTITY_KG)
            .maxQuantityKg(DEFAULT_MAX_QUANTITY_KG)
            .isActive(DEFAULT_IS_ACTIVE);
        return biddingDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BiddingDetails createUpdatedEntity(EntityManager em) {
        BiddingDetails biddingDetails = new BiddingDetails()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .biddingStatus(UPDATED_BIDDING_STATUS)
            .minPrice(UPDATED_MIN_PRICE)
            .maxPrice(UPDATED_MAX_PRICE)
            .minQuantityKg(UPDATED_MIN_QUANTITY_KG)
            .maxQuantityKg(UPDATED_MAX_QUANTITY_KG)
            .isActive(UPDATED_IS_ACTIVE);
        return biddingDetails;
    }

    @BeforeEach
    public void initTest() {
        biddingDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createBiddingDetails() throws Exception {
        int databaseSizeBeforeCreate = biddingDetailsRepository.findAll().size();
        // Create the BiddingDetails
        BiddingDetailsDTO biddingDetailsDTO = biddingDetailsMapper.toDto(biddingDetails);
        restBiddingDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(biddingDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BiddingDetails in the database
        List<BiddingDetails> biddingDetailsList = biddingDetailsRepository.findAll();
        assertThat(biddingDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        BiddingDetails testBiddingDetails = biddingDetailsList.get(biddingDetailsList.size() - 1);
        assertThat(testBiddingDetails.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBiddingDetails.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testBiddingDetails.getBiddingStatus()).isEqualTo(DEFAULT_BIDDING_STATUS);
        assertThat(testBiddingDetails.getMinPrice()).isEqualTo(DEFAULT_MIN_PRICE);
        assertThat(testBiddingDetails.getMaxPrice()).isEqualTo(DEFAULT_MAX_PRICE);
        assertThat(testBiddingDetails.getMinQuantityKg()).isEqualTo(DEFAULT_MIN_QUANTITY_KG);
        assertThat(testBiddingDetails.getMaxQuantityKg()).isEqualTo(DEFAULT_MAX_QUANTITY_KG);
        assertThat(testBiddingDetails.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createBiddingDetailsWithExistingId() throws Exception {
        // Create the BiddingDetails with an existing ID
        biddingDetails.setId(1L);
        BiddingDetailsDTO biddingDetailsDTO = biddingDetailsMapper.toDto(biddingDetails);

        int databaseSizeBeforeCreate = biddingDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBiddingDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(biddingDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BiddingDetails in the database
        List<BiddingDetails> biddingDetailsList = biddingDetailsRepository.findAll();
        assertThat(biddingDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBiddingDetails() throws Exception {
        // Initialize the database
        biddingDetailsRepository.saveAndFlush(biddingDetails);

        // Get all the biddingDetailsList
        restBiddingDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biddingDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.[*].biddingStatus").value(hasItem(DEFAULT_BIDDING_STATUS.toString())))
            .andExpect(jsonPath("$.[*].minPrice").value(hasItem(DEFAULT_MIN_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxPrice").value(hasItem(DEFAULT_MAX_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].minQuantityKg").value(hasItem(DEFAULT_MIN_QUANTITY_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].maxQuantityKg").value(hasItem(DEFAULT_MAX_QUANTITY_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getBiddingDetails() throws Exception {
        // Initialize the database
        biddingDetailsRepository.saveAndFlush(biddingDetails);

        // Get the biddingDetails
        restBiddingDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, biddingDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(biddingDetails.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE))
            .andExpect(jsonPath("$.biddingStatus").value(DEFAULT_BIDDING_STATUS.toString()))
            .andExpect(jsonPath("$.minPrice").value(DEFAULT_MIN_PRICE.doubleValue()))
            .andExpect(jsonPath("$.maxPrice").value(DEFAULT_MAX_PRICE.doubleValue()))
            .andExpect(jsonPath("$.minQuantityKg").value(DEFAULT_MIN_QUANTITY_KG.doubleValue()))
            .andExpect(jsonPath("$.maxQuantityKg").value(DEFAULT_MAX_QUANTITY_KG.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBiddingDetails() throws Exception {
        // Get the biddingDetails
        restBiddingDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBiddingDetails() throws Exception {
        // Initialize the database
        biddingDetailsRepository.saveAndFlush(biddingDetails);

        int databaseSizeBeforeUpdate = biddingDetailsRepository.findAll().size();

        // Update the biddingDetails
        BiddingDetails updatedBiddingDetails = biddingDetailsRepository.findById(biddingDetails.getId()).get();
        // Disconnect from session so that the updates on updatedBiddingDetails are not directly saved in db
        em.detach(updatedBiddingDetails);
        updatedBiddingDetails
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .biddingStatus(UPDATED_BIDDING_STATUS)
            .minPrice(UPDATED_MIN_PRICE)
            .maxPrice(UPDATED_MAX_PRICE)
            .minQuantityKg(UPDATED_MIN_QUANTITY_KG)
            .maxQuantityKg(UPDATED_MAX_QUANTITY_KG)
            .isActive(UPDATED_IS_ACTIVE);
        BiddingDetailsDTO biddingDetailsDTO = biddingDetailsMapper.toDto(updatedBiddingDetails);

        restBiddingDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, biddingDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(biddingDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the BiddingDetails in the database
        List<BiddingDetails> biddingDetailsList = biddingDetailsRepository.findAll();
        assertThat(biddingDetailsList).hasSize(databaseSizeBeforeUpdate);
        BiddingDetails testBiddingDetails = biddingDetailsList.get(biddingDetailsList.size() - 1);
        assertThat(testBiddingDetails.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBiddingDetails.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBiddingDetails.getBiddingStatus()).isEqualTo(UPDATED_BIDDING_STATUS);
        assertThat(testBiddingDetails.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testBiddingDetails.getMaxPrice()).isEqualTo(UPDATED_MAX_PRICE);
        assertThat(testBiddingDetails.getMinQuantityKg()).isEqualTo(UPDATED_MIN_QUANTITY_KG);
        assertThat(testBiddingDetails.getMaxQuantityKg()).isEqualTo(UPDATED_MAX_QUANTITY_KG);
        assertThat(testBiddingDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingBiddingDetails() throws Exception {
        int databaseSizeBeforeUpdate = biddingDetailsRepository.findAll().size();
        biddingDetails.setId(count.incrementAndGet());

        // Create the BiddingDetails
        BiddingDetailsDTO biddingDetailsDTO = biddingDetailsMapper.toDto(biddingDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBiddingDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, biddingDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(biddingDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BiddingDetails in the database
        List<BiddingDetails> biddingDetailsList = biddingDetailsRepository.findAll();
        assertThat(biddingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBiddingDetails() throws Exception {
        int databaseSizeBeforeUpdate = biddingDetailsRepository.findAll().size();
        biddingDetails.setId(count.incrementAndGet());

        // Create the BiddingDetails
        BiddingDetailsDTO biddingDetailsDTO = biddingDetailsMapper.toDto(biddingDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBiddingDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(biddingDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BiddingDetails in the database
        List<BiddingDetails> biddingDetailsList = biddingDetailsRepository.findAll();
        assertThat(biddingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBiddingDetails() throws Exception {
        int databaseSizeBeforeUpdate = biddingDetailsRepository.findAll().size();
        biddingDetails.setId(count.incrementAndGet());

        // Create the BiddingDetails
        BiddingDetailsDTO biddingDetailsDTO = biddingDetailsMapper.toDto(biddingDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBiddingDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(biddingDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BiddingDetails in the database
        List<BiddingDetails> biddingDetailsList = biddingDetailsRepository.findAll();
        assertThat(biddingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBiddingDetailsWithPatch() throws Exception {
        // Initialize the database
        biddingDetailsRepository.saveAndFlush(biddingDetails);

        int databaseSizeBeforeUpdate = biddingDetailsRepository.findAll().size();

        // Update the biddingDetails using partial update
        BiddingDetails partialUpdatedBiddingDetails = new BiddingDetails();
        partialUpdatedBiddingDetails.setId(biddingDetails.getId());

        partialUpdatedBiddingDetails
            .biddingStatus(UPDATED_BIDDING_STATUS)
            .minPrice(UPDATED_MIN_PRICE)
            .maxQuantityKg(UPDATED_MAX_QUANTITY_KG)
            .isActive(UPDATED_IS_ACTIVE);

        restBiddingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBiddingDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBiddingDetails))
            )
            .andExpect(status().isOk());

        // Validate the BiddingDetails in the database
        List<BiddingDetails> biddingDetailsList = biddingDetailsRepository.findAll();
        assertThat(biddingDetailsList).hasSize(databaseSizeBeforeUpdate);
        BiddingDetails testBiddingDetails = biddingDetailsList.get(biddingDetailsList.size() - 1);
        assertThat(testBiddingDetails.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBiddingDetails.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testBiddingDetails.getBiddingStatus()).isEqualTo(UPDATED_BIDDING_STATUS);
        assertThat(testBiddingDetails.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testBiddingDetails.getMaxPrice()).isEqualTo(DEFAULT_MAX_PRICE);
        assertThat(testBiddingDetails.getMinQuantityKg()).isEqualTo(DEFAULT_MIN_QUANTITY_KG);
        assertThat(testBiddingDetails.getMaxQuantityKg()).isEqualTo(UPDATED_MAX_QUANTITY_KG);
        assertThat(testBiddingDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateBiddingDetailsWithPatch() throws Exception {
        // Initialize the database
        biddingDetailsRepository.saveAndFlush(biddingDetails);

        int databaseSizeBeforeUpdate = biddingDetailsRepository.findAll().size();

        // Update the biddingDetails using partial update
        BiddingDetails partialUpdatedBiddingDetails = new BiddingDetails();
        partialUpdatedBiddingDetails.setId(biddingDetails.getId());

        partialUpdatedBiddingDetails
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .biddingStatus(UPDATED_BIDDING_STATUS)
            .minPrice(UPDATED_MIN_PRICE)
            .maxPrice(UPDATED_MAX_PRICE)
            .minQuantityKg(UPDATED_MIN_QUANTITY_KG)
            .maxQuantityKg(UPDATED_MAX_QUANTITY_KG)
            .isActive(UPDATED_IS_ACTIVE);

        restBiddingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBiddingDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBiddingDetails))
            )
            .andExpect(status().isOk());

        // Validate the BiddingDetails in the database
        List<BiddingDetails> biddingDetailsList = biddingDetailsRepository.findAll();
        assertThat(biddingDetailsList).hasSize(databaseSizeBeforeUpdate);
        BiddingDetails testBiddingDetails = biddingDetailsList.get(biddingDetailsList.size() - 1);
        assertThat(testBiddingDetails.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBiddingDetails.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBiddingDetails.getBiddingStatus()).isEqualTo(UPDATED_BIDDING_STATUS);
        assertThat(testBiddingDetails.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testBiddingDetails.getMaxPrice()).isEqualTo(UPDATED_MAX_PRICE);
        assertThat(testBiddingDetails.getMinQuantityKg()).isEqualTo(UPDATED_MIN_QUANTITY_KG);
        assertThat(testBiddingDetails.getMaxQuantityKg()).isEqualTo(UPDATED_MAX_QUANTITY_KG);
        assertThat(testBiddingDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingBiddingDetails() throws Exception {
        int databaseSizeBeforeUpdate = biddingDetailsRepository.findAll().size();
        biddingDetails.setId(count.incrementAndGet());

        // Create the BiddingDetails
        BiddingDetailsDTO biddingDetailsDTO = biddingDetailsMapper.toDto(biddingDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBiddingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, biddingDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(biddingDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BiddingDetails in the database
        List<BiddingDetails> biddingDetailsList = biddingDetailsRepository.findAll();
        assertThat(biddingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBiddingDetails() throws Exception {
        int databaseSizeBeforeUpdate = biddingDetailsRepository.findAll().size();
        biddingDetails.setId(count.incrementAndGet());

        // Create the BiddingDetails
        BiddingDetailsDTO biddingDetailsDTO = biddingDetailsMapper.toDto(biddingDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBiddingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(biddingDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BiddingDetails in the database
        List<BiddingDetails> biddingDetailsList = biddingDetailsRepository.findAll();
        assertThat(biddingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBiddingDetails() throws Exception {
        int databaseSizeBeforeUpdate = biddingDetailsRepository.findAll().size();
        biddingDetails.setId(count.incrementAndGet());

        // Create the BiddingDetails
        BiddingDetailsDTO biddingDetailsDTO = biddingDetailsMapper.toDto(biddingDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBiddingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(biddingDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BiddingDetails in the database
        List<BiddingDetails> biddingDetailsList = biddingDetailsRepository.findAll();
        assertThat(biddingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBiddingDetails() throws Exception {
        // Initialize the database
        biddingDetailsRepository.saveAndFlush(biddingDetails);

        int databaseSizeBeforeDelete = biddingDetailsRepository.findAll().size();

        // Delete the biddingDetails
        restBiddingDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, biddingDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BiddingDetails> biddingDetailsList = biddingDetailsRepository.findAll();
        assertThat(biddingDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
