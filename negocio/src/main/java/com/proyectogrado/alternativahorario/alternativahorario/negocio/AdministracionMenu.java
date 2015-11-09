package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Menu;
import com.proyectogrado.alternativahorario.persistencia.FachadaPersistenciaMenuLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Steven
 */
@Stateless
public class AdministracionMenu implements AdministracionMenuLocal {

    @EJB
    private FachadaPersistenciaMenuLocal fachadaPersistenciaMenu;
    
    @Override
    public List<Menu> getMenus(){
        List<Menu> lista = null;
        try {
            lista = fachadaPersistenciaMenu.findAll();
        } catch (Exception e) {
            System.out.println("Error Listando Menus " + e);
        }
        return lista;
    }
        
}
