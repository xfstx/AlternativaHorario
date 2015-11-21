package com.proyectogrado.alternativahorario.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "facultades")
@SequenceGenerator(name="SecuenciaFacultades", sequenceName = "SEC_IDFACULTADES")
@NamedQueries({
    @NamedQuery(name = "Facultad.findByNombre", query = "SELECT f FROM Facultad f WHERE f.nombre = :nombre")})
public class Facultad implements Serializable {
            
    private static final long serialVersionUID = 1L;
    
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SecuenciaFacultades")
    @Basic(optional = false)
    @Column(name = "id")
    private BigDecimal id;
    
    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;
    
    @Getter
    @Setter
    @JoinColumn(name = "sede", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sede sede;
    
    @Getter
    @Setter
    @OneToMany(mappedBy = "facultad", fetch = FetchType.LAZY)
    private List<Carrera> carreraList;
    
    public Facultad() {
    }

    public Facultad(BigDecimal id) {
        this.id = id;
    }

}
