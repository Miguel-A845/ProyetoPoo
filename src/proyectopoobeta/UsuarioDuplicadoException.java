package proyectopoobeta;

public class UsuarioDuplicadoException extends Exception {
    private String idUsuario;
    
    public UsuarioDuplicadoException(String idUsuario) {
        super("Ya existe un usuario registrado con el ID: " + idUsuario);
        this.idUsuario = idUsuario;
    }
    
    public String getIdUsuario() {
        return idUsuario;
    }
}
