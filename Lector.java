package Trabajo-Practico-Final;

/**
 * Clase que representa un usuario de la plataforma que solamente consume el contenido de la misma.
 * @author Garcia Ayelen, Gimenez Micaela, Petrangeli Dylan.
 * @version 1.0
 */
public class Lector extends Persona implements IExportable{
    private Integer dni;
    private String nombre;
    private Integer edad;
    
    /**
     * Constructor de un objeto que representa a un lector que se registra en la página.
     * @param dni DNI del lector.
     * @param nombre Nombre del lector.
     * @param edad Edad del lector.
     */
    public Lector(Integer dni, String nombre, Integer edad){
        super(dni, nombre);
        if(edad < 0) throw new IllegalArgumentException("ERROR: La edad no puede ser negativa.");
        this.edad = edad;
    }

    /**
     * Método getter que devuelve la edad del lector.
     * @return Edad del lector.
     */
    public Integer getEdad(){
        return edad;
    }

    /**
     * Método para imprimir con formato el perfil del lector.
     * Imprime su DNI, Nombre y Edad.
     */
    public void mostrarPerfil() {
        System.out.println("AUTOR | DNI: " + getDni() + 
                           " | Nombre: " + getNombre() + 
                           " | Edad: " + getEdad());
    }

    /**
     * Cumple con la implementacion del metodo de la interfaz IExportable.
     * Genera un String con los datos del autor separados por punto y coma (;) 
     * listo para ser insertado directamente en el archivo .txt.
     * @return Cadena de texto formateada para el guardado.
     */
    @Override
    public String generarLineaArchivo(){
        return getDni()+ ";" + getNombre() + ";" + getEdad();
    }
}
