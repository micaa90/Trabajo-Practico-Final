
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase central del sistema que representa un artículo publicado en el portal.
 * Gestiona su propio ciclo de vida y es la dueña absoluta (Composición) 
 * de los comentarios que los lectores le realizan.
 * @author Gimenenez Micaela, Garcia Ayelen, Petrangeli Dylan
 * @version 1.0
 */
public class Noticia implements IExportable {
    private String titulo;
    private String detalle;
    private LocalDate fecha; 
    private Autor autor; // agregacion
    private ArrayList<Comentario> comentarios; // composicion

    /**
     * Constructor de Noticia. Recibe la fecha desglosada para mayor facilidad 
     * en el menú interactivo, pero la empaqueta internamente.
     * @param titulo El título principal de la noticia.
     * @param detalle El cuerpo o desarrollo del artículo.
     * @param dia Día de publicación.
     * @param mes Mes de publicación.
     * @param anio Año de publicación.
     * @param autor El objeto Autor responsable de esta publicación.
     */
    public Noticia(String titulo, String detalle, Integer dia, Integer mes, Integer anio, Autor autor) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Error de integridad: El título de la noticia no puede estar vacío.");
        }
        if (detalle == null || detalle.trim().isEmpty()) {
            throw new IllegalArgumentException("Error de integridad: El detalle de la noticia no puede estar vacío.");
        }
        // No puede haber noticia sin autor
        if (autor == null) {
            throw new NullPointerException("Error de integridad: La noticia debe estar asociada a un Autor válido.");
        }
        if (dia == null || mes == null || anio == null) {
            throw new IllegalArgumentException("Error: Los datos de la fecha no pueden ser nulos.");
        }
        // si los números son ilógicos (32/13/2026), Java lanza automáticamente una DateTimeException.
        LocalDate fechaIngresada = LocalDate.of(anio, mes, dia);
        if (fechaIngresada.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Error de lógica: La fecha de la noticia (" + fechaIngresada + ") no puede ser posterior al día de hoy.");
        }
        this.titulo = titulo;
        this.detalle = detalle;
        this.autor = autor;
        fecha = fechaIngresada;
        comentarios = new ArrayList<>();
    }

    public ArrayList<Comentario> getComentarios() {
        return this.comentarios;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public Autor getAutor() {
        return autor;
    }
    /**
     * Delega a la noticia la responsabilidad de administrar sus propios comentarios.
     * @param texto La descripcion del comentario que se asociara a la noticia.
     * @param lector El objeto que contiene la identidad del lector que realiza el comentario.
     */
    public void agregarComentario(String texto, Lector lector) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El texto del comentario no puede estar vacío.");
        }
        if (lector == null) {
            throw new NullPointerException("Error: No se puede asociar un comentario sin un lector válido.");
        }
        
        int numeroComentario = this.comentarios.size() + 1; 
        
        Comentario nuevoComentario = new Comentario(numeroComentario, texto, lector);
        this.comentarios.add(nuevoComentario);
    }

    /**
     * Muestra por consola el contenido completo de la noticia, 
     * incluyendo los datos de su autor y todos los comentarios asociados.
     */
    public void mostrarNoticiaYComentarios() {
        System.out.println("\n========================================");
        System.out.println("TÍTULO: " + this.titulo);
        System.out.println("Fecha: " + this.fecha + " | Autor: " + this.autor.getNombre() + " (" + this.autor.getDni() + ")");
        System.out.println("----------------------------------------");
        System.out.println(this.detalle);
        System.out.println("========================================");
        
        System.out.println("COMENTARIOS (" + this.comentarios.size() + "):");
        if (this.comentarios.isEmpty()) {
            System.out.println("  No hay comentarios todavía.");
        } else {
            for (Comentario c : comentarios) {
                c.mostrarComentario(); 
            }
        }
    }
    

    /**
     * Exporta los datos estructurales de la noticia implementando la interfaz asociada. 
     * Guarda el DNI del autor para mantener la referencia cruzada en la persistencia.
     * * @return Cadena de texto formateada para el guardado.
     */
    @Override
    public String generarLineaArchivo() {
        return this.titulo + " / " + this.detalle +  " / " +  this.fecha.toString() + " / " + this.autor.getDni();
    }
}