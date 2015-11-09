package com.proyectogrado.alternativahorario.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Steven
 */
@Entity
@Table(name = "materias")
@NamedQueries({
    @NamedQuery(name = "Materia.findAll", query = "SELECT m FROM Materia m")})
public class Materia implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Getter
    @Setter
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private BigDecimal id;
    
    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;
    
    @Getter
    @Setter
    @Column(name = "semestre")
    private BigInteger semestre;
    
    @Getter
    @Setter
    @Column(name = "creditos")
    private BigInteger creditos;
    
    @Getter
    @Setter
    @Column(name = "intensidad_horaria")
    private BigInteger intensidadHoraria;
    
    @Getter
    @Setter
    @OneToMany(mappedBy = "materia", fetch = FetchType.LAZY)
    private List<Clase> claseList;
    
    @Getter
    @Setter
    @JoinColumn(name = "carrera", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Carrera carrera;

    public Materia() {
    }

    public Materia(BigDecimal id) {
        this.id = id;
    }

}
