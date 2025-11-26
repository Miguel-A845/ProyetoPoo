package proyectopoobeta;

public interface Servicios {
    public Prestamo prestarLibro(Usuario usuario, Libro libro);
    public Devolucion devolverLibro(Prestamo prestamo);
}
