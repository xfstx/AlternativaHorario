package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Menu;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Steven
 */
@Stateless
public class FachadaPersistenciaMenu extends AbstractFacade<Menu> implements FachadaPersistenciaMenuLocal {

   @PersistenceContext(unitName = "com.proyectogrado.AlternativaHorario_AlternativaHorario-persistencia_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FachadaPersistenciaMenu() {
        super(Menu.class);
    }
    
}
