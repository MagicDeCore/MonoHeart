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
 * Criteria class for the {@link by.tractorsheart.domain.NodeT} entity. This class is used
 * in {@link by.tractorsheart.web.rest.NodeTResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /node-ts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NodeTCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter detailTId;

    private LongFilter moduleTId;

    public NodeTCriteria(){
    }

    public NodeTCriteria(NodeTCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.detailTId = other.detailTId == null ? null : other.detailTId.copy();
        this.moduleTId = other.moduleTId == null ? null : other.moduleTId.copy();
    }

    @Override
    public NodeTCriteria copy() {
        return new NodeTCriteria(this);
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

    public LongFilter getDetailTId() {
        return detailTId;
    }

    public void setDetailTId(LongFilter detailTId) {
        this.detailTId = detailTId;
    }

    public LongFilter getModuleTId() {
        return moduleTId;
    }

    public void setModuleTId(LongFilter moduleTId) {
        this.moduleTId = moduleTId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NodeTCriteria that = (NodeTCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(detailTId, that.detailTId) &&
            Objects.equals(moduleTId, that.moduleTId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        detailTId,
        moduleTId
        );
    }

    @Override
    public String toString() {
        return "NodeTCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (detailTId != null ? "detailTId=" + detailTId + ", " : "") +
                (moduleTId != null ? "moduleTId=" + moduleTId + ", " : "") +
            "}";
    }

}
