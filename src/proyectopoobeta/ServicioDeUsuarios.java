package proyectopoobeta;

import java.util.List;

public interface ServicioDeUsuarios {
    public void RegistrarUsuario(Usuario usuario);
    public Usuario BuscarUsuarioID(String ID);
    public List<Usuario> getUsuarios();
}
