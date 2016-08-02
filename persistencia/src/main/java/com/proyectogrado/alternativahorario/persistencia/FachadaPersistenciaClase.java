package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Clase;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Steven
 */
@Stateless
public class FachadaPersistenciaClase extends AbstractFacade<Clase> implements FachadaPersistenciaClaseLocal {

    @PersistenceContext(unitName = "com.proyectogrado.AlternativaHorario_AlternativaHorario-persistencia_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FachadaPersistenciaClase() {
        super(Clase.class);
    }

    @Override
    public List<Clase> findByMateria(Materia materia) {
        List<Clase> clases = null;
        try {
            Query query = getEntityManager().createNamedQuery("Clase.findByMateria");
            query.setParameter("materia", materia);
            clases = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error buscando Clases por materia " + materia.getNombre() + e);
        }
        return clases;
    }
    
    @Override
    public Clase findByMateriaGrupo(Materia materia, String grupo) {
        Clase clase = null;
        try {
            Query query = getEntityManager().createNamedQuery("Clase.findByMateria");
            query.setParameter("materia", materia);
            clase = (Clase) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error buscando Clase por materia " + materia.getNombre() + " y grupo "+grupo+" "+e);
        }
        return clase;
    }

}
