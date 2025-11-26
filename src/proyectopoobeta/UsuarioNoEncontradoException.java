package proyectopoobeta;

public class UsuarioNoEncontradoException extends Exception{
    private String idUsuario;
    
    public UsuarioNoEncontradoException(String idUsuario) {
        super("Usuario no encontrado con ID: " + idUsuario);
        this.idUsuario = idUsuario;
    }
    
    public String getIdUsuario() {
        return idUsuario;
    }
}
