package com.proyectogrado.alternativahorario.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name = "sedes")
@NamedQueries({
    @NamedQuery(name = "Sede.findAll", query = "SELECT s FROM Sede s")})
public class Sede implements Serializable {
    
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
    @Column(name = "direccion")
    private String direccion;
    
    @Getter
    @Setter
    @OneToMany(mappedBy = "sede", fetch = FetchType.LAZY)
    private List<Facultad> facultadList;

    public Sede() {
    }

    public Sede(BigDecimal id) {
        this.id = id;
    }
    
}
