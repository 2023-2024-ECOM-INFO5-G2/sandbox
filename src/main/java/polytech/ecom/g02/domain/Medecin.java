package polytech.ecom.g02.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Medecin.
 */
@Entity
@Table(name = "medecin")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Medecin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_medecin", nullable = false, unique = true)
    private Integer idMedecin;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idMedecin")
    @JsonIgnoreProperties(value = { "idMedecin", "idPatients", "idPatients", "idPatients" }, allowSetters = true)
    private Set<Patient> idMedecins = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Medecin id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdMedecin() {
        return this.idMedecin;
    }

    public Medecin idMedecin(Integer idMedecin) {
        this.setIdMedecin(idMedecin);
        return this;
    }

    public void setIdMedecin(Integer idMedecin) {
        this.idMedecin = idMedecin;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Medecin firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Medecin lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Patient> getIdMedecins() {
        return this.idMedecins;
    }

    public void setIdMedecins(Set<Patient> patients) {
        if (this.idMedecins != null) {
            this.idMedecins.forEach(i -> i.setIdMedecin(null));
        }
        if (patients != null) {
            patients.forEach(i -> i.setIdMedecin(this));
        }
        this.idMedecins = patients;
    }

    public Medecin idMedecins(Set<Patient> patients) {
        this.setIdMedecins(patients);
        return this;
    }

    public Medecin addIdMedecin(Patient patient) {
        this.idMedecins.add(patient);
        patient.setIdMedecin(this);
        return this;
    }

    public Medecin removeIdMedecin(Patient patient) {
        this.idMedecins.remove(patient);
        patient.setIdMedecin(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medecin)) {
            return false;
        }
        return getId() != null && getId().equals(((Medecin) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medecin{" +
            "id=" + getId() +
            ", idMedecin=" + getIdMedecin() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
