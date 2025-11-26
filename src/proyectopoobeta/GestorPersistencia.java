package proyectopoobeta;

public class GestorPersistencia {
    private ServicioPersistencia servicioPersistencia;
    private Sistema sistema;
    
    public GestorPersistencia(Sistema sistema) {
        this.sistema = sistema;
        this.servicioPersistencia = new PersistenciaArchivos(sistema);
    }
    
    /**
     * Intenta cargar datos existentes al iniciar
     */
    public void cargarDatosIniciales() {
        if (servicioPersistencia.existenDatos()) {
            System.out.println("\n Se encontraron datos previos. Cargando...");
            try {
                servicioPersistencia.cargarTodo();
            } catch (ErrorCargaException e) {
                System.err.println("️ Error al cargar datos: " + e.getMessage());
                System.out.println("Se iniciará con datos en blanco.");
            }
        } else {
            System.out.println("\n No se encontraron datos previos. Iniciando con sistema vacío.");
        }
    }
    
    /**
     * Guarda los datos antes de cerrar
     */
    public void guardarDatosAlSalir() {
        System.out.println("\n Guardando datos...");
        try {
            servicioPersistencia.guardarTodo();
        } catch (ErrorGuardadoException e) {
            System.err.println(" Error al guardar datos: " + e.getMessage());
        }
    }
    
    /**
     * Guarda datos manualmente
     */
    public void guardarManualmente() throws ErrorGuardadoException {
        servicioPersistencia.guardarTodo();
    }
    
    /**
     * Recarga los datos desde archivos
     */
    public void recargarDatos() throws ErrorCargaException {
        servicioPersistencia.cargarTodo();
    }
}
