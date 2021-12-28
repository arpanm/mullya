package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.Banner;
import com.mullya.app.repository.BannerRepository;
import com.mullya.app.service.dto.BannerDTO;
import com.mullya.app.service.mapper.BannerMapper;
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
 * Integration tests for the {@link BannerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BannerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LANDING_URL = "AAAAAAAAAA";
    private static final String UPDATED_LANDING_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HTML = "AAAAAAAAAA";
    private static final String UPDATED_HTML = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_HTML = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_HTML = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_START_DATE = "AAAAAAAAAA";
    private static final String UPDATED_START_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_END_DATE = "AAAAAAAAAA";
    private static final String UPDATED_END_DATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/banners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBannerMockMvc;

    private Banner banner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banner createEntity(EntityManager em) {
        Banner banner = new Banner()
            .name(DEFAULT_NAME)
            .imageUrl(DEFAULT_IMAGE_URL)
            .mobileImageUrl(DEFAULT_MOBILE_IMAGE_URL)
            .landingUrl(DEFAULT_LANDING_URL)
            .description(DEFAULT_DESCRIPTION)
            .html(DEFAULT_HTML)
            .mobileHtml(DEFAULT_MOBILE_HTML)
            .isActive(DEFAULT_IS_ACTIVE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return banner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banner createUpdatedEntity(EntityManager em) {
        Banner banner = new Banner()
            .name(UPDATED_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .mobileImageUrl(UPDATED_MOBILE_IMAGE_URL)
            .landingUrl(UPDATED_LANDING_URL)
            .description(UPDATED_DESCRIPTION)
            .html(UPDATED_HTML)
            .mobileHtml(UPDATED_MOBILE_HTML)
            .isActive(UPDATED_IS_ACTIVE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return banner;
    }

    @BeforeEach
    public void initTest() {
        banner = createEntity(em);
    }

    @Test
    @Transactional
    void createBanner() throws Exception {
        int databaseSizeBeforeCreate = bannerRepository.findAll().size();
        // Create the Banner
        BannerDTO bannerDTO = bannerMapper.toDto(banner);
        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bannerDTO)))
            .andExpect(status().isCreated());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeCreate + 1);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBanner.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testBanner.getMobileImageUrl()).isEqualTo(DEFAULT_MOBILE_IMAGE_URL);
        assertThat(testBanner.getLandingUrl()).isEqualTo(DEFAULT_LANDING_URL);
        assertThat(testBanner.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBanner.getHtml()).isEqualTo(DEFAULT_HTML);
        assertThat(testBanner.getMobileHtml()).isEqualTo(DEFAULT_MOBILE_HTML);
        assertThat(testBanner.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBanner.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBanner.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void createBannerWithExistingId() throws Exception {
        // Create the Banner with an existing ID
        banner.setId(1L);
        BannerDTO bannerDTO = bannerMapper.toDto(banner);

        int databaseSizeBeforeCreate = bannerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bannerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBanners() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList
        restBannerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banner.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].mobileImageUrl").value(hasItem(DEFAULT_MOBILE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].landingUrl").value(hasItem(DEFAULT_LANDING_URL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].html").value(hasItem(DEFAULT_HTML)))
            .andExpect(jsonPath("$.[*].mobileHtml").value(hasItem(DEFAULT_MOBILE_HTML)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE)));
    }

    @Test
    @Transactional
    void getBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get the banner
        restBannerMockMvc
            .perform(get(ENTITY_API_URL_ID, banner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(banner.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.mobileImageUrl").value(DEFAULT_MOBILE_IMAGE_URL))
            .andExpect(jsonPath("$.landingUrl").value(DEFAULT_LANDING_URL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.html").value(DEFAULT_HTML))
            .andExpect(jsonPath("$.mobileHtml").value(DEFAULT_MOBILE_HTML))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE));
    }

    @Test
    @Transactional
    void getNonExistingBanner() throws Exception {
        // Get the banner
        restBannerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Update the banner
        Banner updatedBanner = bannerRepository.findById(banner.getId()).get();
        // Disconnect from session so that the updates on updatedBanner are not directly saved in db
        em.detach(updatedBanner);
        updatedBanner
            .name(UPDATED_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .mobileImageUrl(UPDATED_MOBILE_IMAGE_URL)
            .landingUrl(UPDATED_LANDING_URL)
            .description(UPDATED_DESCRIPTION)
            .html(UPDATED_HTML)
            .mobileHtml(UPDATED_MOBILE_HTML)
            .isActive(UPDATED_IS_ACTIVE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        BannerDTO bannerDTO = bannerMapper.toDto(updatedBanner);

        restBannerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bannerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bannerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBanner.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testBanner.getMobileImageUrl()).isEqualTo(UPDATED_MOBILE_IMAGE_URL);
        assertThat(testBanner.getLandingUrl()).isEqualTo(UPDATED_LANDING_URL);
        assertThat(testBanner.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBanner.getHtml()).isEqualTo(UPDATED_HTML);
        assertThat(testBanner.getMobileHtml()).isEqualTo(UPDATED_MOBILE_HTML);
        assertThat(testBanner.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBanner.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBanner.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void putNonExistingBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // Create the Banner
        BannerDTO bannerDTO = bannerMapper.toDto(banner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bannerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bannerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // Create the Banner
        BannerDTO bannerDTO = bannerMapper.toDto(banner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bannerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // Create the Banner
        BannerDTO bannerDTO = bannerMapper.toDto(banner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bannerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBannerWithPatch() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Update the banner using partial update
        Banner partialUpdatedBanner = new Banner();
        partialUpdatedBanner.setId(banner.getId());

        partialUpdatedBanner
            .name(UPDATED_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .mobileImageUrl(UPDATED_MOBILE_IMAGE_URL)
            .landingUrl(UPDATED_LANDING_URL)
            .mobileHtml(UPDATED_MOBILE_HTML)
            .endDate(UPDATED_END_DATE);

        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanner))
            )
            .andExpect(status().isOk());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBanner.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testBanner.getMobileImageUrl()).isEqualTo(UPDATED_MOBILE_IMAGE_URL);
        assertThat(testBanner.getLandingUrl()).isEqualTo(UPDATED_LANDING_URL);
        assertThat(testBanner.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBanner.getHtml()).isEqualTo(DEFAULT_HTML);
        assertThat(testBanner.getMobileHtml()).isEqualTo(UPDATED_MOBILE_HTML);
        assertThat(testBanner.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBanner.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBanner.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void fullUpdateBannerWithPatch() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Update the banner using partial update
        Banner partialUpdatedBanner = new Banner();
        partialUpdatedBanner.setId(banner.getId());

        partialUpdatedBanner
            .name(UPDATED_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .mobileImageUrl(UPDATED_MOBILE_IMAGE_URL)
            .landingUrl(UPDATED_LANDING_URL)
            .description(UPDATED_DESCRIPTION)
            .html(UPDATED_HTML)
            .mobileHtml(UPDATED_MOBILE_HTML)
            .isActive(UPDATED_IS_ACTIVE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanner))
            )
            .andExpect(status().isOk());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBanner.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testBanner.getMobileImageUrl()).isEqualTo(UPDATED_MOBILE_IMAGE_URL);
        assertThat(testBanner.getLandingUrl()).isEqualTo(UPDATED_LANDING_URL);
        assertThat(testBanner.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBanner.getHtml()).isEqualTo(UPDATED_HTML);
        assertThat(testBanner.getMobileHtml()).isEqualTo(UPDATED_MOBILE_HTML);
        assertThat(testBanner.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBanner.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBanner.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // Create the Banner
        BannerDTO bannerDTO = bannerMapper.toDto(banner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bannerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bannerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // Create the Banner
        BannerDTO bannerDTO = bannerMapper.toDto(banner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bannerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // Create the Banner
        BannerDTO bannerDTO = bannerMapper.toDto(banner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bannerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeDelete = bannerRepository.findAll().size();

        // Delete the banner
        restBannerMockMvc
            .perform(delete(ENTITY_API_URL_ID, banner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
