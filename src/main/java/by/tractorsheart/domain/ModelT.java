package by.tractorsheart.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ModelT.
 */
@Entity
@Table(name = "modelt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ModelT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "wrong")
    private String wrong;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "modelt_partt",
               joinColumns = @JoinColumn(name = "modelt_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "partt_id", referencedColumnName = "id"))
    private Set<PartT> partTS = new HashSet<>();

    @ManyToMany(mappedBy = "modelTS")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<TypeT> typeTS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ModelT name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWrong() {
        return wrong;
    }

    public ModelT wrong(String wrong) {
        this.wrong = wrong;
        return this;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public Set<PartT> getPartTS() {
        return partTS;
    }

    public ModelT partTS(Set<PartT> partTS) {
        this.partTS = partTS;
        return this;
    }

    public ModelT addPartT(PartT partT) {
        this.partTS.add(partT);
        partT.getModelTS().add(this);
        return this;
    }

    public ModelT removePartT(PartT partT) {
        this.partTS.remove(partT);
        partT.getModelTS().remove(this);
        return this;
    }

    public void setPartTS(Set<PartT> partTS) {
        this.partTS = partTS;
    }

    public Set<TypeT> getTypeTS() {
        return typeTS;
    }

    public ModelT typeTS(Set<TypeT> typeTS) {
        this.typeTS = typeTS;
        return this;
    }

    public ModelT addTypeT(TypeT typeT) {
        this.typeTS.add(typeT);
        typeT.getModelTS().add(this);
        return this;
    }

    public ModelT removeTypeT(TypeT typeT) {
        this.typeTS.remove(typeT);
        typeT.getModelTS().remove(this);
        return this;
    }

    public void setTypeTS(Set<TypeT> typeTS) {
        this.typeTS = typeTS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModelT)) {
            return false;
        }
        return id != null && id.equals(((ModelT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ModelT{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", wrong='" + getWrong() + "'" +
            "}";
    }
}
