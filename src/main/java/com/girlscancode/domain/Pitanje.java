package com.girlscancode.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pitanje.
 */
@Entity
@Table(name = "pitanje")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pitanje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 256)
    @Column(name = "tekst", length = 256)
    private String tekst;

    @Column(name = "redni_broj")
    private Integer redniBroj;

    @ManyToOne
    @JsonIgnoreProperties("pitanjas")
    private Sekcija sekcija;

    @OneToMany(mappedBy = "pitanje")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Odgovor> odgovoris = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public Pitanje tekst(String tekst) {
        this.tekst = tekst;
        return this;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Integer getRedniBroj() {
        return redniBroj;
    }

    public Pitanje redniBroj(Integer redniBroj) {
        this.redniBroj = redniBroj;
        return this;
    }

    public void setRedniBroj(Integer redniBroj) {
        this.redniBroj = redniBroj;
    }

    public Sekcija getSekcija() {
        return sekcija;
    }

    public Pitanje sekcija(Sekcija sekcija) {
        this.sekcija = sekcija;
        return this;
    }

    public void setSekcija(Sekcija sekcija) {
        this.sekcija = sekcija;
    }

    public Set<Odgovor> getOdgovoris() {
        return odgovoris;
    }

    public Pitanje odgovoris(Set<Odgovor> odgovors) {
        this.odgovoris = odgovors;
        return this;
    }

    public Pitanje addOdgovori(Odgovor odgovor) {
        this.odgovoris.add(odgovor);
        odgovor.setPitanje(this);
        return this;
    }

    public Pitanje removeOdgovori(Odgovor odgovor) {
        this.odgovoris.remove(odgovor);
        odgovor.setPitanje(null);
        return this;
    }

    public void setOdgovoris(Set<Odgovor> odgovors) {
        this.odgovoris = odgovors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pitanje)) {
            return false;
        }
        return id != null && id.equals(((Pitanje) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pitanje{" +
            "id=" + getId() +
            ", tekst='" + getTekst() + "'" +
            ", redniBroj=" + getRedniBroj() +
            "}";
    }
}
