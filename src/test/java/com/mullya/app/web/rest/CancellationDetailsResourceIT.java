package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.CancellationDetails;
import com.mullya.app.domain.enumeration.CancelationType;
import com.mullya.app.domain.enumeration.CancellationStatus;
import com.mullya.app.repository.CancellationDetailsRepository;
import com.mullya.app.service.dto.CancellationDetailsDTO;
import com.mullya.app.service.mapper.CancellationDetailsMapper;
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
 * Integration tests for the {@link CancellationDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CancellationDetailsResourceIT {

    private static final CancelationType DEFAULT_CANCELATION_TYPE = CancelationType.ByFarmer;
    private static final CancelationType UPDATED_CANCELATION_TYPE = CancelationType.ByAgent;

    private static final String DEFAULT_CANCELLATION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_CANCELLATION_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_CANCELLATION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_CANCELLATION_DATE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CANCELLATION_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CANCELLATION_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REFUND_ID = "AAAAAAAAAA";
    private static final String UPDATED_REFUND_ID = "BBBBBBBBBB";

    private static final CancellationStatus DEFAULT_CANCELLATION_STATUS = CancellationStatus.Init;
    private static final CancellationStatus UPDATED_CANCELLATION_STATUS = CancellationStatus.Approved;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cancellation-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CancellationDetailsRepository cancellationDetailsRepository;

    @Autowired
    private CancellationDetailsMapper cancellationDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCancellationDetailsMockMvc;

    private CancellationDetails cancellationDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CancellationDetails createEntity(EntityManager em) {
        CancellationDetails cancellationDetails = new CancellationDetails()
            .cancelationType(DEFAULT_CANCELATION_TYPE)
            .cancellationReason(DEFAULT_CANCELLATION_REASON)
            .cancellationDate(DEFAULT_CANCELLATION_DATE)
            .cancellationTime(DEFAULT_CANCELLATION_TIME)
            .refundId(DEFAULT_REFUND_ID)
            .cancellationStatus(DEFAULT_CANCELLATION_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return cancellationDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CancellationDetails createUpdatedEntity(EntityManager em) {
        CancellationDetails cancellationDetails = new CancellationDetails()
            .cancelationType(UPDATED_CANCELATION_TYPE)
            .cancellationReason(UPDATED_CANCELLATION_REASON)
            .cancellationDate(UPDATED_CANCELLATION_DATE)
            .cancellationTime(UPDATED_CANCELLATION_TIME)
            .refundId(UPDATED_REFUND_ID)
            .cancellationStatus(UPDATED_CANCELLATION_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return cancellationDetails;
    }

    @BeforeEach
    public void initTest() {
        cancellationDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createCancellationDetails() throws Exception {
        int databaseSizeBeforeCreate = cancellationDetailsRepository.findAll().size();
        // Create the CancellationDetails
        CancellationDetailsDTO cancellationDetailsDTO = cancellationDetailsMapper.toDto(cancellationDetails);
        restCancellationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cancellationDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CancellationDetails in the database
        List<CancellationDetails> cancellationDetailsList = cancellationDetailsRepository.findAll();
        assertThat(cancellationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        CancellationDetails testCancellationDetails = cancellationDetailsList.get(cancellationDetailsList.size() - 1);
        assertThat(testCancellationDetails.getCancelationType()).isEqualTo(DEFAULT_CANCELATION_TYPE);
        assertThat(testCancellationDetails.getCancellationReason()).isEqualTo(DEFAULT_CANCELLATION_REASON);
        assertThat(testCancellationDetails.getCancellationDate()).isEqualTo(DEFAULT_CANCELLATION_DATE);
        assertThat(testCancellationDetails.getCancellationTime()).isEqualTo(DEFAULT_CANCELLATION_TIME);
        assertThat(testCancellationDetails.getRefundId()).isEqualTo(DEFAULT_REFUND_ID);
        assertThat(testCancellationDetails.getCancellationStatus()).isEqualTo(DEFAULT_CANCELLATION_STATUS);
        assertThat(testCancellationDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCancellationDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCancellationDetails.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testCancellationDetails.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createCancellationDetailsWithExistingId() throws Exception {
        // Create the CancellationDetails with an existing ID
        cancellationDetails.setId(1L);
        CancellationDetailsDTO cancellationDetailsDTO = cancellationDetailsMapper.toDto(cancellationDetails);

        int databaseSizeBeforeCreate = cancellationDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCancellationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cancellationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CancellationDetails in the database
        List<CancellationDetails> cancellationDetailsList = cancellationDetailsRepository.findAll();
        assertThat(cancellationDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCancellationDetails() throws Exception {
        // Initialize the database
        cancellationDetailsRepository.saveAndFlush(cancellationDetails);

        // Get all the cancellationDetailsList
        restCancellationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cancellationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].cancelationType").value(hasItem(DEFAULT_CANCELATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cancellationReason").value(hasItem(DEFAULT_CANCELLATION_REASON)))
            .andExpect(jsonPath("$.[*].cancellationDate").value(hasItem(DEFAULT_CANCELLATION_DATE)))
            .andExpect(jsonPath("$.[*].cancellationTime").value(hasItem(DEFAULT_CANCELLATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].refundId").value(hasItem(DEFAULT_REFUND_ID)))
            .andExpect(jsonPath("$.[*].cancellationStatus").value(hasItem(DEFAULT_CANCELLATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getCancellationDetails() throws Exception {
        // Initialize the database
        cancellationDetailsRepository.saveAndFlush(cancellationDetails);

        // Get the cancellationDetails
        restCancellationDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, cancellationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cancellationDetails.getId().intValue()))
            .andExpect(jsonPath("$.cancelationType").value(DEFAULT_CANCELATION_TYPE.toString()))
            .andExpect(jsonPath("$.cancellationReason").value(DEFAULT_CANCELLATION_REASON))
            .andExpect(jsonPath("$.cancellationDate").value(DEFAULT_CANCELLATION_DATE))
            .andExpect(jsonPath("$.cancellationTime").value(DEFAULT_CANCELLATION_TIME.toString()))
            .andExpect(jsonPath("$.refundId").value(DEFAULT_REFUND_ID))
            .andExpect(jsonPath("$.cancellationStatus").value(DEFAULT_CANCELLATION_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingCancellationDetails() throws Exception {
        // Get the cancellationDetails
        restCancellationDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCancellationDetails() throws Exception {
        // Initialize the database
        cancellationDetailsRepository.saveAndFlush(cancellationDetails);

        int databaseSizeBeforeUpdate = cancellationDetailsRepository.findAll().size();

        // Update the cancellationDetails
        CancellationDetails updatedCancellationDetails = cancellationDetailsRepository.findById(cancellationDetails.getId()).get();
        // Disconnect from session so that the updates on updatedCancellationDetails are not directly saved in db
        em.detach(updatedCancellationDetails);
        updatedCancellationDetails
            .cancelationType(UPDATED_CANCELATION_TYPE)
            .cancellationReason(UPDATED_CANCELLATION_REASON)
            .cancellationDate(UPDATED_CANCELLATION_DATE)
            .cancellationTime(UPDATED_CANCELLATION_TIME)
            .refundId(UPDATED_REFUND_ID)
            .cancellationStatus(UPDATED_CANCELLATION_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        CancellationDetailsDTO cancellationDetailsDTO = cancellationDetailsMapper.toDto(updatedCancellationDetails);

        restCancellationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cancellationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cancellationDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the CancellationDetails in the database
        List<CancellationDetails> cancellationDetailsList = cancellationDetailsRepository.findAll();
        assertThat(cancellationDetailsList).hasSize(databaseSizeBeforeUpdate);
        CancellationDetails testCancellationDetails = cancellationDetailsList.get(cancellationDetailsList.size() - 1);
        assertThat(testCancellationDetails.getCancelationType()).isEqualTo(UPDATED_CANCELATION_TYPE);
        assertThat(testCancellationDetails.getCancellationReason()).isEqualTo(UPDATED_CANCELLATION_REASON);
        assertThat(testCancellationDetails.getCancellationDate()).isEqualTo(UPDATED_CANCELLATION_DATE);
        assertThat(testCancellationDetails.getCancellationTime()).isEqualTo(UPDATED_CANCELLATION_TIME);
        assertThat(testCancellationDetails.getRefundId()).isEqualTo(UPDATED_REFUND_ID);
        assertThat(testCancellationDetails.getCancellationStatus()).isEqualTo(UPDATED_CANCELLATION_STATUS);
        assertThat(testCancellationDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCancellationDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCancellationDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testCancellationDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingCancellationDetails() throws Exception {
        int databaseSizeBeforeUpdate = cancellationDetailsRepository.findAll().size();
        cancellationDetails.setId(count.incrementAndGet());

        // Create the CancellationDetails
        CancellationDetailsDTO cancellationDetailsDTO = cancellationDetailsMapper.toDto(cancellationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCancellationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cancellationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cancellationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CancellationDetails in the database
        List<CancellationDetails> cancellationDetailsList = cancellationDetailsRepository.findAll();
        assertThat(cancellationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCancellationDetails() throws Exception {
        int databaseSizeBeforeUpdate = cancellationDetailsRepository.findAll().size();
        cancellationDetails.setId(count.incrementAndGet());

        // Create the CancellationDetails
        CancellationDetailsDTO cancellationDetailsDTO = cancellationDetailsMapper.toDto(cancellationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCancellationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cancellationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CancellationDetails in the database
        List<CancellationDetails> cancellationDetailsList = cancellationDetailsRepository.findAll();
        assertThat(cancellationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCancellationDetails() throws Exception {
        int databaseSizeBeforeUpdate = cancellationDetailsRepository.findAll().size();
        cancellationDetails.setId(count.incrementAndGet());

        // Create the CancellationDetails
        CancellationDetailsDTO cancellationDetailsDTO = cancellationDetailsMapper.toDto(cancellationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCancellationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cancellationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CancellationDetails in the database
        List<CancellationDetails> cancellationDetailsList = cancellationDetailsRepository.findAll();
        assertThat(cancellationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCancellationDetailsWithPatch() throws Exception {
        // Initialize the database
        cancellationDetailsRepository.saveAndFlush(cancellationDetails);

        int databaseSizeBeforeUpdate = cancellationDetailsRepository.findAll().size();

        // Update the cancellationDetails using partial update
        CancellationDetails partialUpdatedCancellationDetails = new CancellationDetails();
        partialUpdatedCancellationDetails.setId(cancellationDetails.getId());

        partialUpdatedCancellationDetails
            .refundId(UPDATED_REFUND_ID)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restCancellationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCancellationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCancellationDetails))
            )
            .andExpect(status().isOk());

        // Validate the CancellationDetails in the database
        List<CancellationDetails> cancellationDetailsList = cancellationDetailsRepository.findAll();
        assertThat(cancellationDetailsList).hasSize(databaseSizeBeforeUpdate);
        CancellationDetails testCancellationDetails = cancellationDetailsList.get(cancellationDetailsList.size() - 1);
        assertThat(testCancellationDetails.getCancelationType()).isEqualTo(DEFAULT_CANCELATION_TYPE);
        assertThat(testCancellationDetails.getCancellationReason()).isEqualTo(DEFAULT_CANCELLATION_REASON);
        assertThat(testCancellationDetails.getCancellationDate()).isEqualTo(DEFAULT_CANCELLATION_DATE);
        assertThat(testCancellationDetails.getCancellationTime()).isEqualTo(DEFAULT_CANCELLATION_TIME);
        assertThat(testCancellationDetails.getRefundId()).isEqualTo(UPDATED_REFUND_ID);
        assertThat(testCancellationDetails.getCancellationStatus()).isEqualTo(DEFAULT_CANCELLATION_STATUS);
        assertThat(testCancellationDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCancellationDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCancellationDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testCancellationDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateCancellationDetailsWithPatch() throws Exception {
        // Initialize the database
        cancellationDetailsRepository.saveAndFlush(cancellationDetails);

        int databaseSizeBeforeUpdate = cancellationDetailsRepository.findAll().size();

        // Update the cancellationDetails using partial update
        CancellationDetails partialUpdatedCancellationDetails = new CancellationDetails();
        partialUpdatedCancellationDetails.setId(cancellationDetails.getId());

        partialUpdatedCancellationDetails
            .cancelationType(UPDATED_CANCELATION_TYPE)
            .cancellationReason(UPDATED_CANCELLATION_REASON)
            .cancellationDate(UPDATED_CANCELLATION_DATE)
            .cancellationTime(UPDATED_CANCELLATION_TIME)
            .refundId(UPDATED_REFUND_ID)
            .cancellationStatus(UPDATED_CANCELLATION_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restCancellationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCancellationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCancellationDetails))
            )
            .andExpect(status().isOk());

        // Validate the CancellationDetails in the database
        List<CancellationDetails> cancellationDetailsList = cancellationDetailsRepository.findAll();
        assertThat(cancellationDetailsList).hasSize(databaseSizeBeforeUpdate);
        CancellationDetails testCancellationDetails = cancellationDetailsList.get(cancellationDetailsList.size() - 1);
        assertThat(testCancellationDetails.getCancelationType()).isEqualTo(UPDATED_CANCELATION_TYPE);
        assertThat(testCancellationDetails.getCancellationReason()).isEqualTo(UPDATED_CANCELLATION_REASON);
        assertThat(testCancellationDetails.getCancellationDate()).isEqualTo(UPDATED_CANCELLATION_DATE);
        assertThat(testCancellationDetails.getCancellationTime()).isEqualTo(UPDATED_CANCELLATION_TIME);
        assertThat(testCancellationDetails.getRefundId()).isEqualTo(UPDATED_REFUND_ID);
        assertThat(testCancellationDetails.getCancellationStatus()).isEqualTo(UPDATED_CANCELLATION_STATUS);
        assertThat(testCancellationDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCancellationDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCancellationDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testCancellationDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingCancellationDetails() throws Exception {
        int databaseSizeBeforeUpdate = cancellationDetailsRepository.findAll().size();
        cancellationDetails.setId(count.incrementAndGet());

        // Create the CancellationDetails
        CancellationDetailsDTO cancellationDetailsDTO = cancellationDetailsMapper.toDto(cancellationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCancellationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cancellationDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cancellationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CancellationDetails in the database
        List<CancellationDetails> cancellationDetailsList = cancellationDetailsRepository.findAll();
        assertThat(cancellationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCancellationDetails() throws Exception {
        int databaseSizeBeforeUpdate = cancellationDetailsRepository.findAll().size();
        cancellationDetails.setId(count.incrementAndGet());

        // Create the CancellationDetails
        CancellationDetailsDTO cancellationDetailsDTO = cancellationDetailsMapper.toDto(cancellationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCancellationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cancellationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CancellationDetails in the database
        List<CancellationDetails> cancellationDetailsList = cancellationDetailsRepository.findAll();
        assertThat(cancellationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCancellationDetails() throws Exception {
        int databaseSizeBeforeUpdate = cancellationDetailsRepository.findAll().size();
        cancellationDetails.setId(count.incrementAndGet());

        // Create the CancellationDetails
        CancellationDetailsDTO cancellationDetailsDTO = cancellationDetailsMapper.toDto(cancellationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCancellationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cancellationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CancellationDetails in the database
        List<CancellationDetails> cancellationDetailsList = cancellationDetailsRepository.findAll();
        assertThat(cancellationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCancellationDetails() throws Exception {
        // Initialize the database
        cancellationDetailsRepository.saveAndFlush(cancellationDetails);

        int databaseSizeBeforeDelete = cancellationDetailsRepository.findAll().size();

        // Delete the cancellationDetails
        restCancellationDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, cancellationDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CancellationDetails> cancellationDetailsList = cancellationDetailsRepository.findAll();
        assertThat(cancellationDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
