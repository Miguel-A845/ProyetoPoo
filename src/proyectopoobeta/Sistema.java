package proyectopoobeta;


public class Sistema {
    private static Sistema instancia;

    private final ServicioDeUsuarios usuarioService;
    private final ServicioDeLibros libroService;
    private final ServicioDeSanciones sancionService;
    private final Servicios prestamoService;

    private Sistema() {
        this.usuarioService = new UsuarioP2();
        this.libroService = new LibroP2();
        this.sancionService = new SancionP2();
        this.prestamoService = new ServicioP2();
    }

    public static Sistema getInstance() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }
    
    public ServicioDeUsuarios getUsuarioService() {
        return usuarioService;
    }

    public ServicioDeLibros getLibroService() {
        return libroService;
    }

    public ServicioDeSanciones getSancionService() {
        return sancionService;
    }

    public Servicios getPrestamoService() {
        return prestamoService;
    }
}