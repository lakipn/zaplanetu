package com.girlscancode.service.dto;

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
 * Criteria class for the {@link com.girlscancode.domain.Pojasnjenje} entity. This class is used
 * in {@link com.girlscancode.web.rest.PojasnjenjeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pojasnjenjes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PojasnjenjeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter odgovorId;

    public PojasnjenjeCriteria(){
    }

    public PojasnjenjeCriteria(PojasnjenjeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.odgovorId = other.odgovorId == null ? null : other.odgovorId.copy();
    }

    @Override
    public PojasnjenjeCriteria copy() {
        return new PojasnjenjeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getOdgovorId() {
        return odgovorId;
    }

    public void setOdgovorId(LongFilter odgovorId) {
        this.odgovorId = odgovorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PojasnjenjeCriteria that = (PojasnjenjeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(odgovorId, that.odgovorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        odgovorId
        );
    }

    @Override
    public String toString() {
        return "PojasnjenjeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (odgovorId != null ? "odgovorId=" + odgovorId + ", " : "") +
            "}";
    }

}
