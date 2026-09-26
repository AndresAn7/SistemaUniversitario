package com.miapp.modelo;

import com.miapp.servicios.Inscribible;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo: representa la entidad Estudiante.
 * Hereda de Persona e implementa Inscribible (puede inscribirse en Cursos).
 */
public class Estudiante extends Persona implements Inscribible {

    private static int totalEstudiantes = 0;
    public static final int PROMEDIO_MINIMO = 0;
    public static final int PROMEDIO_MAXIMO = 5;
    public static final String CARRERA_PREDETERMINADA = "Sin especificar";
    public static final int MAX_MATERIAS = 7;

    // ── Atributos de instancia ────────────────────────────────────────────────
    private String carrera;
    private double promedio;

    // Lado de la asociación N:M: los cursos en los que está inscrito.
    private List<Curso> cursos;

    // ── Constructor ───────────────────────────────────────────────────────────

    public Estudiante(int id, String nombre, String apellido, String carrera, double promedio) {
        super(nombre, apellido, id);
        this.carrera = carrera;

        if (promedio >= PROMEDIO_MINIMO && promedio <= PROMEDIO_MAXIMO) {
            this.promedio = promedio;
        } else {
            this.promedio = 0.0;  // Por defecto si está fuera de rango
        }

        this.cursos = new ArrayList<>();

        // Incrementa el contador estático de estudiantes
        totalEstudiantes++;
    }

    // ── Métodos estáticos (de clase) ──────────────────────────────────────────

    public static int getTotalEstudiantes() {
        return totalEstudiantes;
    }

    public static void reiniciarContador() {
        totalEstudiantes = 0;
    }

    public static int getProximoId() {
        return totalEstudiantes + 1;
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String getCarrera() {
        return carrera;
    }

    public double getPromedio() {
        return promedio;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    // ── Setters ──────────────────────────────────────────────────────────────

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    /**
     * Valida el promedio antes de asignarlo usando constantes finales.
     * @param p promedio a validar (debe estar entre PROMEDIO_MINIMO y PROMEDIO_MAXIMO)
     */
    public void setPromedio(double p) {
        if (p >= PROMEDIO_MINIMO && p <= PROMEDIO_MAXIMO) {
            this.promedio = p;
        }
    }

    // ── Interfaz Inscribible ─────────────────────────────────────────────────

    /**
     * Inscribe al estudiante en un curso, siempre que:
     * - el curso no sea null,
     * - no supere MAX_MATERIAS,
     * - no esté ya inscrito en ese curso.
     * Mantiene sincronizados ambos lados de la relación N:M.
     */
    @Override
    public boolean inscribir(Curso curso) {
        if (curso == null) {
            return false;
        }
        if (cursos.size() >= MAX_MATERIAS) {
            return false;
        }
        if (cursos.contains(curso)) {
            return false;
        }
        boolean agregado = cursos.add(curso);
        if (agregado) {
            curso.agregarEstudiante(this);
        }
        return agregado;
    }

    // ── Implementación del método abstracto de Persona ─────────────────────────

    /**
     * Cálculo de "pago" para un estudiante. Aquí no es un salario sino,
     * por ejemplo, lo que le correspondería pagar según sus créditos
     * matriculados. Ajusta esta fórmula según lo que pida tu profesora.
     */
    @Override
    public double calcularPago() {
        double valorPorCredito = 50000.0;
        int totalCreditos = 0;
        for (Curso c : cursos) {
            totalCreditos += c.getCreditos();
        }
        return totalCreditos * valorPorCredito;
    }

    /**
     * Método final: no puede ser sobrescrito por subclases.
     */
    @Override
    public final String toString() {
        return super.toString()
             + " | Carrera: " + carrera
             + " | Promedio: " + String.format("%.2f", promedio);
    }
}