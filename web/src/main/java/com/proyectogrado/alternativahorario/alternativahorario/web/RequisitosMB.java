package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Steven
 */
@Named(value = "requisitos")
@ViewScoped
public class RequisitosMB implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private DualListModel<String> materias;

    @PostConstruct
    public void init() {
        List<String> materiasSource = llenarMaterias();
        List<String> materiasTarget = new ArrayList<String>();
        materias = new DualListModel<String>(materiasSource, materiasTarget);
    }

    public List<String> llenarMaterias() {
        List<Materia> mater = fachadaNegocio.getMaterias();
        List<String> materiasChar = new ArrayList();
        for (Materia materiaOrig : mater) {
            materiasChar.add(materiaOrig.getNombre());
        }
        return materiasChar;
    }

}
