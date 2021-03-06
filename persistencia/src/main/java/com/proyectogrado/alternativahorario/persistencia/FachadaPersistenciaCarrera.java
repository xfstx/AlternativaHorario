package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Carrera;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Steven
 */
@Stateless
public class FachadaPersistenciaCarrera extends AbstractFacade<Carrera> implements FachadaPersistenciaCarreraLocal {
    @PersistenceContext(unitName = "com.proyectogrado.AlternativaHorario_AlternativaHorario-persistencia_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FachadaPersistenciaCarrera() {
        super(Carrera.class);
    }

    @Override
    public Carrera findByNombre(String nombre) {
        Carrera user = null;
        try {
            Query query = getEntityManager().createNamedQuery("Carrera.findByNombre");
            query.setParameter("nombre", nombre);
            user = (Carrera) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error buscando Carrrea por nombre " + e);
        }
        return user;
    }
    
}
