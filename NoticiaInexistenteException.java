package Trabajo-Practico-Final;

/**
 * Excepción personalizada que se lanza cuando un lector intenta 
 * interactuar con una noticia que no figura en el sistema.
 * @version 1.0
 */
public class NoticiaInexistenteException extends Exception {
    
    /**
     * Constructor de la excepción.
     * @param mensaje El detalle del error que se mostrará en pantalla.
     */
    public NoticiaInexistenteException(String mensaje) {
        super(mensaje);
    }
}