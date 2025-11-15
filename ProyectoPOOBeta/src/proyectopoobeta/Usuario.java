/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopoobeta;

import java.util.ArrayList;

/**
 *
 * @author masan
 */
public class Usuario {
    private String nombre;
    private String ID;
    private Tipo tipo;
    private ArrayList<Prestamo> historial;
    private ArrayList<Sancion> sanciones;

    public Usuario(String nombre, String ID, Tipo tipo) {
        this.nombre = nombre;
        this.ID = ID;
        this.tipo = tipo;
        this.historial = new ArrayList<>();
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getID(){
        return ID;
    }
    
    public Tipo getTipo(){
        return tipo;
    }
}
