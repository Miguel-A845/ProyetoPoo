package proyectopoobeta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaArchivos implements ServicioPersistencia {
    
    
    private static final String DIRECTORIO_DATOS = "datos/";
    private static final String ARCHIVO_USUARIOS = DIRECTORIO_DATOS + "usuarios.csv";
    private static final String ARCHIVO_LIBROS = DIRECTORIO_DATOS + "libros.csv";
    private static final String ARCHIVO_PRESTAMOS = DIRECTORIO_DATOS + "prestamos.csv";
    private static final String ARCHIVO_SANCIONES = DIRECTORIO_DATOS + "sanciones.csv";
    
    // Separador CSV
    private static final String SEPARADOR = ";";
    
    // Formato de fechas
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ISO_LOCAL_DATE;
    
    // Referencia al sistema
    private Sistema sistema;
    
    public PersistenciaArchivos(Sistema sistema) {
        this.sistema = sistema;
        crearDirectorioSiNoExiste();
    }
    
    /**
     * Crea el directorio de datos si no existe
     */
    private void crearDirectorioSiNoExiste() {
        File directorio = new File(DIRECTORIO_DATOS);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }
    
    // ==================== GUARDAR USUARIOS ====================
    
    @Override
    public void guardarUsuarios(List<Usuario> usuarios) throws ErrorGuardadoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS))) {
            // Escribir encabezado
            writer.write("ID;NOMBRE;TIPO");
            writer.newLine();
            
            // Escribir cada usuario
            for (Usuario usuario : usuarios) {
                String linea = String.join(SEPARADOR,
                    usuario.getID(),
                    usuario.getNombre(),
                    usuario.getTipo().toString()
                );
                writer.write(linea);
                writer.newLine();
            }
            
            // Guardar sanciones por separado
            guardarSanciones(usuarios);
            
            // Guardar préstamos por separado
            guardarPrestamos(usuarios);
            
        } catch (IOException e) {
            throw new ErrorGuardadoException("No se pudieron guardar los usuarios", e);
        }
    }
    
    /**
     * Guarda las sanciones de todos los usuarios
     */
    private void guardarSanciones(List<Usuario> usuarios) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_SANCIONES))) {
            // Encabezado
            writer.write("ID_USUARIO;FECHA_INICIO;FECHA_FIN;ACTIVO");
            writer.newLine();
            
            for (Usuario usuario : usuarios) {
                for (Sancion sancion : usuario.getSanciones()) {
                    String linea = String.join(SEPARADOR,
                        usuario.getID(),
                        sancion.getFechaInicio().format(FORMATO_FECHA),
                        sancion.getFechaFin().format(FORMATO_FECHA),
                        String.valueOf(sancion.isActivo())
                    );
                    writer.write(linea);
                    writer.newLine();
                }
            }
        }
    }
    
    /**
     * Guarda los préstamos de todos los usuarios
     */
    private void guardarPrestamos(List<Usuario> usuarios) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_PRESTAMOS))) {
            // Encabezado
            writer.write("ID_USUARIO;TITULO_LIBRO;FECHA_PRESTAMO;ESTADO");
            writer.newLine();
            
            for (Usuario usuario : usuarios) {
                for (Prestamo prestamo : usuario.getHistorial()) {
                    String linea = String.join(SEPARADOR,
                        usuario.getID(),
                        prestamo.getLibro().getTitulo(),
                        prestamo.getFecha().format(FORMATO_FECHA),
                        String.valueOf(prestamo.getEstado())
                    );
                    writer.write(linea);
                    writer.newLine();
                }
            }
        }
    }
    
    // ==================== CARGAR USUARIOS ====================
    
    @Override
    public List<Usuario> cargarUsuarios() throws ErrorCargaException {
        List<Usuario> usuarios = new ArrayList<>();
        
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) {
            return usuarios; // Retornar lista vacía si no existe el archivo
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            boolean primerLinea = true;
            
            while ((linea = reader.readLine()) != null) {
                // Saltar encabezado
                if (primerLinea) {
                    primerLinea = false;
                    continue;
                }
                
                String[] partes = linea.split(SEPARADOR);
                if (partes.length != 3) {
                    throw new ErrorCargaException("Formato inválido en usuarios.csv: " + linea, null);
                }
                
                String id = partes[0];
                String nombre = partes[1];
                Tipo tipo = Tipo.valueOf(partes[2]);
                
                Usuario usuario = new Usuario(nombre, id, tipo);
                usuarios.add(usuario);
            }
            
            // Cargar sanciones
            cargarSanciones(usuarios);
            
        } catch (IOException e) {
            throw new ErrorCargaException("No se pudieron cargar los usuarios", e);
        } catch (IllegalArgumentException e) {
            throw new ErrorCargaException("Datos corruptos en usuarios.csv", e);
        }
        
        return usuarios;
    }
    
    /**
     * Carga las sanciones y las asigna a los usuarios
     */
    private void cargarSanciones(List<Usuario> usuarios) throws ErrorCargaException {
        File archivo = new File(ARCHIVO_SANCIONES);
        if (!archivo.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_SANCIONES))) {
            String linea;
            boolean primerLinea = true;
            
            while ((linea = reader.readLine()) != null) {
                if (primerLinea) {
                    primerLinea = false;
                    continue;
                }
                
                String[] partes = linea.split(SEPARADOR);
                if (partes.length != 4) {
                    throw new ErrorCargaException("Formato inválido en sanciones.csv: " + linea, null);
                }
                
                String idUsuario = partes[0];
                LocalDate fechaInicio = LocalDate.parse(partes[1], FORMATO_FECHA);
                LocalDate fechaFin = LocalDate.parse(partes[2], FORMATO_FECHA);
                boolean activo = Boolean.parseBoolean(partes[3]);
                
                // Buscar usuario
                Usuario usuario = buscarUsuarioPorID(usuarios, idUsuario);
                if (usuario != null) {
                    Sancion sancion = new Sancion(usuario, fechaInicio, fechaFin);
                    usuario.getSanciones().add(sancion);
                }
            }
        } catch (IOException e) {
            throw new ErrorCargaException("Error al cargar sanciones", e);
        }
    }
    
    /**
     * Carga los préstamos y los asigna a los usuarios
     */
    private void cargarPrestamos(List<Usuario> usuarios, List<Libro> libros) throws ErrorCargaException {
        File archivo = new File(ARCHIVO_PRESTAMOS);
        if (!archivo.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_PRESTAMOS))) {
            String linea;
            boolean primerLinea = true;
            
            while ((linea = reader.readLine()) != null) {
                if (primerLinea) {
                    primerLinea = false;
                    continue;
                }
                
                String[] partes = linea.split(SEPARADOR);
                if (partes.length != 4) {
                    throw new ErrorCargaException("Formato inválido en prestamos.csv: " + linea, null);
                }
                
                String idUsuario = partes[0];
                String tituloLibro = partes[1];
                LocalDate fechaPrestamo = LocalDate.parse(partes[2], FORMATO_FECHA);
                boolean estado = Boolean.parseBoolean(partes[3]);
                
                // Buscar usuario y libro
                Usuario usuario = buscarUsuarioPorID(usuarios, idUsuario);
                Libro libro = buscarLibroPorTitulo(libros, tituloLibro);
                
                if (usuario != null && libro != null) {
                    Prestamo prestamo = new Prestamo(usuario, libro, fechaPrestamo);
                    prestamo.setEstado(estado);
                    usuario.getHistorial().add(prestamo);
                }
            }
        } catch (IOException e) {
            throw new ErrorCargaException("Error al cargar préstamos", e);
        }
    }
    
    // ==================== GUARDAR LIBROS ====================
    
    @Override
    public void guardarLibros(List<Libro> libros) throws ErrorGuardadoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_LIBROS))) {
            // Encabezado
            writer.write("TITULO;AUTOR;EDITORIAL;AÑO;EJEMPLARES;DISPONIBILIDAD");
            writer.newLine();
            
            for (Libro libro : libros) {
                String linea = String.join(SEPARADOR,
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getEditorial(),
                    String.valueOf(libro.getAño()),
                    String.valueOf(libro.getEjemplares()),
                    String.valueOf(libro.getDisponibilidad())
                );
                writer.write(linea);
                writer.newLine();
            }
            
        } catch (IOException e) {
            throw new ErrorGuardadoException("No se pudieron guardar los libros", e);
        }
    }
    
    // ==================== CARGAR LIBROS ====================
    
    @Override
    public List<Libro> cargarLibros() throws ErrorCargaException {
        List<Libro> libros = new ArrayList<>();
        
        File archivo = new File(ARCHIVO_LIBROS);
        if (!archivo.exists()) {
            return libros;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_LIBROS))) {
            String linea;
            boolean primerLinea = true;
            
            while ((linea = reader.readLine()) != null) {
                if (primerLinea) {
                    primerLinea = false;
                    continue;
                }
                
                String[] partes = linea.split(SEPARADOR);
                if (partes.length != 6) {
                    throw new ErrorCargaException("Formato inválido en libros.csv: " + linea, null);
                }
                
                String titulo = partes[0];
                String autor = partes[1];
                String editorial = partes[2];
                int año = Integer.parseInt(partes[3]);
                int ejemplares = Integer.parseInt(partes[4]);
                boolean disponibilidad = Boolean.parseBoolean(partes[5]);
                
                Libro libro = new Libro(titulo, autor, editorial, año, ejemplares, disponibilidad);
                libros.add(libro);
            }
            
        } catch (IOException e) {
            throw new ErrorCargaException("No se pudieron cargar los libros", e);
        } catch (NumberFormatException e) {
            throw new ErrorCargaException("Datos numéricos inválidos en libros.csv", e);
        }
        
        return libros;
    }
    
    // ==================== GUARDAR/CARGAR TODO ====================
    
    @Override
    public void guardarTodo() throws ErrorGuardadoException {
        try {
            List<Usuario> usuarios = sistema.getUsuarioService().getUsuarios();
            List<Libro> libros = sistema.getLibroService().getLibros();
            
            guardarUsuarios(usuarios);
            guardarLibros(libros);
            
            System.out.println("✓ Datos guardados exitosamente en " + DIRECTORIO_DATOS);
            
        } catch (Exception e) {
            throw new ErrorGuardadoException("Error al guardar todos los datos", e);
        }
    }
    
    @Override
    public void cargarTodo() throws ErrorCargaException {
        try {
            // Primero cargar usuarios y libros
            List<Usuario> usuarios = cargarUsuarios();
            List<Libro> libros = cargarLibros();
            
            // Luego cargar préstamos (necesita ambos)
            cargarPrestamos(usuarios, libros);
            
            // Registrar en el sistema
            for (Usuario usuario : usuarios) {
                sistema.getUsuarioService().RegistrarUsuario(usuario);
            }
            
            for (Libro libro : libros) {
                sistema.getLibroService().RegistrarLibro(libro);
            }
            
            System.out.println("✓ Datos cargados exitosamente desde " + DIRECTORIO_DATOS);
            System.out.println("  → Usuarios cargados: " + usuarios.size());
            System.out.println("  → Libros cargados: " + libros.size());
            
        } catch (Exception e) {
            throw new ErrorCargaException("Error al cargar todos los datos", e);
        }
    }
    
    @Override
    public boolean existenDatos() {
        File archivoUsuarios = new File(ARCHIVO_USUARIOS);
        File archivoLibros = new File(ARCHIVO_LIBROS);
        return archivoUsuarios.exists() || archivoLibros.exists();
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    private Usuario buscarUsuarioPorID(List<Usuario> usuarios, String id) {
        return usuarios.stream()
            .filter(u -> u.getID().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    private Libro buscarLibroPorTitulo(List<Libro> libros, String titulo) {
        return libros.stream()
            .filter(l -> l.getTitulo().equals(titulo))
            .findFirst()
            .orElse(null);
    }
}
