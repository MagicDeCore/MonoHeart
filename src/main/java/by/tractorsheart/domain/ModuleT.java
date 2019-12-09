package by.tractorsheart.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ModuleT.
 */
@Entity
@Table(name = "modulet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ModuleT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "modulet_nodet",
               joinColumns = @JoinColumn(name = "modulet_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "nodet_id", referencedColumnName = "id"))
    private Set<NodeT> nodeTS = new HashSet<>();

    @ManyToMany(mappedBy = "moduleTS")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<PartT> partTS = new HashSet<>();

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

    public ModuleT name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<NodeT> getNodeTS() {
        return nodeTS;
    }

    public ModuleT nodeTS(Set<NodeT> nodeTS) {
        this.nodeTS = nodeTS;
        return this;
    }

    public ModuleT addNodeT(NodeT nodeT) {
        this.nodeTS.add(nodeT);
        nodeT.getModuleTS().add(this);
        return this;
    }

    public ModuleT removeNodeT(NodeT nodeT) {
        this.nodeTS.remove(nodeT);
        nodeT.getModuleTS().remove(this);
        return this;
    }

    public void setNodeTS(Set<NodeT> nodeTS) {
        this.nodeTS = nodeTS;
    }

    public Set<PartT> getPartTS() {
        return partTS;
    }

    public ModuleT partTS(Set<PartT> partTS) {
        this.partTS = partTS;
        return this;
    }

    public ModuleT addPartT(PartT partT) {
        this.partTS.add(partT);
        partT.getModuleTS().add(this);
        return this;
    }

    public ModuleT removePartT(PartT partT) {
        this.partTS.remove(partT);
        partT.getModuleTS().remove(this);
        return this;
    }

    public void setPartTS(Set<PartT> partTS) {
        this.partTS = partTS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModuleT)) {
            return false;
        }
        return id != null && id.equals(((ModuleT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ModuleT{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
