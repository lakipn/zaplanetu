package com.girlscancode.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Odgovor.
 */
@Entity
@Table(name = "odgovor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Odgovor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 64)
    @Column(name = "tekst", length = 64)
    private String tekst;

    @Column(name = "tacan")
    private Boolean tacan;

    @OneToOne
    @JoinColumn(unique = true)
    private Pojasnjenje pojasnjenje;

    @ManyToOne
    @JsonIgnoreProperties("odgovoris")
    private Pitanje pitanje;

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

    public Odgovor tekst(String tekst) {
        this.tekst = tekst;
        return this;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Boolean isTacan() {
        return tacan;
    }

    public Odgovor tacan(Boolean tacan) {
        this.tacan = tacan;
        return this;
    }

    public void setTacan(Boolean tacan) {
        this.tacan = tacan;
    }

    public Pojasnjenje getPojasnjenje() {
        return pojasnjenje;
    }

    public Odgovor pojasnjenje(Pojasnjenje pojasnjenje) {
        this.pojasnjenje = pojasnjenje;
        return this;
    }

    public void setPojasnjenje(Pojasnjenje pojasnjenje) {
        this.pojasnjenje = pojasnjenje;
    }

    public Pitanje getPitanje() {
        return pitanje;
    }

    public Odgovor pitanje(Pitanje pitanje) {
        this.pitanje = pitanje;
        return this;
    }

    public void setPitanje(Pitanje pitanje) {
        this.pitanje = pitanje;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Odgovor)) {
            return false;
        }
        return id != null && id.equals(((Odgovor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Odgovor{" +
            "id=" + getId() +
            ", tekst='" + getTekst() + "'" +
            ", tacan='" + isTacan() + "'" +
            "}";
    }
}
