package com.proyectogrado.alternativahorario.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "clases")
@NamedQueries({
    @NamedQuery(name = "Clase.findByMateria", query = "SELECT c FROM Clase c WHERE c.materia = :materia")})
public class Clase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private BigDecimal id;

    @Getter
    @Setter
    @Column(name = "grupo")
    private String grupo;

    @Getter
    @Setter
    @OneToMany(mappedBy = "clases", fetch = FetchType.LAZY)
    private List<Horario> horarioList;

    @Getter
    @Setter
    @JoinColumn(name = "materia", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Materia materia;

    @Getter
    @Setter
    @JoinColumn(name = "profesor", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Profesor profesor;

    public Clase() {
    }

    public Clase(BigDecimal id) {
        this.id = id;
    }

}
