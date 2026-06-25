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
@Table(name = "LIADMINACCIONSCRIPT")

public class Liadminaccionscript implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "AASIDACCIONSCRIPT")
    private Integer aasidaccionscript;
    @Column(name = "AASSCRIPT")
    private String aasscript;
    @JoinColumn(name = "AASIDACCION", referencedColumnName = "AAIDACCIONES")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Liadminacciones aasidaccion;

    public Liadminaccionscript() {
    }

    public Liadminaccionscript(Integer aasidaccionscript) {
        this.aasidaccionscript = aasidaccionscript;
    }

    public Integer getAasidaccionscript() {
        return aasidaccionscript;
    }

    public void setAasidaccionscript(Integer aasidaccionscript) {
        this.aasidaccionscript = aasidaccionscript;
    }

    public String getAasscript() {
        return aasscript;
    }

    public void setAasscript(String aasscript) {
        this.aasscript = aasscript;
    }

    public Liadminacciones getAasidaccion() {
        return aasidaccion;
    }

    public void setAasidaccion(Liadminacciones aasidaccion) {
        this.aasidaccion = aasidaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aasidaccionscript != null ? aasidaccionscript.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liadminaccionscript)) {
            return false;
        }
        Liadminaccionscript other = (Liadminaccionscript) object;
        if ((this.aasidaccionscript == null && other.aasidaccionscript != null) || (this.aasidaccionscript != null && !this.aasidaccionscript.equals(other.aasidaccionscript))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Liadminaccionscript[aasidaccionscript=" + aasidaccionscript + "]";
    }

}
