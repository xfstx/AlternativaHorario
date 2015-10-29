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
@Table(name = "carreras")
@NamedQueries({
    @NamedQuery(name = "Carrera.findAll", query = "SELECT c FROM Carrera c")})
public class Carrera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Getter
    @Setter
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
    @ManyToOne(fetch = FetchType.EAGER)
    private Facultad facultad;
    
    @Getter
    @Setter
    @OneToMany(mappedBy = "carrera", fetch = FetchType.EAGER)
    private List<Materia> materiaList;

    public Carrera() {
    }

    public Carrera(BigDecimal id) {
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
        return "com.proyectogrado.alternativahorario.entidades.Carrera[ id=" + id + " ]";
    }

}
