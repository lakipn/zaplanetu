package com.girlscancode.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.girlscancode.domain.enumeration.TipPoena;

/**
 * A Poen.
 */
@Entity
@Table(name = "poen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Poen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip")
    private TipPoena tip;

    @ManyToOne
    @JsonIgnoreProperties("poens")
    private User korisnik;

    @ManyToOne
    @JsonIgnoreProperties("poens")
    private Drzava drzava;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipPoena getTip() {
        return tip;
    }

    public Poen tip(TipPoena tip) {
        this.tip = tip;
        return this;
    }

    public void setTip(TipPoena tip) {
        this.tip = tip;
    }

    public User getKorisnik() {
        return korisnik;
    }

    public Poen korisnik(User user) {
        this.korisnik = user;
        return this;
    }

    public void setKorisnik(User user) {
        this.korisnik = user;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public Poen drzava(Drzava drzava) {
        this.drzava = drzava;
        return this;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Poen)) {
            return false;
        }
        return id != null && id.equals(((Poen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Poen{" +
            "id=" + getId() +
            ", tip='" + getTip() + "'" +
            "}";
    }
}
