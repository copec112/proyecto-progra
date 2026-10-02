/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
package gestion_inmobilaria;
 
/**
 * Representa una propiedad de tipo Casa, con un número identificador propio
 * además de los atributos comunes heredados de {@link Propiedad}.
 *
 * @author luisi
 */
public class Casa extends Propiedad {

    // Atributos
    private int numeroCasa;

    // Constructores
    /**
     * Crea una Casa con valores por defecto (sin número asignado).
     */
    public Casa() {
        super();
        this.numeroCasa = 0;
    }

    /**
     * Crea una Casa con todos sus datos.
     *
     * @param descripcion descripción general de la propiedad.
     * @param numHabitaciones cantidad de habitaciones.
     * @param numBaños cantidad de baños.
     * @param valorUF valor de la propiedad en UF.
     * @param estacionamiento indica si la propiedad cuenta con estacionamiento.
     * @param numeroCasa número identificador de la casa.
     */
    public Casa(String descripcion, int numHabitaciones, int numBaños, int valorUF, boolean estacionamiento, int numeroCasa) {
        super(descripcion, numHabitaciones, numBaños, valorUF, estacionamiento);
        this.numeroCasa = numeroCasa;
        System.out.println("Casa N°" + numeroCasa + " creada: " + descripcion);
    }

    // Una Casa se puede vender directamente, sin exigir un interesado previo
    // registrado (a diferencia de Departamento, ver su setVendido()): se
    // asume una negociación más directa, sin etapa formal de "reserva".
    /**
     * Marca la Casa como vendida o disponible. A diferencia de
     * {@link Departamento#setVendido(boolean)}, no exige interesados
     * registrados previamente, ya que se asume una negociación más directa.
     *
     * @param vendido {@code true} para marcar la casa como vendida,
     *                {@code false} para marcarla como disponible.
     * @throws PropiedadVendidaException si la casa ya estaba vendida y se
     *         intenta marcar como vendida nuevamente.
     */
    @Override
    public void setVendido(boolean vendido) throws PropiedadVendidaException {
        if (this.vendido && vendido) {
            System.out.println("Error: la Casa N°" + numeroCasa + " ya estaba vendida.");
            throw new PropiedadVendidaException();
        }
        this.vendido = vendido;
        System.out.println("Casa N°" + numeroCasa + " marcada como " + (vendido ? "VENDIDA" : "DISPONIBLE") + ".");
    }
 
    // Get/set
    /** @param numeroCasa nuevo número identificador de la casa. */
    public void setNumeroCasa(int numeroCasa) {this.numeroCasa = numeroCasa;}
    /** @return el número identificador de la casa. */
    public int getNumeroCasa() {return numeroCasa;}
}
