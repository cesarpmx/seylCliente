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
@Table(name = "LIUSUARIOPUESTO")
public class Liusuariopuesto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "UPIDPUESTO")
    private Integer upidpuesto;
    @Column(name = "UPNOMBREPUESTO")
    private String upnombrepuesto;
    @Column(name = "UPHABILITADO")
    private Integer uphabilitado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuidpuesto", fetch = FetchType.LAZY)
    private Collection<Liusuarios> liusuariosCollection;
    @JoinColumn(name = "UPIDORIGEN", referencedColumnName = "OIDORIGEN")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Lictlorigen upidorigen;

    public Liusuariopuesto() {
    }

    public Liusuariopuesto(Integer upidpuesto) {
        this.upidpuesto = upidpuesto;
    }

    public Integer getUpidpuesto() {
        return upidpuesto;
    }

    public void setUpidpuesto(Integer upidpuesto) {
        this.upidpuesto = upidpuesto;
    }

    public String getUpnombrepuesto() {
        return upnombrepuesto;
    }

    public void setUpnombrepuesto(String upnombrepuesto) {
        this.upnombrepuesto = upnombrepuesto;
    }

    public Integer getUphabilitado() {
        return uphabilitado;
    }

    public void setUphabilitado(Integer uphabilitado) {
        this.uphabilitado = uphabilitado;
    }

    public Collection<Liusuarios> getLiusuariosCollection() {
        return liusuariosCollection;
    }

    public void setLiusuariosCollection(Collection<Liusuarios> liusuariosCollection) {
        this.liusuariosCollection = liusuariosCollection;
    }

    public Lictlorigen getUpidorigen() {
        return upidorigen;
    }

    public void setUpidorigen(Lictlorigen upidorigen) {
        this.upidorigen = upidorigen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (upidpuesto != null ? upidpuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liusuariopuesto)) {
            return false;
        }
        Liusuariopuesto other = (Liusuariopuesto) object;
        if ((this.upidpuesto == null && other.upidpuesto != null) || (this.upidpuesto != null && !this.upidpuesto.equals(other.upidpuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Liusuariopuesto[upidpuesto=" + upidpuesto + "]";
    }

}
