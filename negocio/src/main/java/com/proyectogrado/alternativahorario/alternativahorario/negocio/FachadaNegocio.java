package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Materia;
import com.proyectogrado.alternativahorario.entidades.Menu;
import com.proyectogrado.alternativahorario.entidades.Usuario;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Steven
 */
@Stateless
public class FachadaNegocio implements FachadaNegocioLocal {

    @EJB
    private AdministracionUsuarioLocal administracionUsuario;
    
    @EJB
    private AdministracionMenuLocal administracionMenu;
    
    @EJB
    private AdministracionMateriaLocal administracionMateria;
    
    @EJB
    private AdministracionCarreraLocal administracionCarrera;
    
    @Override
    public List<Usuario> buscarUsuario() {
        return administracionUsuario.buscarUsuario();
    }

    @Override
    public Usuario getUsuario(String id) {
        return administracionUsuario.getUsuario(id);
    }
    
    @Override
    public Usuario getUsuarioPorNombre(String nombre){
        return administracionUsuario.getUsuarioPorNombre(nombre);
    }
       
    @Override
    public boolean iniciarSesion(Usuario usuario){
        return administracionUsuario.iniciarSesion(usuario);       
    }
    
    @Override
    public List<Menu> getMenus(){
        return administracionMenu.getMenus();
    }
    
    @Override
    public List<Materia> getMaterias(){
        return administracionMateria.getMaterias();
    }
    
    @Override
    public boolean eliminarMateria(Materia materia){
        return administracionMateria.eliminarMateria(materia);
    }
    
    @Override
    public void agregarMateria(Materia materia){
        administracionMateria.agregarMateria(materia);
    }
    
    @Override
    public List<Carrera> getCarreras(){
        return administracionCarrera.getCarreras();
    }

}
