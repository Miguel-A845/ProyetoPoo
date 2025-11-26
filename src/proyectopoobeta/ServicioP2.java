package proyectopoobeta;

import java.time.LocalDate;


public class ServicioP2 implements Servicios {
    
    @Override
    public Prestamo prestarLibro(Usuario usuario, Libro libro){
        
       if (libro.disponibilidad()) {
            libro.reducirEjemplar();
            
            Prestamo prestamo = new Prestamo(usuario, libro, LocalDate.now());
            usuario.getHistorial().add(prestamo);
            
            System.out.println("Prestamo realizado correctamente. Ejemplares restantes: "+ libro.getEjemplares());
            return prestamo;
        } else {
            System.out.println("No hay ejemplares disponibles de: "+ libro.getTitulo());
            return null;
        }
    }
    
    @Override
    public Devolucion devolverLibro(Prestamo prestamo){
        
        if (prestamo == null){
            System.out.println("Erro. Prestamo no valido");
            return null;
        }
        
        if(!prestamo.getEstado()){
            System.out.println("Este prestamo ya fue devuelto");
            return null;
        }
        
        prestamo.getLibro().aumentarEjemplar();
        System.out.println("El libro ha sido devuelto: ");
        System.out.println("Ejemplares disponibles: "+ prestamo.getLibro().getEjemplares());
        
        prestamo.setEstado(false);
        
        return new Devolucion(LocalDate.now(), prestamo);
    }
}
