/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;

import com.miapp.servicios.IBuscador;

/**
 *
 * @author Estudiante
 */
public class Profesor extends Persona implements IBuscador {
    
    private final double salarioBase;

    public Profesor(String nombre, String apellido, int id, double salarioBase) {
    super(nombre, apellido, id);
    this.salarioBase = salarioBase;
}

public double getSalarioBase() {
    return salarioBase;

}

public void impartirClase() {
    System.out.println(getNombre() + " " + getApellido() + " está impartiendo su clase.");
}



    @Override
    public double calcularPago() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void buscarEstudiante(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void buscarEstudiantePorCarrera(String carrera) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cargarDatos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
