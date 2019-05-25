package com.girlscancode.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.girlscancode.domain.Drzava} entity.
 */
public class DrzavaDTO implements Serializable {

    private Long id;

    @Size(max = 64)
    private String naziv;

    @Lob
    private byte[] slika;

    private String slikaContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public byte[] getSlika() {
        return slika;
    }

    public void setSlika(byte[] slika) {
        this.slika = slika;
    }

    public String getSlikaContentType() {
        return slikaContentType;
    }

    public void setSlikaContentType(String slikaContentType) {
        this.slikaContentType = slikaContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DrzavaDTO drzavaDTO = (DrzavaDTO) o;
        if (drzavaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drzavaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DrzavaDTO{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            ", slika='" + getSlika() + "'" +
            "}";
    }
}
