package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.Hub;
import com.mullya.app.repository.HubRepository;
import com.mullya.app.service.dto.HubDTO;
import com.mullya.app.service.mapper.HubMapper;
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
 * Integration tests for the {@link HubResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HubResourceIT {

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hubs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HubRepository hubRepository;

    @Autowired
    private HubMapper hubMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHubMockMvc;

    private Hub hub;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hub createEntity(EntityManager em) {
        Hub hub = new Hub()
            .tag(DEFAULT_TAG)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return hub;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hub createUpdatedEntity(EntityManager em) {
        Hub hub = new Hub()
            .tag(UPDATED_TAG)
            .isActive(UPDATED_IS_ACTIVE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return hub;
    }

    @BeforeEach
    public void initTest() {
        hub = createEntity(em);
    }

    @Test
    @Transactional
    void createHub() throws Exception {
        int databaseSizeBeforeCreate = hubRepository.findAll().size();
        // Create the Hub
        HubDTO hubDTO = hubMapper.toDto(hub);
        restHubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hubDTO)))
            .andExpect(status().isCreated());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeCreate + 1);
        Hub testHub = hubList.get(hubList.size() - 1);
        assertThat(testHub.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testHub.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testHub.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testHub.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testHub.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testHub.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createHubWithExistingId() throws Exception {
        // Create the Hub with an existing ID
        hub.setId(1L);
        HubDTO hubDTO = hubMapper.toDto(hub);

        int databaseSizeBeforeCreate = hubRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hubDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHubs() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get all the hubList
        restHubMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hub.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getHub() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get the hub
        restHubMockMvc
            .perform(get(ENTITY_API_URL_ID, hub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hub.getId().intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingHub() throws Exception {
        // Get the hub
        restHubMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHub() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        int databaseSizeBeforeUpdate = hubRepository.findAll().size();

        // Update the hub
        Hub updatedHub = hubRepository.findById(hub.getId()).get();
        // Disconnect from session so that the updates on updatedHub are not directly saved in db
        em.detach(updatedHub);
        updatedHub
            .tag(UPDATED_TAG)
            .isActive(UPDATED_IS_ACTIVE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        HubDTO hubDTO = hubMapper.toDto(updatedHub);

        restHubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hubDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hubDTO))
            )
            .andExpect(status().isOk());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeUpdate);
        Hub testHub = hubList.get(hubList.size() - 1);
        assertThat(testHub.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testHub.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testHub.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testHub.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testHub.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testHub.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingHub() throws Exception {
        int databaseSizeBeforeUpdate = hubRepository.findAll().size();
        hub.setId(count.incrementAndGet());

        // Create the Hub
        HubDTO hubDTO = hubMapper.toDto(hub);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hubDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHub() throws Exception {
        int databaseSizeBeforeUpdate = hubRepository.findAll().size();
        hub.setId(count.incrementAndGet());

        // Create the Hub
        HubDTO hubDTO = hubMapper.toDto(hub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHub() throws Exception {
        int databaseSizeBeforeUpdate = hubRepository.findAll().size();
        hub.setId(count.incrementAndGet());

        // Create the Hub
        HubDTO hubDTO = hubMapper.toDto(hub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHubMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hubDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHubWithPatch() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        int databaseSizeBeforeUpdate = hubRepository.findAll().size();

        // Update the hub using partial update
        Hub partialUpdatedHub = new Hub();
        partialUpdatedHub.setId(hub.getId());

        partialUpdatedHub.isActive(UPDATED_IS_ACTIVE).createdOn(UPDATED_CREATED_ON).createdBy(UPDATED_CREATED_BY);

        restHubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHub))
            )
            .andExpect(status().isOk());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeUpdate);
        Hub testHub = hubList.get(hubList.size() - 1);
        assertThat(testHub.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testHub.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testHub.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testHub.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testHub.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testHub.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateHubWithPatch() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        int databaseSizeBeforeUpdate = hubRepository.findAll().size();

        // Update the hub using partial update
        Hub partialUpdatedHub = new Hub();
        partialUpdatedHub.setId(hub.getId());

        partialUpdatedHub
            .tag(UPDATED_TAG)
            .isActive(UPDATED_IS_ACTIVE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restHubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHub))
            )
            .andExpect(status().isOk());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeUpdate);
        Hub testHub = hubList.get(hubList.size() - 1);
        assertThat(testHub.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testHub.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testHub.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testHub.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testHub.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testHub.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingHub() throws Exception {
        int databaseSizeBeforeUpdate = hubRepository.findAll().size();
        hub.setId(count.incrementAndGet());

        // Create the Hub
        HubDTO hubDTO = hubMapper.toDto(hub);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hubDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHub() throws Exception {
        int databaseSizeBeforeUpdate = hubRepository.findAll().size();
        hub.setId(count.incrementAndGet());

        // Create the Hub
        HubDTO hubDTO = hubMapper.toDto(hub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHub() throws Exception {
        int databaseSizeBeforeUpdate = hubRepository.findAll().size();
        hub.setId(count.incrementAndGet());

        // Create the Hub
        HubDTO hubDTO = hubMapper.toDto(hub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHubMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hubDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHub() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        int databaseSizeBeforeDelete = hubRepository.findAll().size();

        // Delete the hub
        restHubMockMvc.perform(delete(ENTITY_API_URL_ID, hub.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
