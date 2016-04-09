package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Clase;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Steven
 */
public class Cromosoma {

    @Getter
    @Setter
    private List<Clase> clases;
    
    @Getter
    @Setter
    private Integer aptitud;

    public Cromosoma() {
    }

    public Cromosoma(List<Clase> clases) {
        this.clases = clases;
    }

}
