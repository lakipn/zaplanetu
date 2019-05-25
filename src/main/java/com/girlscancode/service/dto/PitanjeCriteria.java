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
 * Criteria class for the {@link com.girlscancode.domain.Pitanje} entity. This class is used
 * in {@link com.girlscancode.web.rest.PitanjeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pitanjes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PitanjeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tekst;

    private IntegerFilter redniBroj;

    private LongFilter sekcijaId;

    private LongFilter odgovoriId;

    public PitanjeCriteria(){
    }

    public PitanjeCriteria(PitanjeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tekst = other.tekst == null ? null : other.tekst.copy();
        this.redniBroj = other.redniBroj == null ? null : other.redniBroj.copy();
        this.sekcijaId = other.sekcijaId == null ? null : other.sekcijaId.copy();
        this.odgovoriId = other.odgovoriId == null ? null : other.odgovoriId.copy();
    }

    @Override
    public PitanjeCriteria copy() {
        return new PitanjeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTekst() {
        return tekst;
    }

    public void setTekst(StringFilter tekst) {
        this.tekst = tekst;
    }

    public IntegerFilter getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(IntegerFilter redniBroj) {
        this.redniBroj = redniBroj;
    }

    public LongFilter getSekcijaId() {
        return sekcijaId;
    }

    public void setSekcijaId(LongFilter sekcijaId) {
        this.sekcijaId = sekcijaId;
    }

    public LongFilter getOdgovoriId() {
        return odgovoriId;
    }

    public void setOdgovoriId(LongFilter odgovoriId) {
        this.odgovoriId = odgovoriId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PitanjeCriteria that = (PitanjeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tekst, that.tekst) &&
            Objects.equals(redniBroj, that.redniBroj) &&
            Objects.equals(sekcijaId, that.sekcijaId) &&
            Objects.equals(odgovoriId, that.odgovoriId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tekst,
        redniBroj,
        sekcijaId,
        odgovoriId
        );
    }

    @Override
    public String toString() {
        return "PitanjeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tekst != null ? "tekst=" + tekst + ", " : "") +
                (redniBroj != null ? "redniBroj=" + redniBroj + ", " : "") +
                (sekcijaId != null ? "sekcijaId=" + sekcijaId + ", " : "") +
                (odgovoriId != null ? "odgovoriId=" + odgovoriId + ", " : "") +
            "}";
    }

}
