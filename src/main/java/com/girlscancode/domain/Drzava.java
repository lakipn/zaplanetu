package com.girlscancode.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Drzava.
 */
@Entity
@Table(name = "drzava")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Drzava implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 64)
    @Column(name = "naziv", length = 64)
    private String naziv;

    @Lob
    @Column(name = "slika")
    private byte[] slika;

    @Column(name = "slika_content_type")
    private String slikaContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public Drzava naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public byte[] getSlika() {
        return slika;
    }

    public Drzava slika(byte[] slika) {
        this.slika = slika;
        return this;
    }

    public void setSlika(byte[] slika) {
        this.slika = slika;
    }

    public String getSlikaContentType() {
        return slikaContentType;
    }

    public Drzava slikaContentType(String slikaContentType) {
        this.slikaContentType = slikaContentType;
        return this;
    }

    public void setSlikaContentType(String slikaContentType) {
        this.slikaContentType = slikaContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Drzava)) {
            return false;
        }
        return id != null && id.equals(((Drzava) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Drzava{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            ", slika='" + getSlika() + "'" +
            ", slikaContentType='" + getSlikaContentType() + "'" +
            "}";
    }
}
