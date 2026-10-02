/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 * Representa una propiedad de tipo Departamento, con un número identificador
 * propio además de los atributos comunes heredados de {@link Propiedad}.
 *
 * @author luisi
 */
public class Departamento extends Propiedad {

    // Atributos
    private int numeroDepartamento;

    // Constructores
    /**
     * Crea un Departamento con valores por defecto (sin número asignado).
     */
    public Departamento() {
        super();
        this.numeroDepartamento = 0;
    }

    /**
     * Crea un Departamento con todos sus datos.
     *
     * @param descripcion descripción general de la propiedad.
     * @param numHabitaciones cantidad de habitaciones.
     * @param numBaños cantidad de baños.
     * @param valorUF valor de la propiedad en UF.
     * @param estacionamiento indica si la propiedad cuenta con estacionamiento.
     * @param numeroDepartamento número identificador del departamento.
     */
    public Departamento(String descripcion, int numHabitaciones, int numBaños, int valorUF, boolean estacionamiento, int numeroDepartamento) {
        super(descripcion, numHabitaciones, numBaños, valorUF, estacionamiento);
        this.numeroDepartamento = numeroDepartamento;
        System.out.println("Departamento N°" + numeroDepartamento + " creado: " + descripcion);
    }

    // A diferencia de Casa, un Departamento solo puede venderse si ya se
    // registró al menos un interesado (registrarInteresados()). Esto modela
    // que en un edificio/condominio la venta suele pasar por una etapa previa
    // de "reserva"/interés formal, cosa que no aplica igual para una Casa.
    /**
     * Marca el Departamento como vendido o disponible. A diferencia de
     * {@link Casa#setVendido(boolean)}, exige que ya exista al menos un
     * interesado registrado antes de poder marcarlo como vendido, ya que en
     * un edificio/condominio la venta suele pasar por una etapa previa de
     * "reserva"/interés formal.
     *
     * @param vendido {@code true} para marcar el departamento como vendido,
     *                {@code false} para marcarlo como disponible.
     * @throws PropiedadVendidaException si el departamento ya estaba vendido,
     *         o si se intenta marcarlo como vendido sin tener al menos un
     *         interesado registrado.
     */
    @Override
    public void setVendido(boolean vendido) throws PropiedadVendidaException {
        if (this.vendido && vendido) {
            System.out.println("Error: el Departamento N°" + numeroDepartamento + " ya estaba vendido.");
            throw new PropiedadVendidaException();
        }
        if (vendido && getNumInteresados() == 0) {
            System.out.println("Error: el Departamento N°" + numeroDepartamento
                    + " no puede venderse sin al menos un interesado registrado.");
            throw new PropiedadVendidaException(
                    "El departamento necesita al menos un interesado registrado antes de poder venderse.");
        }
        this.vendido = vendido;
        System.out.println("Departamento N°" + numeroDepartamento + " marcado como " + (vendido ? "VENDIDO" : "DISPONIBLE") + ".");
    }

    // Get/set
    /** @param numeroDepartamento nuevo número identificador del departamento. */
    public void setNumeroDepartamento(int numeroDepartamento) {this.numeroDepartamento = numeroDepartamento;}
    /** @return el número identificador del departamento. */
    public int getNumeroDepartamento() {return numeroDepartamento;}
}
