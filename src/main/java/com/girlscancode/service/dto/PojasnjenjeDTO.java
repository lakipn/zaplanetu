package com.girlscancode.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.girlscancode.domain.Pojasnjenje} entity.
 */
public class PojasnjenjeDTO implements Serializable {

    private Long id;

    @Lob
    private String tekst;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PojasnjenjeDTO pojasnjenjeDTO = (PojasnjenjeDTO) o;
        if (pojasnjenjeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pojasnjenjeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PojasnjenjeDTO{" +
            "id=" + getId() +
            ", tekst='" + getTekst() + "'" +
            "}";
    }
}
