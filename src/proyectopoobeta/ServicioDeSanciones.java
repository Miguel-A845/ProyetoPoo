
package proyectopoobeta;

public interface ServicioDeSanciones {
    public void generarSancion(Usuario usuario, int dias);
    public boolean gestionSanciones(Usuario usuario);
}
