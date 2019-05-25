package com.girlscancode.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.girlscancode.domain.enumeration.TipPoena;

/**
 * A DTO for the {@link com.girlscancode.domain.Poen} entity.
 */
public class PoenDTO implements Serializable {

    private Long id;

    private TipPoena tip;


    private Long korisnikId;

    private Long drzavaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipPoena getTip() {
        return tip;
    }

    public void setTip(TipPoena tip) {
        this.tip = tip;
    }

    public Long getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Long userId) {
        this.korisnikId = userId;
    }

    public Long getDrzavaId() {
        return drzavaId;
    }

    public void setDrzavaId(Long drzavaId) {
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

        PoenDTO poenDTO = (PoenDTO) o;
        if (poenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PoenDTO{" +
            "id=" + getId() +
            ", tip='" + getTip() + "'" +
            ", korisnik=" + getKorisnikId() +
            ", drzava=" + getDrzavaId() +
            "}";
    }
}
