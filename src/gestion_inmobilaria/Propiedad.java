/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 * Clase abstracta que representa una propiedad genérica (Casa, Departamento, etc).
 * Sirve como base para las distintas propiedades del sistema.
 *
 * @author luisi
 */
public abstract class Propiedad {

    // Atributos
    private String descripcion;
    private int numHabitaciones;
    private int numBaños;
    private int valorUF;
    private int numInteresados;
    protected boolean vendido; // protected: los hijos deben poder modificarlo en setVendido()
    private boolean estacionamiento;

    // Constructores
    /**
     * Crea una propiedad vacía, con todos sus atributos en sus valores por
     * defecto (descripción vacía, contadores en 0, no vendida y sin
     * estacionamiento).
     */
    public Propiedad() {
        this.descripcion = "";
        this.numHabitaciones = 0;
        this.numBaños = 0;
        this.valorUF = 0;
        this.numInteresados = 0;
        this.vendido = false;
        this.estacionamiento = false;
    }

    /**
     * Crea una propiedad con los datos indicados. Queda inicialmente no
     * vendida y sin interesados registrados.
     *
     * @param descripcion descripción de la propiedad.
     * @param numHabitaciones número de habitaciones.
     * @param numBaños número de baños.
     * @param valorUF valor de la propiedad en UF.
     * @param estacionamiento si la propiedad cuenta con estacionamiento.
     */
    public Propiedad(String descripcion, int numHabitaciones, int numBaños, int valorUF, boolean estacionamiento) {
        this.descripcion = descripcion;
        this.numHabitaciones = numHabitaciones;
        this.numBaños = numBaños;
        this.valorUF = valorUF;
        this.numInteresados = 0;
        this.vendido = false;
        this.estacionamiento = estacionamiento;
    }

    // Suma un interesado más a la propiedad
    /**
     * Incrementa en uno el número de interesados registrados en esta
     * propiedad.
     */
    public void registrarInteresados() {
        this.numInteresados++;
        System.out.println("Se registró un nuevo interesado en \"" + descripcion + "\". Total interesados: " + numInteresados);
    }

    // Cada subclase (Casa, Departamento) decide cómo marcar la venta,
    // y debe lanzar PropiedadVendidaException si ya estaba vendida.
    /**
     * Marca la propiedad como vendida o no vendida. Cada subclase decide
     * cómo implementa este cambio de estado, pero el contrato general es
     * el mismo: si la propiedad ya estaba vendida, la implementación debe
     * lanzar {@link PropiedadVendidaException} en vez de sobrescribir el
     * estado silenciosamente.
     *
     * @param vendido nuevo estado de venta de la propiedad.
     * @throws PropiedadVendidaException si la propiedad ya se encontraba vendida.
     */
    public abstract void setVendido(boolean vendido) throws PropiedadVendidaException;

    // TODO: ajustar la fórmula real según la lógica de negocio del proyecto.
    // Se deja un cálculo simple como base (a modificar).
    /**
     * Calcula el precio actual de la propiedad a partir de su valor en UF
     * y el número de interesados. Cálculo placeholder, pendiente de ajustar
     * según la lógica de negocio real.
     *
     * @param valorUF valor base de la propiedad en UF.
     * @param numInteresados cantidad de interesados registrados.
     * @return el precio actual calculado, en UF.
     */
    public int calcularPrecioActual(int valorUF, int numInteresados) {
        int precio = valorUF + (numInteresados * 1); // placeholder
        System.out.println("Precio actual calculado para \"" + descripcion + "\": " + precio + " UF");
        return precio;
    }

    // Get/set
    /** @param descripcion la nueva descripción de la propiedad. */
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    /** @return la descripción de la propiedad. */
    public String getDescripcion() {return descripcion;}

    /** @param numHabitaciones el nuevo número de habitaciones. */
    public void setNumHabitaciones(int numHabitaciones) {this.numHabitaciones = numHabitaciones;}
    /** @return el número de habitaciones. */
    public int getNumHabitaciones() {return numHabitaciones;}

    /** @param numBaños el nuevo número de baños. */
    public void setNumBaños(int numBaños) {this.numBaños = numBaños;}
    /** @return el número de baños. */
    public int getNumBaños() {return numBaños;}

    /** @param valorUF el nuevo valor de la propiedad en UF. */
    public void setValorUF(int valorUF) {this.valorUF = valorUF;}
    /** @return el valor de la propiedad en UF. */
    public int getValorUF() {return valorUF;}

    /** @param numInteresados el nuevo número de interesados. */
    public void setNumInteresados(int numInteresados) {this.numInteresados = numInteresados;}
    /** @return el número de interesados registrados. */
    public int getNumInteresados() {return numInteresados;}

    // Solo getter: el diagrama marca <<get>> para vendido (el set real es setVendido())
    /** @return {@code true} si la propiedad ya fue vendida. */
    public boolean isVendido() {return vendido;}

    /** @param estacionamiento si la propiedad cuenta con estacionamiento. */
    public void setEstacionamiento(boolean estacionamiento) {this.estacionamiento = estacionamiento;}
    /** @return {@code true} si la propiedad cuenta con estacionamiento. */
    public boolean isEstacionamiento() {return estacionamiento;}
}