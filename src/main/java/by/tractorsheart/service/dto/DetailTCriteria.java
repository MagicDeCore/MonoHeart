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
 * Criteria class for the {@link by.tractorsheart.domain.DetailT} entity. This class is used
 * in {@link by.tractorsheart.web.rest.DetailTResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /detail-ts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DetailTCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter nodeTId;

    public DetailTCriteria(){
    }

    public DetailTCriteria(DetailTCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.nodeTId = other.nodeTId == null ? null : other.nodeTId.copy();
    }

    @Override
    public DetailTCriteria copy() {
        return new DetailTCriteria(this);
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

    public LongFilter getNodeTId() {
        return nodeTId;
    }

    public void setNodeTId(LongFilter nodeTId) {
        this.nodeTId = nodeTId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DetailTCriteria that = (DetailTCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(nodeTId, that.nodeTId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        nodeTId
        );
    }

    @Override
    public String toString() {
        return "DetailTCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (nodeTId != null ? "nodeTId=" + nodeTId + ", " : "") +
            "}";
    }

}
