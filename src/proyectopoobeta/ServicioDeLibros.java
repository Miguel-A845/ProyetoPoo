package proyectopoobeta;

import java.util.*;

public interface ServicioDeLibros {
    public void RegistrarLibro(Libro libro);
    public void AumentarEjemplar(Libro libro);
    public void DisminuirEjemplar(Libro libro);
    public List<Libro> buscarporTitulo(String titulo);
    public List<Libro> getLibros();
    
}
