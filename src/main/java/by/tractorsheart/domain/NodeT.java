package by.tractorsheart.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A NodeT.
 */
@Entity
@Table(name = "nodet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NodeT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "nodet_detailt",
               joinColumns = @JoinColumn(name = "nodet_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "detailt_id", referencedColumnName = "id"))
    private Set<DetailT> detailTS = new HashSet<>();

    @ManyToMany(mappedBy = "nodeTS")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<ModuleT> moduleTS = new HashSet<>();

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

    public NodeT name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DetailT> getDetailTS() {
        return detailTS;
    }

    public NodeT detailTS(Set<DetailT> detailTS) {
        this.detailTS = detailTS;
        return this;
    }

    public NodeT addDetailT(DetailT detailT) {
        this.detailTS.add(detailT);
        detailT.getNodeTS().add(this);
        return this;
    }

    public NodeT removeDetailT(DetailT detailT) {
        this.detailTS.remove(detailT);
        detailT.getNodeTS().remove(this);
        return this;
    }

    public void setDetailTS(Set<DetailT> detailTS) {
        this.detailTS = detailTS;
    }

    public Set<ModuleT> getModuleTS() {
        return moduleTS;
    }

    public NodeT moduleTS(Set<ModuleT> moduleTS) {
        this.moduleTS = moduleTS;
        return this;
    }

    public NodeT addModuleT(ModuleT moduleT) {
        this.moduleTS.add(moduleT);
        moduleT.getNodeTS().add(this);
        return this;
    }

    public NodeT removeModuleT(ModuleT moduleT) {
        this.moduleTS.remove(moduleT);
        moduleT.getNodeTS().remove(this);
        return this;
    }

    public void setModuleTS(Set<ModuleT> moduleTS) {
        this.moduleTS = moduleTS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NodeT)) {
            return false;
        }
        return id != null && id.equals(((NodeT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NodeT{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
