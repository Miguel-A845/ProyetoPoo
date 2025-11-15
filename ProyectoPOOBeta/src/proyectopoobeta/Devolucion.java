/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopoobeta;

import java.time.LocalDate;

/**
 *
 * @author masan
 */
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
}
