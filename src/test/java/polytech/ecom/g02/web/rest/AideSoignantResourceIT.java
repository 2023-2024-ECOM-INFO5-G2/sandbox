package polytech.ecom.g02.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import polytech.ecom.g02.IntegrationTest;
import polytech.ecom.g02.domain.AideSoignant;
import polytech.ecom.g02.repository.AideSoignantRepository;

/**
 * Integration tests for the {@link AideSoignantResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AideSoignantResourceIT {

    private static final Integer DEFAULT_ID_SOIGNANT = 1;
    private static final Integer UPDATED_ID_SOIGNANT = 2;

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aide-soignants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AideSoignantRepository aideSoignantRepository;

    @Mock
    private AideSoignantRepository aideSoignantRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAideSoignantMockMvc;

    private AideSoignant aideSoignant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AideSoignant createEntity(EntityManager em) {
        AideSoignant aideSoignant = new AideSoignant().idSoignant(DEFAULT_ID_SOIGNANT).prenom(DEFAULT_PRENOM).nom(DEFAULT_NOM);
        return aideSoignant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AideSoignant createUpdatedEntity(EntityManager em) {
        AideSoignant aideSoignant = new AideSoignant().idSoignant(UPDATED_ID_SOIGNANT).prenom(UPDATED_PRENOM).nom(UPDATED_NOM);
        return aideSoignant;
    }

    @BeforeEach
    public void initTest() {
        aideSoignant = createEntity(em);
    }

    @Test
    @Transactional
    void createAideSoignant() throws Exception {
        int databaseSizeBeforeCreate = aideSoignantRepository.findAll().size();
        // Create the AideSoignant
        restAideSoignantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aideSoignant)))
            .andExpect(status().isCreated());

        // Validate the AideSoignant in the database
        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeCreate + 1);
        AideSoignant testAideSoignant = aideSoignantList.get(aideSoignantList.size() - 1);
        assertThat(testAideSoignant.getIdSoignant()).isEqualTo(DEFAULT_ID_SOIGNANT);
        assertThat(testAideSoignant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAideSoignant.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    void createAideSoignantWithExistingId() throws Exception {
        // Create the AideSoignant with an existing ID
        aideSoignant.setId(1L);

        int databaseSizeBeforeCreate = aideSoignantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAideSoignantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aideSoignant)))
            .andExpect(status().isBadRequest());

        // Validate the AideSoignant in the database
        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdSoignantIsRequired() throws Exception {
        int databaseSizeBeforeTest = aideSoignantRepository.findAll().size();
        // set the field null
        aideSoignant.setIdSoignant(null);

        // Create the AideSoignant, which fails.

        restAideSoignantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aideSoignant)))
            .andExpect(status().isBadRequest());

        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = aideSoignantRepository.findAll().size();
        // set the field null
        aideSoignant.setPrenom(null);

        // Create the AideSoignant, which fails.

        restAideSoignantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aideSoignant)))
            .andExpect(status().isBadRequest());

        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = aideSoignantRepository.findAll().size();
        // set the field null
        aideSoignant.setNom(null);

        // Create the AideSoignant, which fails.

        restAideSoignantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aideSoignant)))
            .andExpect(status().isBadRequest());

        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAideSoignants() throws Exception {
        // Initialize the database
        aideSoignantRepository.saveAndFlush(aideSoignant);

        // Get all the aideSoignantList
        restAideSoignantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aideSoignant.getId().intValue())))
            .andExpect(jsonPath("$.[*].idSoignant").value(hasItem(DEFAULT_ID_SOIGNANT)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAideSoignantsWithEagerRelationshipsIsEnabled() throws Exception {
        when(aideSoignantRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAideSoignantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(aideSoignantRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAideSoignantsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(aideSoignantRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAideSoignantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(aideSoignantRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAideSoignant() throws Exception {
        // Initialize the database
        aideSoignantRepository.saveAndFlush(aideSoignant);

        // Get the aideSoignant
        restAideSoignantMockMvc
            .perform(get(ENTITY_API_URL_ID, aideSoignant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aideSoignant.getId().intValue()))
            .andExpect(jsonPath("$.idSoignant").value(DEFAULT_ID_SOIGNANT))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    void getNonExistingAideSoignant() throws Exception {
        // Get the aideSoignant
        restAideSoignantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAideSoignant() throws Exception {
        // Initialize the database
        aideSoignantRepository.saveAndFlush(aideSoignant);

        int databaseSizeBeforeUpdate = aideSoignantRepository.findAll().size();

        // Update the aideSoignant
        AideSoignant updatedAideSoignant = aideSoignantRepository.findById(aideSoignant.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAideSoignant are not directly saved in db
        em.detach(updatedAideSoignant);
        updatedAideSoignant.idSoignant(UPDATED_ID_SOIGNANT).prenom(UPDATED_PRENOM).nom(UPDATED_NOM);

        restAideSoignantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAideSoignant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAideSoignant))
            )
            .andExpect(status().isOk());

        // Validate the AideSoignant in the database
        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeUpdate);
        AideSoignant testAideSoignant = aideSoignantList.get(aideSoignantList.size() - 1);
        assertThat(testAideSoignant.getIdSoignant()).isEqualTo(UPDATED_ID_SOIGNANT);
        assertThat(testAideSoignant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAideSoignant.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void putNonExistingAideSoignant() throws Exception {
        int databaseSizeBeforeUpdate = aideSoignantRepository.findAll().size();
        aideSoignant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAideSoignantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aideSoignant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aideSoignant))
            )
            .andExpect(status().isBadRequest());

        // Validate the AideSoignant in the database
        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAideSoignant() throws Exception {
        int databaseSizeBeforeUpdate = aideSoignantRepository.findAll().size();
        aideSoignant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAideSoignantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aideSoignant))
            )
            .andExpect(status().isBadRequest());

        // Validate the AideSoignant in the database
        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAideSoignant() throws Exception {
        int databaseSizeBeforeUpdate = aideSoignantRepository.findAll().size();
        aideSoignant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAideSoignantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aideSoignant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AideSoignant in the database
        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAideSoignantWithPatch() throws Exception {
        // Initialize the database
        aideSoignantRepository.saveAndFlush(aideSoignant);

        int databaseSizeBeforeUpdate = aideSoignantRepository.findAll().size();

        // Update the aideSoignant using partial update
        AideSoignant partialUpdatedAideSoignant = new AideSoignant();
        partialUpdatedAideSoignant.setId(aideSoignant.getId());

        partialUpdatedAideSoignant.idSoignant(UPDATED_ID_SOIGNANT).prenom(UPDATED_PRENOM).nom(UPDATED_NOM);

        restAideSoignantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAideSoignant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAideSoignant))
            )
            .andExpect(status().isOk());

        // Validate the AideSoignant in the database
        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeUpdate);
        AideSoignant testAideSoignant = aideSoignantList.get(aideSoignantList.size() - 1);
        assertThat(testAideSoignant.getIdSoignant()).isEqualTo(UPDATED_ID_SOIGNANT);
        assertThat(testAideSoignant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAideSoignant.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void fullUpdateAideSoignantWithPatch() throws Exception {
        // Initialize the database
        aideSoignantRepository.saveAndFlush(aideSoignant);

        int databaseSizeBeforeUpdate = aideSoignantRepository.findAll().size();

        // Update the aideSoignant using partial update
        AideSoignant partialUpdatedAideSoignant = new AideSoignant();
        partialUpdatedAideSoignant.setId(aideSoignant.getId());

        partialUpdatedAideSoignant.idSoignant(UPDATED_ID_SOIGNANT).prenom(UPDATED_PRENOM).nom(UPDATED_NOM);

        restAideSoignantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAideSoignant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAideSoignant))
            )
            .andExpect(status().isOk());

        // Validate the AideSoignant in the database
        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeUpdate);
        AideSoignant testAideSoignant = aideSoignantList.get(aideSoignantList.size() - 1);
        assertThat(testAideSoignant.getIdSoignant()).isEqualTo(UPDATED_ID_SOIGNANT);
        assertThat(testAideSoignant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAideSoignant.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void patchNonExistingAideSoignant() throws Exception {
        int databaseSizeBeforeUpdate = aideSoignantRepository.findAll().size();
        aideSoignant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAideSoignantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aideSoignant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aideSoignant))
            )
            .andExpect(status().isBadRequest());

        // Validate the AideSoignant in the database
        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAideSoignant() throws Exception {
        int databaseSizeBeforeUpdate = aideSoignantRepository.findAll().size();
        aideSoignant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAideSoignantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aideSoignant))
            )
            .andExpect(status().isBadRequest());

        // Validate the AideSoignant in the database
        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAideSoignant() throws Exception {
        int databaseSizeBeforeUpdate = aideSoignantRepository.findAll().size();
        aideSoignant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAideSoignantMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(aideSoignant))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AideSoignant in the database
        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAideSoignant() throws Exception {
        // Initialize the database
        aideSoignantRepository.saveAndFlush(aideSoignant);

        int databaseSizeBeforeDelete = aideSoignantRepository.findAll().size();

        // Delete the aideSoignant
        restAideSoignantMockMvc
            .perform(delete(ENTITY_API_URL_ID, aideSoignant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AideSoignant> aideSoignantList = aideSoignantRepository.findAll();
        assertThat(aideSoignantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
