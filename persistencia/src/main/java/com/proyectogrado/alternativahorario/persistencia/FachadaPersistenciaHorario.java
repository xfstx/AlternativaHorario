package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Clase;
import com.proyectogrado.alternativahorario.entidades.Horario;
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
public class FachadaPersistenciaHorario extends AbstractFacade<Horario> implements FachadaPersistenciaHorarioLocal {

    @PersistenceContext(unitName = "com.proyectogrado.AlternativaHorario_AlternativaHorario-persistencia_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FachadaPersistenciaHorario() {
        super(Horario.class);
    }

    @Override
    public List<Horario> findByClase(Clase clase) {
        List<Horario> horarios = null;
        try {
            Query query = getEntityManager().createNamedQuery("Horario.findByClase");
            query.setParameter("clase", clase);
            horarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error buscando Horaris por clase " + clase.getGrupo() + e);
        }
        return horarios;
    }

}
