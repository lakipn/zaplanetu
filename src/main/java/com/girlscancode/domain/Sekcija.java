package com.girlscancode.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Sekcija.
 */
@Entity
@Table(name = "sekcija")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sekcija implements Serializable {

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

    @Lob
    @Column(name = "pojasnjenje")
    private String pojasnjenje;

    @OneToMany(mappedBy = "sekcija")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pitanje> pitanjas = new HashSet<>();

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

    public Sekcija naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public byte[] getSlika() {
        return slika;
    }

    public Sekcija slika(byte[] slika) {
        this.slika = slika;
        return this;
    }

    public void setSlika(byte[] slika) {
        this.slika = slika;
    }

    public String getSlikaContentType() {
        return slikaContentType;
    }

    public Sekcija slikaContentType(String slikaContentType) {
        this.slikaContentType = slikaContentType;
        return this;
    }

    public void setSlikaContentType(String slikaContentType) {
        this.slikaContentType = slikaContentType;
    }

    public String getPojasnjenje() {
        return pojasnjenje;
    }

    public Sekcija pojasnjenje(String pojasnjenje) {
        this.pojasnjenje = pojasnjenje;
        return this;
    }

    public void setPojasnjenje(String pojasnjenje) {
        this.pojasnjenje = pojasnjenje;
    }

    public Set<Pitanje> getPitanjas() {
        return pitanjas;
    }

    public Sekcija pitanjas(Set<Pitanje> pitanjes) {
        this.pitanjas = pitanjes;
        return this;
    }

    public Sekcija addPitanja(Pitanje pitanje) {
        this.pitanjas.add(pitanje);
        pitanje.setSekcija(this);
        return this;
    }

    public Sekcija removePitanja(Pitanje pitanje) {
        this.pitanjas.remove(pitanje);
        pitanje.setSekcija(null);
        return this;
    }

    public void setPitanjas(Set<Pitanje> pitanjes) {
        this.pitanjas = pitanjes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sekcija)) {
            return false;
        }
        return id != null && id.equals(((Sekcija) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sekcija{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            ", slika='" + getSlika() + "'" +
            ", slikaContentType='" + getSlikaContentType() + "'" +
            ", pojasnjenje='" + getPojasnjenje() + "'" +
            "}";
    }
}
