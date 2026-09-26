/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.miapp;

import com.miapp.vista.EstudianteView;
import com.miapp.vista.GestionView;
import com.miapp.controlador.EstudianteController;
import com.miapp.controlador.GestionController;

public class SistemaUniversitario {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Módulo de Estudiantes
            EstudianteView vistaEstudiante = new EstudianteView();
            EstudianteController controladorEstudiante = new EstudianteController(vistaEstudiante);
            vistaEstudiante.setVisible(true);

            // Módulo de Cursos, Profesores y Matrícula
            GestionController controladorGestion = new GestionController(controladorEstudiante);
            GestionView vistaGestion = new GestionView();
            controladorGestion.setVista(vistaGestion);
            vistaGestion.setControlador(controladorGestion);
            vistaGestion.setVisible(true);
        });
    }
}