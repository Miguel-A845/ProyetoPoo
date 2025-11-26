
package proyectopoobeta;

public class ErrorCargaException extends Exception{
    public ErrorCargaException(String mensaje, Throwable causa) {
        super("Error al cargar datos: " + mensaje, causa);
    }
}
