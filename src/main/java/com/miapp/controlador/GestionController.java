package com.miapp.controlador;

import com.miapp.modelo.Curso;
import com.miapp.modelo.Estudiante;
import com.miapp.modelo.Profesor;
import com.miapp.vista.GestionView;
import com.miapp.utilidades.EstadoMatricula;

import java.util.ArrayList;
import java.util.List;

public class GestionController {

    private static final String MSG_CAMPOS_OBLIGATORIOS = "Todos los campos son obligatorios.";
    private static final String MSG_CURSO_DUPLICADO = "Ya existe un curso con ese código.";
    private static final String MSG_CURSO_CREADO = "Curso creado correctamente.";
    private static final String MSG_PROFESOR_CREADO = "Profesor creado correctamente.";
    private static final String MSG_ESTUDIANTE_NO_ENCONTRADO = "No existe un estudiante con ese ID.";
    private static final String MSG_CURSO_NO_ENCONTRADO = "No existe un curso con ese código.";
    private static final String MSG_MATRICULA_EXITOSA = "Matrícula realizada correctamente.";
    private static final String MSG_MATRICULA_FALLIDA =
            "No se pudo matricular (curso ya inscrito o se alcanzó MAX_MATERIAS).";
    private static final String MSG_ESTADO_ACTUALIZADO = "Estado de matrícula actualizado correctamente.";
    private final EstudianteController estudianteController;
    private final List<Curso> cursos;
    private final List<Profesor> profesores;

    private GestionView vista;

    public GestionController(EstudianteController estudianteController) {
        this.estudianteController = estudianteController;
        this.cursos = new ArrayList<>();
        this.profesores = new ArrayList<>();
        cargarDatosIniciales();
    }

    public void setVista(GestionView vista) {
        this.vista = vista;
    }

    // ── Datos de ejemplo ─────────────────────────────────────────────────────

    private void cargarDatosIniciales() {
        cursos.add(new Curso("SIS101", 3));
        cursos.add(new Curso("SIS202", 4));
        cursos.add(new Curso("MAT101", 5));

        profesores.add(new Profesor("Jorge", "Ramírez", 101, 3200000.0));
        profesores.add(new Profesor("Patricia", "Núñez", 102, 3500000.0));
    }

    // ── Cursos ────────────────────────────────────────────────────────────────

    public boolean agregarCurso(String codigo, int creditos) {
        if (codigo == null || codigo.isEmpty() || creditos <= 0) {
            vista.mostrarError(MSG_CAMPOS_OBLIGATORIOS);
            return false;
        }
        if (obtenerCursoPorCodigo(codigo) != null) {
            vista.mostrarError(MSG_CURSO_DUPLICADO);
            return false;
        }
        cursos.add(new Curso(codigo, creditos));
        vista.mostrarMensaje(MSG_CURSO_CREADO);
        return true;
    }

    public Curso obtenerCursoPorCodigo(String codigo) {
        for (Curso c : cursos) {
            if (c.getCodigo().equalsIgnoreCase(codigo)) {
                return c;
            }
        }
        return null;
    }

    public List<Curso> obtenerCursos() {
        return cursos;
    }

    // ── Profesores ────────────────────────────────────────────────────────────

    public boolean agregarProfesor(String nombre, String apellido, int id, double salarioBase) {
        if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() || salarioBase <= 0) {
            vista.mostrarError(MSG_CAMPOS_OBLIGATORIOS);
            return false;
        }
        profesores.add(new Profesor(nombre, apellido, id, salarioBase));
        vista.mostrarMensaje(MSG_PROFESOR_CREADO);
        return true;
    }

    public List<Profesor> obtenerProfesores() {
        return profesores;
    }

    // ── Matrícula (usa la asociación N:M Estudiante-Curso) ──────────────────────

    public boolean matricular(int idEstudiante, String codigoCurso) {
        Estudiante estudiante = estudianteController.obtenerEstudiantePorId(idEstudiante);
        if (estudiante == null) {
            vista.mostrarError(MSG_ESTUDIANTE_NO_ENCONTRADO);
            return false;
        }

        Curso curso = obtenerCursoPorCodigo(codigoCurso);
        if (curso == null) {
            vista.mostrarError(MSG_CURSO_NO_ENCONTRADO);
            return false;
        }

        boolean matriculado = estudiante.inscribir(curso);
        if (matriculado) {
            vista.mostrarMensaje(MSG_MATRICULA_EXITOSA);
        } else {
            vista.mostrarError(MSG_MATRICULA_FALLIDA);
        }
        return matriculado;
    }

    // ── Estado de matrícula ──────────────────────────────────────────────────

    public boolean cambiarEstadoMatricula(int idEstudiante, EstadoMatricula nuevoEstado) {
        boolean cambiado = estudianteController.cambiarEstado(idEstudiante, nuevoEstado);
        if (cambiado) {
            vista.mostrarMensaje(MSG_ESTADO_ACTUALIZADO);
        } else {
            vista.mostrarError(MSG_ESTUDIANTE_NO_ENCONTRADO);
    }
        return cambiado;
}

    public List<Estudiante> buscarPorEstado(EstadoMatricula estado) {
        return estudianteController.obtenerEstudiantesPorEstado(estado);
}

}