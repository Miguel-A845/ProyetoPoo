package proyectopoobeta;
public class PrestamoYaDevueltoException extends Exception{
    private Prestamo prestamo;
    
    public PrestamoYaDevueltoException(Prestamo prestamo) {
        super("El préstamo del libro '" + prestamo.getLibro().getTitulo() 
              + "' ya fue devuelto anteriormente");
        this.prestamo = prestamo;
    }
    
    public Prestamo getPrestamo() {
        return prestamo;
    }
}
