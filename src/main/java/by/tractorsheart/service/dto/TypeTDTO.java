package by.tractorsheart.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link by.tractorsheart.domain.TypeT} entity.
 */
public class TypeTDTO implements Serializable {

    private Long id;

    private String name;


    private Set<ModelTDTO> modelTS = new HashSet<>();

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

    public Set<ModelTDTO> getModelTS() {
        return modelTS;
    }

    public void setModelTS(Set<ModelTDTO> modelTS) {
        this.modelTS = modelTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeTDTO typeTDTO = (TypeTDTO) o;
        if (typeTDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeTDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeTDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
