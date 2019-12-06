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
 * Criteria class for the {@link by.tractorsheart.domain.TypeT} entity. This class is used
 * in {@link by.tractorsheart.web.rest.TypeTResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /type-ts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TypeTCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter modelTId;

    private LongFilter markTId;

    public TypeTCriteria(){
    }

    public TypeTCriteria(TypeTCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.modelTId = other.modelTId == null ? null : other.modelTId.copy();
        this.markTId = other.markTId == null ? null : other.markTId.copy();
    }

    @Override
    public TypeTCriteria copy() {
        return new TypeTCriteria(this);
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

    public LongFilter getModelTId() {
        return modelTId;
    }

    public void setModelTId(LongFilter modelTId) {
        this.modelTId = modelTId;
    }

    public LongFilter getMarkTId() {
        return markTId;
    }

    public void setMarkTId(LongFilter markTId) {
        this.markTId = markTId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TypeTCriteria that = (TypeTCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(modelTId, that.modelTId) &&
            Objects.equals(markTId, that.markTId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        modelTId,
        markTId
        );
    }

    @Override
    public String toString() {
        return "TypeTCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (modelTId != null ? "modelTId=" + modelTId + ", " : "") +
                (markTId != null ? "markTId=" + markTId + ", " : "") +
            "}";
    }

}
