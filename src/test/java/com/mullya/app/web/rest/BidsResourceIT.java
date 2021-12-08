package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.Bids;
import com.mullya.app.domain.enumeration.BidStatus;
import com.mullya.app.repository.BidsRepository;
import com.mullya.app.service.dto.BidsDTO;
import com.mullya.app.service.mapper.BidsMapper;
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
 * Integration tests for the {@link BidsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BidsResourceIT {

    private static final Float DEFAULT_BID_PRICE = 1F;
    private static final Float UPDATED_BID_PRICE = 2F;

    private static final Float DEFAULT_QUANTITY_KG = 1F;
    private static final Float UPDATED_QUANTITY_KG = 2F;

    private static final BidStatus DEFAULT_BID_STATUS = BidStatus.New;
    private static final BidStatus UPDATED_BID_STATUS = BidStatus.Won;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BidsRepository bidsRepository;

    @Autowired
    private BidsMapper bidsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBidsMockMvc;

    private Bids bids;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bids createEntity(EntityManager em) {
        Bids bids = new Bids()
            .bidPrice(DEFAULT_BID_PRICE)
            .quantityKg(DEFAULT_QUANTITY_KG)
            .bidStatus(DEFAULT_BID_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return bids;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bids createUpdatedEntity(EntityManager em) {
        Bids bids = new Bids()
            .bidPrice(UPDATED_BID_PRICE)
            .quantityKg(UPDATED_QUANTITY_KG)
            .bidStatus(UPDATED_BID_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return bids;
    }

    @BeforeEach
    public void initTest() {
        bids = createEntity(em);
    }

    @Test
    @Transactional
    void createBids() throws Exception {
        int databaseSizeBeforeCreate = bidsRepository.findAll().size();
        // Create the Bids
        BidsDTO bidsDTO = bidsMapper.toDto(bids);
        restBidsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bidsDTO)))
            .andExpect(status().isCreated());

        // Validate the Bids in the database
        List<Bids> bidsList = bidsRepository.findAll();
        assertThat(bidsList).hasSize(databaseSizeBeforeCreate + 1);
        Bids testBids = bidsList.get(bidsList.size() - 1);
        assertThat(testBids.getBidPrice()).isEqualTo(DEFAULT_BID_PRICE);
        assertThat(testBids.getQuantityKg()).isEqualTo(DEFAULT_QUANTITY_KG);
        assertThat(testBids.getBidStatus()).isEqualTo(DEFAULT_BID_STATUS);
        assertThat(testBids.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testBids.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBids.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testBids.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createBidsWithExistingId() throws Exception {
        // Create the Bids with an existing ID
        bids.setId(1L);
        BidsDTO bidsDTO = bidsMapper.toDto(bids);

        int databaseSizeBeforeCreate = bidsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBidsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bidsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bids in the database
        List<Bids> bidsList = bidsRepository.findAll();
        assertThat(bidsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBids() throws Exception {
        // Initialize the database
        bidsRepository.saveAndFlush(bids);

        // Get all the bidsList
        restBidsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bids.getId().intValue())))
            .andExpect(jsonPath("$.[*].bidPrice").value(hasItem(DEFAULT_BID_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantityKg").value(hasItem(DEFAULT_QUANTITY_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].bidStatus").value(hasItem(DEFAULT_BID_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getBids() throws Exception {
        // Initialize the database
        bidsRepository.saveAndFlush(bids);

        // Get the bids
        restBidsMockMvc
            .perform(get(ENTITY_API_URL_ID, bids.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bids.getId().intValue()))
            .andExpect(jsonPath("$.bidPrice").value(DEFAULT_BID_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantityKg").value(DEFAULT_QUANTITY_KG.doubleValue()))
            .andExpect(jsonPath("$.bidStatus").value(DEFAULT_BID_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingBids() throws Exception {
        // Get the bids
        restBidsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBids() throws Exception {
        // Initialize the database
        bidsRepository.saveAndFlush(bids);

        int databaseSizeBeforeUpdate = bidsRepository.findAll().size();

        // Update the bids
        Bids updatedBids = bidsRepository.findById(bids.getId()).get();
        // Disconnect from session so that the updates on updatedBids are not directly saved in db
        em.detach(updatedBids);
        updatedBids
            .bidPrice(UPDATED_BID_PRICE)
            .quantityKg(UPDATED_QUANTITY_KG)
            .bidStatus(UPDATED_BID_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        BidsDTO bidsDTO = bidsMapper.toDto(updatedBids);

        restBidsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bidsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bidsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Bids in the database
        List<Bids> bidsList = bidsRepository.findAll();
        assertThat(bidsList).hasSize(databaseSizeBeforeUpdate);
        Bids testBids = bidsList.get(bidsList.size() - 1);
        assertThat(testBids.getBidPrice()).isEqualTo(UPDATED_BID_PRICE);
        assertThat(testBids.getQuantityKg()).isEqualTo(UPDATED_QUANTITY_KG);
        assertThat(testBids.getBidStatus()).isEqualTo(UPDATED_BID_STATUS);
        assertThat(testBids.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBids.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBids.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testBids.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingBids() throws Exception {
        int databaseSizeBeforeUpdate = bidsRepository.findAll().size();
        bids.setId(count.incrementAndGet());

        // Create the Bids
        BidsDTO bidsDTO = bidsMapper.toDto(bids);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBidsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bidsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bidsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bids in the database
        List<Bids> bidsList = bidsRepository.findAll();
        assertThat(bidsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBids() throws Exception {
        int databaseSizeBeforeUpdate = bidsRepository.findAll().size();
        bids.setId(count.incrementAndGet());

        // Create the Bids
        BidsDTO bidsDTO = bidsMapper.toDto(bids);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBidsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bidsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bids in the database
        List<Bids> bidsList = bidsRepository.findAll();
        assertThat(bidsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBids() throws Exception {
        int databaseSizeBeforeUpdate = bidsRepository.findAll().size();
        bids.setId(count.incrementAndGet());

        // Create the Bids
        BidsDTO bidsDTO = bidsMapper.toDto(bids);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBidsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bidsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bids in the database
        List<Bids> bidsList = bidsRepository.findAll();
        assertThat(bidsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBidsWithPatch() throws Exception {
        // Initialize the database
        bidsRepository.saveAndFlush(bids);

        int databaseSizeBeforeUpdate = bidsRepository.findAll().size();

        // Update the bids using partial update
        Bids partialUpdatedBids = new Bids();
        partialUpdatedBids.setId(bids.getId());

        partialUpdatedBids.bidPrice(UPDATED_BID_PRICE).createdOn(UPDATED_CREATED_ON).createdBy(UPDATED_CREATED_BY);

        restBidsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBids.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBids))
            )
            .andExpect(status().isOk());

        // Validate the Bids in the database
        List<Bids> bidsList = bidsRepository.findAll();
        assertThat(bidsList).hasSize(databaseSizeBeforeUpdate);
        Bids testBids = bidsList.get(bidsList.size() - 1);
        assertThat(testBids.getBidPrice()).isEqualTo(UPDATED_BID_PRICE);
        assertThat(testBids.getQuantityKg()).isEqualTo(DEFAULT_QUANTITY_KG);
        assertThat(testBids.getBidStatus()).isEqualTo(DEFAULT_BID_STATUS);
        assertThat(testBids.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBids.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBids.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testBids.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateBidsWithPatch() throws Exception {
        // Initialize the database
        bidsRepository.saveAndFlush(bids);

        int databaseSizeBeforeUpdate = bidsRepository.findAll().size();

        // Update the bids using partial update
        Bids partialUpdatedBids = new Bids();
        partialUpdatedBids.setId(bids.getId());

        partialUpdatedBids
            .bidPrice(UPDATED_BID_PRICE)
            .quantityKg(UPDATED_QUANTITY_KG)
            .bidStatus(UPDATED_BID_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restBidsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBids.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBids))
            )
            .andExpect(status().isOk());

        // Validate the Bids in the database
        List<Bids> bidsList = bidsRepository.findAll();
        assertThat(bidsList).hasSize(databaseSizeBeforeUpdate);
        Bids testBids = bidsList.get(bidsList.size() - 1);
        assertThat(testBids.getBidPrice()).isEqualTo(UPDATED_BID_PRICE);
        assertThat(testBids.getQuantityKg()).isEqualTo(UPDATED_QUANTITY_KG);
        assertThat(testBids.getBidStatus()).isEqualTo(UPDATED_BID_STATUS);
        assertThat(testBids.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBids.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBids.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testBids.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingBids() throws Exception {
        int databaseSizeBeforeUpdate = bidsRepository.findAll().size();
        bids.setId(count.incrementAndGet());

        // Create the Bids
        BidsDTO bidsDTO = bidsMapper.toDto(bids);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBidsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bidsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bidsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bids in the database
        List<Bids> bidsList = bidsRepository.findAll();
        assertThat(bidsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBids() throws Exception {
        int databaseSizeBeforeUpdate = bidsRepository.findAll().size();
        bids.setId(count.incrementAndGet());

        // Create the Bids
        BidsDTO bidsDTO = bidsMapper.toDto(bids);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBidsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bidsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bids in the database
        List<Bids> bidsList = bidsRepository.findAll();
        assertThat(bidsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBids() throws Exception {
        int databaseSizeBeforeUpdate = bidsRepository.findAll().size();
        bids.setId(count.incrementAndGet());

        // Create the Bids
        BidsDTO bidsDTO = bidsMapper.toDto(bids);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBidsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bidsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bids in the database
        List<Bids> bidsList = bidsRepository.findAll();
        assertThat(bidsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBids() throws Exception {
        // Initialize the database
        bidsRepository.saveAndFlush(bids);

        int databaseSizeBeforeDelete = bidsRepository.findAll().size();

        // Delete the bids
        restBidsMockMvc
            .perform(delete(ENTITY_API_URL_ID, bids.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bids> bidsList = bidsRepository.findAll();
        assertThat(bidsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
