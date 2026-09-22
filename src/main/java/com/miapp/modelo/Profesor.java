/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;

/**
 *
 * @author Estudiante
 */
public class Profesor extends Persona {
    
    private final double salarioBase = 0;

    public Profesor(String nombre, String apellido, int id) {
        super(nombre, apellido, id);
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   public void impartirClase() {
   
   }
    
    
}
