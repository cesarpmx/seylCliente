/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.entity;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author marco
 */
@Entity
@Table(name = "LIADMINMODULOACCIONES")

public class Liadminmoduloacciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "AMAIDMODULOACCIONES")
    private Integer amaidmoduloacciones;
    @JoinColumn(name = "AMAIDACCIONES", referencedColumnName = "AAIDACCIONES")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Liadminacciones amaidacciones;
    @JoinColumn(name = "AMAIDMODULO", referencedColumnName = "AMIDMODULO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Liadminmodulo amaidmodulo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "armidmoduloacciones", fetch = FetchType.LAZY)
    private Collection<Liadminrolmoduloacciones> liadminrolmoduloaccionesCollection;

    public Liadminmoduloacciones() {
    }

    public Liadminmoduloacciones(Integer amaidmoduloacciones) {
        this.amaidmoduloacciones = amaidmoduloacciones;
    }

    public Integer getAmaidmoduloacciones() {
        return amaidmoduloacciones;
    }

    public void setAmaidmoduloacciones(Integer amaidmoduloacciones) {
        this.amaidmoduloacciones = amaidmoduloacciones;
    }

    public Liadminacciones getAmaidacciones() {
        return amaidacciones;
    }

    public void setAmaidacciones(Liadminacciones amaidacciones) {
        this.amaidacciones = amaidacciones;
    }

    public Liadminmodulo getAmaidmodulo() {
        return amaidmodulo;
    }

    public void setAmaidmodulo(Liadminmodulo amaidmodulo) {
        this.amaidmodulo = amaidmodulo;
    }

    public Collection<Liadminrolmoduloacciones> getLiadminrolmoduloaccionesCollection() {
        return liadminrolmoduloaccionesCollection;
    }

    public void setLiadminrolmoduloaccionesCollection(Collection<Liadminrolmoduloacciones> liadminrolmoduloaccionesCollection) {
        this.liadminrolmoduloaccionesCollection = liadminrolmoduloaccionesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (amaidmoduloacciones != null ? amaidmoduloacciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liadminmoduloacciones)) {
            return false;
        }
        Liadminmoduloacciones other = (Liadminmoduloacciones) object;
        if ((this.amaidmoduloacciones == null && other.amaidmoduloacciones != null) || (this.amaidmoduloacciones != null && !this.amaidmoduloacciones.equals(other.amaidmoduloacciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Liadminmoduloacciones[amaidmoduloacciones=" + amaidmoduloacciones + "]";
    }

}
