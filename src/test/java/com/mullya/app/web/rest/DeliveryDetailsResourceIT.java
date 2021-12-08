package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.DeliveryDetails;
import com.mullya.app.domain.enumeration.DeliveryStatus;
import com.mullya.app.domain.enumeration.DeliveryType;
import com.mullya.app.repository.DeliveryDetailsRepository;
import com.mullya.app.service.dto.DeliveryDetailsDTO;
import com.mullya.app.service.mapper.DeliveryDetailsMapper;
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
 * Integration tests for the {@link DeliveryDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeliveryDetailsResourceIT {

    private static final DeliveryType DEFAULT_DELIVERY_TYPE = DeliveryType.FarmerToBuyer;
    private static final DeliveryType UPDATED_DELIVERY_TYPE = DeliveryType.FarmerToHub;

    private static final String DEFAULT_PICKUP_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PICKUP_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_TRUCK_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_TRUCK_DETAILS = "BBBBBBBBBB";

    private static final Long DEFAULT_DELIVERY_AGENT_PHONE = 1000000000L;
    private static final Long UPDATED_DELIVERY_AGENT_PHONE = 1000000001L;

    private static final LocalDate DEFAULT_PICKUP_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PICKUP_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELIVERY_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERY_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final DeliveryStatus DEFAULT_DELIVERY_STATUS = DeliveryStatus.PendingConfirmation;
    private static final DeliveryStatus UPDATED_DELIVERY_STATUS = DeliveryStatus.Accepted;

    private static final String ENTITY_API_URL = "/api/delivery-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeliveryDetailsRepository deliveryDetailsRepository;

    @Autowired
    private DeliveryDetailsMapper deliveryDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeliveryDetailsMockMvc;

    private DeliveryDetails deliveryDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryDetails createEntity(EntityManager em) {
        DeliveryDetails deliveryDetails = new DeliveryDetails()
            .deliveryType(DEFAULT_DELIVERY_TYPE)
            .pickupDate(DEFAULT_PICKUP_DATE)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .truckDetails(DEFAULT_TRUCK_DETAILS)
            .deliveryAgentPhone(DEFAULT_DELIVERY_AGENT_PHONE)
            .pickupTime(DEFAULT_PICKUP_TIME)
            .deliveryTime(DEFAULT_DELIVERY_TIME)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .deliveryStatus(DEFAULT_DELIVERY_STATUS);
        return deliveryDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryDetails createUpdatedEntity(EntityManager em) {
        DeliveryDetails deliveryDetails = new DeliveryDetails()
            .deliveryType(UPDATED_DELIVERY_TYPE)
            .pickupDate(UPDATED_PICKUP_DATE)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .truckDetails(UPDATED_TRUCK_DETAILS)
            .deliveryAgentPhone(UPDATED_DELIVERY_AGENT_PHONE)
            .pickupTime(UPDATED_PICKUP_TIME)
            .deliveryTime(UPDATED_DELIVERY_TIME)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .deliveryStatus(UPDATED_DELIVERY_STATUS);
        return deliveryDetails;
    }

    @BeforeEach
    public void initTest() {
        deliveryDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createDeliveryDetails() throws Exception {
        int databaseSizeBeforeCreate = deliveryDetailsRepository.findAll().size();
        // Create the DeliveryDetails
        DeliveryDetailsDTO deliveryDetailsDTO = deliveryDetailsMapper.toDto(deliveryDetails);
        restDeliveryDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deliveryDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DeliveryDetails in the database
        List<DeliveryDetails> deliveryDetailsList = deliveryDetailsRepository.findAll();
        assertThat(deliveryDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryDetails testDeliveryDetails = deliveryDetailsList.get(deliveryDetailsList.size() - 1);
        assertThat(testDeliveryDetails.getDeliveryType()).isEqualTo(DEFAULT_DELIVERY_TYPE);
        assertThat(testDeliveryDetails.getPickupDate()).isEqualTo(DEFAULT_PICKUP_DATE);
        assertThat(testDeliveryDetails.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testDeliveryDetails.getTruckDetails()).isEqualTo(DEFAULT_TRUCK_DETAILS);
        assertThat(testDeliveryDetails.getDeliveryAgentPhone()).isEqualTo(DEFAULT_DELIVERY_AGENT_PHONE);
        assertThat(testDeliveryDetails.getPickupTime()).isEqualTo(DEFAULT_PICKUP_TIME);
        assertThat(testDeliveryDetails.getDeliveryTime()).isEqualTo(DEFAULT_DELIVERY_TIME);
        assertThat(testDeliveryDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDeliveryDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDeliveryDetails.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testDeliveryDetails.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDeliveryDetails.getDeliveryStatus()).isEqualTo(DEFAULT_DELIVERY_STATUS);
    }

    @Test
    @Transactional
    void createDeliveryDetailsWithExistingId() throws Exception {
        // Create the DeliveryDetails with an existing ID
        deliveryDetails.setId(1L);
        DeliveryDetailsDTO deliveryDetailsDTO = deliveryDetailsMapper.toDto(deliveryDetails);

        int databaseSizeBeforeCreate = deliveryDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deliveryDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryDetails in the database
        List<DeliveryDetails> deliveryDetailsList = deliveryDetailsRepository.findAll();
        assertThat(deliveryDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeliveryDetails() throws Exception {
        // Initialize the database
        deliveryDetailsRepository.saveAndFlush(deliveryDetails);

        // Get all the deliveryDetailsList
        restDeliveryDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveryType").value(hasItem(DEFAULT_DELIVERY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pickupDate").value(hasItem(DEFAULT_PICKUP_DATE)))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE)))
            .andExpect(jsonPath("$.[*].truckDetails").value(hasItem(DEFAULT_TRUCK_DETAILS)))
            .andExpect(jsonPath("$.[*].deliveryAgentPhone").value(hasItem(DEFAULT_DELIVERY_AGENT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].pickupTime").value(hasItem(DEFAULT_PICKUP_TIME.toString())))
            .andExpect(jsonPath("$.[*].deliveryTime").value(hasItem(DEFAULT_DELIVERY_TIME.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].deliveryStatus").value(hasItem(DEFAULT_DELIVERY_STATUS.toString())));
    }

    @Test
    @Transactional
    void getDeliveryDetails() throws Exception {
        // Initialize the database
        deliveryDetailsRepository.saveAndFlush(deliveryDetails);

        // Get the deliveryDetails
        restDeliveryDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, deliveryDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryDetails.getId().intValue()))
            .andExpect(jsonPath("$.deliveryType").value(DEFAULT_DELIVERY_TYPE.toString()))
            .andExpect(jsonPath("$.pickupDate").value(DEFAULT_PICKUP_DATE))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE))
            .andExpect(jsonPath("$.truckDetails").value(DEFAULT_TRUCK_DETAILS))
            .andExpect(jsonPath("$.deliveryAgentPhone").value(DEFAULT_DELIVERY_AGENT_PHONE.intValue()))
            .andExpect(jsonPath("$.pickupTime").value(DEFAULT_PICKUP_TIME.toString()))
            .andExpect(jsonPath("$.deliveryTime").value(DEFAULT_DELIVERY_TIME.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.deliveryStatus").value(DEFAULT_DELIVERY_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDeliveryDetails() throws Exception {
        // Get the deliveryDetails
        restDeliveryDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDeliveryDetails() throws Exception {
        // Initialize the database
        deliveryDetailsRepository.saveAndFlush(deliveryDetails);

        int databaseSizeBeforeUpdate = deliveryDetailsRepository.findAll().size();

        // Update the deliveryDetails
        DeliveryDetails updatedDeliveryDetails = deliveryDetailsRepository.findById(deliveryDetails.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryDetails are not directly saved in db
        em.detach(updatedDeliveryDetails);
        updatedDeliveryDetails
            .deliveryType(UPDATED_DELIVERY_TYPE)
            .pickupDate(UPDATED_PICKUP_DATE)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .truckDetails(UPDATED_TRUCK_DETAILS)
            .deliveryAgentPhone(UPDATED_DELIVERY_AGENT_PHONE)
            .pickupTime(UPDATED_PICKUP_TIME)
            .deliveryTime(UPDATED_DELIVERY_TIME)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .deliveryStatus(UPDATED_DELIVERY_STATUS);
        DeliveryDetailsDTO deliveryDetailsDTO = deliveryDetailsMapper.toDto(updatedDeliveryDetails);

        restDeliveryDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deliveryDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryDetails in the database
        List<DeliveryDetails> deliveryDetailsList = deliveryDetailsRepository.findAll();
        assertThat(deliveryDetailsList).hasSize(databaseSizeBeforeUpdate);
        DeliveryDetails testDeliveryDetails = deliveryDetailsList.get(deliveryDetailsList.size() - 1);
        assertThat(testDeliveryDetails.getDeliveryType()).isEqualTo(UPDATED_DELIVERY_TYPE);
        assertThat(testDeliveryDetails.getPickupDate()).isEqualTo(UPDATED_PICKUP_DATE);
        assertThat(testDeliveryDetails.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testDeliveryDetails.getTruckDetails()).isEqualTo(UPDATED_TRUCK_DETAILS);
        assertThat(testDeliveryDetails.getDeliveryAgentPhone()).isEqualTo(UPDATED_DELIVERY_AGENT_PHONE);
        assertThat(testDeliveryDetails.getPickupTime()).isEqualTo(UPDATED_PICKUP_TIME);
        assertThat(testDeliveryDetails.getDeliveryTime()).isEqualTo(UPDATED_DELIVERY_TIME);
        assertThat(testDeliveryDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDeliveryDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDeliveryDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testDeliveryDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDeliveryDetails.getDeliveryStatus()).isEqualTo(UPDATED_DELIVERY_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingDeliveryDetails() throws Exception {
        int databaseSizeBeforeUpdate = deliveryDetailsRepository.findAll().size();
        deliveryDetails.setId(count.incrementAndGet());

        // Create the DeliveryDetails
        DeliveryDetailsDTO deliveryDetailsDTO = deliveryDetailsMapper.toDto(deliveryDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deliveryDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryDetails in the database
        List<DeliveryDetails> deliveryDetailsList = deliveryDetailsRepository.findAll();
        assertThat(deliveryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeliveryDetails() throws Exception {
        int databaseSizeBeforeUpdate = deliveryDetailsRepository.findAll().size();
        deliveryDetails.setId(count.incrementAndGet());

        // Create the DeliveryDetails
        DeliveryDetailsDTO deliveryDetailsDTO = deliveryDetailsMapper.toDto(deliveryDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryDetails in the database
        List<DeliveryDetails> deliveryDetailsList = deliveryDetailsRepository.findAll();
        assertThat(deliveryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeliveryDetails() throws Exception {
        int databaseSizeBeforeUpdate = deliveryDetailsRepository.findAll().size();
        deliveryDetails.setId(count.incrementAndGet());

        // Create the DeliveryDetails
        DeliveryDetailsDTO deliveryDetailsDTO = deliveryDetailsMapper.toDto(deliveryDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deliveryDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeliveryDetails in the database
        List<DeliveryDetails> deliveryDetailsList = deliveryDetailsRepository.findAll();
        assertThat(deliveryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeliveryDetailsWithPatch() throws Exception {
        // Initialize the database
        deliveryDetailsRepository.saveAndFlush(deliveryDetails);

        int databaseSizeBeforeUpdate = deliveryDetailsRepository.findAll().size();

        // Update the deliveryDetails using partial update
        DeliveryDetails partialUpdatedDeliveryDetails = new DeliveryDetails();
        partialUpdatedDeliveryDetails.setId(deliveryDetails.getId());

        partialUpdatedDeliveryDetails
            .pickupDate(UPDATED_PICKUP_DATE)
            .truckDetails(UPDATED_TRUCK_DETAILS)
            .deliveryAgentPhone(UPDATED_DELIVERY_AGENT_PHONE)
            .deliveryTime(UPDATED_DELIVERY_TIME)
            .createdOn(UPDATED_CREATED_ON);

        restDeliveryDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeliveryDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeliveryDetails))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryDetails in the database
        List<DeliveryDetails> deliveryDetailsList = deliveryDetailsRepository.findAll();
        assertThat(deliveryDetailsList).hasSize(databaseSizeBeforeUpdate);
        DeliveryDetails testDeliveryDetails = deliveryDetailsList.get(deliveryDetailsList.size() - 1);
        assertThat(testDeliveryDetails.getDeliveryType()).isEqualTo(DEFAULT_DELIVERY_TYPE);
        assertThat(testDeliveryDetails.getPickupDate()).isEqualTo(UPDATED_PICKUP_DATE);
        assertThat(testDeliveryDetails.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testDeliveryDetails.getTruckDetails()).isEqualTo(UPDATED_TRUCK_DETAILS);
        assertThat(testDeliveryDetails.getDeliveryAgentPhone()).isEqualTo(UPDATED_DELIVERY_AGENT_PHONE);
        assertThat(testDeliveryDetails.getPickupTime()).isEqualTo(DEFAULT_PICKUP_TIME);
        assertThat(testDeliveryDetails.getDeliveryTime()).isEqualTo(UPDATED_DELIVERY_TIME);
        assertThat(testDeliveryDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDeliveryDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDeliveryDetails.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testDeliveryDetails.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDeliveryDetails.getDeliveryStatus()).isEqualTo(DEFAULT_DELIVERY_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateDeliveryDetailsWithPatch() throws Exception {
        // Initialize the database
        deliveryDetailsRepository.saveAndFlush(deliveryDetails);

        int databaseSizeBeforeUpdate = deliveryDetailsRepository.findAll().size();

        // Update the deliveryDetails using partial update
        DeliveryDetails partialUpdatedDeliveryDetails = new DeliveryDetails();
        partialUpdatedDeliveryDetails.setId(deliveryDetails.getId());

        partialUpdatedDeliveryDetails
            .deliveryType(UPDATED_DELIVERY_TYPE)
            .pickupDate(UPDATED_PICKUP_DATE)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .truckDetails(UPDATED_TRUCK_DETAILS)
            .deliveryAgentPhone(UPDATED_DELIVERY_AGENT_PHONE)
            .pickupTime(UPDATED_PICKUP_TIME)
            .deliveryTime(UPDATED_DELIVERY_TIME)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .deliveryStatus(UPDATED_DELIVERY_STATUS);

        restDeliveryDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeliveryDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeliveryDetails))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryDetails in the database
        List<DeliveryDetails> deliveryDetailsList = deliveryDetailsRepository.findAll();
        assertThat(deliveryDetailsList).hasSize(databaseSizeBeforeUpdate);
        DeliveryDetails testDeliveryDetails = deliveryDetailsList.get(deliveryDetailsList.size() - 1);
        assertThat(testDeliveryDetails.getDeliveryType()).isEqualTo(UPDATED_DELIVERY_TYPE);
        assertThat(testDeliveryDetails.getPickupDate()).isEqualTo(UPDATED_PICKUP_DATE);
        assertThat(testDeliveryDetails.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testDeliveryDetails.getTruckDetails()).isEqualTo(UPDATED_TRUCK_DETAILS);
        assertThat(testDeliveryDetails.getDeliveryAgentPhone()).isEqualTo(UPDATED_DELIVERY_AGENT_PHONE);
        assertThat(testDeliveryDetails.getPickupTime()).isEqualTo(UPDATED_PICKUP_TIME);
        assertThat(testDeliveryDetails.getDeliveryTime()).isEqualTo(UPDATED_DELIVERY_TIME);
        assertThat(testDeliveryDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDeliveryDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDeliveryDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testDeliveryDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDeliveryDetails.getDeliveryStatus()).isEqualTo(UPDATED_DELIVERY_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingDeliveryDetails() throws Exception {
        int databaseSizeBeforeUpdate = deliveryDetailsRepository.findAll().size();
        deliveryDetails.setId(count.incrementAndGet());

        // Create the DeliveryDetails
        DeliveryDetailsDTO deliveryDetailsDTO = deliveryDetailsMapper.toDto(deliveryDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deliveryDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryDetails in the database
        List<DeliveryDetails> deliveryDetailsList = deliveryDetailsRepository.findAll();
        assertThat(deliveryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeliveryDetails() throws Exception {
        int databaseSizeBeforeUpdate = deliveryDetailsRepository.findAll().size();
        deliveryDetails.setId(count.incrementAndGet());

        // Create the DeliveryDetails
        DeliveryDetailsDTO deliveryDetailsDTO = deliveryDetailsMapper.toDto(deliveryDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryDetails in the database
        List<DeliveryDetails> deliveryDetailsList = deliveryDetailsRepository.findAll();
        assertThat(deliveryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeliveryDetails() throws Exception {
        int databaseSizeBeforeUpdate = deliveryDetailsRepository.findAll().size();
        deliveryDetails.setId(count.incrementAndGet());

        // Create the DeliveryDetails
        DeliveryDetailsDTO deliveryDetailsDTO = deliveryDetailsMapper.toDto(deliveryDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeliveryDetails in the database
        List<DeliveryDetails> deliveryDetailsList = deliveryDetailsRepository.findAll();
        assertThat(deliveryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeliveryDetails() throws Exception {
        // Initialize the database
        deliveryDetailsRepository.saveAndFlush(deliveryDetails);

        int databaseSizeBeforeDelete = deliveryDetailsRepository.findAll().size();

        // Delete the deliveryDetails
        restDeliveryDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, deliveryDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeliveryDetails> deliveryDetailsList = deliveryDetailsRepository.findAll();
        assertThat(deliveryDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
