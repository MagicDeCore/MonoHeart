package by.tractorsheart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MarkT.
 */
@Entity
@Table(name = "markt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MarkT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "markt_typet",
               joinColumns = @JoinColumn(name = "markt_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "typet_id", referencedColumnName = "id"))
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

    public MarkT name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TypeT> getTypeTS() {
        return typeTS;
    }

    public MarkT typeTS(Set<TypeT> typeTS) {
        this.typeTS = typeTS;
        return this;
    }

    public MarkT addTypeT(TypeT typeT) {
        this.typeTS.add(typeT);
        typeT.getMarkTS().add(this);
        return this;
    }

    public MarkT removeTypeT(TypeT typeT) {
        this.typeTS.remove(typeT);
        typeT.getMarkTS().remove(this);
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
        if (!(o instanceof MarkT)) {
            return false;
        }
        return id != null && id.equals(((MarkT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MarkT{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
