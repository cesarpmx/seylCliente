/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Sistemas
 */
@Entity
@Table(name = "LIPRDMOVER")
public class Liprdmover implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PMOID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_LIPRDMOVER")
    @SequenceGenerator(name="SEQ_LIPRDMOVER", sequenceName = "SEQ_LIPRDMOVER",allocationSize=1)
    private Integer pmoid;
    @Basic(optional = true)
    @Column(name = "PMOPRODUCTO")
    private String pmoproducto;
    @Basic(optional = true)
    @Column(name = "PMOALMACEN")
    private String pmoalmacen;
    @Basic(optional = true)
    @Column(name = "PMOCANTIDAD")
    private Integer pmocantidad;
    @Basic(optional = true)
    @Column(name = "PMOUSUARIO")
    private Integer pmousuario;
    @Column(name = "PMOCOMS")
    private String pmocoms;
    @Basic(optional = true)
    @Column(name = "PMOESTATUS")
    private String pmoestatus;
    @Basic(optional = true)
    @Column(name = "PMOACCION")
    private String pmoaccion;
   
    public Liprdmover() {
    }

    public Liprdmover(Integer pmoid) {
        this.pmoid = pmoid;
    }

    public Liprdmover(Integer pmoid, String pmoproducto, String pmoalmacen, Integer pmocantidad, Integer pmousuario, String pmocoms, String pmoestatus, String pmoaccion) {
        this.pmoid = pmoid;
        this.pmoproducto = pmoproducto;
        this.pmoalmacen = pmoalmacen;
        this.pmocantidad = pmocantidad;
        this.pmousuario = pmousuario;
        this.pmocoms = pmocoms;
        this.pmoestatus = pmoestatus;
        this.pmoaccion = pmoaccion;
    }

    
    public Integer getPmoid() {
        return pmoid;
    }

    public void setPmoid(Integer pmoid) {
        this.pmoid = pmoid;
    }

    public String getPmoproducto() {
        return pmoproducto;
    }

    public void setPmoproducto(String pmoproducto) {
        this.pmoproducto = pmoproducto;
    }

    public String getPmoalmacen() {
        return pmoalmacen;
    }

    public void setPmoalmacen(String pmoalmacen) {
        this.pmoalmacen = pmoalmacen;
    }

    public Integer getPmocantidad() {
        return pmocantidad;
    }

    public void setPmocantidad(Integer pmocantidad) {
        this.pmocantidad = pmocantidad;
    }

    public Integer getPmousuario() {
        return pmousuario;
    }

    public void setPmousuario(Integer pmousuario) {
        this.pmousuario = pmousuario;
    }

    public String getPmocoms() {
        return pmocoms;
    }

    public void setPmocoms(String pmocoms) {
        this.pmocoms = pmocoms;
    }

    public String getPmoestatus() {
        return pmoestatus;
    }

    public void setPmoestatus(String pmoestatus) {
        this.pmoestatus = pmoestatus;
    }

    public String getPmoaccion() {
        return pmoaccion;
    }

    public void setPmoaccion(String pmoaccion) {
        this.pmoaccion = pmoaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmoid != null ? pmoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liprdmover)) {
            return false;
        }
        Liprdmover other = (Liprdmover) object;
        if ((this.pmoid == null && other.pmoid != null) || (this.pmoid != null && !this.pmoid.equals(other.pmoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Liprdmover[pmoid=" + pmoid + "]";
    }

}
