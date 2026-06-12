package Trabajo-Practico-Final;

/**
 * Clase abstracta que representa a un individuo genérico dentro del sistema.
 * Centraliza los datos comunes (DNI y nombre) para ser heredados por las 
 * entidades concretas como Autor y Lector, aplicando el principio de abstracción.
 * @author Gimenenez Micaela, Garcia Ayelen, Petrangeli Dylan
 * @version 1.0
 */
public abstract class Persona {
    
    private Integer dni;
    private String nombre;

    /**
     * Constructor base para inicializar los datos comunes de una persona.
     * * @param dni El número de documento de identidad (sin puntos).
     * @param nombre El nombre completo de la persona.
     */
    public Persona(Integer dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    /**
     * Obtiene el DNI de la persona.
     * * @return El número de DNI encapsulado.
     */
    public Integer getDni() {
        return dni;
    }

    /**
     * Obtiene el nombre de la persona.
     * * @return El nombre encapsulado.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método abstracto que obliga a las clases hijas a definir 
     * su propia representación en formato de texto.
     */
    public abstract void mostrarPerfil();
}