package by.tractorsheart.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link by.tractorsheart.domain.NodeT} entity.
 */
public class NodeTDTO implements Serializable {

    private Long id;

    private String name;


    private Set<DetailTDTO> detailTS = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DetailTDTO> getDetailTS() {
        return detailTS;
    }

    public void setDetailTS(Set<DetailTDTO> detailTS) {
        this.detailTS = detailTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NodeTDTO nodeTDTO = (NodeTDTO) o;
        if (nodeTDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nodeTDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NodeTDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
