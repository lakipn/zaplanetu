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
 * Criteria class for the {@link com.girlscancode.domain.Odgovor} entity. This class is used
 * in {@link com.girlscancode.web.rest.OdgovorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /odgovors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OdgovorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tekst;

    private BooleanFilter tacan;

    private LongFilter pojasnjenjeId;

    private LongFilter pitanjeId;

    public OdgovorCriteria(){
    }

    public OdgovorCriteria(OdgovorCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tekst = other.tekst == null ? null : other.tekst.copy();
        this.tacan = other.tacan == null ? null : other.tacan.copy();
        this.pojasnjenjeId = other.pojasnjenjeId == null ? null : other.pojasnjenjeId.copy();
        this.pitanjeId = other.pitanjeId == null ? null : other.pitanjeId.copy();
    }

    @Override
    public OdgovorCriteria copy() {
        return new OdgovorCriteria(this);
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

    public BooleanFilter getTacan() {
        return tacan;
    }

    public void setTacan(BooleanFilter tacan) {
        this.tacan = tacan;
    }

    public LongFilter getPojasnjenjeId() {
        return pojasnjenjeId;
    }

    public void setPojasnjenjeId(LongFilter pojasnjenjeId) {
        this.pojasnjenjeId = pojasnjenjeId;
    }

    public LongFilter getPitanjeId() {
        return pitanjeId;
    }

    public void setPitanjeId(LongFilter pitanjeId) {
        this.pitanjeId = pitanjeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OdgovorCriteria that = (OdgovorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tekst, that.tekst) &&
            Objects.equals(tacan, that.tacan) &&
            Objects.equals(pojasnjenjeId, that.pojasnjenjeId) &&
            Objects.equals(pitanjeId, that.pitanjeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tekst,
        tacan,
        pojasnjenjeId,
        pitanjeId
        );
    }

    @Override
    public String toString() {
        return "OdgovorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tekst != null ? "tekst=" + tekst + ", " : "") +
                (tacan != null ? "tacan=" + tacan + ", " : "") +
                (pojasnjenjeId != null ? "pojasnjenjeId=" + pojasnjenjeId + ", " : "") +
                (pitanjeId != null ? "pitanjeId=" + pitanjeId + ", " : "") +
            "}";
    }

}
