package com.proyectogrado.alternativahorario.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Steven
 */
@Entity
@Table(name = "usuarios")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Getter
    @Setter
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.proyectogrado.alternativahorario.entidades.Usuario[ id=" + id + " ]";
    }

}
