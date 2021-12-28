package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.Stock;
import com.mullya.app.domain.enumeration.StockStatus;
import com.mullya.app.repository.StockRepository;
import com.mullya.app.service.dto.StockDTO;
import com.mullya.app.service.mapper.StockMapper;
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
 * Integration tests for the {@link StockResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StockResourceIT {

    private static final Float DEFAULT_MIN_PRICE = 1F;
    private static final Float UPDATED_MIN_PRICE = 2F;

    private static final Float DEFAULT_MAX_PRICE = 1F;
    private static final Float UPDATED_MAX_PRICE = 2F;

    private static final Float DEFAULT_QUANTITY_KG = 1F;
    private static final Float UPDATED_QUANTITY_KG = 2F;

    private static final String DEFAULT_EXPIRY = "AAAAAAAAAA";
    private static final String UPDATED_EXPIRY = "BBBBBBBBBB";

    private static final String DEFAULT_AVIALABLE_FROM = "AAAAAAAAAA";
    private static final String UPDATED_AVIALABLE_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final StockStatus DEFAULT_STOCK_STATUS = StockStatus.New;
    private static final StockStatus UPDATED_STOCK_STATUS = StockStatus.Expired;

    private static final Boolean DEFAULT_IS_OPEN_FOR_BIDDING = false;
    private static final Boolean UPDATED_IS_OPEN_FOR_BIDDING = true;

    private static final String ENTITY_API_URL = "/api/stocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockMockMvc;

    private Stock stock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stock createEntity(EntityManager em) {
        Stock stock = new Stock()
            .minPrice(DEFAULT_MIN_PRICE)
            .maxPrice(DEFAULT_MAX_PRICE)
            .quantityKg(DEFAULT_QUANTITY_KG)
            .expiry(DEFAULT_EXPIRY)
            .avialableFrom(DEFAULT_AVIALABLE_FROM)
            .description(DEFAULT_DESCRIPTION)
            .stockStatus(DEFAULT_STOCK_STATUS)
            .isOpenForBidding(DEFAULT_IS_OPEN_FOR_BIDDING);
        return stock;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stock createUpdatedEntity(EntityManager em) {
        Stock stock = new Stock()
            .minPrice(UPDATED_MIN_PRICE)
            .maxPrice(UPDATED_MAX_PRICE)
            .quantityKg(UPDATED_QUANTITY_KG)
            .expiry(UPDATED_EXPIRY)
            .avialableFrom(UPDATED_AVIALABLE_FROM)
            .description(UPDATED_DESCRIPTION)
            .stockStatus(UPDATED_STOCK_STATUS)
            .isOpenForBidding(UPDATED_IS_OPEN_FOR_BIDDING);
        return stock;
    }

    @BeforeEach
    public void initTest() {
        stock = createEntity(em);
    }

    @Test
    @Transactional
    void createStock() throws Exception {
        int databaseSizeBeforeCreate = stockRepository.findAll().size();
        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);
        restStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isCreated());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeCreate + 1);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getMinPrice()).isEqualTo(DEFAULT_MIN_PRICE);
        assertThat(testStock.getMaxPrice()).isEqualTo(DEFAULT_MAX_PRICE);
        assertThat(testStock.getQuantityKg()).isEqualTo(DEFAULT_QUANTITY_KG);
        assertThat(testStock.getExpiry()).isEqualTo(DEFAULT_EXPIRY);
        assertThat(testStock.getAvialableFrom()).isEqualTo(DEFAULT_AVIALABLE_FROM);
        assertThat(testStock.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStock.getStockStatus()).isEqualTo(DEFAULT_STOCK_STATUS);
        assertThat(testStock.getIsOpenForBidding()).isEqualTo(DEFAULT_IS_OPEN_FOR_BIDDING);
    }

    @Test
    @Transactional
    void createStockWithExistingId() throws Exception {
        // Create the Stock with an existing ID
        stock.setId(1L);
        StockDTO stockDTO = stockMapper.toDto(stock);

        int databaseSizeBeforeCreate = stockRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStocks() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList
        restStockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stock.getId().intValue())))
            .andExpect(jsonPath("$.[*].minPrice").value(hasItem(DEFAULT_MIN_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxPrice").value(hasItem(DEFAULT_MAX_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantityKg").value(hasItem(DEFAULT_QUANTITY_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].expiry").value(hasItem(DEFAULT_EXPIRY)))
            .andExpect(jsonPath("$.[*].avialableFrom").value(hasItem(DEFAULT_AVIALABLE_FROM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].stockStatus").value(hasItem(DEFAULT_STOCK_STATUS.toString())))
            .andExpect(jsonPath("$.[*].isOpenForBidding").value(hasItem(DEFAULT_IS_OPEN_FOR_BIDDING.booleanValue())));
    }

    @Test
    @Transactional
    void getStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get the stock
        restStockMockMvc
            .perform(get(ENTITY_API_URL_ID, stock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stock.getId().intValue()))
            .andExpect(jsonPath("$.minPrice").value(DEFAULT_MIN_PRICE.doubleValue()))
            .andExpect(jsonPath("$.maxPrice").value(DEFAULT_MAX_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantityKg").value(DEFAULT_QUANTITY_KG.doubleValue()))
            .andExpect(jsonPath("$.expiry").value(DEFAULT_EXPIRY))
            .andExpect(jsonPath("$.avialableFrom").value(DEFAULT_AVIALABLE_FROM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.stockStatus").value(DEFAULT_STOCK_STATUS.toString()))
            .andExpect(jsonPath("$.isOpenForBidding").value(DEFAULT_IS_OPEN_FOR_BIDDING.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingStock() throws Exception {
        // Get the stock
        restStockMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Update the stock
        Stock updatedStock = stockRepository.findById(stock.getId()).get();
        // Disconnect from session so that the updates on updatedStock are not directly saved in db
        em.detach(updatedStock);
        updatedStock
            .minPrice(UPDATED_MIN_PRICE)
            .maxPrice(UPDATED_MAX_PRICE)
            .quantityKg(UPDATED_QUANTITY_KG)
            .expiry(UPDATED_EXPIRY)
            .avialableFrom(UPDATED_AVIALABLE_FROM)
            .description(UPDATED_DESCRIPTION)
            .stockStatus(UPDATED_STOCK_STATUS)
            .isOpenForBidding(UPDATED_IS_OPEN_FOR_BIDDING);
        StockDTO stockDTO = stockMapper.toDto(updatedStock);

        restStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockDTO))
            )
            .andExpect(status().isOk());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testStock.getMaxPrice()).isEqualTo(UPDATED_MAX_PRICE);
        assertThat(testStock.getQuantityKg()).isEqualTo(UPDATED_QUANTITY_KG);
        assertThat(testStock.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testStock.getAvialableFrom()).isEqualTo(UPDATED_AVIALABLE_FROM);
        assertThat(testStock.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStock.getStockStatus()).isEqualTo(UPDATED_STOCK_STATUS);
        assertThat(testStock.getIsOpenForBidding()).isEqualTo(UPDATED_IS_OPEN_FOR_BIDDING);
    }

    @Test
    @Transactional
    void putNonExistingStock() throws Exception {
        int databaseSizeBeforeUpdate = stockRepository.findAll().size();
        stock.setId(count.incrementAndGet());

        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStock() throws Exception {
        int databaseSizeBeforeUpdate = stockRepository.findAll().size();
        stock.setId(count.incrementAndGet());

        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStock() throws Exception {
        int databaseSizeBeforeUpdate = stockRepository.findAll().size();
        stock.setId(count.incrementAndGet());

        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStockWithPatch() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Update the stock using partial update
        Stock partialUpdatedStock = new Stock();
        partialUpdatedStock.setId(stock.getId());

        partialUpdatedStock.minPrice(UPDATED_MIN_PRICE).maxPrice(UPDATED_MAX_PRICE).stockStatus(UPDATED_STOCK_STATUS);

        restStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStock.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStock))
            )
            .andExpect(status().isOk());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testStock.getMaxPrice()).isEqualTo(UPDATED_MAX_PRICE);
        assertThat(testStock.getQuantityKg()).isEqualTo(DEFAULT_QUANTITY_KG);
        assertThat(testStock.getExpiry()).isEqualTo(DEFAULT_EXPIRY);
        assertThat(testStock.getAvialableFrom()).isEqualTo(DEFAULT_AVIALABLE_FROM);
        assertThat(testStock.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStock.getStockStatus()).isEqualTo(UPDATED_STOCK_STATUS);
        assertThat(testStock.getIsOpenForBidding()).isEqualTo(DEFAULT_IS_OPEN_FOR_BIDDING);
    }

    @Test
    @Transactional
    void fullUpdateStockWithPatch() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Update the stock using partial update
        Stock partialUpdatedStock = new Stock();
        partialUpdatedStock.setId(stock.getId());

        partialUpdatedStock
            .minPrice(UPDATED_MIN_PRICE)
            .maxPrice(UPDATED_MAX_PRICE)
            .quantityKg(UPDATED_QUANTITY_KG)
            .expiry(UPDATED_EXPIRY)
            .avialableFrom(UPDATED_AVIALABLE_FROM)
            .description(UPDATED_DESCRIPTION)
            .stockStatus(UPDATED_STOCK_STATUS)
            .isOpenForBidding(UPDATED_IS_OPEN_FOR_BIDDING);

        restStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStock.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStock))
            )
            .andExpect(status().isOk());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testStock.getMaxPrice()).isEqualTo(UPDATED_MAX_PRICE);
        assertThat(testStock.getQuantityKg()).isEqualTo(UPDATED_QUANTITY_KG);
        assertThat(testStock.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testStock.getAvialableFrom()).isEqualTo(UPDATED_AVIALABLE_FROM);
        assertThat(testStock.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStock.getStockStatus()).isEqualTo(UPDATED_STOCK_STATUS);
        assertThat(testStock.getIsOpenForBidding()).isEqualTo(UPDATED_IS_OPEN_FOR_BIDDING);
    }

    @Test
    @Transactional
    void patchNonExistingStock() throws Exception {
        int databaseSizeBeforeUpdate = stockRepository.findAll().size();
        stock.setId(count.incrementAndGet());

        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stockDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStock() throws Exception {
        int databaseSizeBeforeUpdate = stockRepository.findAll().size();
        stock.setId(count.incrementAndGet());

        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStock() throws Exception {
        int databaseSizeBeforeUpdate = stockRepository.findAll().size();
        stock.setId(count.incrementAndGet());

        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        int databaseSizeBeforeDelete = stockRepository.findAll().size();

        // Delete the stock
        restStockMockMvc
            .perform(delete(ENTITY_API_URL_ID, stock.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
