package com.proyectogrado.alternativahorario.alternativahorario.web;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Steven
 */
public class HorarioTable {
    
    @Getter
    @Setter
    private int id;
    
    @Getter
    @Setter
    private String hora;
    
    @Getter
    @Setter
    private String lunes;
            
    @Getter
    @Setter
    private String martes;
    
    @Getter
    @Setter
    private String miercoles;
    
    @Getter
    @Setter
    private String jueves;
    
    @Getter
    @Setter
    private String viernes;
    
    @Getter
    @Setter
    private String sabado;

    public HorarioTable() {
    }

    public HorarioTable(int id, String hora, String lunes, String martes, String miercoles, String jueves, String viernes, String sabado) {
        this.id = id;
        this.hora = hora;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
    }

}
