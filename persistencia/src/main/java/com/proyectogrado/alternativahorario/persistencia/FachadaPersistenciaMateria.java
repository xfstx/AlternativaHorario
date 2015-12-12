package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Carrera;
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
public class FachadaPersistenciaMateria extends AbstractFacade<Materia> implements FachadaPersistenciaMateriaLocal {

    @PersistenceContext(unitName = "com.proyectogrado.AlternativaHorario_AlternativaHorario-persistencia_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FachadaPersistenciaMateria() {
        super(Materia.class);
    }

    @Override
    public Materia findByNombre(String nombre) {
        Materia materia = null;
        try {
            Query query = getEntityManager().createNamedQuery("Materia.findByNombre");
            query.setParameter("nombre", nombre);
            materia = (Materia) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error buscando Materia por nombre " + e);
        }
        return materia;
    }

    @Override
    public List<Materia> findByCarreraSemestre(Carrera carrera, int semestre) {
        List<Materia> materias = null;
        try {
            Query query = getEntityManager().createNamedQuery("Materia.findByCarreraSemestre");
            query.setParameter("carrera", carrera);
            query.setParameter("semestre", semestre);
            materias = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error buscando Materias por carrera y semestre " + e);
        }
        return materias;
    }

}
