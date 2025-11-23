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
interface ILibroService {
    +agregarLibro(Libro libro)
    +buscarPorTitulo(String titulo) : List<Libro>
    +buscarPorAutor(String autor) : List<Libro>
    +disminuirEjemplar(Libro libro)
    +aumentarEjemplar(Libro libro)
}

interface IUsuarioService {
    +agregarUsuario(Usuario usuario)
    +buscarUsuarioPorId(String id) : Usuario
}

interface IPrestamoService {
    +prestarLibro(Usuario usuario, Libro libro) : Prestamo
    +devolverLibro(Prestamo prestamo) : Devolucion
    +verificarSanciones(Usuario usuario)
}

class Libro {
    -titulo : String
    -autor : String
    -editorial : String
    -anio : int
    -ejemplares : int
}

class Usuario {
    -id : String
    -nombre : String
    -tipo : String
    -historial : List<Prestamo>
}

class Prestamo {
    -usuario : Usuario
    -libro : Libro
    -fechaPrestamo : LocalDate
    -fechaDevolucion : LocalDate
    -estado : boolean
}

class Devolucion {
    -fechaDevolucion : LocalDate
    -prestamo : Prestamo
}

class LibroService implements ILibroService
class UsuarioService implements IUsuarioService
class PrestamoService implements IPrestamoService

class Sistema {
    -libroService : ILibroService
    -usuarioService : IUsuarioService
    -prestamoService : IPrestamoService
    +Sistema(ILibroService, IUsuarioService, IPrestamoService)
}

Sistema --> ILibroService
Sistema --> IUsuarioService
Sistema --> IPrestamoService

UsuarioService --> Usuario
LibroService --> Libro
PrestamoService --> Prestamo
PrestamoService --> Devolucion

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


DIAGRAMA DE FRECUENCIA: Realizar un prestamo

┌────────┐        ┌──────┐       ┌─────────┐      ┌──────────┐      ┌───────┐      ┌──────────┐
│Usuario │        │ Main │       │ Sistema │      │UsuarioP2 │      │Libro  │      │ServicioP2│
└────┬───┘        └──┬───┘       └────┬────┘      └────┬─────┘      └───┬───┘      └────┬─────┘
     │               │                │                │                │               │
     │ Realizar      │                │                │                │               │
     │ Préstamo      │                │                │                │               │
     ├──────────────>│                │                │                │               │
     │               │                │                │                │               │
     │               │ getInstance()  │                │                │               │
     │               ├───────────────>│                │                │               │
     │               │                │                │                │               │
     │               │ getUsuarioService()             │                │               │
     │               ├───────────────>│                │                │               │
     │               │                │                │                │               │
     │               │ BusacarUsuarioID("12345")       │                │               │
     │               ├────────────────┼───────────────>│                │               │
     │               │                │                │                │               │
     │               │                │      retorna Usuario            │               │
     │               │<───────────────┼────────────────┤                │               │
     │               │                │                │                │               │
     │               │ getSancionService()             │                │               │
     │               ├───────────────>│                │                │               │
     │               │                │                │                │               │
     │               │ gestionSanciones(usuario)       │                │               │
     │               ├────────────────┤                │                │               │
     │               │                │                │                │               │
     │               │      retorna false (sin sanciones)               │               │
     │               │<───────────────┤                │                │               │
     │               │                │                │                │               │
     │               │ getLibroService()               │                │               │
     │               ├───────────────>│                │                │               │
     │               │                │                │                │               │
     │               │ buscarporTitulo("Java")         │                │               │
     │               ├────────────────┤                │                │               │
     │               │                │                │                │               │
     │               │            retorna List[Libro]  │                │               │
     │               │<───────────────┤                │                │               │
     │               │                │                │                │               │
     │               │ disponibilidad()                │                │               │
     │               ├────────────────┼────────────────┼───────────────>│               │
     │               │                │                │                │               │
     │               │            retorna true         │                │               │
     │               │<───────────────┼────────────────┼────────────────┤               │
     │               │                │                │                │               │
     │               │ getPrestamoService()            │                │               │
     │               ├───────────────>│                │                │               │
     │               │                │                │                │               │
     │               │ prestarLibro(usuario, libro)    │                │               │
     │               ├────────────────┼────────────────┼────────────────┼──────────────>│
     │               │                │                │                │               │
     │               │                │                │      reducirEjemplar()         │
     │               │                │                │<──────────────┼────────────────┤
     │               │                │                │                │               │
     │               │                │                │    new Prestamo()              │
     │               │                │                │<───────────────────────────────┤
     │               │                │                │                │               │
     │               │                │      retorna Prestamo           │               │
     │               │<───────────────┼────────────────┼────────────────┼───────────────┤
     │               │                │                │                │               │
     │ Confirmación  │                │                │                │               │
     │<──────────────┤                │                │                │               │


<img width="795" height="682" alt="image" src="https://github.com/user-attachments/assets/b8a9bb2c-83fc-49c4-9883-febf6e6be77d" />
<img width="722" height="798" alt="image" src="https://github.com/user-attachments/assets/1494d0b0-d434-483e-9ed2-b55f2127009e" />


