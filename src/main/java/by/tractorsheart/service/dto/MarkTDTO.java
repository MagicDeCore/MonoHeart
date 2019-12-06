package by.tractorsheart.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link by.tractorsheart.domain.MarkT} entity.
 */
public class MarkTDTO implements Serializable {

    private Long id;

    private String name;


    private Set<TypeTDTO> typeTS = new HashSet<>();

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

    public Set<TypeTDTO> getTypeTS() {
        return typeTS;
    }

    public void setTypeTS(Set<TypeTDTO> typeTS) {
        this.typeTS = typeTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarkTDTO markTDTO = (MarkTDTO) o;
        if (markTDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), markTDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarkTDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
