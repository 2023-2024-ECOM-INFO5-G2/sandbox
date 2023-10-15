package polytech.ecom.g02.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_patient", nullable = false, unique = true)
    private Integer idPatient;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "sexe", nullable = false)
    private String sexe;

    @NotNull
    @Column(name = "date_de_naissance", nullable = false)
    private LocalDate dateDeNaissance;

    @NotNull
    @Column(name = "chambre", nullable = false)
    private Integer chambre;

    @Column(name = "poids")
    private Float poids;

    @NotNull
    @Column(name = "date_arrivee", nullable = false)
    private LocalDate dateArrivee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "idMedecins" }, allowSetters = true)
    private Medecin idMedecin;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "idSoignants")
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "idInfirmieres")
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "idRepas")
    @JsonIgnoreProperties(value = { "idSoignants" }, allowSetters = true)
    private Set<AideSoignant> idPatients = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "idSoignants")
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "idInfirmieres")
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "idRepas")
    @JsonIgnoreProperties(value = { "idInfirmieres" }, allowSetters = true)
    private Set<Infirmiere> idPatients = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "idSoignants")
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "idInfirmieres")
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "idRepas")
    @JsonIgnoreProperties(value = { "idRepas" }, allowSetters = true)
    private Set<Repas> idPatients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Patient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPatient() {
        return this.idPatient;
    }

    public Patient idPatient(Integer idPatient) {
        this.setIdPatient(idPatient);
        return this;
    }

    public void setIdPatient(Integer idPatient) {
        this.idPatient = idPatient;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Patient prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public Patient nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSexe() {
        return this.sexe;
    }

    public Patient sexe(String sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDateDeNaissance() {
        return this.dateDeNaissance;
    }

    public Patient dateDeNaissance(LocalDate dateDeNaissance) {
        this.setDateDeNaissance(dateDeNaissance);
        return this;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public Integer getChambre() {
        return this.chambre;
    }

    public Patient chambre(Integer chambre) {
        this.setChambre(chambre);
        return this;
    }

    public void setChambre(Integer chambre) {
        this.chambre = chambre;
    }

    public Float getPoids() {
        return this.poids;
    }

    public Patient poids(Float poids) {
        this.setPoids(poids);
        return this;
    }

    public void setPoids(Float poids) {
        this.poids = poids;
    }

    public LocalDate getDateArrivee() {
        return this.dateArrivee;
    }

    public Patient dateArrivee(LocalDate dateArrivee) {
        this.setDateArrivee(dateArrivee);
        return this;
    }

    public void setDateArrivee(LocalDate dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public Medecin getIdMedecin() {
        return this.idMedecin;
    }

    public void setIdMedecin(Medecin medecin) {
        this.idMedecin = medecin;
    }

    public Patient idMedecin(Medecin medecin) {
        this.setIdMedecin(medecin);
        return this;
    }

    public Set<AideSoignant> getIdPatients() {
        return this.idPatients;
    }

    public void setIdPatients(Set<AideSoignant> aideSoignants) {
        if (this.idPatients != null) {
            this.idPatients.forEach(i -> i.removeIdSoignant(this));
        }
        if (aideSoignants != null) {
            aideSoignants.forEach(i -> i.addIdSoignant(this));
        }
        this.idPatients = aideSoignants;
    }

    public Patient idPatients(Set<AideSoignant> aideSoignants) {
        this.setIdPatients(aideSoignants);
        return this;
    }

    public Patient addIdPatient(AideSoignant aideSoignant) {
        this.idPatients.add(aideSoignant);
        aideSoignant.getIdSoignants().add(this);
        return this;
    }

    public Patient removeIdPatient(AideSoignant aideSoignant) {
        this.idPatients.remove(aideSoignant);
        aideSoignant.getIdSoignants().remove(this);
        return this;
    }

    public Set<Infirmiere> getIdPatients() {
        return this.idPatients;
    }

    public void setIdPatients(Set<Infirmiere> infirmieres) {
        if (this.idPatients != null) {
            this.idPatients.forEach(i -> i.removeIdInfirmiere(this));
        }
        if (infirmieres != null) {
            infirmieres.forEach(i -> i.addIdInfirmiere(this));
        }
        this.idPatients = infirmieres;
    }

    public Patient idPatients(Set<Infirmiere> infirmieres) {
        this.setIdPatients(infirmieres);
        return this;
    }

    public Patient addIdPatient(Infirmiere infirmiere) {
        this.idPatients.add(infirmiere);
        infirmiere.getIdInfirmieres().add(this);
        return this;
    }

    public Patient removeIdPatient(Infirmiere infirmiere) {
        this.idPatients.remove(infirmiere);
        infirmiere.getIdInfirmieres().remove(this);
        return this;
    }

    public Set<Repas> getIdPatients() {
        return this.idPatients;
    }

    public void setIdPatients(Set<Repas> repas) {
        if (this.idPatients != null) {
            this.idPatients.forEach(i -> i.removeIdRepas(this));
        }
        if (repas != null) {
            repas.forEach(i -> i.addIdRepas(this));
        }
        this.idPatients = repas;
    }

    public Patient idPatients(Set<Repas> repas) {
        this.setIdPatients(repas);
        return this;
    }

    public Patient addIdPatient(Repas repas) {
        this.idPatients.add(repas);
        repas.getIdRepas().add(this);
        return this;
    }

    public Patient removeIdPatient(Repas repas) {
        this.idPatients.remove(repas);
        repas.getIdRepas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return getId() != null && getId().equals(((Patient) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", idPatient=" + getIdPatient() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", chambre=" + getChambre() +
            ", poids=" + getPoids() +
            ", dateArrivee='" + getDateArrivee() + "'" +
            "}";
    }
}
