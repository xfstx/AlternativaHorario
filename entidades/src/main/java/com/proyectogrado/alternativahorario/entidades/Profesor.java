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
@Table(name = "profesores")
@NamedQueries({
    @NamedQuery(name = "Profesor.findAll", query = "SELECT p FROM Profesor p")})
public class Profesor implements Serializable {
    
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
    @Column(name = "titulo")
    private String titulo;
    
    @Getter
    @Setter
    @Column(name = "tipo")
    private String tipo;
    
    @Getter
    @Setter
    @JoinColumn(name = "facultad", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Facultad facultad;
    
    @Getter
    @Setter
    @OneToMany(mappedBy = "profesor", fetch = FetchType.EAGER)
    private List<Clase> claseList;

    public Profesor() {
    }

    public Profesor(BigDecimal id) {
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
        return "com.proyectogrado.alternativahorario.entidades.Profesor[ id=" + id + " ]";
    }

}
