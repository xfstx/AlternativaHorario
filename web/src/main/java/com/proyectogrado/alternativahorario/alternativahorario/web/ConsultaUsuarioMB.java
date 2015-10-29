package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Steven
 */
@Named(value = "consultaUsuario")
@RequestScoped
public class ConsultaUsuarioMB {
    
    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private List<Usuario> usuarios;
    
    public ConsultaUsuarioMB() {
    }
    
    @PostConstruct
    public void init(){
        usuarios = new ArrayList();
    }
    
    public void consultarTodosUsuarios(){
        this.usuarios = fachadaNegocio.buscarUsuario();
        for (Usuario usuario : usuarios) {
            System.out.println(usuario.getId()+" "+usuario.getUsuario());            
        }
    }
    
}
