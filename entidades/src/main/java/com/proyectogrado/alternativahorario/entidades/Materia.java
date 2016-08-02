package com.proyectogrado.alternativahorario.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Steven
 */ 
@Entity
@Table(name = "materias")
@SequenceGenerator(name = "SecuenciaMaterias", sequenceName = "SEC_IDMATERIAS")
@NamedQueries({
    @NamedQuery(name = "Materia.findByNombre", query = "SELECT m FROM Materia m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Materia.findByCarreraSemestre", query = "SELECT m FROM Materia m WHERE m.carrera = :carrera AND m.semestre = :semestre")})
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SecuenciaMaterias")
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
    private Integer semestre;

    @Getter
    @Setter
    @Column(name = "creditos")
    private Integer creditos;

    @Getter
    @Setter
    @Column(name = "intensidad_horaria")
    private Integer intensidadHoraria;

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
        claseList = new ArrayList();
    }

    public Materia(BigDecimal id) {
        this.id = id;
        claseList = new ArrayList();
    }

}
