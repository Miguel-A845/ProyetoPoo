package proyectopoobeta;

import java.time.LocalDate;

public class Sancion {
    private Usuario usuario;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean activo;

    public Sancion(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin) {
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activo = true;
    }
    
    public boolean Activo(){
        
        if (LocalDate.now().isBefore(fechaFin)){
            this.activo = true;
        } else {
            this.activo = false;
        }
        return this.activo;
    }
    
    public Usuario getUsuario(){
        return usuario;
    }
    public LocalDate getFechaInicio(){
        return fechaInicio;
    }
    public LocalDate getFechaFin(){
        return fechaFin;
    }
    public boolean isActivo(){
        return activo;
    }
}
