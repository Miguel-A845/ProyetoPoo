package proyectopoobeta;

public class LibroNoEncontradoException extends Exception{
    private String tituloLibro;
    
    public LibroNoEncontradoException(String tituloLibro) {
        super("Libro no encontrado: " + tituloLibro);
        this.tituloLibro = tituloLibro;
    }
    
    public String getTituloLibro() {
        return tituloLibro;
    }
}
