package Trabajo-Practico-Final;

/**
 * Excepción personalizada que se lanza cuando se intenta registrar 
 * a un Autor o Lector con un DNI que ya existe en los registros del portal.
 * @author Gimenenez Micaela, Garcia Ayelen, Petrangeli Dylan
 * @version 1.0
 */
public class DniDuplicadoException extends Exception {
    
    /**
     * Constructor de la excepción.
     * @param mensaje El detalle del error que se mostrará en pantalla.
     */
    public DniDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
