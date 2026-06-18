/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Marco Andrade
 */
@Entity
@Table(name = "SEREMISIONES")
public class Seremisiones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "REMID")
    private Integer remid;
    @Basic(optional = false)
    @Column(name = "REMCONSECUTIVO")
    private Integer remconsecutivo;
    @Basic(optional = false)
    @Column(name = "REMEMPID")
    private Integer remempid;
    @Basic(optional = false)
    @Column(name = "REMALMID")
    private String remalmid;
    @Basic(optional = false)
    @Column(name = "REMFECHA")
    @Temporal(TemporalType.DATE)
    private Date remfecha;
    @Basic(optional = false)
    @Column(name = "REMMALID")
    private Integer remmalid;
    @Column(name = "REMPOLID")
    private Integer rempolid;
    @Column(name = "REMREFERENCIA")
    private String remreferencia;
    @Column(name = "REMCOMID")
    private Integer remcomid;
    @Column(name = "REMFACID")
    private Integer remfacid;
    @Column(name = "REMDTRID")
    private Integer remdtrid;
    @Column(name = "REMFECSURTIDO")
    @Temporal(TemporalType.DATE)
    private Date remfecsurtido;
    @Basic (optional = false)
    @Column(name = "REMESTATUS")
    private String remestatus;
    @Column(name = "REMCOMS")
    private String remcoms;
    @Column(name = "REMVENTA")
    private Integer remventa;
    @Column(name = "REMOPRID")
    private Integer remoprid;
    

    public Seremisiones() {
    }

    public Seremisiones(Integer remid) {
        this.remid = remid;
    }

    public Seremisiones(Integer remid, Date remfecha, Integer remmalid, String remreferencia) {
        this.remid = remid;
        this.remfecha = remfecha;
        this.remmalid = remmalid;
        this.remreferencia = remreferencia;
    }

    public Integer getRemfolio() {
        return remid;
    }

    public void setRemfolio(Integer remid) {
        this.remid = remid;
    }

    public Date getRemfecha() {
        return remfecha;
    }

    public void setRemfecha(Date remfecha) {
        this.remfecha = remfecha;
    }

    public Integer getRemtipo() {
        return remmalid;
    }

    public void setRemtipo(Integer remmalid) {
        this.remmalid = remmalid;
    }

    public String getRemcoms() {
        return remcoms;
    }

    public void setRemcoms(String remcoms) {
        this.remcoms = remcoms;
    }

    public String getRemreferencia() {
        return remreferencia;
    }

    public void setRemreferencia(String remreferencia) {
        this.remreferencia = remreferencia;
    }

    public Date getRemfecsurtido() {
        return remfecsurtido;
    }

    public void setRemfecsurtido(Date remfecsurtido) {
        this.remfecsurtido = remfecsurtido;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (remid != null ? remid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seremisiones)) {
            return false;
        }
        Seremisiones other = (Seremisiones) object;
        if ((this.remid == null && other.remid != null) || (this.remid != null && !this.remid.equals(other.remid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Seremisiones[remid=" + remid + "]";
    }

}
