package proyectopoobeta;

import java.time.LocalDate;

public class Devolucion {
    private LocalDate fechaDevolucion;
    private Prestamo prestamo;

    public Devolucion(LocalDate fechaDevolucion, Prestamo prestamo) {
        this.fechaDevolucion = fechaDevolucion;
        this.prestamo = prestamo;
    }
    
    public LocalDate getFechaDev(){
        return fechaDevolucion;
    }
    
    public Prestamo getPrestamo(){
        return prestamo;
    }
}
