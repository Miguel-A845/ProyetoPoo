1- Una biblioteca académica con miles de títulos no cuenta con un sistema informático para gestionar préstamos, lo cual provoca pérdida de libros, mal seguimiento de devoluciones y falta de control sobre sanciones.
Este sistema debe registrar libros, usuarios y gestionar el proceso completo de préstamo y devolución. Además, debe controlar sanciones automáticas y ofrecer búsqueda avanzada de libros.
El sistema debe detectar préstamos vencidos y libros no disponibles. Los préstamos y usuarios deben persistirse para asegurar trazabilidad.

 2. Se implemento un sistema que permite
   A) Regitrar libros y usuarios. B) Gestionar el proceso Completo de prestamo y devolucion.
   C) Controlar sanciones automaticas por retrasos. D) Ofrecer una busqueda avanzada de libros.
   E) Consultar el historial de usuarios. F) Generara estadisticas del Inventario

 REQUISITOS FUNCIONALES IMPLEMENTADOS

 1: Gestión de Usuarios
- Registrar usuario: Crear nuevos usuarios con nombre, ID y tipo (Estudiante/Docente)
- Listar usuarios: Ver todos los usuarios registrados con su estado de sanciones
- Buscar usuario: Localizar usuarios por ID único
- Validación: No permite IDs duplicados

 2: Gestión de Libros
- Registrar libro: Agregar libros con título, autor, editorial, año y ejemplares disponibles
- Listar catálogo: Visualizar todos los libros con disponibilidad
- Búsqueda avanzada: Buscar libros por título

 3: Gestión de Préstamos
- Realizar préstamo: Asignar libro a usuario si cumple estas condiciones:
  - Usuario sin sanciones activas
  - Libro con ejemplares disponibles
- Reducción automática: Disminuye ejemplares al prestar
- Registro en historial: Guarda el préstamo en el historial del usuario
- Período de préstamo: 15 días desde la fecha de préstamo

 4: Gestión de Devoluciones
- Procesar devolución: Registrar devolución de libros prestados
- Aumento automático: Incrementa ejemplares disponibles
- Detección de retrasos: Identifica préstamos vencidos automáticamente
- Aplicación de sanciones: Genera sanción si hay retraso

 5: Sistema de Sanciones
- Sanción automática: Se genera al devolver tarde
- Verificación de estado: Actualización automática de sanciones activas/inactivas
- Restricción de préstamos: Usuario sancionado no puede pedir préstamos

 6: Consultas y Reportes
- Historial de usuario: Ver todos los préstamos
- Estado de préstamos: Indica si están vigentes o vencidos
- Estadísticas de inventario:
  - Total de títulos registrados
  - Total de ejemplares
  - Libros con disponibilidad
  - Libros sin stock
- Días restantes: Muestra cuántos días faltan para vencimiento

 7: Validaciones Implementadas
- Campos no vacíos (nombre, título, ID)
- Formato de números (año, ejemplares)
- Cantidad mínima de ejemplares (≥ 1)
- Usuarios/libros existentes antes de operar
- Disponibilidad antes de prestar
- Préstamos activos antes de devolver


INSTRUCCIONES DE USO

 1. Registrar un Usuario
   
1. Gestión de Usuarios → 1. Registrar Usuario
Ingrese nombre completo: Juan Pérez
Ingrese ID/Cédula: 12345
Tipo de usuario (1=Estudiante, 2=Docente): 1

Usuario registrado exitosamente.

 2. Registrar un Libro

2. Gestión de Libros → 1. Registrar Libro
Ingrese título del libro: Cien años de soledad
Ingrese autor: Gabriel García Márquez
Ingrese editorial: Sudamericana
Ingrese año de publicación: 1967
Ingrese número de ejemplares: 5

Libro registrado exitosamente.


 3. Realizar un Préstamo

3. Realizar Préstamo
Ingrese ID del usuario: 12345
Ingrese título del libro: Cien años de soledad

 Préstamo realizado exitosamente.
   Usuario: Juan Pérez (12345)
   Libro: Cien años de soledad
   Fecha de préstamo: 23/11/2025
   Ejemplares restantes: 4


 4. Procesar Devolución

4. Procesar Devolución
Ingrese ID del usuario: 12345

[ PRÉSTAMOS ACTIVOS ]
%     TÍTULO                           FECHA PRÉSTAMO  ESTADO
1     Cien años de soledad            08/11/2025      VENCIDO

Ingrese número de préstamo a devolver: 1

Devolución procesada exitosamente.

SANCIÓN APLICADA POR RETRASO
   Días de retraso: 5
   Fecha fin de sanción: 28/11/2025


  DIAGRAMA DE CLASES

@startuml
enum Tipo {
  Estudiante
  Docente
}

class Usuario {
  - String nombre
  - String ID
  - Tipo tipo
  - List<Prestamo> historial
  - List<Sancion> sanciones
  + List<Sancion> getSanciones()
  + List<Prestamo> getHistorial()
}

class Libro {
  - String titulo
  - String autor
  - String editorial
  - int año
  - int ejemplares
  - boolean disponibilidad
  + Libro(String, String, String, int, int, boolean)
  + boolean disponibilidad()
  + void reducirEjemplar()
  + void aumentarEjemplar()
 
}

class Prestamo {
  - Usuario usuario
  - Libro libro
  - LocalDate fechaPrestamo
  - boolean estado
  + String toString()
}

class Devolucion {
  - LocalDate fechaDevolucion
  - Prestamo prestamo
}

class Sancion {
  - Usuario usuario
  - LocalDate fechaInicio
  - LocalDate fechaFin
  - boolean activo
  + boolean Activo()
}

interface ServicioDeLibros {
  + void RegistrarLibro(Libro)
  + void AumentarEjemplar(Libro)
  + void DisminuirEjemplar(Libro)
  + List<Libro> buscarporTitulo(String)
  + List<Libro> getLibros()
}

interface ServicioDeSanciones {
  + void generarSancion(Usuario, int)
  + boolean gestionSanciones(Usuario)
}

interface ServicioDeUsuarios {
  + void RegistrarUsuario(Usuario)
  + Usuario BuscarUsuarioID(String)
  + List<Usuario> getUsuarios()
}

interface Servicios {
  + Prestamo prestarLibro(Usuario, Libro)
  + Devolucion devolverLibro(Prestamo)
}

interface ServicioPersistencia {
  + void guardarUsuarios(List<Usuario>)
  + List<Usuario> cargarUsuarios()
  + void guardarLibros(List<Libro>)
  + List<Libro> cargarLibros()
  + void guardarTodo()
  + void cargarTodo()
  + boolean existenDatos()
}

class LibroP2 {
  - ArrayList<Libro> libros
  + void RegistrarLibro(Libro)
  + void AumentarEjemplar(Libro)
  + void DisminuirEjemplar(Libro)
  + List<Libro> buscarporTitulo(String)
  + List<Libro> getLibros()
}

class SancionP2 {
  + void generarSancion(Usuario, int)
  + boolean gestionSanciones(Usuario)
}

class UsuarioP2 {
  - List<Usuario> usuarios
  + void RegistrarUsuario(Usuario)
  + Usuario BuscarUsuarioID(String)
  + List<Usuario> getUsuarios()
}

class ServicioP2 {
  + Prestamo prestarLibro(Usuario, Libro)
  + Devolucion devolverLibro(Prestamo)
}

class PersistenciaArchivos {
  - {static} final String DIRECTORIO_DATOS
  - {static} final String ARCHIVO_USUARIOS
  - {static} final String ARCHIVO_LIBROS
  - {static} final String ARCHIVO_PRESTAMOS
  - {static} final String ARCHIVO_SANCIONES
  - {static} final String SEPARADOR
  - {static} final DateTimeFormatter FORMATO_FECHA
  - Sistema sistema
  + PersistenciaArchivos(Sistema)
  - void crearDirectorioSiNoExiste()
  + void guardarUsuarios(List<Usuario>)
  - void guardarSanciones(List<Usuario>)
  - void guardarPrestamos(List<Usuario>)
  + List<Usuario> cargarUsuarios()
  - void cargarSanciones(List<Usuario>)
  - void cargarPrestamos(List<Usuario>, List<Libro>)
  + void guardarLibros(List<Libro>)
  + List<Libro> cargarLibros()
  + void guardarTodo()
  + void cargarTodo()
  + boolean existenDatos()
  - Usuario buscarUsuarioPorID(List<Usuario>, String)
  - Libro buscarLibroPorTitulo(List<Libro>, String)
}

class Sistema {
  - {static} Sistema instancia
  - final ServicioDeUsuarios usuarioService
  - final ServicioDeLibros libroService
  - final ServicioDeSanciones sancionService
  - final Servicios prestamoService
  - Sistema()
  + {static} Sistema getInstance()
  + ServicioDeUsuarios getUsuarioService()
  + ServicioDeLibros getLibroService()
  + ServicioDeSanciones getSancionService()
  + Servicios getPrestamoService()
}

class GestorPersistencia {
  - ServicioPersistencia servicioPersistencia
  - Sistema sistema
  + GestorPersistencia(Sistema)
  + void cargarDatosIniciales()
  + void guardarDatosAlSalir()
  + void guardarManualmente()
  + void recargarDatos()
}

class UsuarioNoEncontradoException {
  - String idUsuario
  + UsuarioNoEncontradoException(String)
  + String getIdUsuario()
}

class UsuarioDuplicadoException {
  - String idUsuario
  + UsuarioDuplicadoException(String)
  + String getIdUsuario()
}

class LibroNoEncontradoException {
  - String tituloLibro
  + LibroNoEncontradoException(String)
  + String getTituloLibro()
}

class PrestamoYaDevueltoException {
  - Prestamo prestamo
  + PrestamoYaDevueltoException(Prestamo)
  + Prestamo getPrestamo()
}

class ErrorGuardadoException {
  + ErrorGuardadoException(String, Throwable)
}

class ErrorCargaException {
  + ErrorCargaException(String, Throwable)
}

class LibroP2 implements ServicioDeLibros
class UsuarioP2 implements ServicioDeUsuarios
class ServicioP2 implements Servicios
class SancionP2 implements ServicioDeSanciones
class PersistenciaArchivos implements ServicioPersistencia

Sistema --> ServicioDeLibros
Sistema --> ServicioDeUsuarios
Sistema --> Servicios
Sistema --> ServicioDeSanciones

UsuarioP2 --> Usuario
LibroP2 --> Libro
ServicioP2 --> Prestamo
ServicioP2 --> Devolucion
SancionP2 --> Sancion
Prestamo -->  Usuario
Prestamo -->  Libro 
Devolucion -->  Prestamo
Sancion -->  Usuario 
Usuario --> Tipo 
LibroP2 o--  Libro
UsuarioP2 o--  Usuario
Usuario  *--  Prestamo
Usuario  *--  Sancion

UsuarioNoEncontradoException --> Exception
UsuarioDuplicadoException --> Exception
LibroNoEncontradoException --> Exception
ErrorCargaException --> Exception
PrestamoYaDevueltoException --> Exception
ErrorGuardadoException --> Exception

GestorPersistencia --> ServicioPersistencia
GestorPersistencia --> Sistema
PersistenciaArchivos --> Sistema

 UML FINAL
 <img width="2800" height="2227" alt="image" src="https://github.com/user-attachments/assets/19b64838-d990-4eb3-a5a3-4e4e5bfdee6e" />

@enduml

  CASOS DE USO

  
CU-01 – Registrar Usuario

Identificador: CU-01

Descripción: Permite al bibliotecario registrar un nuevo usuario (estudiante o docente) en el sistema.

Actor Primario: Bibliotecario

Actor Secundario: —

Precondiciones:

 1 El sistema debe estar en ejecución.
 
Flujo Principal:

 l bibliotecario selecciona la opción “Registrar Usuario”.
 
 2 sistema solicita nombre, ID y tipo de usuario.
 
 3 bibliotecario ingresa los datos.
 
 4 sistema verifica que el ID no exista previamente.
 
 5 sistema registra el usuario.
 
 6 sistema confirma el registro exitoso.

Postcondiciones:

   El usuario queda almacenado en la lista de usuarios del sistema.

Flujos Alternativos: ID duplicado:

 1 En el paso 4, si el ID ya existe → el sistema muestra un mensaje de error.
 
 2 El usuario no es registrado.

 

CU-02 – Registrar Libro

Identificador: CU-02

Descripción:

Registra un libro nuevo en el inventario de la biblioteca.

Actor Primario: Bibliotecario

Precondiciones:

 1 El bibliotecario debe proporcionar todos los datos del libro.

Flujo Principal:

 l bibliotecario selecciona “Registrar Libro”.
 
 2 sistema solicita título, autor, editorial, año y número de ejemplares.
 
 3 bibliotecario ingresa los datos.
 
 4 sistema registra el libro en el inventario.
 
 5 sistema confirma el registro.

Postcondiciones:

 1 El libro queda disponible en el inventario.

Flujos Alternativos: Datos incompletos:

 1 Si no se ingresan todos los campos, el sistema muestra un error
 
 2 No se registra el libro.



CU-03 – Buscar Libro (Búsqueda Avanzada)

Identificador: CU-03

Descripción: Permite buscar libros en el inventario por título.

Actor Primario: Bibliotecario

Precondiciones:

 1 Debe existir al menos un libro en el sistema.

Flujo Principal:

 1 El bibliotecario selecciona “Buscar Libro”.
 
 2 Ingresa un texto de búsqueda.
 
 3 El sistema filtra los libros cuyo título coincida utilizando expresión “contains” o “ignoreCase”.
 
 4 sistema muestra los resultados.

Postcondiciones:

 1 Se muestra una lista filtrada de libros.

Flujos Alternativos: No hay coincidencias

 Si ningún libro coincide con el texto buscado → se muestra “No se encontraron resultados”.



CU-04 – Consultar Ejemplares

Identificador: CU-04

Descripción: Permite conocer cuántos ejemplares disponibles tiene un libro.

Actor Primario: Bibliotecario

Precondiciones:

 1 Debe existir el libro buscado.
 
 2 Flujo Principal:
 
 3 El bibliotecario ingresa el título del libro.
 
 4 El sistema busca el libro.
 
 5 El sistema muestra: título, ejemplares disponibles, disponibilidad.

Postcondiciones:

 1 Ningún dato se modifica.

Flujos Alternativos: Libro no encontrado.

 1 Si el título no coincide con ningún libro, se muestra mensaje “No existe el libro”.



CU-05 – Prestar Libro

Identificador: CU-05

Descripción: Permite prestar un libro a un usuario registrado.

Actor Primario: Bibliotecario

Actor Secundario: Usuario (Estudiante/Docente)

Precondiciones:

 1 El usuario debe existir.
 
 2 El libro debe estar registrado.
 
 3 El libro debe tener ejemplares disponibles.
 
 4 El usuario no debe tener sanciones activas.

Flujo Principal:

 1 El bibliotecario ingresa el ID del usuario.
 
 2 El sistema verifica que el usuario exista.
 
 3 El bibliotecario ingresa el título del libro.
 
 4 El sistema localiza el libro.
 
 5 El sistema verifica disponibilidad.
 
 6 El sistema crea el préstamo.
 
 7 Reduce ejemplares del libro.
 
 8 Muestra mensaje de éxito.

Postcondiciones:

Se crea un registro de préstamo.

Los ejemplares del libro disminuyen en 1.

Flujos Alternativos:

 A1 – Usuario no existe 
 
   En el paso 2 → mensaje “Usuario no encontrado”.
 
 A2 – Libro no disponible
 
   En el paso 5 → mensaje “Sin ejemplares disponibles”.
 
 A3 – Usuario sancionado
 
   El sistema detecta sanción activa → “Préstamo no permitido”.



CU-06 – Devolver Libro

Identificador: CU-06

Descripción: Permite registrar la devolución de un libro.

Actor Primario: Bibliotecario

Precondiciones:

 1 Debe existir el préstamo.

Flujo Principal:

 1 El bibliotecario selecciona “Devolver Libro”.
 
 2 Ingresa información del préstamo.
 
 3 El sistema aumenta los ejemplares del libro.
 
 4 Se crea la devolución.
 
 5 El sistema verifica si hubo retraso.
 
 6 Si hay retraso → genera sanción automática.

Postcondiciones:

 1 El préstamo se marca como devuelto.
 
 2 Los ejemplares del libro aumentan en 1.
 
 3 Si aplica, se genera sanción.

Flujos Alternativos:

 A1 – No existe el préstamo
 
   El sistema muestra “Préstamo no válido”.



CU-07 – Generar Sanción Automática

Identificador: CU-07

Descripción: Crea una sanción cuando una devolución tiene retraso.

Actor Primario: Sistema

Actor Secundario: Bibliotecario, Usuario

Precondiciones:

 1 Debe existir un préstamo vencido.

Flujo Principal:

 1 Al devolver un libro, el sistema compara fechas.
 
 2 Si la fecha actual es mayor a la fecha límite:
 
 3 Se genera una sanción con fecha de inicio y fin.
 
 4 Se agrega la sanción al usuario.

Postcondiciones:

 1 El usuario queda sancionado.

Flujos Alternativos:

 A1 – No hubo retraso
 
   El sistema no genera sanción.



DIAGRAMA DE SECUENCIA: Realizar un prestamo

<img width="752" height="650" alt="image" src="https://github.com/user-attachments/assets/801fa2cc-833a-46fa-87aa-f9062e094f6f" />
<img width="722" height="798" alt="image" src="https://github.com/user-attachments/assets/1494d0b0-d434-483e-9ed2-b55f2127009e" />

## Principios de Programación Orientada a Objetos

## 1. Encapsulación
Definición: Ocultar los detalles internos de una clase y exponer solo lo necesario mediante métodos públicos.

Implementación en el proyecto:
java
public class Usuario {
    private String nombre;      
    private String ID;          
    private Tipo tipo;
    public String getNombre() {
        return nombre;
    }    
    public String getID() {
        return ID;
    }
}

### 2. **Abstracción** 
Definición: Mostrar solo las características esenciales de un objeto, ocultando la complejidad.

Implementación en el proyecto:
java
public interface ServicioDeUsuarios {
    void RegistrarUsuario(Usuario usuario);
    Usuario BusacarUsuarioID(String ID);
}

### 3. **Herencia** 
Definición: Crear nuevas clases basadas en clases existentes, heredando sus propiedades y métodos.

Implementación en el proyecto:
java
public class Prestamo extends Object {
    @Override
    public String toString() {  // ← Sobrescribe método de Object
        return "Prestamo realizado a: " + usuario.getNombre();
    }
}


### 4. **Polimorfismo** 
Definición: Capacidad de un objeto de tomar muchas formas. Permite tratar objetos de diferentes clases de manera uniforme.

Implementación en el proyecto:
java
ServicioDeUsuarios servicio1 = new UsuarioP2();
ServicioDeUsuarios servicio2 = new UsuarioP3(); // Hipotética otra implementación

servicio1.RegistrarUsuario(usuario);
servicio2.RegistrarUsuario(usuario);


## Principios SOLID Aplicados

### **S - Single Responsibility Principle**
Principio: Una clase debe tener una sola razón para cambiar.

**Aplicación**:
- Usuario → Solo representa usuarios
- UsuarioP2 → Solo gestiona usuarios
- Main → Solo maneja la interfaz de usuario
- Sistema → Solo coordina servicios

Cada clase tiene una responsabilidad única y bien definida.


### **O - Open/Closed Principle**
**Principio**: Abierto para extensión, cerrado para modificación.

**Aplicación**:
java
// Puedes crear nuevas implementaciones sin modificar código existente
public class UsuarioP3 implements ServicioDeUsuarios {
    // Nueva implementación con base de datos
    @Override
    public void RegistrarUsuario(Usuario usuario) {
        // Guardar en BD
    }
}
Puedes extender funcionalidad sin modificar clases existentes.

### **L - Liskov Substitution Principle**
**Principio**: Los objetos de una clase derivada deben poder reemplazar objetos de la clase base.

**Aplicación**:
java
ServicioDeUsuarios servicio = new UsuarioP2();
servicio = new UsuarioP3();

### **I - Interface Segregation Principle**
**Principio**: Los clientes no deben depender de interfaces que no usan.

**Aplicación**:
java
// Interfaces específicas en lugar de una interfaz gigante
ServicioDeUsuarios  → Solo métodos de usuarios
ServicioDeLibros    → Solo métodos de libros
ServicioDeSanciones → Solo métodos de sanciones

### **D - Dependency Inversion Principle**
**Principio**: Depender de abstracciones, no de concreciones.

**Aplicación**:
java
public class Sistema {
    // Depende de la INTERFAZ, no de la implementación concreta
    private ServicioDeUsuarios usuarioService;
}


##  Patrones de Diseño Implementados

### 1. **Singleton Pattern** 

**Propósito**: Garantizar que una clase tenga una sola instancia y proporcionar un punto de acceso global.

**Implementación**:
java
public class Sistema {
    private static Sistema instancia;  // ← Única instancia
        private Sistema() {
        this.usuarioService = new UsuarioP2();
        this.libroService = new LibroP2();
        // ...
    }
    // Método público para obtener la instancia
    public static Sistema getInstance() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }
}


**Uso**:
java
// Todas estas llamadas retornan la MISMA instancia
Sistema sistema1 = Sistema.getInstance();
Sistema sistema2 = Sistema.getInstance();
// sistema1 == sistema2 → true

**Beneficios**:
-  Una sola instancia en toda la aplicación
-  Acceso global controlado
-  Datos consistentes
-  Evita múltiples instancias que podrían causar inconsistencias

**Preguntas generales para todos los proyectos:**

### 1.	**Principios de POO**
¿Cómo aplicaste los principios de abstracción, encapsulación, herencia y polimorfismo en tu proyecto?.
R// Abstracción: Creando interfaces como ServicioDeLibros, ServicioDeUsuarios y más. Encapsulación: Todos los atributos son privados y solo se puede acceder a ellos mediante getters/setters. Herencia: En las excepciones personalizadas ya que extienden de Exception. Polimorfismo: Las clases LibroP2, UsuarioP2, ServicioP2, SancionP2 y PersistenciaArchivos implementan sus respectivas interfaces, permitiendo usar diferentes implementaciones de forma intercambiable.

¿Puedes explicar qué parte de tu código representa una clase base y qué clases derivadas extienden su funcionalidad?
R// Clase base: Excepción y de ella derivan las excepciones personalizadas, ya sean UsuarioDuplicadoException o ErrorCargaException.

¿Dónde aplicaste polimorfismo dinámico y por qué?
R// En la clase sistema, donde las respectivas referencias apuntan a UsuarioP2, LibroP2, SancionP2 y ServicioP2. Esto permite intercambiar la implementación sin modificar el código cliente

### 2.	**Relación entre clases**
¿Puedes describir las relaciones (asociación, composición, agregación) entre las clases principales de tu sistema?
R//
- Composición: Usuario tiene List<Prestamo> y List<Sancion>. Si el usuario se destruye sus prestamos y sanciones también
- Asociación: Préstamo asocia a un Usuario con un Libro. Devolución asocia una fecha de devolución con un Prestamo
- Agregación: Sistema agrega los servicios. Los servicios pueden seguir existiendo independientemente del Sistema
- Composición: GestorPersistencia contiene una instancia de ServicioPersistencia y un sistema
 
¿Qué criterios usaste para decidir cuándo usar una interfaz y cuándo una clase abstracta?
R// Para las interfaces definen un “que hace” sin saber el “como lo hace”, esto permite mayor flexibilidad ya que cualquier clase puede implementar ese servicio. Sobre las clases abstractas no las use en este programa

¿Implementaste sobrecarga o sobreescritura de métodos? Muestra un ejemplo.
R// Si, es esta parte 
<img width="672" height="177" alt="image" src="https://github.com/user-attachments/assets/b290c58e-c1a0-42c9-b951-fecdb1dd564c" />


### 3.	**Diseño UML**
¿Tu diagrama de clases refleja fielmente la estructura del código fuente? ¿Puedes mostrar un caso en donde hiciste cambios en el diseño durante la implementación?
R// Si, el diagrama UML debe mostrar
 1.	Clases Principales
 2.	Interfaces
 3.	Implementaciones y sus servicios de implementaciones
 4.	Enum Tipo
 5.	Clase sistema con Patrón Singleton
 6.	Relaciones de composición y asociación
    
¿Qué herramienta utilizaste para crear los diagramas? ¿Cómo te aseguraste de que fueran coherentes con el modelo implementado?
R// Si, Planttext

### 4.	**Aplicación de Patrones de Diseño**
¿Qué patrón o patrones de diseño aplicaste en tu solución (por ejemplo, Singleton, Factory Method, Observer)? ¿Cuál fue la motivación detrás de esa elección?
R// Singleton y fue para tener una sola instancia de biblioteca en todo el sistema, para poder manejar la trazabilidad en el programa

¿Puedes explicar el patrón que implementaste y señalar su ubicación en tu código?
R// Singleton, en la clase Sistema. Asegura que solo exista una instancia del sistema mediante un constructor privado, una variable estática y un método llamado getInstance() que controla la creacion

### 5.	**Principios SOLID**
¿Qué principios de SOLID aplicaste? Menciona uno y explícalo usando una parte específica de tu proyecto.
R// Single Responsability, Cada una de las clases creadas tiene su respectiva responsabilidad clara, un ejemplo de esto son las clases de devolución, préstamo, libro y usuario.

¿Qué decisiones de diseño tomaste para garantizar un bajo acoplamiento y alta cohesión en tu sistema?
R// Para el bajo acoplamiento se implemento el uso de interfaces y para la alta cohesión cada clase agrupa funcionalidades relacionadas. PersistenciaArchivos solo maneja archivos CSV.

### 6.	**Excepciones y manejo de errores**
¿Implementaste excepciones personalizadas? ¿En qué parte del sistema se lanzan y para qué casos?
R// Si, por ejemplo UsuarioDuplicadoException: Se lanza en RegistrarUsuario() si ya existe un usuario con ese ID. LibroNoEncontradoException: Se lanza en BuscarPorTitulo() cuando no se encuentra un libro, etc etc.

¿Cómo aseguras que el sistema no se detenga ante errores inesperados?
R// Mediante el bloque Try – catch en puntos críticos donde pueden ocurrir estos errores

### 7.	**Persistencia**
¿Cómo implementaste la persistencia de datos? ¿Usaste serialización o archivos de texto/JSON?
R// Fue usando CSV mediante la clase PersistenciaArchivos

¿Qué clases son responsables de leer y escribir información persistente?
R// La clase PersistenciaArchivos que implementa ServicioPersistencia y realiza todas las operaciones de lectura y la clase GestorPersistencia que coordina cuando se debe guardar

### 8.	**Interfaz de Usuario**
¿Qué tecnología usaste para construir la interfaz (Swing, JavaFX, consola, etc.)?
R// Consola simplemente.

¿Cómo se relaciona tu interfaz con la lógica de negocio? ¿Aplicaste separación de responsabilidades?
R// Si, el Main (Muestra menús, captura la entrada de usuarios y formatea la salida), Servicios (Valida reglas del negocio, procesa prestamos, devoluciones, sanciones y gestiona el catálogo) y PersistenciaArchivos (Lee archivos y maneja el formato CSV).

### 9.	**Calidad del Código**
¿Cómo estructuraste el código para mantenerlo legible y modular?
R// Métodos pequeños con responsabilidades únicas, nombres exactos, comentarios, separación en múltiples clases y mas.

¿Qué convenciones seguiste para nombrar tus clases, atributos y métodos?
R// Simplemente los nombraba por lo que hacían y para no perderme 

### 10.	**Cumplimiento de Requisitos Funcionales**
¿Qué requisitos adicionales o extras implementaste?
R// Sistema de sanciones automáticos por retraso, validación de disponibilidad de libros antes de prestar, sistema de búsqueda por título, estadísticas del inventario, consulta de historial por préstamo y mas.

¿Qué funcionalidad fue más difícil de desarrollar y por qué?
R// la de persistencia de los datos, más que todo porque es un tema que todavía no termino de entender.
