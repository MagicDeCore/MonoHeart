package by.tractorsheart.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DetailT.
 */
@Entity
@Table(name = "detailt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DetailT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "detailTS")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<NodeT> nodeTS = new HashSet<>();

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

    public DetailT name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<NodeT> getNodeTS() {
        return nodeTS;
    }

    public DetailT nodeTS(Set<NodeT> nodeTS) {
        this.nodeTS = nodeTS;
        return this;
    }

    public DetailT addNodeT(NodeT nodeT) {
        this.nodeTS.add(nodeT);
        nodeT.getDetailTS().add(this);
        return this;
    }

    public DetailT removeNodeT(NodeT nodeT) {
        this.nodeTS.remove(nodeT);
        nodeT.getDetailTS().remove(this);
        return this;
    }

    public void setNodeTS(Set<NodeT> nodeTS) {
        this.nodeTS = nodeTS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailT)) {
            return false;
        }
        return id != null && id.equals(((DetailT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DetailT{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
