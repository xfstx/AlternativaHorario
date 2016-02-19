package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Menu;
import com.proyectogrado.alternativahorario.entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Steven
 */
@Named(value = "menu")
@ViewScoped
public class MenuMB implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @EJB
    private FachadaNegocioLocal fachadaNegocio;
    
    private List<Menu> lista;
    
    @Getter
    @Setter
    private MenuModel model;
    
    @PostConstruct
    public void init(){
        this.lista = fachadaNegocio.getMenus();
        this.model = new DefaultMenuModel();
        establecerPermisos();
    }
    
    public void establecerPermisos(){
        Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        // user = fachadaNegocio.getUsuarioPorNombre(user.getUsuario());
        for (Menu submenu : lista) {
            if (submenu.getTipo().equals("SUBMENU") // && submenu.getTipousuario().equals(user.getTipo())
                    ) {
                DefaultSubMenu firstSubMenu = new DefaultSubMenu(submenu.getNombre());
                for (Menu menu : lista) {
                    if (menu.getSubmenu() != null) {
                        if (menu.getSubmenu().getId() == submenu.getId()) {
                            DefaultMenuItem item = new DefaultMenuItem(menu.getNombre());
                            item.setUrl(menu.getUrl());
                            firstSubMenu.addElement(item);
                        }
                    }
                }
                model.addElement(firstSubMenu);
            } else {
                if (submenu.getSubmenu() == null // && submenu.getTipousuario().equals(user.getTipo())
                        ) {
                    DefaultMenuItem item = new DefaultMenuItem(submenu.getNombre());
                    item.setUrl(submenu.getUrl());
                    model.addElement(item);
                }
            }
        }
    }
    
    public void cerrarSesion(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
