package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.PaymentDetails;
import com.mullya.app.domain.enumeration.PGType;
import com.mullya.app.domain.enumeration.PaymentStatus;
import com.mullya.app.domain.enumeration.PaymentType;
import com.mullya.app.repository.PaymentDetailsRepository;
import com.mullya.app.service.dto.PaymentDetailsDTO;
import com.mullya.app.service.mapper.PaymentDetailsMapper;
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
 * Integration tests for the {@link PaymentDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentDetailsResourceIT {

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

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

    private static final String DEFAULT_OFFLINE_TXN_COLLECTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_OFFLINE_TXN_COLLECTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_OFFLINE_TXN_CLEARING_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_OFFLINE_TXN_CLEARING_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_DATE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAYMENT_INIT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_INIT_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PAYMENT_UPDATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_UPDATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.Pending;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.Initiated;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    private PaymentDetailsMapper paymentDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentDetailsMockMvc;

    private PaymentDetails paymentDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentDetails createEntity(EntityManager em) {
        PaymentDetails paymentDetails = new PaymentDetails()
            .amount(DEFAULT_AMOUNT)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .onlinePgType(DEFAULT_ONLINE_PG_TYPE)
            .pgTxnId(DEFAULT_PG_TXN_ID)
            .pgStatus(DEFAULT_PG_STATUS)
            .offlineTxnId(DEFAULT_OFFLINE_TXN_ID)
            .offlineTxnDetails(DEFAULT_OFFLINE_TXN_DETAILS)
            .offlineTxnCollectedBy(DEFAULT_OFFLINE_TXN_COLLECTED_BY)
            .offlineTxnClearingStatus(DEFAULT_OFFLINE_TXN_CLEARING_STATUS)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .paymentInitTime(DEFAULT_PAYMENT_INIT_TIME)
            .paymentUpdateTime(DEFAULT_PAYMENT_UPDATE_TIME)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return paymentDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentDetails createUpdatedEntity(EntityManager em) {
        PaymentDetails paymentDetails = new PaymentDetails()
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .onlinePgType(UPDATED_ONLINE_PG_TYPE)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgStatus(UPDATED_PG_STATUS)
            .offlineTxnId(UPDATED_OFFLINE_TXN_ID)
            .offlineTxnDetails(UPDATED_OFFLINE_TXN_DETAILS)
            .offlineTxnCollectedBy(UPDATED_OFFLINE_TXN_COLLECTED_BY)
            .offlineTxnClearingStatus(UPDATED_OFFLINE_TXN_CLEARING_STATUS)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentInitTime(UPDATED_PAYMENT_INIT_TIME)
            .paymentUpdateTime(UPDATED_PAYMENT_UPDATE_TIME)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return paymentDetails;
    }

    @BeforeEach
    public void initTest() {
        paymentDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentDetails() throws Exception {
        int databaseSizeBeforeCreate = paymentDetailsRepository.findAll().size();
        // Create the PaymentDetails
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);
        restPaymentDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPaymentDetails.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testPaymentDetails.getOnlinePgType()).isEqualTo(DEFAULT_ONLINE_PG_TYPE);
        assertThat(testPaymentDetails.getPgTxnId()).isEqualTo(DEFAULT_PG_TXN_ID);
        assertThat(testPaymentDetails.getPgStatus()).isEqualTo(DEFAULT_PG_STATUS);
        assertThat(testPaymentDetails.getOfflineTxnId()).isEqualTo(DEFAULT_OFFLINE_TXN_ID);
        assertThat(testPaymentDetails.getOfflineTxnDetails()).isEqualTo(DEFAULT_OFFLINE_TXN_DETAILS);
        assertThat(testPaymentDetails.getOfflineTxnCollectedBy()).isEqualTo(DEFAULT_OFFLINE_TXN_COLLECTED_BY);
        assertThat(testPaymentDetails.getOfflineTxnClearingStatus()).isEqualTo(DEFAULT_OFFLINE_TXN_CLEARING_STATUS);
        assertThat(testPaymentDetails.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testPaymentDetails.getPaymentInitTime()).isEqualTo(DEFAULT_PAYMENT_INIT_TIME);
        assertThat(testPaymentDetails.getPaymentUpdateTime()).isEqualTo(DEFAULT_PAYMENT_UPDATE_TIME);
        assertThat(testPaymentDetails.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPaymentDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPaymentDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPaymentDetails.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testPaymentDetails.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createPaymentDetailsWithExistingId() throws Exception {
        // Create the PaymentDetails with an existing ID
        paymentDetails.setId(1L);
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        int databaseSizeBeforeCreate = paymentDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList
        restPaymentDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].onlinePgType").value(hasItem(DEFAULT_ONLINE_PG_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pgTxnId").value(hasItem(DEFAULT_PG_TXN_ID)))
            .andExpect(jsonPath("$.[*].pgStatus").value(hasItem(DEFAULT_PG_STATUS)))
            .andExpect(jsonPath("$.[*].offlineTxnId").value(hasItem(DEFAULT_OFFLINE_TXN_ID)))
            .andExpect(jsonPath("$.[*].offlineTxnDetails").value(hasItem(DEFAULT_OFFLINE_TXN_DETAILS)))
            .andExpect(jsonPath("$.[*].offlineTxnCollectedBy").value(hasItem(DEFAULT_OFFLINE_TXN_COLLECTED_BY)))
            .andExpect(jsonPath("$.[*].offlineTxnClearingStatus").value(hasItem(DEFAULT_OFFLINE_TXN_CLEARING_STATUS)))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE)))
            .andExpect(jsonPath("$.[*].paymentInitTime").value(hasItem(DEFAULT_PAYMENT_INIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].paymentUpdateTime").value(hasItem(DEFAULT_PAYMENT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get the paymentDetails
        restPaymentDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentDetails.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.onlinePgType").value(DEFAULT_ONLINE_PG_TYPE.toString()))
            .andExpect(jsonPath("$.pgTxnId").value(DEFAULT_PG_TXN_ID))
            .andExpect(jsonPath("$.pgStatus").value(DEFAULT_PG_STATUS))
            .andExpect(jsonPath("$.offlineTxnId").value(DEFAULT_OFFLINE_TXN_ID))
            .andExpect(jsonPath("$.offlineTxnDetails").value(DEFAULT_OFFLINE_TXN_DETAILS))
            .andExpect(jsonPath("$.offlineTxnCollectedBy").value(DEFAULT_OFFLINE_TXN_COLLECTED_BY))
            .andExpect(jsonPath("$.offlineTxnClearingStatus").value(DEFAULT_OFFLINE_TXN_CLEARING_STATUS))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE))
            .andExpect(jsonPath("$.paymentInitTime").value(DEFAULT_PAYMENT_INIT_TIME.toString()))
            .andExpect(jsonPath("$.paymentUpdateTime").value(DEFAULT_PAYMENT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingPaymentDetails() throws Exception {
        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // Update the paymentDetails
        PaymentDetails updatedPaymentDetails = paymentDetailsRepository.findById(paymentDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentDetails are not directly saved in db
        em.detach(updatedPaymentDetails);
        updatedPaymentDetails
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .onlinePgType(UPDATED_ONLINE_PG_TYPE)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgStatus(UPDATED_PG_STATUS)
            .offlineTxnId(UPDATED_OFFLINE_TXN_ID)
            .offlineTxnDetails(UPDATED_OFFLINE_TXN_DETAILS)
            .offlineTxnCollectedBy(UPDATED_OFFLINE_TXN_COLLECTED_BY)
            .offlineTxnClearingStatus(UPDATED_OFFLINE_TXN_CLEARING_STATUS)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentInitTime(UPDATED_PAYMENT_INIT_TIME)
            .paymentUpdateTime(UPDATED_PAYMENT_UPDATE_TIME)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(updatedPaymentDetails);

        restPaymentDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPaymentDetails.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPaymentDetails.getOnlinePgType()).isEqualTo(UPDATED_ONLINE_PG_TYPE);
        assertThat(testPaymentDetails.getPgTxnId()).isEqualTo(UPDATED_PG_TXN_ID);
        assertThat(testPaymentDetails.getPgStatus()).isEqualTo(UPDATED_PG_STATUS);
        assertThat(testPaymentDetails.getOfflineTxnId()).isEqualTo(UPDATED_OFFLINE_TXN_ID);
        assertThat(testPaymentDetails.getOfflineTxnDetails()).isEqualTo(UPDATED_OFFLINE_TXN_DETAILS);
        assertThat(testPaymentDetails.getOfflineTxnCollectedBy()).isEqualTo(UPDATED_OFFLINE_TXN_COLLECTED_BY);
        assertThat(testPaymentDetails.getOfflineTxnClearingStatus()).isEqualTo(UPDATED_OFFLINE_TXN_CLEARING_STATUS);
        assertThat(testPaymentDetails.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPaymentDetails.getPaymentInitTime()).isEqualTo(UPDATED_PAYMENT_INIT_TIME);
        assertThat(testPaymentDetails.getPaymentUpdateTime()).isEqualTo(UPDATED_PAYMENT_UPDATE_TIME);
        assertThat(testPaymentDetails.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPaymentDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPaymentDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPaymentDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testPaymentDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();
        paymentDetails.setId(count.incrementAndGet());

        // Create the PaymentDetails
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();
        paymentDetails.setId(count.incrementAndGet());

        // Create the PaymentDetails
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();
        paymentDetails.setId(count.incrementAndGet());

        // Create the PaymentDetails
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentDetailsWithPatch() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // Update the paymentDetails using partial update
        PaymentDetails partialUpdatedPaymentDetails = new PaymentDetails();
        partialUpdatedPaymentDetails.setId(paymentDetails.getId());

        partialUpdatedPaymentDetails
            .paymentType(UPDATED_PAYMENT_TYPE)
            .onlinePgType(UPDATED_ONLINE_PG_TYPE)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .offlineTxnCollectedBy(UPDATED_OFFLINE_TXN_COLLECTED_BY)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentInitTime(UPDATED_PAYMENT_INIT_TIME)
            .paymentUpdateTime(UPDATED_PAYMENT_UPDATE_TIME)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restPaymentDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentDetails))
            )
            .andExpect(status().isOk());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPaymentDetails.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPaymentDetails.getOnlinePgType()).isEqualTo(UPDATED_ONLINE_PG_TYPE);
        assertThat(testPaymentDetails.getPgTxnId()).isEqualTo(UPDATED_PG_TXN_ID);
        assertThat(testPaymentDetails.getPgStatus()).isEqualTo(DEFAULT_PG_STATUS);
        assertThat(testPaymentDetails.getOfflineTxnId()).isEqualTo(DEFAULT_OFFLINE_TXN_ID);
        assertThat(testPaymentDetails.getOfflineTxnDetails()).isEqualTo(DEFAULT_OFFLINE_TXN_DETAILS);
        assertThat(testPaymentDetails.getOfflineTxnCollectedBy()).isEqualTo(UPDATED_OFFLINE_TXN_COLLECTED_BY);
        assertThat(testPaymentDetails.getOfflineTxnClearingStatus()).isEqualTo(DEFAULT_OFFLINE_TXN_CLEARING_STATUS);
        assertThat(testPaymentDetails.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPaymentDetails.getPaymentInitTime()).isEqualTo(UPDATED_PAYMENT_INIT_TIME);
        assertThat(testPaymentDetails.getPaymentUpdateTime()).isEqualTo(UPDATED_PAYMENT_UPDATE_TIME);
        assertThat(testPaymentDetails.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPaymentDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPaymentDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPaymentDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testPaymentDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePaymentDetailsWithPatch() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // Update the paymentDetails using partial update
        PaymentDetails partialUpdatedPaymentDetails = new PaymentDetails();
        partialUpdatedPaymentDetails.setId(paymentDetails.getId());

        partialUpdatedPaymentDetails
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .onlinePgType(UPDATED_ONLINE_PG_TYPE)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgStatus(UPDATED_PG_STATUS)
            .offlineTxnId(UPDATED_OFFLINE_TXN_ID)
            .offlineTxnDetails(UPDATED_OFFLINE_TXN_DETAILS)
            .offlineTxnCollectedBy(UPDATED_OFFLINE_TXN_COLLECTED_BY)
            .offlineTxnClearingStatus(UPDATED_OFFLINE_TXN_CLEARING_STATUS)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentInitTime(UPDATED_PAYMENT_INIT_TIME)
            .paymentUpdateTime(UPDATED_PAYMENT_UPDATE_TIME)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restPaymentDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentDetails))
            )
            .andExpect(status().isOk());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPaymentDetails.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPaymentDetails.getOnlinePgType()).isEqualTo(UPDATED_ONLINE_PG_TYPE);
        assertThat(testPaymentDetails.getPgTxnId()).isEqualTo(UPDATED_PG_TXN_ID);
        assertThat(testPaymentDetails.getPgStatus()).isEqualTo(UPDATED_PG_STATUS);
        assertThat(testPaymentDetails.getOfflineTxnId()).isEqualTo(UPDATED_OFFLINE_TXN_ID);
        assertThat(testPaymentDetails.getOfflineTxnDetails()).isEqualTo(UPDATED_OFFLINE_TXN_DETAILS);
        assertThat(testPaymentDetails.getOfflineTxnCollectedBy()).isEqualTo(UPDATED_OFFLINE_TXN_COLLECTED_BY);
        assertThat(testPaymentDetails.getOfflineTxnClearingStatus()).isEqualTo(UPDATED_OFFLINE_TXN_CLEARING_STATUS);
        assertThat(testPaymentDetails.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPaymentDetails.getPaymentInitTime()).isEqualTo(UPDATED_PAYMENT_INIT_TIME);
        assertThat(testPaymentDetails.getPaymentUpdateTime()).isEqualTo(UPDATED_PAYMENT_UPDATE_TIME);
        assertThat(testPaymentDetails.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPaymentDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPaymentDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPaymentDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testPaymentDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();
        paymentDetails.setId(count.incrementAndGet());

        // Create the PaymentDetails
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();
        paymentDetails.setId(count.incrementAndGet());

        // Create the PaymentDetails
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();
        paymentDetails.setId(count.incrementAndGet());

        // Create the PaymentDetails
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        int databaseSizeBeforeDelete = paymentDetailsRepository.findAll().size();

        // Delete the paymentDetails
        restPaymentDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
