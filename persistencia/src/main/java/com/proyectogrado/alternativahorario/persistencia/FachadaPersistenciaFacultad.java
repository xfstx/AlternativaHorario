package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Facultad;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Steven
 */
@Stateless
public class FachadaPersistenciaFacultad extends AbstractFacade<Facultad> implements FachadaPersistenciaFacultadLocal {
    @PersistenceContext(unitName = "com.proyectogrado.AlternativaHorario_AlternativaHorario-persistencia_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FachadaPersistenciaFacultad() {
        super(Facultad.class);
    }

    @Override
    public Facultad findByNombre(String nombre) {
        Facultad facultad = null;
        try {
            Query query = getEntityManager().createNamedQuery("Facultad.findByNombre");
            query.setParameter("nombre", nombre);
            facultad = (Facultad) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error buscando Facultad por nombre " + e);
        }
        return facultad;
    }
    
}
