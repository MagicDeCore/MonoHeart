package by.tractorsheart.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeT.
 */
@Entity
@Table(name = "typet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "typet_modelt",
               joinColumns = @JoinColumn(name = "typet_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "modelt_id", referencedColumnName = "id"))
    private Set<ModelT> modelTS = new HashSet<>();

    @ManyToMany(mappedBy = "typeTS")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<MarkT> markTS = new HashSet<>();

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

    public TypeT name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ModelT> getModelTS() {
        return modelTS;
    }

    public TypeT modelTS(Set<ModelT> modelTS) {
        this.modelTS = modelTS;
        return this;
    }

    public TypeT addModelT(ModelT modelT) {
        this.modelTS.add(modelT);
        modelT.getTypeTS().add(this);
        return this;
    }

    public TypeT removeModelT(ModelT modelT) {
        this.modelTS.remove(modelT);
        modelT.getTypeTS().remove(this);
        return this;
    }

    public void setModelTS(Set<ModelT> modelTS) {
        this.modelTS = modelTS;
    }

    public Set<MarkT> getMarkTS() {
        return markTS;
    }

    public TypeT markTS(Set<MarkT> markTS) {
        this.markTS = markTS;
        return this;
    }

    public TypeT addMarkT(MarkT markT) {
        this.markTS.add(markT);
        markT.getTypeTS().add(this);
        return this;
    }

    public TypeT removeMarkT(MarkT markT) {
        this.markTS.remove(markT);
        markT.getTypeTS().remove(this);
        return this;
    }

    public void setMarkTS(Set<MarkT> markTS) {
        this.markTS = markTS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeT)) {
            return false;
        }
        return id != null && id.equals(((TypeT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeT{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
