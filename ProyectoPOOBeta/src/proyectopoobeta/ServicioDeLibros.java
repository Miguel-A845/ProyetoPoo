package proyectopoobeta;

import java.util.ArrayList;

public interface ServicioDeLibros {
    void RegistrarLibro(Libro libro);
    void AumentarEjemplar(Libro libro);
    void DisminuirEjemplar(Libro libro);
    ArrayList<Libro> buscarporTitulo(String titulo);
    ArrayList<Libro> buscarPorAutor(String Autor);
    
}
