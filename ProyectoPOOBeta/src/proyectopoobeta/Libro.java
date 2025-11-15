package proyectopoobeta;

public class Libro {
    private String titulo;
    private String autor;
    private String editorial;
    private int año;
    private int ejemplares;
    private boolean disponibilidad;

    public Libro(String titulo, String autor, String editorial ,int año, int ejemplares, boolean disponibilidad) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.año = año;
        this.ejemplares = ejemplares;
        this.disponibilidad = disponibilidad;
    }
    
    public String getTitulo(){
        return titulo;
    }
    
    public String getAutor(){
        return autor;
    }
    
    public String getEditorial(){
        return editorial;
    }
    
    public int getAño(){
        return año;
    }
    
    public int getEjemplares(){
        return ejemplares;
    }
    
    public boolean getDisponibilidad(){
        return disponibilidad;
    }
    
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    
    public void setAutor(String autor){
        this.autor = autor;
    }
    
    public void setEditorial(String editorial){
        this.editorial = editorial;
    }
    
    public void setEjemplares(int ejemplares){
        this.ejemplares = ejemplares;
    }
    
    public void setAño(int año){
        this.año = año;
    }
    
    public void setDisponible(boolean disponibilidad){
        this.disponibilidad = disponibilidad;
    }
}
