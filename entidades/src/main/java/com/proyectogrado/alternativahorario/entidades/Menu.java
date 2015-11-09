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
@Table(name = "menus")
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")})
public class Menu implements Serializable {

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
    @Column(name = "tipo")
    private String tipo;

    @Getter
    @Setter
    @Column(name = "tipousuario")
    private String tipousuario;

    @Getter
    @Setter
    @Column(name = "url")
    private String url;

    @Getter
    @Setter
    @Column(name = "estado")
    private Boolean estado;

    @Getter
    @Setter
    @OneToMany(mappedBy = "submenu", fetch = FetchType.LAZY)
    private List<Menu> menuList;

    @Getter
    @Setter
    @JoinColumn(name = "submenu", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu submenu;

    public Menu() {
    }

    public Menu(BigDecimal id) {
        this.id = id;
    }

}
