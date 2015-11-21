package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Sede;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Steven
 */
@Stateless
public class FachadaPersistenciaSede extends AbstractFacade<Sede> implements FachadaPersistenciaSedeLocal {
    @PersistenceContext(unitName = "com.proyectogrado.AlternativaHorario_AlternativaHorario-persistencia_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FachadaPersistenciaSede() {
        super(Sede.class);
    }

    @Override
    public Sede findByNombre(String nombre) {
        Sede sede = null;
        try {
            Query query = getEntityManager().createNamedQuery("Sede.findByNombre");
            query.setParameter("nombre", nombre);
            sede = (Sede) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error buscando Sede por nombre " + e);
        }
        return sede;
    }
    
}
