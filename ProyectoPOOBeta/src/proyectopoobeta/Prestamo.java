package proyectopoobeta;

import java.time.LocalDate;

public class Prestamo {
    private Usuario usuario;
    private Libro libro;
    private LocalDate fechaPrestamo;
    private boolean estado;

    public Prestamo(Usuario usuario, Libro libro, LocalDate fechaPrestamo, boolean estado) {
        this.usuario = usuario;
        this.libro = libro;
        this.estado = estado;
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public Usuario getUsuario(){
        return usuario;
    }
    
    public Libro getLibro(){
        return libro;
    }
    
    public LocalDate getFecha(){
        return fechaPrestamo;
    }
    
    @Override
    public String toString(){
        return "Prestamo realizado a: "+ usuario 
                + " Fecha de prestamo: "+ fechaPrestamo 
                + " Estado del libro: "+ estado;
    }  

}
