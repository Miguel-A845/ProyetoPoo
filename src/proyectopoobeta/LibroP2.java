
package proyectopoobeta;

import java.util.*;

public class LibroP2 implements ServicioDeLibros{
   private ArrayList<Libro> libros = new ArrayList<>();
   
   @Override
   public void RegistrarLibro(Libro libro){
       libros.add(libro);
   }
   
   @Override
   public void AumentarEjemplar(Libro libro){
       libro.aumentarEjemplar();
   }
   
   @Override
   public void DisminuirEjemplar(Libro libro){
       libro.reducirEjemplar();
   }
   
   @Override
   public List<Libro> buscarporTitulo(String titulo){
       return libros.stream()
               .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
               .toList();
   }
   
   @Override
   public List<Libro> getLibros(){
       return libros;
   }
}
