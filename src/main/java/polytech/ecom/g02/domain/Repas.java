package polytech.ecom.g02.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Repas.
 */
@Entity
@Table(name = "repas")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Repas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_repas", nullable = false, unique = true)
    private Integer idRepas;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "apport_calorique", nullable = false)
    private Integer apportCalorique;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_repas__id_repas",
        joinColumns = @JoinColumn(name = "repas_id"),
        inverseJoinColumns = @JoinColumn(name = "id_repas_id")
    )
    @JsonIgnoreProperties(value = { "idMedecin", "idPatients", "idPatients", "idPatients" }, allowSetters = true)
    private Set<Patient> idRepas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Repas id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdRepas() {
        return this.idRepas;
    }

    public Repas idRepas(Integer idRepas) {
        this.setIdRepas(idRepas);
        return this;
    }

    public void setIdRepas(Integer idRepas) {
        this.idRepas = idRepas;
    }

    public String getNom() {
        return this.nom;
    }

    public Repas nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Repas description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getApportCalorique() {
        return this.apportCalorique;
    }

    public Repas apportCalorique(Integer apportCalorique) {
        this.setApportCalorique(apportCalorique);
        return this;
    }

    public void setApportCalorique(Integer apportCalorique) {
        this.apportCalorique = apportCalorique;
    }

    public Set<Patient> getIdRepas() {
        return this.idRepas;
    }

    public void setIdRepas(Set<Patient> patients) {
        this.idRepas = patients;
    }

    public Repas idRepas(Set<Patient> patients) {
        this.setIdRepas(patients);
        return this;
    }

    public Repas addIdRepas(Patient patient) {
        this.idRepas.add(patient);
        patient.getIdPatients().add(this);
        return this;
    }

    public Repas removeIdRepas(Patient patient) {
        this.idRepas.remove(patient);
        patient.getIdPatients().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Repas)) {
            return false;
        }
        return getId() != null && getId().equals(((Repas) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Repas{" +
            "id=" + getId() +
            ", idRepas=" + getIdRepas() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", apportCalorique=" + getApportCalorique() +
            "}";
    }
}
