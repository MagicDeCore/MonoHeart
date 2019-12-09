package by.tractorsheart.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link by.tractorsheart.domain.ModuleT} entity.
 */
public class ModuleTDTO implements Serializable {

    private Long id;

    private String name;


    private Set<NodeTDTO> nodeTS = new HashSet<>();

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

    public Set<NodeTDTO> getNodeTS() {
        return nodeTS;
    }

    public void setNodeTS(Set<NodeTDTO> nodeTS) {
        this.nodeTS = nodeTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModuleTDTO moduleTDTO = (ModuleTDTO) o;
        if (moduleTDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moduleTDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModuleTDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
