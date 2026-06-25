/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author marco
 */
@Entity
@Table(name = "LIADMINROLMODULOACCIONES")

public class Liadminrolmoduloacciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ARMIDROLMODULOACC")
    private Integer armidrolmoduloacc;
    @JoinColumn(name = "ARMIDMODULOACCIONES", referencedColumnName = "AMAIDMODULOACCIONES")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Liadminmoduloacciones armidmoduloacciones;
    @JoinColumn(name = "ARMIDPUESTO", referencedColumnName = "UPIDPUESTO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Liusuariopuesto armidpuesto;

    public Liadminrolmoduloacciones() {
    }

    public Liadminrolmoduloacciones(Integer armidrolmoduloacc) {
        this.armidrolmoduloacc = armidrolmoduloacc;
    }

    public Integer getArmidrolmoduloacc() {
        return armidrolmoduloacc;
    }

    public void setArmidrolmoduloacc(Integer armidrolmoduloacc) {
        this.armidrolmoduloacc = armidrolmoduloacc;
    }

    public Liadminmoduloacciones getArmidmoduloacciones() {
        return armidmoduloacciones;
    }

    public void setArmidmoduloacciones(Liadminmoduloacciones armidmoduloacciones) {
        this.armidmoduloacciones = armidmoduloacciones;
    }

    public Liusuariopuesto getArmidpuesto() {
        return armidpuesto;
    }

    public void setArmidpuesto(Liusuariopuesto armidpuesto) {
        this.armidpuesto = armidpuesto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (armidrolmoduloacc != null ? armidrolmoduloacc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liadminrolmoduloacciones)) {
            return false;
        }
        Liadminrolmoduloacciones other = (Liadminrolmoduloacciones) object;
        if ((this.armidrolmoduloacc == null && other.armidrolmoduloacc != null) || (this.armidrolmoduloacc != null && !this.armidrolmoduloacc.equals(other.armidrolmoduloacc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Liadminrolmoduloacciones[armidrolmoduloacc=" + armidrolmoduloacc + "]";
    }

}
