package Trabajo-Practico-Final;

/**
 * Clase que representa a un periodista o creador de contenido dentro del portal.
 * Hereda de la clase abstracta Persona e implementa la interfaz IExportable 
 * para garantizar su persistencia en archivos de texto.
 * @author Gimenenez Micaela, Garcia Ayelen, Petrangeli Dylan
 * @version 1.0
 */
public class Autor extends Persona implements IExportable {
    private String medio;

    /**
     * Constructor de la clase Autor.
     * @param dni El número de documento del autor.
     * @param nombre El nombre completo del autor.
     * @param medio El nombre del medio de comunicación o empresa donde trabaja el autor.
     */
    public Autor(Integer dni, String nombre, String medio) {
        super(dni, nombre); 
        this.medio = medio;
    }

    /**
     * Sobreescribe el método abstracto de Persona aplicando polimorfismo.
     * Define cómo se debe mostrar por consola el perfil específico de un Autor.
     */
    @Override
    public void mostrarPerfil() {
        System.out.println("AUTOR | DNI: " + getDni() + 
                           " | Nombre: " + getNombre() + 
                           " | Medio periodístico: " + medio);
    }

    /**
     * Cumple con la implementacion del metodo de la interfaz IExportable.
     * Genera un String con los datos del autor separados por punto y coma (;) 
     * listo para ser insertado directamente en el archivo .txt.
     * @return Cadena de texto formateada para el guardado.
     */
    @Override
    public String generarLineaArchivo() {
        return getDni() + ";" + getNombre() + ";" + this.medio;
    }
}
