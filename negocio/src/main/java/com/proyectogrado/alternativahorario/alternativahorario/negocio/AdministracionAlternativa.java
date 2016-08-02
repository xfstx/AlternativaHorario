package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Clase;
import com.proyectogrado.alternativahorario.entidades.Horario;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.lang3.Range;

/**
 *
 * @author Steven
 */
@Stateless
public class AdministracionAlternativa implements AdministracionAlternativaLocal {
    
    @EJB
    private FachadaNegocioLocal fachadaNegocio;
    
    private List<Cromosoma> poblacionTemporal;
    
    private List<Cromosoma> poblacion;

    private List<Materia> materiasSeleccionadas;

    private final int CANTIDAD_MAXIMA_ITERACIONES = 100;

    private final int CANTIDAD_POBLACION = 40;
    
    private int CORTE_A;
    
    private int CORTE_B;

    private Random random;
    
    // Metodo Inicial !!!
    @Override
    public List<Cromosoma> ejecutarAlgoritmoGenetico(List<Materia> materiasSeleccionadas) {
        this.materiasSeleccionadas = materiasSeleccionadas;
        crearRandom();        
        generarPoblacionInicial();
        evolucionarCromosomas();
        return seleccionarMejoresIndividuos();
    }

    public void crearRandom() {
        random = new Random(System.currentTimeMillis());
    }

    public int getNumeroRandom(int base) {
        return random.nextInt(base);
    }
    
    public void calcularPuntosDeCorte(Cromosoma crom) {
        int cantidadGenes = crom.getClases().size();
        int interva = Math.abs(cantidadGenes / 3);
        this.CORTE_A = interva;
        this.CORTE_B = interva * 2;
    }
    
    public void generarPoblacionInicial() {
        List<Cromosoma> poblacionInicial = new ArrayList();
        Cromosoma crom;
        for (int i = 0; i < CANTIDAD_POBLACION; i++) {
            crom = crearCromosomaAleatorio();
            poblacionInicial.add(crom);
        }
        poblacion = poblacionInicial;
    }

    public Cromosoma crearCromosomaAleatorio() {
        Cromosoma crom = new Cromosoma();
        List<Clase> clases = new ArrayList();
        for (Materia materia : this.materiasSeleccionadas) {
            Clase claseGen = claseAleatoriaporMateria(materia);
            clases.add(claseGen);
        }
        crom.setClases(clases);
        return crom;
    }

    public Clase claseAleatoriaporMateria(Materia materia) {
        List<Clase> clasesPorMateria = fachadaNegocio.getClasesPorMateria(materia);
        return clasesPorMateria.get(getNumeroRandom(clasesPorMateria.size()));
    }
    
    public void asignarMateriasSeleccionadas(List<Materia> materiasSeleccionadas) {
        this.materiasSeleccionadas = materiasSeleccionadas;
    }
    
    public void evolucionarCromosomas() {
        for (int i = 0; i < CANTIDAD_MAXIMA_ITERACIONES; i++) {
            evaluarPoblacion();
            operarPoblacion();
        }
    }

    public void operarPoblacion() {
        evaluarPoblacion();
        ordenarPoblacion();
        depurarPoblacion();
        cruzarPoblacion();
        mutarPoblacion();
    }
    
    public void evaluarPoblacion() {
        for (Cromosoma individuo : poblacion) {
            evaluarIndividuo(individuo);
        }
    }

    public void evaluarIndividuo(Cromosoma individuo) {
        int cantidadHoraCruze = calcularCantidadHorasCruce(individuo);
        int cantidadHorasEspera = calcularCantidadHorasEspera(individuo);
        int resultado = Math.abs(1 / (cantidadHoraCruze + cantidadHorasEspera + 1));
        individuo.setAptitud(resultado);
    }
    
    public int calcularCantidadHorasCruce(Cromosoma horario) {
        int horasDeCruce = 0;
        for (Clase clase : horario.getClases()) {
            for (Horario hora : clase.getHorarioList()) {
               horasDeCruce =+ compararHorasCruzadas(hora, horario);
            }
        }
        return horasDeCruce;
    }
    
    public int compararHorasCruzadas(Horario horarioBase, Cromosoma clases){
        int horasCruzadas = 0;
        for (Clase clase : clases.getClases()) {
            for (Horario horaCalculo : clase.getHorarioList()) {
                horasCruzadas =+ calculoDeRangosHorasCruzadas(horarioBase, horaCalculo);
            }
        }
        return horasCruzadas;
    }

    public int calculoDeRangosHorasCruzadas(Horario horarioA, Horario horarioB){
        Range horarA = Range.between(horarioA.getHorainicio(), horarioA.getHorafin());
        Range horarB = Range.between(horarioB.getHorainicio(), horarioB.getHorafin());
        Range intersect = null;
        Integer minimo = null;
        Integer maximo = 0;
        try {
            intersect = horarA.intersectionWith(horarB);
            minimo = (Integer) intersect.getMinimum();
            maximo = (Integer) intersect.getMaximum();
            maximo = maximo - minimo;
        } catch (Exception e) {
        }
        return maximo;
    }
    
    public int calcularCantidadHorasEspera(Cromosoma horario) {
        int horasDeCruce = 0;
        for (Clase clase : horario.getClases()) {
            for (Horario hora : clase.getHorarioList()) {
               horasDeCruce =+ compararHorasEspera(hora, horario);
            }
        }
        return horasDeCruce;
    }
    
    public int compararHorasEspera(Horario horarioBase, Cromosoma clases){
        int horasCruzadas = 0;
        for (Clase clase : clases.getClases()) {
            for (Horario horaCalculo : clase.getHorarioList()) {
                horasCruzadas =+ calculoDeRangosHorasDeEspera(horarioBase, horaCalculo);
            }
        }
        return horasCruzadas;
    }
    
    public int calculoDeRangosHorasDeEspera(Horario horarioA, Horario horarioB){
        int calculo = 0;
        if (horarioA.getDia().equals(horarioB.getDia())) {
            Range horarA = Range.between(horarioA.getHorainicio(), horarioA.getHorafin());
            Range horarB = Range.between(horarioB.getHorainicio(), horarioB.getHorafin());
            Range intersect = null;
            try {
                intersect = horarA.intersectionWith(horarB);
            } catch (Exception e) {
            }
            if (Objects.isNull(intersect)) {
                if (horarA.isAfterRange(horarB)) {
                    calculo = calcularDiferenciaHorasEntreHorarios(horarB, horarA);
                } else {
                    calculo = calcularDiferenciaHorasEntreHorarios(horarA, horarB);
                }
            }
        }
        return calculo;
    }
    
    public int calcularDiferenciaHorasEntreHorarios(Range horarioA, Range horarioB){
        int horaFinalHorarioA = (int) horarioA.getMaximum();
        int horaInicialHorarioB = (int) horarioB.getMinimum();
        return horaInicialHorarioB - horaFinalHorarioA;
    }
    
    
    public void ordenarPoblacion() {
        Collections.sort(poblacion, (Cromosoma o1, Cromosoma o2) -> {
            if (o1.getAptitud() == o2.getAptitud()) {
                return 0;
            }
            if (o1.getAptitud() >= o2.getAptitud()) {
                return 1;
            }
            if (o1.getAptitud() <= o2.getAptitud()) {
                return -1;
            }
            return 0;
        });
    }

    /*
    *   SELECCION
    *   Se seleccionan el 75% de los mejores individuos de la poblacion actual.
    */
    public void depurarPoblacion() {
        poblacionTemporal = new ArrayList();
        int discriminador = (int) Math.abs(poblacion.size()*0.75); // TODO Podria ser un parametro configurable
        for (int i = 0; i < poblacion.size()-discriminador; i++) {
            poblacionTemporal.add(poblacion.get(i));
        }
        poblacion.clear();
    }

    /*
    *   RECOMBINACION
    *   De el 75% de los mejores individuos de la poblacion anterior se combinan aleatoriamente entre ellos para crear
    *   una nueva poblacion que remplazara la anterior.
    */
    public void cruzarPoblacion() {
        Cromosoma hijo;
        for (int i = 0; i < CANTIDAD_POBLACION; i++) {
            hijo = cruzarCromosoma(cromosomaAleatorio(this.poblacionTemporal), cromosomaAleatorio(this.poblacionTemporal));
            poblacion.add(hijo);
        }
    }

    public Cromosoma cromosomaAleatorio(List<Cromosoma> poblacion) {
        return poblacion.get(getNumeroRandom(poblacion.size()));
    }
    
    /*
    * Se realiza un cruze de dos Cromosomas
    */
    public Cromosoma cruzarCromosoma(Cromosoma padre, Cromosoma madre) {
        if (this.CORTE_A == 0) {
            return calcularCruzeUnPunto(padre, madre);
        } else {
            return calcularCruzeDosPuntos(padre, madre);
        }
    }

    public Cromosoma calcularCruzeUnPunto(Cromosoma padre, Cromosoma madre) {
        Cromosoma hijo = new Cromosoma();
        List<Clase> genes = new ArrayList();
        genes.add(padre.getClases().get(0));
        genes.add(madre.getClases().get(1));
        hijo.setClases(genes);
        return hijo;
    }

    public Cromosoma calcularCruzeDosPuntos(Cromosoma padre, Cromosoma madre) {
        Cromosoma hijo = new Cromosoma();
        List<Clase> genes = new ArrayList();        
        // Llenando cromosoma hasta el CorteA
        for (int i = 0; i < this.CORTE_A; i++) {
            genes.add(padre.getClases().get(i));
        }
        // Llenando cromosoma de CorteA a CorteB
        for (int i = this.CORTE_A; i < this.CORTE_B; i++) {
            genes.add(madre.getClases().get(i));            
        }
        // Llenando cromosoma de CorteB hasta completar Cromosoma
        for (int i = this.CORTE_B; i < padre.getClases().size(); i++) {
            genes.add(padre.getClases().get(i));            
        }
        hijo.setClases(genes);
        return hijo;
    }
    
    /*
    *   MUTACION
    */
    public void mutarPoblacion() {
        int individuosAMutar = (int) Math.abs(CANTIDAD_POBLACION/0.05);
        for (int i = 0; i < individuosAMutar; i++) {
            mutarCromosoma(cromosomaAleatorio(poblacion));
        }
    }
    
    public void mutarCromosoma(Cromosoma crom){
        // TODO espero que al sacar el Gen se pueda modificar siendo la refecia y no un nuevo objeto
        int cantidadGenes = crom.getClases().size();
        Clase genAMutar = crom.getClases().get(getNumeroRandom(cantidadGenes));
        List<Clase> opcionesDeMutacion = fachadaNegocio.getClasesPorMateria(genAMutar.getMateria());
        genAMutar = opcionesDeMutacion.get(getNumeroRandom(opcionesDeMutacion.size()));
    }
    
    /*
    * Obtiene los mejores 10 individuos
    */
    public List<Cromosoma> seleccionarMejoresIndividuos() {
        List<Cromosoma> resultado = new ArrayList();
        ordenarPoblacion();
        for (int i = 0; i < 10; i++) {
            resultado.add(poblacion.get(i));
        }
        return resultado;
    }

}
