package com.girlscancode.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.girlscancode.domain.enumeration.TipPoena;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.girlscancode.domain.Poen} entity. This class is used
 * in {@link com.girlscancode.web.rest.PoenResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /poens?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PoenCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipPoena
     */
    public static class TipPoenaFilter extends Filter<TipPoena> {

        public TipPoenaFilter() {
        }

        public TipPoenaFilter(TipPoenaFilter filter) {
            super(filter);
        }

        @Override
        public TipPoenaFilter copy() {
            return new TipPoenaFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TipPoenaFilter tip;

    private LongFilter korisnikId;

    private LongFilter drzavaId;

    public PoenCriteria(){
    }

    public PoenCriteria(PoenCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tip = other.tip == null ? null : other.tip.copy();
        this.korisnikId = other.korisnikId == null ? null : other.korisnikId.copy();
        this.drzavaId = other.drzavaId == null ? null : other.drzavaId.copy();
    }

    @Override
    public PoenCriteria copy() {
        return new PoenCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TipPoenaFilter getTip() {
        return tip;
    }

    public void setTip(TipPoenaFilter tip) {
        this.tip = tip;
    }

    public LongFilter getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(LongFilter korisnikId) {
        this.korisnikId = korisnikId;
    }

    public LongFilter getDrzavaId() {
        return drzavaId;
    }

    public void setDrzavaId(LongFilter drzavaId) {
        this.drzavaId = drzavaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PoenCriteria that = (PoenCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tip, that.tip) &&
            Objects.equals(korisnikId, that.korisnikId) &&
            Objects.equals(drzavaId, that.drzavaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tip,
        korisnikId,
        drzavaId
        );
    }

    @Override
    public String toString() {
        return "PoenCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tip != null ? "tip=" + tip + ", " : "") +
                (korisnikId != null ? "korisnikId=" + korisnikId + ", " : "") +
                (drzavaId != null ? "drzavaId=" + drzavaId + ", " : "") +
            "}";
    }

}
