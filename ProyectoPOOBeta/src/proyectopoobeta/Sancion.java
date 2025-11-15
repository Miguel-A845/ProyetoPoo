/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopoobeta;

import java.time.LocalDate;

public class Sancion {
    private Usuario usuario;
    private String motivo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean activo;

    public Sancion(Usuario usuario, String motivo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.usuario = usuario;
        this.motivo = motivo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activo = true;
    }
    
    public boolean estaActivo(){
        return LocalDate.now().isBefore(fechaFin);
    }
}
