package com.girlscancode.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Pojasnjenje.
 */
@Entity
@Table(name = "pojasnjenje")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pojasnjenje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "tekst")
    private String tekst;

    @OneToOne(mappedBy = "pojasnjenje")
    @JsonIgnore
    private Odgovor odgovor;

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

    public Pojasnjenje tekst(String tekst) {
        this.tekst = tekst;
        return this;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Odgovor getOdgovor() {
        return odgovor;
    }

    public Pojasnjenje odgovor(Odgovor odgovor) {
        this.odgovor = odgovor;
        return this;
    }

    public void setOdgovor(Odgovor odgovor) {
        this.odgovor = odgovor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pojasnjenje)) {
            return false;
        }
        return id != null && id.equals(((Pojasnjenje) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pojasnjenje{" +
            "id=" + getId() +
            ", tekst='" + getTekst() + "'" +
            "}";
    }
}
