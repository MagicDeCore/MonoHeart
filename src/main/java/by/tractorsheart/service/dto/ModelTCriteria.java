package by.tractorsheart.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link by.tractorsheart.domain.ModelT} entity. This class is used
 * in {@link by.tractorsheart.web.rest.ModelTResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /model-ts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ModelTCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter wrong;

    private LongFilter partTId;

    private LongFilter typeTId;

    public ModelTCriteria(){
    }

    public ModelTCriteria(ModelTCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.wrong = other.wrong == null ? null : other.wrong.copy();
        this.partTId = other.partTId == null ? null : other.partTId.copy();
        this.typeTId = other.typeTId == null ? null : other.typeTId.copy();
    }

    @Override
    public ModelTCriteria copy() {
        return new ModelTCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getWrong() {
        return wrong;
    }

    public void setWrong(StringFilter wrong) {
        this.wrong = wrong;
    }

    public LongFilter getPartTId() {
        return partTId;
    }

    public void setPartTId(LongFilter partTId) {
        this.partTId = partTId;
    }

    public LongFilter getTypeTId() {
        return typeTId;
    }

    public void setTypeTId(LongFilter typeTId) {
        this.typeTId = typeTId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ModelTCriteria that = (ModelTCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(wrong, that.wrong) &&
            Objects.equals(partTId, that.partTId) &&
            Objects.equals(typeTId, that.typeTId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        wrong,
        partTId,
        typeTId
        );
    }

    @Override
    public String toString() {
        return "ModelTCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (wrong != null ? "wrong=" + wrong + ", " : "") +
                (partTId != null ? "partTId=" + partTId + ", " : "") +
                (typeTId != null ? "typeTId=" + typeTId + ", " : "") +
            "}";
    }

}
