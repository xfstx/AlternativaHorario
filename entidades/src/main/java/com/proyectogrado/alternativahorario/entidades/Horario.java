package com.proyectogrado.alternativahorario.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
    @NamedQuery(name = "Horario.findByClase", query = "SELECT h FROM Horario h WHERE h.clases = :clase")})
public class Horario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Getter
    @Setter
    @Id
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
    private BigDecimal horainicio;
    
    @Getter
    @Setter
    @Column(name = "horafin")
    private BigDecimal horafin;
    
    @Getter
    @Setter
    @JoinColumn(name = "clases", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Clase clases;

    public Horario() {
    }

    public Horario(BigDecimal id) {
        this.id = id;
    }

}
