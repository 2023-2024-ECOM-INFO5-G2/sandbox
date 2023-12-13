package polytech.ecom.g02.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import polytech.ecom.g02.domain.MesureAlbumine;
import polytech.ecom.g02.repository.MesureAlbumineRepository;
import polytech.ecom.g02.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link polytech.ecom.g02.domain.MesureAlbumine}.
 */
@RestController
@RequestMapping("/api/mesure-albumines")
@Transactional
public class MesureAlbumineResource {

    private final Logger log = LoggerFactory.getLogger(MesureAlbumineResource.class);

    private static final String ENTITY_NAME = "mesureAlbumine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MesureAlbumineRepository mesureAlbumineRepository;

    public MesureAlbumineResource(MesureAlbumineRepository mesureAlbumineRepository) {
        this.mesureAlbumineRepository = mesureAlbumineRepository;
    }

    /**
     * {@code POST  /mesure-albumines} : Create a new mesureAlbumine.
     *
     * @param mesureAlbumine the mesureAlbumine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mesureAlbumine, or with status {@code 400 (Bad Request)} if the mesureAlbumine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MesureAlbumine> createMesureAlbumine(@Valid @RequestBody MesureAlbumine mesureAlbumine)
        throws URISyntaxException {
        log.debug("REST request to save MesureAlbumine : {}", mesureAlbumine);
        if (mesureAlbumine.getId() != null) {
            throw new BadRequestAlertException("A new mesureAlbumine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MesureAlbumine result = mesureAlbumineRepository.save(mesureAlbumine);
        return ResponseEntity
            .created(new URI("/api/mesure-albumines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mesure-albumines/:id} : Updates an existing mesureAlbumine.
     *
     * @param id the id of the mesureAlbumine to save.
     * @param mesureAlbumine the mesureAlbumine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mesureAlbumine,
     * or with status {@code 400 (Bad Request)} if the mesureAlbumine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mesureAlbumine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MesureAlbumine> updateMesureAlbumine(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MesureAlbumine mesureAlbumine
    ) throws URISyntaxException {
        log.debug("REST request to update MesureAlbumine : {}, {}", id, mesureAlbumine);
        if (mesureAlbumine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mesureAlbumine.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mesureAlbumineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MesureAlbumine result = mesureAlbumineRepository.save(mesureAlbumine);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mesureAlbumine.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mesure-albumines/:id} : Partial updates given fields of an existing mesureAlbumine, field will ignore if it is null
     *
     * @param id the id of the mesureAlbumine to save.
     * @param mesureAlbumine the mesureAlbumine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mesureAlbumine,
     * or with status {@code 400 (Bad Request)} if the mesureAlbumine is not valid,
     * or with status {@code 404 (Not Found)} if the mesureAlbumine is not found,
     * or with status {@code 500 (Internal Server Error)} if the mesureAlbumine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MesureAlbumine> partialUpdateMesureAlbumine(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MesureAlbumine mesureAlbumine
    ) throws URISyntaxException {
        log.debug("REST request to partial update MesureAlbumine partially : {}, {}", id, mesureAlbumine);
        if (mesureAlbumine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mesureAlbumine.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mesureAlbumineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MesureAlbumine> result = mesureAlbumineRepository
            .findById(mesureAlbumine.getId())
            .map(existingMesureAlbumine -> {
                if (mesureAlbumine.getValeur() != null) {
                    existingMesureAlbumine.setValeur(mesureAlbumine.getValeur());
                }
                if (mesureAlbumine.getDate() != null) {
                    existingMesureAlbumine.setDate(mesureAlbumine.getDate());
                }

                return existingMesureAlbumine;
            })
            .map(mesureAlbumineRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mesureAlbumine.getId().toString())
        );
    }

    /**
     * {@code GET  /mesure-albumines} : get all the mesureAlbumines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mesureAlbumines in body.
     */
    @GetMapping("")
    public List<MesureAlbumine> getAllMesureAlbumines() {
        log.debug("REST request to get all MesureAlbumines");
        return mesureAlbumineRepository.findAll();
    }

    /**
     * {@code GET  /mesure-albumines/:id} : get the "id" mesureAlbumine.
     *
     * @param id the id of the mesureAlbumine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mesureAlbumine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MesureAlbumine> getMesureAlbumine(@PathVariable Long id) {
        log.debug("REST request to get MesureAlbumine : {}", id);
        Optional<MesureAlbumine> mesureAlbumine = mesureAlbumineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mesureAlbumine);
    }

    /**
     * {@code DELETE  /mesure-albumines/:id} : delete the "id" mesureAlbumine.
     *
     * @param id the id of the mesureAlbumine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMesureAlbumine(@PathVariable Long id) {
        log.debug("REST request to delete MesureAlbumine : {}", id);
        mesureAlbumineRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}