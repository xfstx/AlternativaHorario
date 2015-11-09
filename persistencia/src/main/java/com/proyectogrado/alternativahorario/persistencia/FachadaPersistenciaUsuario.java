package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Steven
 */
@Stateless
public class FachadaPersistenciaUsuario extends AbstractFacade<Usuario> implements FachadaPersistenciaUsuarioLocal {
            
    @PersistenceContext(unitName = "com.proyectogrado.AlternativaHorario_AlternativaHorario-persistencia_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FachadaPersistenciaUsuario() {
        super(Usuario.class);
    }
    
    @Override
    public Usuario findByUsuarioClave(String usuario, String clave){
        Usuario user = null;
        try {
            Query query = getEntityManager().createNamedQuery("Usuario.findByUsuarioClave");
            query.setParameter("usuario", usuario);
            query.setParameter("clave", clave);
            user = (Usuario) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error buscando usuario por Usuario y Clave " + e);
        }
        return user;
    }
    
    @Override
    public Usuario findByUsuario(String usuario){
        Usuario user = null;
        try {
            Query query = getEntityManager().createNamedQuery("Usuario.findByUsuario");
            query.setParameter("usuario", usuario);
            user = (Usuario) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error buscando usuario por Usuario " + e);
        }
        return user;
    }
    
}
