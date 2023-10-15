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
import polytech.ecom.g02.domain.Infirmiere;
import polytech.ecom.g02.repository.InfirmiereRepository;

/**
 * Integration tests for the {@link InfirmiereResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InfirmiereResourceIT {

    private static final Integer DEFAULT_ID_INFIRMIERE = 1;
    private static final Integer UPDATED_ID_INFIRMIERE = 2;

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/infirmieres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InfirmiereRepository infirmiereRepository;

    @Mock
    private InfirmiereRepository infirmiereRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInfirmiereMockMvc;

    private Infirmiere infirmiere;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Infirmiere createEntity(EntityManager em) {
        Infirmiere infirmiere = new Infirmiere().idInfirmiere(DEFAULT_ID_INFIRMIERE).prenom(DEFAULT_PRENOM).nom(DEFAULT_NOM);
        return infirmiere;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Infirmiere createUpdatedEntity(EntityManager em) {
        Infirmiere infirmiere = new Infirmiere().idInfirmiere(UPDATED_ID_INFIRMIERE).prenom(UPDATED_PRENOM).nom(UPDATED_NOM);
        return infirmiere;
    }

    @BeforeEach
    public void initTest() {
        infirmiere = createEntity(em);
    }

    @Test
    @Transactional
    void createInfirmiere() throws Exception {
        int databaseSizeBeforeCreate = infirmiereRepository.findAll().size();
        // Create the Infirmiere
        restInfirmiereMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infirmiere)))
            .andExpect(status().isCreated());

        // Validate the Infirmiere in the database
        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeCreate + 1);
        Infirmiere testInfirmiere = infirmiereList.get(infirmiereList.size() - 1);
        assertThat(testInfirmiere.getIdInfirmiere()).isEqualTo(DEFAULT_ID_INFIRMIERE);
        assertThat(testInfirmiere.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testInfirmiere.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    void createInfirmiereWithExistingId() throws Exception {
        // Create the Infirmiere with an existing ID
        infirmiere.setId(1L);

        int databaseSizeBeforeCreate = infirmiereRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfirmiereMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infirmiere)))
            .andExpect(status().isBadRequest());

        // Validate the Infirmiere in the database
        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdInfirmiereIsRequired() throws Exception {
        int databaseSizeBeforeTest = infirmiereRepository.findAll().size();
        // set the field null
        infirmiere.setIdInfirmiere(null);

        // Create the Infirmiere, which fails.

        restInfirmiereMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infirmiere)))
            .andExpect(status().isBadRequest());

        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = infirmiereRepository.findAll().size();
        // set the field null
        infirmiere.setPrenom(null);

        // Create the Infirmiere, which fails.

        restInfirmiereMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infirmiere)))
            .andExpect(status().isBadRequest());

        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = infirmiereRepository.findAll().size();
        // set the field null
        infirmiere.setNom(null);

        // Create the Infirmiere, which fails.

        restInfirmiereMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infirmiere)))
            .andExpect(status().isBadRequest());

        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInfirmieres() throws Exception {
        // Initialize the database
        infirmiereRepository.saveAndFlush(infirmiere);

        // Get all the infirmiereList
        restInfirmiereMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infirmiere.getId().intValue())))
            .andExpect(jsonPath("$.[*].idInfirmiere").value(hasItem(DEFAULT_ID_INFIRMIERE)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInfirmieresWithEagerRelationshipsIsEnabled() throws Exception {
        when(infirmiereRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInfirmiereMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(infirmiereRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInfirmieresWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(infirmiereRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInfirmiereMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(infirmiereRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInfirmiere() throws Exception {
        // Initialize the database
        infirmiereRepository.saveAndFlush(infirmiere);

        // Get the infirmiere
        restInfirmiereMockMvc
            .perform(get(ENTITY_API_URL_ID, infirmiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(infirmiere.getId().intValue()))
            .andExpect(jsonPath("$.idInfirmiere").value(DEFAULT_ID_INFIRMIERE))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    void getNonExistingInfirmiere() throws Exception {
        // Get the infirmiere
        restInfirmiereMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInfirmiere() throws Exception {
        // Initialize the database
        infirmiereRepository.saveAndFlush(infirmiere);

        int databaseSizeBeforeUpdate = infirmiereRepository.findAll().size();

        // Update the infirmiere
        Infirmiere updatedInfirmiere = infirmiereRepository.findById(infirmiere.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInfirmiere are not directly saved in db
        em.detach(updatedInfirmiere);
        updatedInfirmiere.idInfirmiere(UPDATED_ID_INFIRMIERE).prenom(UPDATED_PRENOM).nom(UPDATED_NOM);

        restInfirmiereMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInfirmiere.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInfirmiere))
            )
            .andExpect(status().isOk());

        // Validate the Infirmiere in the database
        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeUpdate);
        Infirmiere testInfirmiere = infirmiereList.get(infirmiereList.size() - 1);
        assertThat(testInfirmiere.getIdInfirmiere()).isEqualTo(UPDATED_ID_INFIRMIERE);
        assertThat(testInfirmiere.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testInfirmiere.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void putNonExistingInfirmiere() throws Exception {
        int databaseSizeBeforeUpdate = infirmiereRepository.findAll().size();
        infirmiere.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfirmiereMockMvc
            .perform(
                put(ENTITY_API_URL_ID, infirmiere.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infirmiere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infirmiere in the database
        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInfirmiere() throws Exception {
        int databaseSizeBeforeUpdate = infirmiereRepository.findAll().size();
        infirmiere.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfirmiereMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infirmiere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infirmiere in the database
        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInfirmiere() throws Exception {
        int databaseSizeBeforeUpdate = infirmiereRepository.findAll().size();
        infirmiere.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfirmiereMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infirmiere)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Infirmiere in the database
        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInfirmiereWithPatch() throws Exception {
        // Initialize the database
        infirmiereRepository.saveAndFlush(infirmiere);

        int databaseSizeBeforeUpdate = infirmiereRepository.findAll().size();

        // Update the infirmiere using partial update
        Infirmiere partialUpdatedInfirmiere = new Infirmiere();
        partialUpdatedInfirmiere.setId(infirmiere.getId());

        partialUpdatedInfirmiere.prenom(UPDATED_PRENOM).nom(UPDATED_NOM);

        restInfirmiereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfirmiere.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInfirmiere))
            )
            .andExpect(status().isOk());

        // Validate the Infirmiere in the database
        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeUpdate);
        Infirmiere testInfirmiere = infirmiereList.get(infirmiereList.size() - 1);
        assertThat(testInfirmiere.getIdInfirmiere()).isEqualTo(DEFAULT_ID_INFIRMIERE);
        assertThat(testInfirmiere.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testInfirmiere.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void fullUpdateInfirmiereWithPatch() throws Exception {
        // Initialize the database
        infirmiereRepository.saveAndFlush(infirmiere);

        int databaseSizeBeforeUpdate = infirmiereRepository.findAll().size();

        // Update the infirmiere using partial update
        Infirmiere partialUpdatedInfirmiere = new Infirmiere();
        partialUpdatedInfirmiere.setId(infirmiere.getId());

        partialUpdatedInfirmiere.idInfirmiere(UPDATED_ID_INFIRMIERE).prenom(UPDATED_PRENOM).nom(UPDATED_NOM);

        restInfirmiereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfirmiere.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInfirmiere))
            )
            .andExpect(status().isOk());

        // Validate the Infirmiere in the database
        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeUpdate);
        Infirmiere testInfirmiere = infirmiereList.get(infirmiereList.size() - 1);
        assertThat(testInfirmiere.getIdInfirmiere()).isEqualTo(UPDATED_ID_INFIRMIERE);
        assertThat(testInfirmiere.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testInfirmiere.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void patchNonExistingInfirmiere() throws Exception {
        int databaseSizeBeforeUpdate = infirmiereRepository.findAll().size();
        infirmiere.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfirmiereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, infirmiere.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infirmiere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infirmiere in the database
        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInfirmiere() throws Exception {
        int databaseSizeBeforeUpdate = infirmiereRepository.findAll().size();
        infirmiere.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfirmiereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infirmiere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infirmiere in the database
        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInfirmiere() throws Exception {
        int databaseSizeBeforeUpdate = infirmiereRepository.findAll().size();
        infirmiere.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfirmiereMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(infirmiere))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Infirmiere in the database
        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInfirmiere() throws Exception {
        // Initialize the database
        infirmiereRepository.saveAndFlush(infirmiere);

        int databaseSizeBeforeDelete = infirmiereRepository.findAll().size();

        // Delete the infirmiere
        restInfirmiereMockMvc
            .perform(delete(ENTITY_API_URL_ID, infirmiere.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Infirmiere> infirmiereList = infirmiereRepository.findAll();
        assertThat(infirmiereList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
