package proyectopoobeta;

public class ErrorGuardadoException extends Exception {
    public ErrorGuardadoException(String mensaje, Throwable causa) {
        super("Error al guardar datos: " + mensaje, causa);
    }
}
