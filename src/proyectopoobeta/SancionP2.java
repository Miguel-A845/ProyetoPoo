package proyectopoobeta;

import java.time.LocalDate;

public class SancionP2 implements ServicioDeSanciones{
    
    @Override
    public void generarSancion(Usuario usuario, int dias){
        LocalDate inicio = LocalDate.now();
        LocalDate fin = inicio.plusDays(dias);
        
        Sancion sancion = new Sancion(usuario, inicio, fin);
        usuario.getSanciones().add(sancion);
        
        System.out.println("Se genero una sancion para: "+usuario.getNombre());
    }
    
    @Override
    public boolean gestionSanciones(Usuario usuario){
        return usuario.getSanciones().stream().anyMatch(s -> s.Activo());
    }    
}
