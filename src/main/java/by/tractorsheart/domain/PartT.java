package by.tractorsheart.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PartT.
 */
@Entity
@Table(name = "partt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PartT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "partt_modulet",
               joinColumns = @JoinColumn(name = "partt_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "modulet_id", referencedColumnName = "id"))
    private Set<ModuleT> moduleTS = new HashSet<>();

    @ManyToMany(mappedBy = "partTS")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<ModelT> modelTS = new HashSet<>();

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

    public PartT name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ModuleT> getModuleTS() {
        return moduleTS;
    }

    public PartT moduleTS(Set<ModuleT> moduleTS) {
        this.moduleTS = moduleTS;
        return this;
    }

    public PartT addModuleT(ModuleT moduleT) {
        this.moduleTS.add(moduleT);
        moduleT.getPartTS().add(this);
        return this;
    }

    public PartT removeModuleT(ModuleT moduleT) {
        this.moduleTS.remove(moduleT);
        moduleT.getPartTS().remove(this);
        return this;
    }

    public void setModuleTS(Set<ModuleT> moduleTS) {
        this.moduleTS = moduleTS;
    }

    public Set<ModelT> getModelTS() {
        return modelTS;
    }

    public PartT modelTS(Set<ModelT> modelTS) {
        this.modelTS = modelTS;
        return this;
    }

    public PartT addModelT(ModelT modelT) {
        this.modelTS.add(modelT);
        modelT.getPartTS().add(this);
        return this;
    }

    public PartT removeModelT(ModelT modelT) {
        this.modelTS.remove(modelT);
        modelT.getPartTS().remove(this);
        return this;
    }

    public void setModelTS(Set<ModelT> modelTS) {
        this.modelTS = modelTS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartT)) {
            return false;
        }
        return id != null && id.equals(((PartT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PartT{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
