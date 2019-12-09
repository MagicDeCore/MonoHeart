package by.tractorsheart.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link by.tractorsheart.domain.ModelT} entity.
 */
public class ModelTDTO implements Serializable {

    private Long id;

    private String name;

    private String wrong;


    private Set<PartTDTO> partTS = new HashSet<>();

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

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public Set<PartTDTO> getPartTS() {
        return partTS;
    }

    public void setPartTS(Set<PartTDTO> partTS) {
        this.partTS = partTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModelTDTO modelTDTO = (ModelTDTO) o;
        if (modelTDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modelTDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModelTDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", wrong='" + getWrong() + "'" +
            "}";
    }
}
