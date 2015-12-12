package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Steven
 */
@Named(value = "agregarUsuario")
@RequestScoped
public class AgregarUsuarioMB {

    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private String usuario;

    @Getter
    @Setter
    private String clave;

    @Getter
    @Setter
    private String tipo;

    @PostConstruct
    public void init() {

    }

    public void limpiarPantalla() {
        this.usuario = "";
        this.clave = "";
        this.tipo = "";
    }

    public void agregarUsuario() {
    }

}
