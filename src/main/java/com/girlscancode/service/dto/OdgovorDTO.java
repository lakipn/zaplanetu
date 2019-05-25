package com.girlscancode.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.girlscancode.domain.Odgovor} entity.
 */
public class OdgovorDTO implements Serializable {

    private Long id;

    @Size(max = 64)
    private String tekst;

    private Boolean tacan;


    private Long pojasnjenjeId;

    private Long pitanjeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Boolean isTacan() {
        return tacan;
    }

    public void setTacan(Boolean tacan) {
        this.tacan = tacan;
    }

    public Long getPojasnjenjeId() {
        return pojasnjenjeId;
    }

    public void setPojasnjenjeId(Long pojasnjenjeId) {
        this.pojasnjenjeId = pojasnjenjeId;
    }

    public Long getPitanjeId() {
        return pitanjeId;
    }

    public void setPitanjeId(Long pitanjeId) {
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

        OdgovorDTO odgovorDTO = (OdgovorDTO) o;
        if (odgovorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), odgovorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OdgovorDTO{" +
            "id=" + getId() +
            ", tekst='" + getTekst() + "'" +
            ", tacan='" + isTacan() + "'" +
            ", pojasnjenje=" + getPojasnjenjeId() +
            ", pitanje=" + getPitanjeId() +
            "}";
    }
}
