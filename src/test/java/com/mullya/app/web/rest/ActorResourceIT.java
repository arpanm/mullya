package com.mullya.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mullya.app.IntegrationTest;
import com.mullya.app.domain.Actor;
import com.mullya.app.domain.enumeration.ActorType;
import com.mullya.app.repository.ActorRepository;
import com.mullya.app.service.dto.ActorDTO;
import com.mullya.app.service.mapper.ActorMapper;
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
 * Integration tests for the {@link ActorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActorResourceIT {

    private static final String DEFAULT_EMAIL = "Gqko|@-H.E";
    private static final String UPDATED_EMAIL = "%_P@O|a&3.(tbh";

    private static final Long DEFAULT_PHONE = 1000000000L;
    private static final Long UPDATED_PHONE = 1000000001L;

    private static final Boolean DEFAULT_IS_EMAIL_VERIFIED = false;
    private static final Boolean UPDATED_IS_EMAIL_VERIFIED = true;

    private static final Boolean DEFAULT_IS_PHONE_VERIFIED = false;
    private static final Boolean UPDATED_IS_PHONE_VERIFIED = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final ActorType DEFAULT_TYPE = ActorType.Buyer;
    private static final ActorType UPDATED_TYPE = ActorType.Farmer;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/actors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ActorMapper actorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActorMockMvc;

    private Actor actor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actor createEntity(EntityManager em) {
        Actor actor = new Actor()
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .isEmailVerified(DEFAULT_IS_EMAIL_VERIFIED)
            .isPhoneVerified(DEFAULT_IS_PHONE_VERIFIED)
            .isActive(DEFAULT_IS_ACTIVE)
            .password(DEFAULT_PASSWORD)
            .type(DEFAULT_TYPE)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return actor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actor createUpdatedEntity(EntityManager em) {
        Actor actor = new Actor()
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .isEmailVerified(UPDATED_IS_EMAIL_VERIFIED)
            .isPhoneVerified(UPDATED_IS_PHONE_VERIFIED)
            .isActive(UPDATED_IS_ACTIVE)
            .password(UPDATED_PASSWORD)
            .type(UPDATED_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return actor;
    }

    @BeforeEach
    public void initTest() {
        actor = createEntity(em);
    }

    @Test
    @Transactional
    void createActor() throws Exception {
        int databaseSizeBeforeCreate = actorRepository.findAll().size();
        // Create the Actor
        ActorDTO actorDTO = actorMapper.toDto(actor);
        restActorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actorDTO)))
            .andExpect(status().isCreated());

        // Validate the Actor in the database
        List<Actor> actorList = actorRepository.findAll();
        assertThat(actorList).hasSize(databaseSizeBeforeCreate + 1);
        Actor testActor = actorList.get(actorList.size() - 1);
        assertThat(testActor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testActor.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testActor.getIsEmailVerified()).isEqualTo(DEFAULT_IS_EMAIL_VERIFIED);
        assertThat(testActor.getIsPhoneVerified()).isEqualTo(DEFAULT_IS_PHONE_VERIFIED);
        assertThat(testActor.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testActor.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testActor.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testActor.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testActor.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testActor.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testActor.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createActorWithExistingId() throws Exception {
        // Create the Actor with an existing ID
        actor.setId(1L);
        ActorDTO actorDTO = actorMapper.toDto(actor);

        int databaseSizeBeforeCreate = actorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Actor in the database
        List<Actor> actorList = actorRepository.findAll();
        assertThat(actorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActors() throws Exception {
        // Initialize the database
        actorRepository.saveAndFlush(actor);

        // Get all the actorList
        restActorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actor.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].isEmailVerified").value(hasItem(DEFAULT_IS_EMAIL_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPhoneVerified").value(hasItem(DEFAULT_IS_PHONE_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getActor() throws Exception {
        // Initialize the database
        actorRepository.saveAndFlush(actor);

        // Get the actor
        restActorMockMvc
            .perform(get(ENTITY_API_URL_ID, actor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actor.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()))
            .andExpect(jsonPath("$.isEmailVerified").value(DEFAULT_IS_EMAIL_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.isPhoneVerified").value(DEFAULT_IS_PHONE_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingActor() throws Exception {
        // Get the actor
        restActorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewActor() throws Exception {
        // Initialize the database
        actorRepository.saveAndFlush(actor);

        int databaseSizeBeforeUpdate = actorRepository.findAll().size();

        // Update the actor
        Actor updatedActor = actorRepository.findById(actor.getId()).get();
        // Disconnect from session so that the updates on updatedActor are not directly saved in db
        em.detach(updatedActor);
        updatedActor
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .isEmailVerified(UPDATED_IS_EMAIL_VERIFIED)
            .isPhoneVerified(UPDATED_IS_PHONE_VERIFIED)
            .isActive(UPDATED_IS_ACTIVE)
            .password(UPDATED_PASSWORD)
            .type(UPDATED_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        ActorDTO actorDTO = actorMapper.toDto(updatedActor);

        restActorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, actorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Actor in the database
        List<Actor> actorList = actorRepository.findAll();
        assertThat(actorList).hasSize(databaseSizeBeforeUpdate);
        Actor testActor = actorList.get(actorList.size() - 1);
        assertThat(testActor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testActor.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testActor.getIsEmailVerified()).isEqualTo(UPDATED_IS_EMAIL_VERIFIED);
        assertThat(testActor.getIsPhoneVerified()).isEqualTo(UPDATED_IS_PHONE_VERIFIED);
        assertThat(testActor.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testActor.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testActor.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testActor.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testActor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testActor.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testActor.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingActor() throws Exception {
        int databaseSizeBeforeUpdate = actorRepository.findAll().size();
        actor.setId(count.incrementAndGet());

        // Create the Actor
        ActorDTO actorDTO = actorMapper.toDto(actor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, actorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actor in the database
        List<Actor> actorList = actorRepository.findAll();
        assertThat(actorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActor() throws Exception {
        int databaseSizeBeforeUpdate = actorRepository.findAll().size();
        actor.setId(count.incrementAndGet());

        // Create the Actor
        ActorDTO actorDTO = actorMapper.toDto(actor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actor in the database
        List<Actor> actorList = actorRepository.findAll();
        assertThat(actorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActor() throws Exception {
        int databaseSizeBeforeUpdate = actorRepository.findAll().size();
        actor.setId(count.incrementAndGet());

        // Create the Actor
        ActorDTO actorDTO = actorMapper.toDto(actor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Actor in the database
        List<Actor> actorList = actorRepository.findAll();
        assertThat(actorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActorWithPatch() throws Exception {
        // Initialize the database
        actorRepository.saveAndFlush(actor);

        int databaseSizeBeforeUpdate = actorRepository.findAll().size();

        // Update the actor using partial update
        Actor partialUpdatedActor = new Actor();
        partialUpdatedActor.setId(actor.getId());

        partialUpdatedActor
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .isEmailVerified(UPDATED_IS_EMAIL_VERIFIED)
            .isPhoneVerified(UPDATED_IS_PHONE_VERIFIED)
            .isActive(UPDATED_IS_ACTIVE)
            .type(UPDATED_TYPE);

        restActorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActor))
            )
            .andExpect(status().isOk());

        // Validate the Actor in the database
        List<Actor> actorList = actorRepository.findAll();
        assertThat(actorList).hasSize(databaseSizeBeforeUpdate);
        Actor testActor = actorList.get(actorList.size() - 1);
        assertThat(testActor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testActor.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testActor.getIsEmailVerified()).isEqualTo(UPDATED_IS_EMAIL_VERIFIED);
        assertThat(testActor.getIsPhoneVerified()).isEqualTo(UPDATED_IS_PHONE_VERIFIED);
        assertThat(testActor.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testActor.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testActor.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testActor.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testActor.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testActor.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testActor.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateActorWithPatch() throws Exception {
        // Initialize the database
        actorRepository.saveAndFlush(actor);

        int databaseSizeBeforeUpdate = actorRepository.findAll().size();

        // Update the actor using partial update
        Actor partialUpdatedActor = new Actor();
        partialUpdatedActor.setId(actor.getId());

        partialUpdatedActor
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .isEmailVerified(UPDATED_IS_EMAIL_VERIFIED)
            .isPhoneVerified(UPDATED_IS_PHONE_VERIFIED)
            .isActive(UPDATED_IS_ACTIVE)
            .password(UPDATED_PASSWORD)
            .type(UPDATED_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restActorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActor))
            )
            .andExpect(status().isOk());

        // Validate the Actor in the database
        List<Actor> actorList = actorRepository.findAll();
        assertThat(actorList).hasSize(databaseSizeBeforeUpdate);
        Actor testActor = actorList.get(actorList.size() - 1);
        assertThat(testActor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testActor.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testActor.getIsEmailVerified()).isEqualTo(UPDATED_IS_EMAIL_VERIFIED);
        assertThat(testActor.getIsPhoneVerified()).isEqualTo(UPDATED_IS_PHONE_VERIFIED);
        assertThat(testActor.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testActor.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testActor.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testActor.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testActor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testActor.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testActor.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingActor() throws Exception {
        int databaseSizeBeforeUpdate = actorRepository.findAll().size();
        actor.setId(count.incrementAndGet());

        // Create the Actor
        ActorDTO actorDTO = actorMapper.toDto(actor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, actorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(actorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actor in the database
        List<Actor> actorList = actorRepository.findAll();
        assertThat(actorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActor() throws Exception {
        int databaseSizeBeforeUpdate = actorRepository.findAll().size();
        actor.setId(count.incrementAndGet());

        // Create the Actor
        ActorDTO actorDTO = actorMapper.toDto(actor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(actorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actor in the database
        List<Actor> actorList = actorRepository.findAll();
        assertThat(actorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActor() throws Exception {
        int databaseSizeBeforeUpdate = actorRepository.findAll().size();
        actor.setId(count.incrementAndGet());

        // Create the Actor
        ActorDTO actorDTO = actorMapper.toDto(actor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(actorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Actor in the database
        List<Actor> actorList = actorRepository.findAll();
        assertThat(actorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActor() throws Exception {
        // Initialize the database
        actorRepository.saveAndFlush(actor);

        int databaseSizeBeforeDelete = actorRepository.findAll().size();

        // Delete the actor
        restActorMockMvc
            .perform(delete(ENTITY_API_URL_ID, actor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Actor> actorList = actorRepository.findAll();
        assertThat(actorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
