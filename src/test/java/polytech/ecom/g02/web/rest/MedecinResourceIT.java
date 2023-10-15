package polytech.ecom.g02.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import polytech.ecom.g02.IntegrationTest;
import polytech.ecom.g02.domain.Medecin;
import polytech.ecom.g02.repository.MedecinRepository;

/**
 * Integration tests for the {@link MedecinResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MedecinResourceIT {

    private static final Integer DEFAULT_ID_MEDECIN = 1;
    private static final Integer UPDATED_ID_MEDECIN = 2;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/medecins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedecinMockMvc;

    private Medecin medecin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medecin createEntity(EntityManager em) {
        Medecin medecin = new Medecin().idMedecin(DEFAULT_ID_MEDECIN).firstName(DEFAULT_FIRST_NAME).lastName(DEFAULT_LAST_NAME);
        return medecin;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medecin createUpdatedEntity(EntityManager em) {
        Medecin medecin = new Medecin().idMedecin(UPDATED_ID_MEDECIN).firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME);
        return medecin;
    }

    @BeforeEach
    public void initTest() {
        medecin = createEntity(em);
    }

    @Test
    @Transactional
    void createMedecin() throws Exception {
        int databaseSizeBeforeCreate = medecinRepository.findAll().size();
        // Create the Medecin
        restMedecinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medecin)))
            .andExpect(status().isCreated());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeCreate + 1);
        Medecin testMedecin = medecinList.get(medecinList.size() - 1);
        assertThat(testMedecin.getIdMedecin()).isEqualTo(DEFAULT_ID_MEDECIN);
        assertThat(testMedecin.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMedecin.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    void createMedecinWithExistingId() throws Exception {
        // Create the Medecin with an existing ID
        medecin.setId(1L);

        int databaseSizeBeforeCreate = medecinRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedecinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medecin)))
            .andExpect(status().isBadRequest());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdMedecinIsRequired() throws Exception {
        int databaseSizeBeforeTest = medecinRepository.findAll().size();
        // set the field null
        medecin.setIdMedecin(null);

        // Create the Medecin, which fails.

        restMedecinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medecin)))
            .andExpect(status().isBadRequest());

        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = medecinRepository.findAll().size();
        // set the field null
        medecin.setFirstName(null);

        // Create the Medecin, which fails.

        restMedecinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medecin)))
            .andExpect(status().isBadRequest());

        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = medecinRepository.findAll().size();
        // set the field null
        medecin.setLastName(null);

        // Create the Medecin, which fails.

        restMedecinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medecin)))
            .andExpect(status().isBadRequest());

        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMedecins() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        // Get all the medecinList
        restMedecinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medecin.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMedecin").value(hasItem(DEFAULT_ID_MEDECIN)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));
    }

    @Test
    @Transactional
    void getMedecin() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        // Get the medecin
        restMedecinMockMvc
            .perform(get(ENTITY_API_URL_ID, medecin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medecin.getId().intValue()))
            .andExpect(jsonPath("$.idMedecin").value(DEFAULT_ID_MEDECIN))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME));
    }

    @Test
    @Transactional
    void getNonExistingMedecin() throws Exception {
        // Get the medecin
        restMedecinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMedecin() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();

        // Update the medecin
        Medecin updatedMedecin = medecinRepository.findById(medecin.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMedecin are not directly saved in db
        em.detach(updatedMedecin);
        updatedMedecin.idMedecin(UPDATED_ID_MEDECIN).firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME);

        restMedecinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMedecin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMedecin))
            )
            .andExpect(status().isOk());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
        Medecin testMedecin = medecinList.get(medecinList.size() - 1);
        assertThat(testMedecin.getIdMedecin()).isEqualTo(UPDATED_ID_MEDECIN);
        assertThat(testMedecin.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMedecin.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void putNonExistingMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();
        medecin.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedecinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medecin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medecin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();
        medecin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedecinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medecin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();
        medecin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedecinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medecin)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedecinWithPatch() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();

        // Update the medecin using partial update
        Medecin partialUpdatedMedecin = new Medecin();
        partialUpdatedMedecin.setId(medecin.getId());

        restMedecinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedecin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedecin))
            )
            .andExpect(status().isOk());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
        Medecin testMedecin = medecinList.get(medecinList.size() - 1);
        assertThat(testMedecin.getIdMedecin()).isEqualTo(DEFAULT_ID_MEDECIN);
        assertThat(testMedecin.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMedecin.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    void fullUpdateMedecinWithPatch() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();

        // Update the medecin using partial update
        Medecin partialUpdatedMedecin = new Medecin();
        partialUpdatedMedecin.setId(medecin.getId());

        partialUpdatedMedecin.idMedecin(UPDATED_ID_MEDECIN).firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME);

        restMedecinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedecin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedecin))
            )
            .andExpect(status().isOk());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
        Medecin testMedecin = medecinList.get(medecinList.size() - 1);
        assertThat(testMedecin.getIdMedecin()).isEqualTo(UPDATED_ID_MEDECIN);
        assertThat(testMedecin.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMedecin.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();
        medecin.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedecinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medecin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medecin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();
        medecin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedecinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medecin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();
        medecin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedecinMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(medecin)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedecin() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        int databaseSizeBeforeDelete = medecinRepository.findAll().size();

        // Delete the medecin
        restMedecinMockMvc
            .perform(delete(ENTITY_API_URL_ID, medecin.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
