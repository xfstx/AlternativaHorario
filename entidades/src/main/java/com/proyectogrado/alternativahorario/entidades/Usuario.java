package com.proyectogrado.alternativahorario.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "usuarios")
@SequenceGenerator(name="SecuenciaUsuarios", sequenceName = "SEC_IDUSUARIOS")
@NamedQueries({
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario")})
public class Usuario implements Serializable {
 
    private static final long serialVersionUID = 1L;
    
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SecuenciaUsuarios")
    @Basic(optional = false)
    @Column(name = "id")
    private BigDecimal id;
    
    @Getter
    @Setter
    @Column(name = "usuario")
    private String usuario;
    
    @Getter
    @Setter
    @Column(name = "clave")
    private String clave;
    
    @Getter
    @Setter
    @Column(name = "tipo")
    private String tipo;
    
    @Getter
    @Setter
    @Column(name = "estado")
    private String estado;

    public Usuario() {
    }

    public Usuario(BigDecimal id) {
        this.id = id;
    }

}
