package com.proyectogrado.alternativahorario.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Steven
 */
@Entity
@Table(name = "carreras")
@SequenceGenerator(name="SecuenciaCarreras", sequenceName = "SEC_IDCARRERAS")
@NamedQueries({
    @NamedQuery(name = "Carrera.findByNombre", query = "SELECT c FROM Carrera c WHERE c.nombre = :nombre")})
public class Carrera implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SecuenciaCarreras")
    @Basic(optional = false)
    @Column(name = "id")
    private BigDecimal id;
    
    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;
    
    @Getter
    @Setter
    @Column(name = "plan_estudio")
    private BigInteger planEstudio;
    
    @Getter
    @Setter
    @Column(name = "snies")
    private String snies;
    
    @Getter
    @Setter
    @Column(name = "creditos")
    private BigInteger creditos;
    
    @Getter
    @Setter
    @Column(name = "semestres")
    private BigInteger semestres;
    
    @Getter
    @Setter
    @Column(name = "materias")
    private BigInteger materias;
    
    @Getter
    @Setter
    @Column(name = "descripcion")
    private String descripcion;
    
    @Getter
    @Setter
    @JoinColumn(name = "facultad", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Facultad facultad;

    public Carrera() {
    }

    public Carrera(BigDecimal id) {
        this.id = id;
    }

}
