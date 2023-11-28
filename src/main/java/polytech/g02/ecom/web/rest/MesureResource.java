package polytech.g02.ecom.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import polytech.g02.ecom.domain.Alerte;
import polytech.g02.ecom.domain.Mesure;
import polytech.g02.ecom.domain.Patient;
import polytech.g02.ecom.repository.AlerteRepository;
import polytech.g02.ecom.repository.MesureRepository;
import polytech.g02.ecom.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link polytech.g02.ecom.domain.Mesure}.
 */
@RestController
@RequestMapping("/api/mesures")
@Transactional
public class MesureResource {

    private final Logger log = LoggerFactory.getLogger(MesureResource.class);

    private static final String ENTITY_NAME = "mesure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MesureRepository mesureRepository;

    public MesureResource(MesureRepository mesureRepository) {
        this.mesureRepository = mesureRepository;
    }

    /**
     * {@code POST  /mesures} : Create a new mesure.
     *
     * @param mesure the mesure to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mesure, or with status {@code 400 (Bad Request)} if the mesure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Mesure> createMesure(@Valid @RequestBody Mesure mesure) throws URISyntaxException {
        log.debug("REST request to save Mesure : {}", mesure);
        if (mesure.getId() != null) {
            throw new BadRequestAlertException("A new mesure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mesure result = mesureRepository.save(mesure);
        return ResponseEntity
            .created(new URI("/api/mesures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mesures/:id} : Updates an existing mesure.
     *
     * @param id the id of the mesure to save.
     * @param mesure the mesure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mesure,
     * or with status {@code 400 (Bad Request)} if the mesure is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mesure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Mesure> updateMesure(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Mesure mesure
    ) throws URISyntaxException {
        log.debug("REST request to update Mesure : {}, {}", id, mesure);
        if (mesure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mesure.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mesureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mesure result = mesureRepository.save(mesure);

        String nameValue = mesure.getNomValeur();
        Float value = mesure.getValeur();
        Patient patient = mesure.getPatient();
        Float taille = patient.getTaille();
        LocalDate date = mesure.getDate();
        detectionAlgo(nameValue, value, patient, taille, date);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mesure.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mesures/:id} : Partial updates given fields of an existing mesure, field will ignore if it is null
     *
     * @param id the id of the mesure to save.
     * @param mesure the mesure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mesure,
     * or with status {@code 400 (Bad Request)} if the mesure is not valid,
     * or with status {@code 404 (Not Found)} if the mesure is not found,
     * or with status {@code 500 (Internal Server Error)} if the mesure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Mesure> partialUpdateMesure(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Mesure mesure
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mesure partially : {}, {}", id, mesure);
        if (mesure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mesure.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mesureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mesure> result = mesureRepository
            .findById(mesure.getId())
            .map(existingMesure -> {
                if (mesure.getDate() != null) {
                    existingMesure.setDate(mesure.getDate());
                }
                if (mesure.getNomValeur() != null) {
                    existingMesure.setNomValeur(mesure.getNomValeur());
                }
                if (mesure.getValeur() != null) {
                    existingMesure.setValeur(mesure.getValeur());
                }

                return existingMesure;
            })
            .map(mesureRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mesure.getId().toString())
        );
    }

    /**
     * {@code GET  /mesures} : get all the mesures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mesures in body.
     */
    @GetMapping("")
    public List<Mesure> getAllMesures() {
        log.debug("REST request to get all Mesures");
        return mesureRepository.findAll();
    }

    /**
     * {@code GET  /mesures/:id} : get the "id" mesure.
     *
     * @param id the id of the mesure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mesure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mesure> getMesure(@PathVariable Long id) {
        log.debug("REST request to get Mesure : {}", id);
        Optional<Mesure> mesure = mesureRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mesure);
    }

    /**
     * {@code DELETE  /mesures/:id} : delete the "id" mesure.
     *
     * @param id the id of the mesure to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMesure(@PathVariable Long id) {
        log.debug("REST request to delete Mesure : {}", id);
        mesureRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    public class DateMesureComparator implements Comparator<Mesure> {

        @Override
        public int compare(Mesure m1, Mesure m2) {
            return m1.getDate().compareTo(m2.getDate());
        }
    }

    private final DateMesureComparator DateMesureComparator = new DateMesureComparator();

    /**
     *
     * @param nameValue : Nom de la valeur de la mesure
     * @param value : Valeur de la mesure
     * @param patient : Patient concerné par la mesure
     * @param taille : Taille du patient (pour le calcul de l'IMC
     *
     * Créer une alerte si la mesure comporte une valeur anormale ou inquiétante en suivant
     * les règles suivantes :
     *
     * Variation de poids :
     *               * Severe :
     *                  * >=10% en 1 mois
     *                  * >=15% en 6 mois ou >=15% depuis début hospitalisation
     *                  *
     *               * Moderate :
     *                  * >=5% en 1 mois
     *                  * >=10% en 6 mois ou >=10% depuis début hospitalisation
     * IMC :
     *               * Severe :
     *                  * <=17
     *               * Moderate :
     *                  * >17 et <=18.5
     * Albumine :
     *               * Severe :
     *                  * <=30g/L
     *               * Moderate :
     *                  * >30g/L et <=35g/L
     * EPA :
     *               * Severe : <7
     *
     * Dans le cas ou une alerte est identifié on ajoute au message d'alerte la valeur qui l'as déclenché
     * précédé de la mention "ALERTE" ou "ATTENTION" selon la gravité de l'alerte.
     * Couvre uniquement les cas phénotypiques.
     * */
    private void detectionAlgo(String nameValue, float value, Patient patient, float taille, LocalDate date) {
        String alerteMessage = "";

        switch (nameValue) {
            case "Poids":
                //IMC
                float IMC = IMC(taille, value);
                if (IMC <= 17) {
                    alerteMessage += "ALERTE : IMC très faible : " + IMC + "\n";
                } else if (IMC > 17 && IMC <= 18.5) {
                    alerteMessage += "ATTENTION : IMC faible : " + IMC + "\n";
                }

                //PERTE DE POIDS
                /*
                 * Version Beta :
                 * dateMinusSix = date - 6 mois
                 * dateMinusOne = date - 1 mois
                 *
                 * sixMonthsMesures = Toutes les mesures de poids du patient des 6 derniers mois
                 * oneMonthMesures = Toutes les mesures de poids du patient du dernier mois
                 *
                 * sixMonthsMesure = Mesure de poids la plus ancienne des 6 derniers mois
                 * oneMonthMesure = Mesure de poids la plus ancienne du dernier mois
                 * oldestMesure = Mesure de poids la plus ancienne du patient
                 * */
                LocalDate dateMinusSix = date.plusMonths(-6);
                LocalDate dateMinusOne = date.plusMonths(-1);

                Set<Mesure> sixMonthsMesures = patient
                    .getMesures()
                    .stream()
                    .filter(element -> element.getDate().compareTo(dateMinusSix) > 0 && element.getNomValeur() == "Poids")
                    .collect(Collectors.toSet());
                Set<Mesure> oneMonthsMesures = sixMonthsMesures
                    .stream()
                    .filter(element -> element.getDate().compareTo(dateMinusOne) > 0)
                    .collect(Collectors.toSet());

                //<condition> ? <expression_if_true> : <expression_if_false>;
                Mesure sixMonthsMesure = sixMonthsMesures.stream().min(DateMesureComparator).get();
                Mesure oneMonthMesure = oneMonthsMesures.stream().min(DateMesureComparator).get();
                Mesure oldestMesure = patient.getMesures().stream().min(DateMesureComparator).get();

                float percentageOneMonth;
                if (oneMonthMesure != null) percentageOneMonth = (oneMonthMesure.getValeur() - value) / value; else percentageOneMonth = 1;

                float percentageSixMonths;
                if (sixMonthsMesure != null) percentageSixMonths = (sixMonthsMesure.getValeur() - value) / value; else percentageSixMonths =
                    1;
                float percentageOldest;
                if (oldestMesure != null) percentageOldest = (oldestMesure.getValeur() - value) / value; else percentageOldest = 1;

                if (percentageOneMonth <= -0.1 || percentageSixMonths <= -0.15 || percentageOldest <= -0.15) {
                    alerteMessage +=
                    "ALERTE : Perte de poids sévère : " +
                    percentageOneMonth +
                    "% en 1 mois, " +
                    percentageSixMonths +
                    "% en 6 mois, " +
                    percentageOldest +
                    "% depuis le début de l'hospitalisation\n";
                } else if (percentageOneMonth <= -0.05 || percentageSixMonths <= -0.1 || percentageOldest <= -0.1) {
                    alerteMessage +=
                    "ATTENTION : Perte de poids modérée : " +
                    percentageOneMonth +
                    "% en 1 mois, " +
                    percentageSixMonths +
                    "% en 6 mois, " +
                    percentageOldest +
                    "% depuis le début de l'hospitalisation\n";
                }
                break;
            case "Albumine":
                if (value <= 30) {
                    alerteMessage += "ALERTE : Albumine très faible : " + value + "g/L\n";
                } else if (value > 35 && value <= 35) {
                    alerteMessage += "ATTENTION : Albumine faible : " + value + "g/L\n";
                }
                break;
            case "EPA":
                if (value <= 7) {
                    alerteMessage += "ALERTE : EPA très faible : " + value + "\n";
                }
                break;
            default:
                break;
        }
        if (alerteMessage != "") {
            Alerte alerte = new Alerte();
            Set<Patient> patients = new Set<Patient>();
            patients.add(patient);
            alerte.setPatients(patients);
            alerte.setMessage(alerteMessage);
            alerteRepository.save(alerte);
        }
    }

    private float IMC(float taille, float poids) {
        return poids / (taille * taille);
    }
}
