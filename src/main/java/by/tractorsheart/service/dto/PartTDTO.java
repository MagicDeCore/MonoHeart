package by.tractorsheart.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link by.tractorsheart.domain.PartT} entity.
 */
public class PartTDTO implements Serializable {

    private Long id;

    private String name;


    private Set<ModuleTDTO> moduleTS = new HashSet<>();

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

    public Set<ModuleTDTO> getModuleTS() {
        return moduleTS;
    }

    public void setModuleTS(Set<ModuleTDTO> moduleTS) {
        this.moduleTS = moduleTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartTDTO partTDTO = (PartTDTO) o;
        if (partTDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partTDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartTDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
