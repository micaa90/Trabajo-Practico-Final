import java.io.*;
import java.util.*;
import java.time.*;

/**
 * Clase gestora principal que representa el Sistema de Noticias (NEWS).
 * Se encarga de la administración de autores, lectores y noticias, gestionando
 * las interacciones entre ellos y el ciclo de vida de la persistencia en archivos.
 * @author Gimenez Micaela, Garcia Ayelen, Petrangeli Dylan
 * @version 1.0
 */
public class PortalDeNoticias {

    private HashMap<Integer, Autor> autores;
    private HashMap<Integer, Lector> lectores;
    private ArrayList<Noticia> noticias;
    
    /**
     * Constructor por defecto del Portal de Noticias.
     * Inicializa las estructuras de datos (HashMaps y ArrayList) para comenzar la carga.
     */
    public PortalDeNoticias(){
        this.autores = new HashMap<>();
        this.lectores = new HashMap<>();
        this.noticias = new ArrayList<>();
    }

    /**
     * Registra un nuevo autor en el sistema después de validar sus campos
     * y comprobar que el documento no se encuentre duplicado.
     * @param dni El número de documento del autor.
     * @param nombre El nombre completo del autor.
     * @param medio El medio periodístico al que representa.
     * @throws DniDuplicadoException Si ya existe un autor registrado con el mismo DNI.
     * @throws IllegalArgumentException Si alguno de los argumentos de entrada es inválido o nulo.
     */
    public void registrarAutor(Integer dni, String nombre, String medio) throws DniDuplicadoException{
        if(dni == null || dni < 0) throw new IllegalArgumentException("Error: no se puede estar vacio o ser negativo el dni");
        if(nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("Error: no puede estar vacio el nombre");
        if(medio == null || medio.trim().isEmpty()) throw new IllegalArgumentException("Error: no puede estar vacio el medio");

        for (Autor a : this.autores.values()) {
            if(a.getDni() == dni){
                throw new DniDuplicadoException("Error: el autor ya esta registrado");
            }
        }
        
        Autor nuevoAutor = new Autor(dni, nombre, medio);
        this.autores.put(dni, nuevoAutor);
    }

    /**
     * Registra un nuevo usuario lector en el sistema después de validar sus campos
     * y comprobar que el documento no se encuentre duplicado.
     * @param dni El número de documento del lector.
     * @param nombre El nombre completo del lector.
     * @param edad La edad del lector.
     * @throws DniDuplicadoException Si ya existe un lector registrado con ese DNI.
     * @throws IllegalArgumentException Si alguno de los argumentos de entrada es inválido o nulo.
     */
    public void registrarLector(Integer dni, String nombre, Integer edad) throws DniDuplicadoException{
        if(dni == null || dni < 0) throw new IllegalArgumentException("Error: no se puede estar vacio o ser negativo el dni");
        if(nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("Error: no puede estar vacio el nombre");
        if(edad == null || edad < 0) throw new IllegalArgumentException("Error: no puede estar vacia la edad");

        if(this.lectores.containsKey(dni)){
            throw new DniDuplicadoException("Error: el lector con DNI " + dni + " ya esta registrado");
        }
        
        Lector nuevoLector = new Lector(dni, nombre, edad);
        this.lectores.put(dni, nuevoLector);
    }


    /**
     * Da de alta y publica un nuevo artículo periodístico en el portal.
     * Verifica la existencia del autor responsable antes de instanciar el objeto.
     * @param titulo El título de la noticia.
     * @param detalle El cuerpo de texto o contenido de la noticia.
     * @param dia El día del mes en que se publica.
     * @param mes El número del mes de publicación.
     * @param anio El año de publicación.
     * @param dniAutor El DNI del autor que escribe el artículo (debe estar previamente registrado).
     * @throws IllegalArgumentException Si alguno de los argumentos de entrada es inválido o nulo.
     */
    public void publicarNoticia(String titulo, String detalle, Integer dia, Integer mes, Integer anio, Integer dniAutor){
        if(dniAutor == null || dniAutor < 0) throw new IllegalArgumentException("Error: el dni del autor no puede ser nulo o negativo");

        Autor autor = this.autores.get(dniAutor); // verifico que exista el autor y si existe me da sus datos
        if(autor == null) throw new IllegalArgumentException("Error: autor no registrado");

        Noticia noticia = new Noticia(titulo, detalle, dia, mes, anio, autor);// Noticia lanza sus propias excepciones
        this.noticias.add(noticia);
    }

    /**
     * Vincula un nuevo comentario a una noticia existente en el portal, asociándolo
     * a su vez con el lector registrado que lo emite.
     * @param numero El identificador numérico del comentario.
     * @param texto El contenido textual del comentario.
     * @param tituloNoticia El título de la noticia a comentar.
     * @param dniLector El DNI del lector que realiza el comentario.
     * @throws NoticiaInexistenteException Si se recorre la lista completa y ninguna noticia coincide con el título buscado.
     * @throws IllegalArgumentException Si el lector no existe o los parámetros son inválidos.
     */
    public void comentarNoticia(String texto, String tituloNoticia, Integer dniLector)  throws NoticiaInexistenteException{
        if(texto == null || texto.trim().isEmpty()) throw new IllegalArgumentException("Error: el texto no puede ser nulo");
        if(dniLector == null || dniLector < 0) throw new IllegalArgumentException("Error: el DNI del lector no puede ser nulo o negativo");
        if(tituloNoticia == null || tituloNoticia.trim().isEmpty()) throw new IllegalArgumentException("Error: el titulo de noticia a comentar no puede estar vacio");

        Lector lector = this.lectores.get(dniLector);
        if(lector == null) throw new IllegalArgumentException("Error: el lector no esta registrado");

        Noticia noticiaEncontrada = null;
        for (Noticia n : noticias) {
            if(n.getTitulo().equalsIgnoreCase(tituloNoticia)){
                noticiaEncontrada = n;
                break;
            }
        }

        if(noticiaEncontrada == null) throw new IllegalArgumentException("Error: noticia no encontrada");
        
        noticiaEncontrada.agregarComentario(texto, lector);
    }

    /**
     * Muestra por pantalla el contenido detallado de todas las noticias
     * publicadas durante un año determinado.
     * @param anioBuscado El año buscado.
     * @throws IllegalArgumentException Si el año solicitado se encuentra en el futuro.
     */
    public void listarNoticiasPorAnio(Integer anioBuscado){
        Integer anioActual = LocalDate.now().getYear();
        if(anioBuscado == null || anioBuscado < 0 || anioBuscado > anioActual) throw new IllegalArgumentException("Error: el anio no puede ser nulo, negativo o futuro");

        boolean noticiaEncontrada = false;

        for (Noticia n : this.noticias) {
            if(n.getFecha().getYear() == anioBuscado){
                n.mostrarNoticiaYComentarios();
                noticiaEncontrada = true;
            }
        }

        if(!noticiaEncontrada){
            System.out.println("No se encontro ninguna noticia publicada en ese anio");
        }
    }

    /**
     * Imprime por consola los artículos publicados del ultimo mes y año 
     * que transcurren actualmente en el reloj del sistema.
     */
    public void listarNoticiasPorMes(){
        LocalDate hoy = LocalDate.now();
        Integer mesActual = hoy.getMonthValue();
        Integer anioActual = hoy.getYear();

        boolean noticiaEncontrada = false;
        for (Noticia n : noticias) {
            if((n.getFecha().getMonthValue() == mesActual) && (n.getFecha().getYear() == anioActual) ){
                n.mostrarNoticiaYComentarios();
                noticiaEncontrada = true;
            }
        }

        if(!noticiaEncontrada){
            System.out.println("No se encontro ninguna noticia publicada del ultimo mes");
        }
    }

    /**
     * Busca que el autor esté registrado y lista por consola todas las noticias 
     * que ha publicado bajo su firma en el portal.
     * @param dniAutor El número de DNI del autor buscado.
     * @throws IllegalArgumentException Si se ingresan datos inválidos o el autor no figura en el sistema.
     */
    public void listarNoticiasPorAutor(Integer dniAutor){
        if(dniAutor == null || dniAutor < 0) throw new IllegalArgumentException("Error: el dni del autor buscado no puede ser nulo o negativo");

        Autor autor = autores.get(dniAutor);
        if(autor == null) throw new IllegalArgumentException("Error: el autor no esta registrado");

        boolean noticiaEncontrada = false;
        for (Noticia n : noticias) {
            if(n.getAutor().getDni() == dniAutor){
                n.mostrarNoticiaYComentarios();
                noticiaEncontrada = true;
            }
        }

        if(!noticiaEncontrada){
            System.out.println("Ese autor no publico ninguna noticia todavia");
        }

    }

    /**
     * Método interno y privado encargado de guardar en un archivo las noticias publicadas, autores o lectores.
     * y las inserta renglón por renglón en un archivo de texto plano.
     * @param lista Una estructura iterable contenedora de objetos que implementen
     * @param nombreArchivo El nombre o ruta física del archivo destino (ej: "autores.txt").
     */
    private void guardarLista(Iterable<? extends IExportable> lista, String nombreArchivo){
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
        
            for (IExportable elemento : lista) {
                writer.println(elemento.generarLineaArchivo()); 
            }
            
            System.out.println("Archivo '" + nombreArchivo + "' guardado con exito.");
        
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo " + nombreArchivo + ": " + e.getMessage());
        }
    }

    /**
     * Sincroniza y respalda el estado completo del sistema actual en el disco rígido.
     * Distribuye los autores, lectores y artículos de noticias en sus respectivos archivos de texto plano.
     */
    public void guardarSistema() {
        guardarLista(this.autores.values(), "autores.txt");
        guardarLista(this.lectores.values(), "usuarios.txt");
        guardarLista(this.noticias, "noticias.txt");

        try (PrintWriter escritor = new PrintWriter(new FileWriter("comentarios.txt"))) {
            for (Noticia n : this.noticias) {

                for (Comentario c : n.getComentarios()) {
                    // Formato: TituloNoticia / Texto / DniLector
                    escritor.println(n.getTitulo() + " / " + c.getTexto() + " / " + c.getLector().getDni());
                }
            }
            System.out.println("Archivo 'comentarios.txt' guardado con exito.");
        } catch (IOException e) {
            System.err.println("Error al guardar comentarios: " + e.getMessage());
        }
    }

    /**
     * Lee de forma secuencial los archivos físicos almacenados en el disco local y 
     * reconstruye la base de datos de objetos en memoria.
     * Mantiene el orden estricto de integridad (Autores y lectores antes que las Noticias).
     */
    public void cargarSistema(){
        
        try (BufferedReader lector = new BufferedReader(new FileReader("autores.txt"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                // Formato: "Dni ;  Nombre ; Medio"
                String[] partes = linea.split(" / "); 
                
                Integer dni = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                String medio = partes[2];
                
                this.autores.put(dni, new Autor(dni, nombre, medio));
            }
            System.out.println("Autores cargados con éxito.");
        } catch (IOException e) {
            System.out.println("Aviso: No se pudo cargar autores.txt (puede que sea la primera ejecución).");
        }

        try (BufferedReader lector = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                // Formato: "Dni ; Nombre ; Edad"
                String[] partes = linea.split(" / ");
                
                Integer dni = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                Integer edad = Integer.parseInt(partes[2]);
                
                this.lectores.put(dni, new Lector(dni, nombre, edad));
            }
            System.out.println("Lectores cargados con éxito.");
        } catch (IOException e) {
            System.out.println("Aviso: No se pudo cargar usuarios.txt.");
        }

        try (BufferedReader lector = new BufferedReader(new FileReader("noticias.txt"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                // Formato: "Titulo ;Detalle ; año/mes/dia ; dni"
                String[] partes = linea.split(" / ");
                
                String titulo = partes[0];
                String detalle = partes[1];

                LocalDate fechaParseada = LocalDate.parse(partes[2].trim());
        
                // Desglosamos la fecha parseada para poder pasársela al constructor de la clase Noticia
                Integer anio = fechaParseada.getYear();
                Integer mes = fechaParseada.getMonthValue();
                Integer dia = fechaParseada.getDayOfMonth();
                
                Integer dniAutor = Integer.parseInt(partes[3]);
                
                Autor autorResponsable = this.autores.get(dniAutor);
                
                if (autorResponsable != null) {
                    Noticia nuevaNoticia = new Noticia(titulo, detalle, dia, mes, anio, autorResponsable);
                    this.noticias.add(nuevaNoticia);
                } else {
                    System.out.println("Error al cargar una noticia: El autor DNI " + dniAutor + " no existe.");
                }
            }
            System.out.println("Noticias cargadas con éxito.");
        } catch (IOException e) {
            System.out.println("Aviso: No se pudo cargar noticias.txt.");
        }

        try (BufferedReader lector = new BufferedReader(new FileReader("comentarios.txt"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(" / ");
                
                String tituloNoticia = partes[0].trim();
                String texto = partes[1].trim();
                Integer dniLector = Integer.parseInt(partes[2].trim());
                
                //Recuperamos al lector que hizo el comentario
                Lector lectorEncontrado = this.lectores.get(dniLector);
                
                if (lectorEncontrado != null) {
                    //Buscamos a qué noticia iba dirigido
                    for (Noticia n : this.noticias) {
                        if (n.getTitulo().equalsIgnoreCase(tituloNoticia)) {
                            //Lo agregamos
                            n.agregarComentario(texto, lectorEncontrado);
                            break; 
                        }
                    }
                }
            }
            System.out.println("Comentarios cargados con éxito.");
        } catch (IOException e) {
            System.out.println("Aviso: No se pudo cargar comentarios.txt (puede que sea tu primera vez guardando).");
        }
    }



    
}