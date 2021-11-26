package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.OTPAttempt;
import com.mullya.app.repository.OTPAttemptRepository;
import com.mullya.app.service.dto.OTPAttemptDTO;
import com.mullya.app.service.mapper.OTPAttemptMapper;
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
 * Integration tests for the {@link OTPAttemptResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OTPAttemptResourceIT {

    private static final Integer DEFAULT_OTP = 1;
    private static final Integer UPDATED_OTP = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PHONE = 1;
    private static final Integer UPDATED_PHONE = 2;

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final String DEFAULT_COOOKIE = "AAAAAAAAAA";
    private static final String UPDATED_COOOKIE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/otp-attempts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OTPAttemptRepository oTPAttemptRepository;

    @Autowired
    private OTPAttemptMapper oTPAttemptMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOTPAttemptMockMvc;

    private OTPAttempt oTPAttempt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OTPAttempt createEntity(EntityManager em) {
        OTPAttempt oTPAttempt = new OTPAttempt()
            .otp(DEFAULT_OTP)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .ip(DEFAULT_IP)
            .coookie(DEFAULT_COOOKIE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT);
        return oTPAttempt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OTPAttempt createUpdatedEntity(EntityManager em) {
        OTPAttempt oTPAttempt = new OTPAttempt()
            .otp(UPDATED_OTP)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .ip(UPDATED_IP)
            .coookie(UPDATED_COOOKIE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);
        return oTPAttempt;
    }

    @BeforeEach
    public void initTest() {
        oTPAttempt = createEntity(em);
    }

    @Test
    @Transactional
    void createOTPAttempt() throws Exception {
        int databaseSizeBeforeCreate = oTPAttemptRepository.findAll().size();
        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);
        restOTPAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isCreated());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeCreate + 1);
        OTPAttempt testOTPAttempt = oTPAttemptList.get(oTPAttemptList.size() - 1);
        assertThat(testOTPAttempt.getOtp()).isEqualTo(DEFAULT_OTP);
        assertThat(testOTPAttempt.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOTPAttempt.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testOTPAttempt.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testOTPAttempt.getCoookie()).isEqualTo(DEFAULT_COOOKIE);
        assertThat(testOTPAttempt.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOTPAttempt.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createOTPAttemptWithExistingId() throws Exception {
        // Create the OTPAttempt with an existing ID
        oTPAttempt.setId(1L);
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        int databaseSizeBeforeCreate = oTPAttemptRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOTPAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOTPAttempts() throws Exception {
        // Initialize the database
        oTPAttemptRepository.saveAndFlush(oTPAttempt);

        // Get all the oTPAttemptList
        restOTPAttemptMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oTPAttempt.getId().intValue())))
            .andExpect(jsonPath("$.[*].otp").value(hasItem(DEFAULT_OTP)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].coookie").value(hasItem(DEFAULT_COOOKIE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getOTPAttempt() throws Exception {
        // Initialize the database
        oTPAttemptRepository.saveAndFlush(oTPAttempt);

        // Get the oTPAttempt
        restOTPAttemptMockMvc
            .perform(get(ENTITY_API_URL_ID, oTPAttempt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oTPAttempt.getId().intValue()))
            .andExpect(jsonPath("$.otp").value(DEFAULT_OTP))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
            .andExpect(jsonPath("$.coookie").value(DEFAULT_COOOKIE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOTPAttempt() throws Exception {
        // Get the oTPAttempt
        restOTPAttemptMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOTPAttempt() throws Exception {
        // Initialize the database
        oTPAttemptRepository.saveAndFlush(oTPAttempt);

        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();

        // Update the oTPAttempt
        OTPAttempt updatedOTPAttempt = oTPAttemptRepository.findById(oTPAttempt.getId()).get();
        // Disconnect from session so that the updates on updatedOTPAttempt are not directly saved in db
        em.detach(updatedOTPAttempt);
        updatedOTPAttempt
            .otp(UPDATED_OTP)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .ip(UPDATED_IP)
            .coookie(UPDATED_COOOKIE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(updatedOTPAttempt);

        restOTPAttemptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oTPAttemptDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO))
            )
            .andExpect(status().isOk());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
        OTPAttempt testOTPAttempt = oTPAttemptList.get(oTPAttemptList.size() - 1);
        assertThat(testOTPAttempt.getOtp()).isEqualTo(UPDATED_OTP);
        assertThat(testOTPAttempt.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOTPAttempt.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOTPAttempt.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testOTPAttempt.getCoookie()).isEqualTo(UPDATED_COOOKIE);
        assertThat(testOTPAttempt.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOTPAttempt.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingOTPAttempt() throws Exception {
        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();
        oTPAttempt.setId(count.incrementAndGet());

        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOTPAttemptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oTPAttemptDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOTPAttempt() throws Exception {
        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();
        oTPAttempt.setId(count.incrementAndGet());

        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOTPAttemptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOTPAttempt() throws Exception {
        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();
        oTPAttempt.setId(count.incrementAndGet());

        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOTPAttemptMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOTPAttemptWithPatch() throws Exception {
        // Initialize the database
        oTPAttemptRepository.saveAndFlush(oTPAttempt);

        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();

        // Update the oTPAttempt using partial update
        OTPAttempt partialUpdatedOTPAttempt = new OTPAttempt();
        partialUpdatedOTPAttempt.setId(oTPAttempt.getId());

        partialUpdatedOTPAttempt
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .ip(UPDATED_IP)
            .coookie(UPDATED_COOOKIE)
            .createdAt(UPDATED_CREATED_AT);

        restOTPAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOTPAttempt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOTPAttempt))
            )
            .andExpect(status().isOk());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
        OTPAttempt testOTPAttempt = oTPAttemptList.get(oTPAttemptList.size() - 1);
        assertThat(testOTPAttempt.getOtp()).isEqualTo(DEFAULT_OTP);
        assertThat(testOTPAttempt.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOTPAttempt.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOTPAttempt.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testOTPAttempt.getCoookie()).isEqualTo(UPDATED_COOOKIE);
        assertThat(testOTPAttempt.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOTPAttempt.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateOTPAttemptWithPatch() throws Exception {
        // Initialize the database
        oTPAttemptRepository.saveAndFlush(oTPAttempt);

        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();

        // Update the oTPAttempt using partial update
        OTPAttempt partialUpdatedOTPAttempt = new OTPAttempt();
        partialUpdatedOTPAttempt.setId(oTPAttempt.getId());

        partialUpdatedOTPAttempt
            .otp(UPDATED_OTP)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .ip(UPDATED_IP)
            .coookie(UPDATED_COOOKIE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);

        restOTPAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOTPAttempt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOTPAttempt))
            )
            .andExpect(status().isOk());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
        OTPAttempt testOTPAttempt = oTPAttemptList.get(oTPAttemptList.size() - 1);
        assertThat(testOTPAttempt.getOtp()).isEqualTo(UPDATED_OTP);
        assertThat(testOTPAttempt.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOTPAttempt.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOTPAttempt.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testOTPAttempt.getCoookie()).isEqualTo(UPDATED_COOOKIE);
        assertThat(testOTPAttempt.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOTPAttempt.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingOTPAttempt() throws Exception {
        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();
        oTPAttempt.setId(count.incrementAndGet());

        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOTPAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, oTPAttemptDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOTPAttempt() throws Exception {
        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();
        oTPAttempt.setId(count.incrementAndGet());

        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOTPAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOTPAttempt() throws Exception {
        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();
        oTPAttempt.setId(count.incrementAndGet());

        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOTPAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOTPAttempt() throws Exception {
        // Initialize the database
        oTPAttemptRepository.saveAndFlush(oTPAttempt);

        int databaseSizeBeforeDelete = oTPAttemptRepository.findAll().size();

        // Delete the oTPAttempt
        restOTPAttemptMockMvc
            .perform(delete(ENTITY_API_URL_ID, oTPAttempt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
