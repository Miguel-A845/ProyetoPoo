package proyectopoobeta;

import java.util.*;

public interface ServicioPersistencia {
    void guardarUsuarios(List<Usuario> usuarios) throws ErrorGuardadoException;
    List<Usuario> cargarUsuarios() throws ErrorCargaException;
    
    void guardarLibros(List<Libro> libros) throws ErrorGuardadoException;
    List<Libro> cargarLibros() throws ErrorCargaException;
    
    void guardarTodo() throws ErrorGuardadoException;
    void cargarTodo() throws ErrorCargaException;
    
    boolean existenDatos();
    
}
