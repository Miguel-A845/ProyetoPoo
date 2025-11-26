package proyectopoobeta;

import java.util.*;

public class Usuario {
    private String nombre;
    private String ID;
    private Tipo tipo;
    private List<Prestamo> historial = new ArrayList<>();
    private List<Sancion> sanciones = new ArrayList<>();

    public Usuario(String nombre, String ID, Tipo tipo) {
        this.nombre = nombre;
        this.ID = ID;
        this.tipo = tipo;
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
    
    public List<Sancion> getSanciones(){
        return sanciones;
    }
    
    public List<Prestamo> getHistorial(){
        return historial;
    }
}    
    
