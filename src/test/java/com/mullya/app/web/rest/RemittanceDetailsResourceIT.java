package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.RemittanceDetails;
import com.mullya.app.domain.enumeration.PGType;
import com.mullya.app.domain.enumeration.PaymentStatus;
import com.mullya.app.domain.enumeration.PaymentType;
import com.mullya.app.repository.RemittanceDetailsRepository;
import com.mullya.app.service.dto.RemittanceDetailsDTO;
import com.mullya.app.service.mapper.RemittanceDetailsMapper;
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
 * Integration tests for the {@link RemittanceDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RemittanceDetailsResourceIT {

    private static final PaymentType DEFAULT_PAYMENT_TYPE = PaymentType.Cheque;
    private static final PaymentType UPDATED_PAYMENT_TYPE = PaymentType.Draft;

    private static final PGType DEFAULT_ONLINE_PG_TYPE = PGType.RazorPay;
    private static final PGType UPDATED_ONLINE_PG_TYPE = PGType.PayTM;

    private static final String DEFAULT_PG_TXN_ID = "AAAAAAAAAA";
    private static final String UPDATED_PG_TXN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PG_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PG_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_OFFLINE_TXN_ID = "AAAAAAAAAA";
    private static final String UPDATED_OFFLINE_TXN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OFFLINE_TXN_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_OFFLINE_TXN_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_OFFLINE_TXN_GIVEN_BY = "AAAAAAAAAA";
    private static final String UPDATED_OFFLINE_TXN_GIVEN_BY = "BBBBBBBBBB";

    private static final String DEFAULT_OFFLINE_TXN_CLEARING_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_OFFLINE_TXN_CLEARING_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_REMITTANCE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_REMITTANCE_DATE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REMITTANCE_INIT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REMITTANCE_INIT_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_REMITTANCE_UPDATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REMITTANCE_UPDATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.Pending;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.Initiated;

    private static final String ENTITY_API_URL = "/api/remittance-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RemittanceDetailsRepository remittanceDetailsRepository;

    @Autowired
    private RemittanceDetailsMapper remittanceDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRemittanceDetailsMockMvc;

    private RemittanceDetails remittanceDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RemittanceDetails createEntity(EntityManager em) {
        RemittanceDetails remittanceDetails = new RemittanceDetails()
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .onlinePgType(DEFAULT_ONLINE_PG_TYPE)
            .pgTxnId(DEFAULT_PG_TXN_ID)
            .pgStatus(DEFAULT_PG_STATUS)
            .offlineTxnId(DEFAULT_OFFLINE_TXN_ID)
            .offlineTxnDetails(DEFAULT_OFFLINE_TXN_DETAILS)
            .offlineTxnGivenBy(DEFAULT_OFFLINE_TXN_GIVEN_BY)
            .offlineTxnClearingStatus(DEFAULT_OFFLINE_TXN_CLEARING_STATUS)
            .remittanceDate(DEFAULT_REMITTANCE_DATE)
            .remittanceInitTime(DEFAULT_REMITTANCE_INIT_TIME)
            .remittanceUpdateTime(DEFAULT_REMITTANCE_UPDATE_TIME)
            .paymentStatus(DEFAULT_PAYMENT_STATUS);
        return remittanceDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RemittanceDetails createUpdatedEntity(EntityManager em) {
        RemittanceDetails remittanceDetails = new RemittanceDetails()
            .paymentType(UPDATED_PAYMENT_TYPE)
            .onlinePgType(UPDATED_ONLINE_PG_TYPE)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgStatus(UPDATED_PG_STATUS)
            .offlineTxnId(UPDATED_OFFLINE_TXN_ID)
            .offlineTxnDetails(UPDATED_OFFLINE_TXN_DETAILS)
            .offlineTxnGivenBy(UPDATED_OFFLINE_TXN_GIVEN_BY)
            .offlineTxnClearingStatus(UPDATED_OFFLINE_TXN_CLEARING_STATUS)
            .remittanceDate(UPDATED_REMITTANCE_DATE)
            .remittanceInitTime(UPDATED_REMITTANCE_INIT_TIME)
            .remittanceUpdateTime(UPDATED_REMITTANCE_UPDATE_TIME)
            .paymentStatus(UPDATED_PAYMENT_STATUS);
        return remittanceDetails;
    }

    @BeforeEach
    public void initTest() {
        remittanceDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createRemittanceDetails() throws Exception {
        int databaseSizeBeforeCreate = remittanceDetailsRepository.findAll().size();
        // Create the RemittanceDetails
        RemittanceDetailsDTO remittanceDetailsDTO = remittanceDetailsMapper.toDto(remittanceDetails);
        restRemittanceDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remittanceDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RemittanceDetails in the database
        List<RemittanceDetails> remittanceDetailsList = remittanceDetailsRepository.findAll();
        assertThat(remittanceDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        RemittanceDetails testRemittanceDetails = remittanceDetailsList.get(remittanceDetailsList.size() - 1);
        assertThat(testRemittanceDetails.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testRemittanceDetails.getOnlinePgType()).isEqualTo(DEFAULT_ONLINE_PG_TYPE);
        assertThat(testRemittanceDetails.getPgTxnId()).isEqualTo(DEFAULT_PG_TXN_ID);
        assertThat(testRemittanceDetails.getPgStatus()).isEqualTo(DEFAULT_PG_STATUS);
        assertThat(testRemittanceDetails.getOfflineTxnId()).isEqualTo(DEFAULT_OFFLINE_TXN_ID);
        assertThat(testRemittanceDetails.getOfflineTxnDetails()).isEqualTo(DEFAULT_OFFLINE_TXN_DETAILS);
        assertThat(testRemittanceDetails.getOfflineTxnGivenBy()).isEqualTo(DEFAULT_OFFLINE_TXN_GIVEN_BY);
        assertThat(testRemittanceDetails.getOfflineTxnClearingStatus()).isEqualTo(DEFAULT_OFFLINE_TXN_CLEARING_STATUS);
        assertThat(testRemittanceDetails.getRemittanceDate()).isEqualTo(DEFAULT_REMITTANCE_DATE);
        assertThat(testRemittanceDetails.getRemittanceInitTime()).isEqualTo(DEFAULT_REMITTANCE_INIT_TIME);
        assertThat(testRemittanceDetails.getRemittanceUpdateTime()).isEqualTo(DEFAULT_REMITTANCE_UPDATE_TIME);
        assertThat(testRemittanceDetails.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void createRemittanceDetailsWithExistingId() throws Exception {
        // Create the RemittanceDetails with an existing ID
        remittanceDetails.setId(1L);
        RemittanceDetailsDTO remittanceDetailsDTO = remittanceDetailsMapper.toDto(remittanceDetails);

        int databaseSizeBeforeCreate = remittanceDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemittanceDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remittanceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemittanceDetails in the database
        List<RemittanceDetails> remittanceDetailsList = remittanceDetailsRepository.findAll();
        assertThat(remittanceDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRemittanceDetails() throws Exception {
        // Initialize the database
        remittanceDetailsRepository.saveAndFlush(remittanceDetails);

        // Get all the remittanceDetailsList
        restRemittanceDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remittanceDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].onlinePgType").value(hasItem(DEFAULT_ONLINE_PG_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pgTxnId").value(hasItem(DEFAULT_PG_TXN_ID)))
            .andExpect(jsonPath("$.[*].pgStatus").value(hasItem(DEFAULT_PG_STATUS)))
            .andExpect(jsonPath("$.[*].offlineTxnId").value(hasItem(DEFAULT_OFFLINE_TXN_ID)))
            .andExpect(jsonPath("$.[*].offlineTxnDetails").value(hasItem(DEFAULT_OFFLINE_TXN_DETAILS)))
            .andExpect(jsonPath("$.[*].offlineTxnGivenBy").value(hasItem(DEFAULT_OFFLINE_TXN_GIVEN_BY)))
            .andExpect(jsonPath("$.[*].offlineTxnClearingStatus").value(hasItem(DEFAULT_OFFLINE_TXN_CLEARING_STATUS)))
            .andExpect(jsonPath("$.[*].remittanceDate").value(hasItem(DEFAULT_REMITTANCE_DATE)))
            .andExpect(jsonPath("$.[*].remittanceInitTime").value(hasItem(DEFAULT_REMITTANCE_INIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].remittanceUpdateTime").value(hasItem(DEFAULT_REMITTANCE_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getRemittanceDetails() throws Exception {
        // Initialize the database
        remittanceDetailsRepository.saveAndFlush(remittanceDetails);

        // Get the remittanceDetails
        restRemittanceDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, remittanceDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(remittanceDetails.getId().intValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.onlinePgType").value(DEFAULT_ONLINE_PG_TYPE.toString()))
            .andExpect(jsonPath("$.pgTxnId").value(DEFAULT_PG_TXN_ID))
            .andExpect(jsonPath("$.pgStatus").value(DEFAULT_PG_STATUS))
            .andExpect(jsonPath("$.offlineTxnId").value(DEFAULT_OFFLINE_TXN_ID))
            .andExpect(jsonPath("$.offlineTxnDetails").value(DEFAULT_OFFLINE_TXN_DETAILS))
            .andExpect(jsonPath("$.offlineTxnGivenBy").value(DEFAULT_OFFLINE_TXN_GIVEN_BY))
            .andExpect(jsonPath("$.offlineTxnClearingStatus").value(DEFAULT_OFFLINE_TXN_CLEARING_STATUS))
            .andExpect(jsonPath("$.remittanceDate").value(DEFAULT_REMITTANCE_DATE))
            .andExpect(jsonPath("$.remittanceInitTime").value(DEFAULT_REMITTANCE_INIT_TIME.toString()))
            .andExpect(jsonPath("$.remittanceUpdateTime").value(DEFAULT_REMITTANCE_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRemittanceDetails() throws Exception {
        // Get the remittanceDetails
        restRemittanceDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRemittanceDetails() throws Exception {
        // Initialize the database
        remittanceDetailsRepository.saveAndFlush(remittanceDetails);

        int databaseSizeBeforeUpdate = remittanceDetailsRepository.findAll().size();

        // Update the remittanceDetails
        RemittanceDetails updatedRemittanceDetails = remittanceDetailsRepository.findById(remittanceDetails.getId()).get();
        // Disconnect from session so that the updates on updatedRemittanceDetails are not directly saved in db
        em.detach(updatedRemittanceDetails);
        updatedRemittanceDetails
            .paymentType(UPDATED_PAYMENT_TYPE)
            .onlinePgType(UPDATED_ONLINE_PG_TYPE)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgStatus(UPDATED_PG_STATUS)
            .offlineTxnId(UPDATED_OFFLINE_TXN_ID)
            .offlineTxnDetails(UPDATED_OFFLINE_TXN_DETAILS)
            .offlineTxnGivenBy(UPDATED_OFFLINE_TXN_GIVEN_BY)
            .offlineTxnClearingStatus(UPDATED_OFFLINE_TXN_CLEARING_STATUS)
            .remittanceDate(UPDATED_REMITTANCE_DATE)
            .remittanceInitTime(UPDATED_REMITTANCE_INIT_TIME)
            .remittanceUpdateTime(UPDATED_REMITTANCE_UPDATE_TIME)
            .paymentStatus(UPDATED_PAYMENT_STATUS);
        RemittanceDetailsDTO remittanceDetailsDTO = remittanceDetailsMapper.toDto(updatedRemittanceDetails);

        restRemittanceDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remittanceDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remittanceDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the RemittanceDetails in the database
        List<RemittanceDetails> remittanceDetailsList = remittanceDetailsRepository.findAll();
        assertThat(remittanceDetailsList).hasSize(databaseSizeBeforeUpdate);
        RemittanceDetails testRemittanceDetails = remittanceDetailsList.get(remittanceDetailsList.size() - 1);
        assertThat(testRemittanceDetails.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testRemittanceDetails.getOnlinePgType()).isEqualTo(UPDATED_ONLINE_PG_TYPE);
        assertThat(testRemittanceDetails.getPgTxnId()).isEqualTo(UPDATED_PG_TXN_ID);
        assertThat(testRemittanceDetails.getPgStatus()).isEqualTo(UPDATED_PG_STATUS);
        assertThat(testRemittanceDetails.getOfflineTxnId()).isEqualTo(UPDATED_OFFLINE_TXN_ID);
        assertThat(testRemittanceDetails.getOfflineTxnDetails()).isEqualTo(UPDATED_OFFLINE_TXN_DETAILS);
        assertThat(testRemittanceDetails.getOfflineTxnGivenBy()).isEqualTo(UPDATED_OFFLINE_TXN_GIVEN_BY);
        assertThat(testRemittanceDetails.getOfflineTxnClearingStatus()).isEqualTo(UPDATED_OFFLINE_TXN_CLEARING_STATUS);
        assertThat(testRemittanceDetails.getRemittanceDate()).isEqualTo(UPDATED_REMITTANCE_DATE);
        assertThat(testRemittanceDetails.getRemittanceInitTime()).isEqualTo(UPDATED_REMITTANCE_INIT_TIME);
        assertThat(testRemittanceDetails.getRemittanceUpdateTime()).isEqualTo(UPDATED_REMITTANCE_UPDATE_TIME);
        assertThat(testRemittanceDetails.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingRemittanceDetails() throws Exception {
        int databaseSizeBeforeUpdate = remittanceDetailsRepository.findAll().size();
        remittanceDetails.setId(count.incrementAndGet());

        // Create the RemittanceDetails
        RemittanceDetailsDTO remittanceDetailsDTO = remittanceDetailsMapper.toDto(remittanceDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemittanceDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remittanceDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remittanceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemittanceDetails in the database
        List<RemittanceDetails> remittanceDetailsList = remittanceDetailsRepository.findAll();
        assertThat(remittanceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRemittanceDetails() throws Exception {
        int databaseSizeBeforeUpdate = remittanceDetailsRepository.findAll().size();
        remittanceDetails.setId(count.incrementAndGet());

        // Create the RemittanceDetails
        RemittanceDetailsDTO remittanceDetailsDTO = remittanceDetailsMapper.toDto(remittanceDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemittanceDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remittanceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemittanceDetails in the database
        List<RemittanceDetails> remittanceDetailsList = remittanceDetailsRepository.findAll();
        assertThat(remittanceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRemittanceDetails() throws Exception {
        int databaseSizeBeforeUpdate = remittanceDetailsRepository.findAll().size();
        remittanceDetails.setId(count.incrementAndGet());

        // Create the RemittanceDetails
        RemittanceDetailsDTO remittanceDetailsDTO = remittanceDetailsMapper.toDto(remittanceDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemittanceDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remittanceDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RemittanceDetails in the database
        List<RemittanceDetails> remittanceDetailsList = remittanceDetailsRepository.findAll();
        assertThat(remittanceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRemittanceDetailsWithPatch() throws Exception {
        // Initialize the database
        remittanceDetailsRepository.saveAndFlush(remittanceDetails);

        int databaseSizeBeforeUpdate = remittanceDetailsRepository.findAll().size();

        // Update the remittanceDetails using partial update
        RemittanceDetails partialUpdatedRemittanceDetails = new RemittanceDetails();
        partialUpdatedRemittanceDetails.setId(remittanceDetails.getId());

        partialUpdatedRemittanceDetails
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgStatus(UPDATED_PG_STATUS)
            .offlineTxnDetails(UPDATED_OFFLINE_TXN_DETAILS)
            .offlineTxnGivenBy(UPDATED_OFFLINE_TXN_GIVEN_BY)
            .remittanceDate(UPDATED_REMITTANCE_DATE)
            .remittanceUpdateTime(UPDATED_REMITTANCE_UPDATE_TIME);

        restRemittanceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemittanceDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRemittanceDetails))
            )
            .andExpect(status().isOk());

        // Validate the RemittanceDetails in the database
        List<RemittanceDetails> remittanceDetailsList = remittanceDetailsRepository.findAll();
        assertThat(remittanceDetailsList).hasSize(databaseSizeBeforeUpdate);
        RemittanceDetails testRemittanceDetails = remittanceDetailsList.get(remittanceDetailsList.size() - 1);
        assertThat(testRemittanceDetails.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testRemittanceDetails.getOnlinePgType()).isEqualTo(DEFAULT_ONLINE_PG_TYPE);
        assertThat(testRemittanceDetails.getPgTxnId()).isEqualTo(UPDATED_PG_TXN_ID);
        assertThat(testRemittanceDetails.getPgStatus()).isEqualTo(UPDATED_PG_STATUS);
        assertThat(testRemittanceDetails.getOfflineTxnId()).isEqualTo(DEFAULT_OFFLINE_TXN_ID);
        assertThat(testRemittanceDetails.getOfflineTxnDetails()).isEqualTo(UPDATED_OFFLINE_TXN_DETAILS);
        assertThat(testRemittanceDetails.getOfflineTxnGivenBy()).isEqualTo(UPDATED_OFFLINE_TXN_GIVEN_BY);
        assertThat(testRemittanceDetails.getOfflineTxnClearingStatus()).isEqualTo(DEFAULT_OFFLINE_TXN_CLEARING_STATUS);
        assertThat(testRemittanceDetails.getRemittanceDate()).isEqualTo(UPDATED_REMITTANCE_DATE);
        assertThat(testRemittanceDetails.getRemittanceInitTime()).isEqualTo(DEFAULT_REMITTANCE_INIT_TIME);
        assertThat(testRemittanceDetails.getRemittanceUpdateTime()).isEqualTo(UPDATED_REMITTANCE_UPDATE_TIME);
        assertThat(testRemittanceDetails.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateRemittanceDetailsWithPatch() throws Exception {
        // Initialize the database
        remittanceDetailsRepository.saveAndFlush(remittanceDetails);

        int databaseSizeBeforeUpdate = remittanceDetailsRepository.findAll().size();

        // Update the remittanceDetails using partial update
        RemittanceDetails partialUpdatedRemittanceDetails = new RemittanceDetails();
        partialUpdatedRemittanceDetails.setId(remittanceDetails.getId());

        partialUpdatedRemittanceDetails
            .paymentType(UPDATED_PAYMENT_TYPE)
            .onlinePgType(UPDATED_ONLINE_PG_TYPE)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgStatus(UPDATED_PG_STATUS)
            .offlineTxnId(UPDATED_OFFLINE_TXN_ID)
            .offlineTxnDetails(UPDATED_OFFLINE_TXN_DETAILS)
            .offlineTxnGivenBy(UPDATED_OFFLINE_TXN_GIVEN_BY)
            .offlineTxnClearingStatus(UPDATED_OFFLINE_TXN_CLEARING_STATUS)
            .remittanceDate(UPDATED_REMITTANCE_DATE)
            .remittanceInitTime(UPDATED_REMITTANCE_INIT_TIME)
            .remittanceUpdateTime(UPDATED_REMITTANCE_UPDATE_TIME)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restRemittanceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemittanceDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRemittanceDetails))
            )
            .andExpect(status().isOk());

        // Validate the RemittanceDetails in the database
        List<RemittanceDetails> remittanceDetailsList = remittanceDetailsRepository.findAll();
        assertThat(remittanceDetailsList).hasSize(databaseSizeBeforeUpdate);
        RemittanceDetails testRemittanceDetails = remittanceDetailsList.get(remittanceDetailsList.size() - 1);
        assertThat(testRemittanceDetails.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testRemittanceDetails.getOnlinePgType()).isEqualTo(UPDATED_ONLINE_PG_TYPE);
        assertThat(testRemittanceDetails.getPgTxnId()).isEqualTo(UPDATED_PG_TXN_ID);
        assertThat(testRemittanceDetails.getPgStatus()).isEqualTo(UPDATED_PG_STATUS);
        assertThat(testRemittanceDetails.getOfflineTxnId()).isEqualTo(UPDATED_OFFLINE_TXN_ID);
        assertThat(testRemittanceDetails.getOfflineTxnDetails()).isEqualTo(UPDATED_OFFLINE_TXN_DETAILS);
        assertThat(testRemittanceDetails.getOfflineTxnGivenBy()).isEqualTo(UPDATED_OFFLINE_TXN_GIVEN_BY);
        assertThat(testRemittanceDetails.getOfflineTxnClearingStatus()).isEqualTo(UPDATED_OFFLINE_TXN_CLEARING_STATUS);
        assertThat(testRemittanceDetails.getRemittanceDate()).isEqualTo(UPDATED_REMITTANCE_DATE);
        assertThat(testRemittanceDetails.getRemittanceInitTime()).isEqualTo(UPDATED_REMITTANCE_INIT_TIME);
        assertThat(testRemittanceDetails.getRemittanceUpdateTime()).isEqualTo(UPDATED_REMITTANCE_UPDATE_TIME);
        assertThat(testRemittanceDetails.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingRemittanceDetails() throws Exception {
        int databaseSizeBeforeUpdate = remittanceDetailsRepository.findAll().size();
        remittanceDetails.setId(count.incrementAndGet());

        // Create the RemittanceDetails
        RemittanceDetailsDTO remittanceDetailsDTO = remittanceDetailsMapper.toDto(remittanceDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemittanceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, remittanceDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remittanceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemittanceDetails in the database
        List<RemittanceDetails> remittanceDetailsList = remittanceDetailsRepository.findAll();
        assertThat(remittanceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRemittanceDetails() throws Exception {
        int databaseSizeBeforeUpdate = remittanceDetailsRepository.findAll().size();
        remittanceDetails.setId(count.incrementAndGet());

        // Create the RemittanceDetails
        RemittanceDetailsDTO remittanceDetailsDTO = remittanceDetailsMapper.toDto(remittanceDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemittanceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remittanceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemittanceDetails in the database
        List<RemittanceDetails> remittanceDetailsList = remittanceDetailsRepository.findAll();
        assertThat(remittanceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRemittanceDetails() throws Exception {
        int databaseSizeBeforeUpdate = remittanceDetailsRepository.findAll().size();
        remittanceDetails.setId(count.incrementAndGet());

        // Create the RemittanceDetails
        RemittanceDetailsDTO remittanceDetailsDTO = remittanceDetailsMapper.toDto(remittanceDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemittanceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remittanceDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RemittanceDetails in the database
        List<RemittanceDetails> remittanceDetailsList = remittanceDetailsRepository.findAll();
        assertThat(remittanceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRemittanceDetails() throws Exception {
        // Initialize the database
        remittanceDetailsRepository.saveAndFlush(remittanceDetails);

        int databaseSizeBeforeDelete = remittanceDetailsRepository.findAll().size();

        // Delete the remittanceDetails
        restRemittanceDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, remittanceDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RemittanceDetails> remittanceDetailsList = remittanceDetailsRepository.findAll();
        assertThat(remittanceDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
