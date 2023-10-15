package polytech.ecom.g02.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AideSoignant.
 */
@Entity
@Table(name = "aide_soignant")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AideSoignant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_soignant", nullable = false, unique = true)
    private Integer idSoignant;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_aide_soignant__id_soignant",
        joinColumns = @JoinColumn(name = "aide_soignant_id"),
        inverseJoinColumns = @JoinColumn(name = "id_soignant_id")
    )
    @JsonIgnoreProperties(value = { "idMedecin", "idPatients", "idPatients", "idPatients" }, allowSetters = true)
    private Set<Patient> idSoignants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AideSoignant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdSoignant() {
        return this.idSoignant;
    }

    public AideSoignant idSoignant(Integer idSoignant) {
        this.setIdSoignant(idSoignant);
        return this;
    }

    public void setIdSoignant(Integer idSoignant) {
        this.idSoignant = idSoignant;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public AideSoignant prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public AideSoignant nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Patient> getIdSoignants() {
        return this.idSoignants;
    }

    public void setIdSoignants(Set<Patient> patients) {
        this.idSoignants = patients;
    }

    public AideSoignant idSoignants(Set<Patient> patients) {
        this.setIdSoignants(patients);
        return this;
    }

    public AideSoignant addIdSoignant(Patient patient) {
        this.idSoignants.add(patient);
        patient.getIdPatients().add(this);
        return this;
    }

    public AideSoignant removeIdSoignant(Patient patient) {
        this.idSoignants.remove(patient);
        patient.getIdPatients().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AideSoignant)) {
            return false;
        }
        return getId() != null && getId().equals(((AideSoignant) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AideSoignant{" +
            "id=" + getId() +
            ", idSoignant=" + getIdSoignant() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
