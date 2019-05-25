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
 * Criteria class for the {@link com.girlscancode.domain.Drzava} entity. This class is used
 * in {@link com.girlscancode.web.rest.DrzavaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /drzavas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DrzavaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter naziv;

    public DrzavaCriteria(){
    }

    public DrzavaCriteria(DrzavaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.naziv = other.naziv == null ? null : other.naziv.copy();
    }

    @Override
    public DrzavaCriteria copy() {
        return new DrzavaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNaziv() {
        return naziv;
    }

    public void setNaziv(StringFilter naziv) {
        this.naziv = naziv;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DrzavaCriteria that = (DrzavaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(naziv, that.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        naziv
        );
    }

    @Override
    public String toString() {
        return "DrzavaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (naziv != null ? "naziv=" + naziv + ", " : "") +
            "}";
    }

}
