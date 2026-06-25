/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.entity;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author marco
 */
@Entity
@Table(name = "LIDEPARTAMENTOS")

public class Lidepartamentos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DEPCLAVE")
    private String depclave;
    @Basic(optional = false)
    @Column(name = "DEPDESCRIPCION")
    private String depdescripcion;
    @OneToMany(mappedBy = "usudepartamento", fetch = FetchType.LAZY)
    private Collection<Liusuarios> liusuariosCollection;

    public Lidepartamentos() {
    }

    public Lidepartamentos(String depclave) {
        this.depclave = depclave;
    }

    public Lidepartamentos(String depclave, String depdescripcion) {
        this.depclave = depclave;
        this.depdescripcion = depdescripcion;
    }

    public String getDepclave() {
        return depclave;
    }

    public void setDepclave(String depclave) {
        this.depclave = depclave;
    }

    public String getDepdescripcion() {
        return depdescripcion;
    }

    public void setDepdescripcion(String depdescripcion) {
        this.depdescripcion = depdescripcion;
    }

    public Collection<Liusuarios> getLiusuariosCollection() {
        return liusuariosCollection;
    }

    public void setLiusuariosCollection(Collection<Liusuarios> liusuariosCollection) {
        this.liusuariosCollection = liusuariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depclave != null ? depclave.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lidepartamentos)) {
            return false;
        }
        Lidepartamentos other = (Lidepartamentos) object;
        if ((this.depclave == null && other.depclave != null) || (this.depclave != null && !this.depclave.equals(other.depclave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Lidepartamentos[depclave=" + depclave + "]";
    }

}
