package polytech.ecom.g02.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Infirmiere.
 */
@Entity
@Table(name = "infirmiere")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Infirmiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_infirmiere", nullable = false, unique = true)
    private Integer idInfirmiere;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_infirmiere__id_infirmiere",
        joinColumns = @JoinColumn(name = "infirmiere_id"),
        inverseJoinColumns = @JoinColumn(name = "id_infirmiere_id")
    )
    @JsonIgnoreProperties(value = { "idMedecin", "idPatients", "idPatients", "idPatients" }, allowSetters = true)
    private Set<Patient> idInfirmieres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Infirmiere id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdInfirmiere() {
        return this.idInfirmiere;
    }

    public Infirmiere idInfirmiere(Integer idInfirmiere) {
        this.setIdInfirmiere(idInfirmiere);
        return this;
    }

    public void setIdInfirmiere(Integer idInfirmiere) {
        this.idInfirmiere = idInfirmiere;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Infirmiere prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public Infirmiere nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Patient> getIdInfirmieres() {
        return this.idInfirmieres;
    }

    public void setIdInfirmieres(Set<Patient> patients) {
        this.idInfirmieres = patients;
    }

    public Infirmiere idInfirmieres(Set<Patient> patients) {
        this.setIdInfirmieres(patients);
        return this;
    }

    public Infirmiere addIdInfirmiere(Patient patient) {
        this.idInfirmieres.add(patient);
        patient.getIdPatients().add(this);
        return this;
    }

    public Infirmiere removeIdInfirmiere(Patient patient) {
        this.idInfirmieres.remove(patient);
        patient.getIdPatients().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Infirmiere)) {
            return false;
        }
        return getId() != null && getId().equals(((Infirmiere) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Infirmiere{" +
            "id=" + getId() +
            ", idInfirmiere=" + getIdInfirmiere() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
