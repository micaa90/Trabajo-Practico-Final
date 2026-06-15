package Trabajo-Practico-Final;

/**
 * Clase que representa un comentario en una publicación.
 * @author Garcia Ayelen, Gimenez Micaela, Petrangeli Dylan.
 * @version 1.0
 */
public class Comentario implements IExportable{
    
    private Integer numero;
    private String texto;
    private Lector lector;

    /**
     * Método constructor de la clase Comentario.
     * @param numero el número de comentario correspondiente.
     * @param texto el texto a comentar.
     * @param lector el lector que publica el comentario.
     */
    public Comentario(Integer numero, String texto, Lector lector){
        if(numero < 0) throw new IllegalArgumentException("ERROR: El numero de comentario no puede ser negativo.");
        if(texto == null || texto.trim().isEmpty()) throw new IllegalArgumentException("ERROR: El comentario no puede estar vacio.");
        if(lector == null) throw new NullPointerException("ERROR: El lector ingresado no existe.");

        this.numero = numero;
        this.texto = texto;
        this.lector = lector;
    }

    /**
     * Método getter que devuelve el número del comentario.
     * @return número del comentario.
     */
    public Integer getNumero() {
		return numero;
	}

    /**
     * Método getter que devuelve el texto comentado.
     * @return texto comentado.
     */
	public String getTexto() {
		return texto;
	}

    /**
     * Método getter que devuelve el lector que publicó el comentario.
     * @return lector que publicó el comentario.
     */
	public Lector getLector() {
		return lector;
	}

	@Override
    public String generarLineaArchivo(){
        return getNumero() + ";" + getTexto() + ";" + getLector();
    }
    
}
