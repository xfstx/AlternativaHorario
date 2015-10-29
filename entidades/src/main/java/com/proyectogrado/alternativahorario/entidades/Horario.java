package com.proyectogrado.alternativahorario.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Steven
 */
@Entity
@Table(name = "horarios")
@NamedQueries({
    @NamedQuery(name = "Horario.findAll", query = "SELECT h FROM Horario h")})
public class Horario implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "id")
    private BigDecimal id;
    
    @Getter
    @Setter
    @Column(name = "dia")
    private String dia;
    
    @Getter
    @Setter
    @Column(name = "horainicio")
    private LocalTime horainicio;
    
    @Getter
    @Setter
    @Column(name = "horafin")
    private LocalTime horafin;
    
    @Getter
    @Setter
    @JoinColumn(name = "clases", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Clase clases;

    public Horario() {
    }

    public Horario(BigDecimal id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "com.proyectogrado.alternativahorario.entidades.Horario[ id=" + id + " ]";
    }

}
