package com.girlscancode.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.girlscancode.domain.Pitanje} entity.
 */
public class PitanjeDTO implements Serializable {

    private Long id;

    @Size(max = 256)
    private String tekst;

    private Integer redniBroj;


    private Long sekcijaId;

    private String sekcijaNaziv;

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

    public Integer getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(Integer redniBroj) {
        this.redniBroj = redniBroj;
    }

    public Long getSekcijaId() {
        return sekcijaId;
    }

    public void setSekcijaId(Long sekcijaId) {
        this.sekcijaId = sekcijaId;
    }

    public String getSekcijaNaziv() {
        return sekcijaNaziv;
    }

    public void setSekcijaNaziv(String sekcijaNaziv) {
        this.sekcijaNaziv = sekcijaNaziv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PitanjeDTO pitanjeDTO = (PitanjeDTO) o;
        if (pitanjeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pitanjeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PitanjeDTO{" +
            "id=" + getId() +
            ", tekst='" + getTekst() + "'" +
            ", redniBroj=" + getRedniBroj() +
            ", sekcija=" + getSekcijaId() +
            ", sekcija='" + getSekcijaNaziv() + "'" +
            "}";
    }
}
