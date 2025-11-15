/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyectopoobeta;

/**
 *
 * @author masan
 */
public interface Servicios {
    Prestamo prestarLibro(Usuario usuario, Libro libro);
    Devolucion devolverLibro(Prestamo prestamo);
    void verificarSanciones(Usuario usuario);
}
