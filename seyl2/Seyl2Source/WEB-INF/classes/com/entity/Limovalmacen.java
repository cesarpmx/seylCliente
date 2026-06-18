/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Sistemas
 */
@Entity
@Table(name = "SEMOVALMACEN")
public class Limovalmacen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MALID")
    private Integer malid;
    @Basic(optional = false)
    @Column(name = "MALNOMBRE")
    private String malnombre;
    @Basic(optional = false)
    @Column(name = "MALTIPO")
    private String maltipo;
    @Column(name = "MALFPOID")
    private String malfpoid;
    @Basic(optional = false)
    @Column(name = "MALESTATUS")
    private String malestatus;
    @Column(name = "MALEMBARQUE")
    private String malembarque;

    public Limovalmacen() {
    }

    public Limovalmacen(Integer malid) {
        this.malid = malid;
    }

    public Limovalmacen(Integer malid, String malnombre, String maltipo) {
        this.malid = malid;
        this.malnombre = malnombre;
        this.maltipo = maltipo;
    }

    public Integer getMovclave() {
        return malid;
    }

    public void setMovclave(Integer malid) {
        this.malid = malid;
    }

    public String getMovdescripcion() {
        return malnombre;
    }

    public void setMovdescripcion(String movdescripcion) {
        this.malnombre = malnombre;
    }

    public String getMovtipo() {
        return maltipo;
    }

    public void setMovtipo(String maltipo) {
        this.maltipo = maltipo;
    }

    public String getMovembarcar() {
        return malembarque;
    }

    public void setMovembarcar(String malembarque) {
        this.malembarque = malembarque;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (malid != null ? malid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Limovalmacen)) {
            return false;
        }
        Limovalmacen other = (Limovalmacen) object;
        if ((this.malid == null && other.malid != null) || (this.malid != null && !this.malid.equals(other.malid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Limovalmacen[movclave=" + malid + "]";
    }

}
