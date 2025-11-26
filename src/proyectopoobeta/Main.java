package proyectopoobeta;

import proyectopoobeta.PersistenciaArchivos.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    private static Scanner sc = new Scanner(System.in);
    private static Sistema sistema = Sistema.getInstance();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static GestorPersistencia gestorPersistencia = new GestorPersistencia(sistema);

    public static void main(String[] args) {
        System.out.println("----------------------------------------------");
        System.out.println("|   SISTEMA DE GESTION DE BIBLIOTECA ACADEMICA         |");
        System.out.println("|   Universidad - Ingeniería de Sistemas                |");
        System.out.println("-----------------------------------------------");
        
        
        gestorPersistencia.cargarDatosIniciales();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            gestorPersistencia.guardarDatosAlSalir();
        }));
        
        boolean salir = false;
        
        
        while (!salir) {
            mostrarMenuPrincipal();
            String opcion = sc.nextLine().trim();
            
            try {
                switch (opcion) {
                    case "1" -> gestionarUsuarios();
                    case "2" -> gestionarLibros();
                    case "3" -> gestionarPrestamos();
                    case "4" -> gestionarDevoluciones();
                    case "5" -> buscarLibros();
                    case "6" -> consultarInventario();
                    case "7" -> consultarHistorial();
                    case "8" -> guardarDatosManualmente();
                    case "9" -> recargarDatos();
                    case "0" -> {
                        System.out.println("Gracias por usar el sistema. ¡Hasta pronto!");
                        gestorPersistencia.guardarDatosAlSalir();
                        salir = true;
                    }
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (UsuarioNoEncontradoException e){
                System.out.println("ERROR: "+ e.getMessage());
            } catch (UsuarioDuplicadoException e) {
                System.err.println("\n❌ Error: " + e.getMessage());
            } catch (LibroNoEncontradoException e) {
                System.err.println("\n❌ Error: " + e.getMessage());
            } catch (PrestamoYaDevueltoException e) {
                System.err.println("\n❌ Error: " + e.getMessage());
            } catch (ErrorGuardadoException e) {
                System.err.println("\n❌ Error al guardar: " + e.getMessage());
            } catch (ErrorCargaException e) {
                System.err.println("\n❌ Error al cargar: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("\n❌ Error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
                
            if (!salir) {
                System.out.println("\nPresione ENTER para continuar...");
                sc.nextLine();
            }
        }
        
        sc.close();
    }
    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("                    MENU PRINCIPAL");
        System.out.println("-".repeat(60));
        System.out.println("  1. Gestion de Usuarios");
        System.out.println("  2. Gestion de Libros");
        System.out.println("  3. Realizar Prestamo");
        System.out.println("  4. Procesar Devolucion");
        System.out.println("  5. Buscar Libros");
        System.out.println("  6. Consultar Inventario");
        System.out.println("  7. Consultar Historial de Usuario");
        System.out.println("  8. Guardar Datos Manualmente");
        System.out.println("  9. Recargar Datos desde Archivos");
        System.out.println("  0. Salir");
        System.out.println("-".repeat(60));
        System.out.print("Seleccione una opcion: ");
    }

    private static void gestionarUsuarios() throws UsuarioDuplicadoException {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("               GESTION DE USUARIOS");
        System.out.println("-".repeat(60));
        System.out.println("  1. Registrar Usuario");
        System.out.println("  2. Listar Usuarios");
        System.out.println("  0. Volver");
        System.out.print("Seleccione una opcion: ");
        
        String opcion = sc.nextLine().trim();
        
        switch (opcion) {
            case "1" -> registrarUsuario();
            case "2" -> listarUsuarios();
            case "0" -> {}
            default -> System.out.println("\nOpción inválida.");
        }
    }
    
    private static void registrarUsuario() throws UsuarioDuplicadoException {
        System.out.println("\n[ REGISTRAR NUEVO USUARIO ]");
        
        System.out.print("Ingrese nombre completo: ");
        String nombre = sc.nextLine().trim();
        
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        
        System.out.print("Ingrese ID: ");
        String id = sc.nextLine().trim();
        
        if (id.isEmpty()) {
            System.out.println("El ID no puede estar vacío.");
            return;
        }
        
        // Verificar si el usuario ya existe
        Usuario usuarioExistente = sistema.getUsuarioService().BuscarUsuarioID(id);
        if (usuarioExistente != null) {
            throw new UsuarioDuplicadoException(id);
        }
        
        System.out.print("Tipo de usuario (1 = Estudiante, 2 = Docente): ");
        String tipoStr = sc.nextLine().trim();
        
        Tipo tipo;
        if (tipoStr.equals("1")) {
            tipo = Tipo.Estudiante;
        } else if (tipoStr.equals("2")) {
            tipo = Tipo.Docente;
        } else {
            System.out.println("Tipo invalido. Use 1 o 2.");
            return;
        }
        
        sistema.getUsuarioService().RegistrarUsuario(new Usuario(nombre, id, tipo));
        System.out.println("\nUsuario registrado exitosamente.");
        System.out.println("   Nombre: " + nombre);
        System.out.println("   ID: " + id);
        System.out.println("   Tipo: " + tipo);
    }
    
    private static void listarUsuarios() {
        System.out.println("\n[ LISTA DE USUARIOS REGISTRADOS ]");
        
        List<Usuario> usuarios = sistema.getUsuarioService().getUsuarios();

        
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados en el sistema.");
            return;
        }
        
        System.out.println("\n" + "-".repeat(80));
        System.out.printf("%-30s %-15s %-15s %-10s%n", "NOMBRE", "ID", "TIPO", "SANCIONES");
        System.out.println("-".repeat(80));
        
        for (Usuario u : usuarios) {
            int numSanciones = u.getSanciones().size();
            boolean tieneSancionActiva = u.getSanciones().stream()
                    .anyMatch(s -> s.Activo());
            
            String estadoSancion = tieneSancionActiva ? "ACTIVA" : 
                                   (numSanciones > 0 ? "INACTIVA" : "NINGUNA");
            
            System.out.printf("%-30s %-15s %-15s %-10s%n", 
                    u.getNombre(), 
                    u.getID(), 
                    u.getTipo(), 
                    estadoSancion);
        }
        
        System.out.println("-".repeat(80));
        System.out.println("Total de usuarios: " + usuarios.size());
    }
 
    private static void gestionarLibros() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("               GESTION DE LIBROS");
        System.out.println("-".repeat(60));
        System.out.println("  1. Registrar Libro");
        System.out.println("  2. Listar Todos los Libros");
        System.out.println("  0. Volver");
        System.out.print("Seleccione una opcion: ");
        
        String opcion = sc.nextLine().trim();
        
        switch (opcion) {
            case "1" -> registrarLibro();
            case "2" -> listarLibros();
            case "0" -> {}
            default -> System.out.println("\nOpción inválida.");
        }
    }
    
    private static void registrarLibro() {
        System.out.println("\n[ REGISTRAR NUEVO LIBRO ]");
        
        System.out.print("Ingrese titulo del libro: ");
        String titulo = sc.nextLine().trim();
        
        if (titulo.isEmpty()) {
            System.out.println("El titulo no puede estar vacio.");
            return;
        }
        
        System.out.print("Ingrese autor: ");
        String autor = sc.nextLine().trim();
        
        if (autor.isEmpty()){
            System.out.println("El autor no puede estar vacio");
            return;
        }
        
        System.out.print("Ingrese editorial: ");
        String editorial = sc.nextLine().trim();
        
        if (editorial.isEmpty()){
            System.out.println("La editorial no puede estar vacia");
            return;
        }
        
        System.out.print("Ingrese año de publicacion: ");
        int anio;
        
        try {
            anio = Integer.parseInt(sc.nextLine().trim());
            if (anio < 1000 || anio > LocalDate.now().getYear()) {
                System.out.println("Año inválido.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Año invalido. Debe ser un nemero.");
            return;
        }
        
        System.out.print("Ingrese número de ejemplares: ");
        int ejemplares;
        try {
            ejemplares = Integer.parseInt(sc.nextLine().trim());
            if (ejemplares < 1) {
                System.out.println("Debe haber al menos 1 ejemplar.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Numero de ejemplares inválido.");
            return;
        }
        
        Libro libro = new Libro(titulo, autor, editorial, anio, ejemplares, true);
        sistema.getLibroService().RegistrarLibro(libro);
        
        System.out.println("\n Libro registrado exitosamente.");
        System.out.println("   Titulo: " + titulo);
        System.out.println("   Autor: " + autor);
        System.out.println("   Editorial: " + editorial);
        System.out.println("   Anio: " + anio);
        System.out.println("   Ejemplares: " + ejemplares);
    }
    
    private static void listarLibros() {
        System.out.println("\n[ CATALOGO COMPLETO DE LIBROS ]");
        
        List<Libro> libros = sistema.getLibroService().getLibros();
        
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en el sistema.");
            return;
        }
        
        System.out.println("\n" + "-".repeat(120));
        System.out.printf("%-40s %-25s %-20s %-8s %-12s%n", 
                "TÍTULO", "AUTOR", "EDITORIAL", "AÑO", "DISPONIBLES");
        System.out.println("-".repeat(120));
        
        for (Libro libro : libros) {
            System.out.printf("%-40s %-25s %-20s %-8d %-12d%n",
                    truncar(libro.getTitulo(), 40),
                    truncar(libro.getAutor(), 25),
                    truncar(libro.getEditorial(), 20),
                    libro.getAño(),
                    libro.getEjemplares());
        }
        
        System.out.println("-".repeat(120));
        System.out.println("Total de libros en catalogo: " + libros.size());
    }
 
    private static void gestionarPrestamos() throws UsuarioNoEncontradoException, LibroNoEncontradoException {
        System.out.println("\n[ REALIZAR PRESTAMO ]");
        
        System.out.print("Ingrese ID del usuario: ");
        String idUsuario = sc.nextLine().trim();
        
        Usuario usuario = sistema.getUsuarioService().BuscarUsuarioID(idUsuario);
        
        if (usuario == null) {
            throw new UsuarioNoEncontradoException(idUsuario);
        }
        
        if (sistema.getSancionService().gestionSanciones(usuario)) {
            System.out.println(" El usuario " + usuario.getNombre() + 
                             " tiene una sancion activa y no puede realizar préstamos.");
            return;
        }
        
        System.out.print("Ingrese título del libro: ");
        String titulo = sc.nextLine().trim();
        if (titulo.isEmpty()) {
            System.out.println("El titulo no puede estar vacio.");
            return;
        }
        
        List<Libro> librosEncontrados = sistema.getLibroService().buscarporTitulo(titulo);
        
        if (librosEncontrados.isEmpty()) {
            throw new LibroNoEncontradoException(titulo);

        }
        
        Libro libro = librosEncontrados.get(0);
        
        if (!libro.disponibilidad()) {
            System.out.println(" No hay ejemplares disponibles de: " + libro.getTitulo());
            System.out.println("  Ejemplares totales: " + libro.getEjemplares());
            return;
        }
        
        Prestamo prestamo = sistema.getPrestamoService().prestarLibro(usuario, libro);
        
        if (prestamo == null) {
            System.out.println(" No se pudo realizar el prestamo.");
            return;
        }
        
        System.out.println("\n Prestamo realizado exitosamente.");
        System.out.println("   Usuario: " + usuario.getNombre() + " (" + usuario.getID() + ")");
        System.out.println("   Libro: " + libro.getTitulo());
        System.out.println("   Fecha de prestamo: " + prestamo.getFecha().format(formatter));
        System.out.println("   Ejemplares restantes: " + libro.getEjemplares());
    }

    private static void gestionarDevoluciones() throws UsuarioNoEncontradoException, PrestamoYaDevueltoException{
        System.out.println("\n[ PROCESAR DEVOLUCION ]");
        
        System.out.print("Ingrese ID del usuario: ");
        String idUsuario = sc.nextLine().trim();
        
        Usuario usuario = sistema.getUsuarioService().BuscarUsuarioID(idUsuario);
        
        if (usuario == null) {
            throw new UsuarioNoEncontradoException(idUsuario);
        }
        
        List<Prestamo> historial = usuario.getHistorial();
        
        if (historial.isEmpty()) {
            System.out.println(" El usuario " + usuario.getNombre() + 
                             " no tiene prestamos registrados.");
            return;
        }
        
        // Mostrar préstamos activos
        List<Prestamo> prestamosActivos = historial.stream()
                .filter(p -> p.getEstado())
                .toList();
        
        if (prestamosActivos.isEmpty()) {
            System.out.println(" El usuario no tiene prestamos activos para devolver.");
            return;
        }
        
        System.out.println("\n[ PRESTAMOS ACTIVOS ]");
        System.out.println("-".repeat(100));
        System.out.printf("%-5s %-40s %-15s %-15s%n", 
                "#", "TITULO", "FECHA PRESTAMO", "ESTADO");
        System.out.println("-".repeat(100));
        
        for (int i = 0; i < prestamosActivos.size(); i++) {
            Prestamo p = prestamosActivos.get(i);
            LocalDate fechaDevolucion = p.getFecha().plusDays(15); // Asumiendo 15 días
            boolean vencido = LocalDate.now().isAfter(fechaDevolucion);
            
            System.out.printf("%-5d %-40s %-15s %-15s%n",
                    (i + 1),
                    truncar(p.getLibro().getTitulo(), 40),
                    p.getFecha().format(formatter),
                    vencido ? "VENCIDO" : "VIGENTE");
        }
        System.out.println("-".repeat(100));
        
        System.out.print("\nIngrese numero de prestamo a devolver: ");
        int numPrestamo;
        try {
            numPrestamo = Integer.parseInt(sc.nextLine().trim());
            if (numPrestamo < 1 || numPrestamo > prestamosActivos.size()) {
                System.out.println(" Numero de prestamo invalido.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(" Entrada invalida.");
            return;
        }
        
        Prestamo prestamoSeleccionado = prestamosActivos.get(numPrestamo - 1);
        if(!prestamoSeleccionado.getEstado()){
            throw new PrestamoYaDevueltoException(prestamoSeleccionado);
        }
        
        Devolucion devolucion = sistema.getPrestamoService().devolverLibro(prestamoSeleccionado);
        
        if (devolucion == null) {
            System.out.println(" No se pudo procesar la devolucion.");
            return;
        }
        
        System.out.println("\n Devolucion procesada exitosamente.");
        System.out.println("   Libro: " + prestamoSeleccionado.getLibro().getTitulo());
        System.out.println("   Fecha de devolucion: " + devolucion.getFechaDev().format(formatter));
        System.out.println("   Ejemplares disponibles ahora: " + 
                         prestamoSeleccionado.getLibro().getEjemplares());
        
        // Generar sanción si está vencido
        LocalDate fechaLimite = prestamoSeleccionado.getFecha().plusDays(15);
        if (LocalDate.now().isAfter(fechaLimite)) {
            long diasRetraso = LocalDate.now().toEpochDay() - fechaLimite.toEpochDay();
            LocalDate finSancion = LocalDate.now().plusDays(diasRetraso);
            
            Sancion sancion = new Sancion(usuario, LocalDate.now(), finSancion);
            usuario.getSanciones().add(sancion);
            
            System.out.println("\n SANCIÓN APLICADA POR RETRASO");
            System.out.println("   Días de retraso: " + diasRetraso);
            System.out.println("   Fecha fin de sanción: " + finSancion.format(formatter));
        }
    }

    private static void buscarLibros() {
        System.out.println("\n[ BUSQUEDA AVANZADA DE LIBROS ]");
        System.out.print("Ingrese termino de busqueda (titulo): ");
        String termino = sc.nextLine().trim();
        
        if (termino.isEmpty()) {
            System.out.println(" Debe ingresar un termino de busqueda.");
            return;
        }
        
        List<Libro> resultados = sistema.getLibroService().buscarporTitulo(termino);
        
        if (resultados.isEmpty()) {
            System.out.println("\n No se encontraron libros que coincidan con: " + termino);
            return;
        }
        
        System.out.println("\n[ RESULTADOS DE LA BUSQUEDA ]");
        System.out.println("-".repeat(120));
        System.out.printf("%-40s %-25s %-20s %-8s %-12s%n", 
                "TÍTULO", "AUTOR", "EDITORIAL", "AÑO", "DISPONIBLES");
        System.out.println("-".repeat(120));
        
        for (Libro libro : resultados) {
            System.out.printf("%-40s %-25s %-20s %-8d %-12d%n",
                    truncar(libro.getTitulo(), 40),
                    truncar(libro.getAutor(), 25),
                    truncar(libro.getEditorial(), 20),
                    libro.getAño(),
                    libro.getEjemplares());
        }
        
        System.out.println("-".repeat(120));
        System.out.println("Resultados encontrados: " + resultados.size());
    }
    
    private static void consultarInventario() {
        System.out.println("\n[ RESUMEN DE INVENTARIO ]");
        
        List<Libro> libros = sistema.getLibroService().getLibros();
        
        if (libros.isEmpty()) {
            System.out.println("No hay libros en el inventario.");
            return;
        }
        
        int totalLibros = libros.size();
        int totalEjemplares = libros.stream().mapToInt(Libro::getEjemplares).sum();
        long librosDisponibles = libros.stream().filter(Libro::disponibilidad).count();
        long librosSinStock = libros.stream().filter(l -> !l.disponibilidad()).count();
        
        System.out.println("\n-----------------------------------");
        System.out.println("|     ESTADÍSTICAS DEL INVENTARIO        |");
        System.out.println("|-----------------------------------|");
        System.out.printf("| Títulos registrados:        %-10d |%n", totalLibros);
        System.out.printf("| Total de ejemplares:        %-10d |%n", totalEjemplares);
        System.out.printf("| Libros con disponibilidad:  %-10d |%n", librosDisponibles);
        System.out.printf("| Libros sin stock:           %-10d |%n", librosSinStock);
        System.out.println("------------------------------------");
    }

    private static void consultarHistorial() {
        System.out.println("\n[ CONSULTAR HISTORIAL DE USUARIO ]");
        
        System.out.print("Ingrese ID del usuario: ");
        String idUsuario = sc.nextLine().trim();
        
        Usuario usuario = sistema.getUsuarioService().BuscarUsuarioID(idUsuario);
        
        if (usuario == null) {
            System.out.println("✗ Usuario no encontrado con ID: " + idUsuario);
            return;
        }
        
        System.out.println("\n-----------------------------------------------");
        System.out.println("|              INFORMACIÓN DEL USUARIO                   |");
        System.out.println("|----------------------------------------------|");
        System.out.printf("| Nombre:  %-45s |%n", usuario.getNombre());
        System.out.printf("| ID:      %-45s |%n", usuario.getID());
        System.out.printf("| Tipo:    %-45s |%n", usuario.getTipo());
        System.out.println("------------------------------------------------");
        
        List<Prestamo> historial = usuario.getHistorial();
        
        if (historial.isEmpty()) {
            System.out.println("\nEste usuario no tiene préstamos registrados.");
            return;
        }
        
        System.out.println("\n[ HISTORIAL DE PRÉSTAMOS ]");
        System.out.println("-".repeat(100));
        System.out.printf("%-40s %-15s %-15s %-15s%n", 
                "TITULO", "FECHA PRESTAMO", "ESTADO", "OBSERVACION");
        System.out.println("-".repeat(100));
        
        for (Prestamo p : historial) {
            String estado = p.getEstado() ? "ACTIVO" : "DEVUELTO";
            String observacion = "";
            
            if (p.getEstado()) {
                LocalDate fechaLimite = p.getFecha().plusDays(15);
                if (LocalDate.now().isAfter(fechaLimite)) {
                    observacion = "VENCIDO";
                } else {
                    long diasRestantes = fechaLimite.toEpochDay() - LocalDate.now().toEpochDay();
                    observacion = diasRestantes + " días restantes";
                }
            }
            
            System.out.printf("%-40s %-15s %-15s %-15s%n",
                    truncar(p.getLibro().getTitulo(), 40),
                    p.getFecha().format(formatter),
                    estado,
                    observacion);
        }
        
        System.out.println("-".repeat(100));
        System.out.println("Total de préstamos: " + historial.size());
        
        // Mostrar sanciones
        List<Sancion> sanciones = usuario.getSanciones();
        if (!sanciones.isEmpty()) {
            System.out.println("\n[ SANCIONES ]");
            System.out.println("-".repeat(80));
            System.out.printf("%-15s %-15s %-15s%n", "FECHA INICIO", "FECHA FIN", "ESTADO");
            System.out.println("-".repeat(80));
            
            for (Sancion s : sanciones) {
                String estadoSancion = s.Activo() ? "ACTIVA" : "FINALIZADA";
                System.out.printf("%-15s %-15s %-15s%n",
                        s.getFechaInicio().format(formatter),
                        s.getFechaFin().format(formatter),
                        estadoSancion);
            }
            System.out.println("-".repeat(80));
        }
    }
    
    private static void guardarDatosManualmente() throws ErrorGuardadoException {
        System.out.println("\n Guardando datos...");
        gestorPersistencia.guardarManualmente();
        System.out.println("Datos guardados exitosamente.");
    }
    
    private static void recargarDatos() throws ErrorCargaException {
        System.out.println("\n Recargando datos desde archivos...");
        System.out.print("  Esto sobrescribirá los datos actuales. ¿Continuar? (s/n): ");
        String respuesta = sc.nextLine().trim().toLowerCase();
        
        if (respuesta.equals("s")) {
            gestorPersistencia.recargarDatos();
            System.out.println(" Datos recargados exitosamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    

    private static String truncar(String texto, int longitud) {
        if (texto == null) return "";
        if (texto.length() <= longitud) return texto;
        return texto.substring(0, longitud - 3) + "...";
    }     
}