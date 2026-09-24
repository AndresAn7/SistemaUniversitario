package com.miapp.modelo;

/**
 *
 * @author Estudiante
 */
public abstract class Persona { 
    
    private String nombre;
    private String apellido;
    protected int id;

    public Persona(String nombre,String apellido, int id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
   
    
    public abstract double calcularPago();
    
    @Override
    public String toString() {
        return "ID: " + id
             + " | Nombre: " + nombre
             + " | Apellido: " + apellido;
    }
    
}
