
package proyectopoobeta;

import java.util.*;

public class UsuarioP2 implements ServicioDeUsuarios{
    private List<Usuario> usuarios = new ArrayList<>();
    
    @Override
    public void RegistrarUsuario(Usuario usuario){
        if(usuario == null){
            System.out.println("No se puede registrar un usuario vacio");
            return;
        }
        
        Usuario existente = this.BuscarUsuarioID(usuario.getID());
        if (existente != null){
            System.out.println("Ya existe un usuario con ID: "+ usuario.getID());
            return;
        }
        usuarios.add(usuario);
    }
    
    @Override
    public Usuario BuscarUsuarioID(String ID){
        
        if(ID == null || ID.trim().isEmpty()){
            return null;
        }
        
        return usuarios.stream()
                .filter(u -> u.getID().equals(ID))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Usuario> getUsuarios() {
        return usuarios;
    }  
}
