package proyectopoobeta;

import java.time.LocalDate;

public class Prestamo {
    private Usuario usuario;
    private Libro libro;
    private LocalDate fechaPrestamo;
    private boolean estado;

    public Prestamo(Usuario usuario, Libro libro, LocalDate fechaPrestamo) {
        this.usuario = usuario;
        this.libro = libro;
        this.estado = true;
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
    
    public boolean getEstado(){
        return estado;
    }
    
    public void setEstado(boolean estado){
        this.estado = estado;
    }
    
    @Override
    public String toString(){
        return "Prestamo realizado a: "+ usuario 
                + " Fecha de prestamo: "+ fechaPrestamo 
                + " Estado del libro: "+ estado;
    }  

}
