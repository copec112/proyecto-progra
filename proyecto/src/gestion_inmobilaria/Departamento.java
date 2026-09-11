/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 *
 * @author jacor
 */
public class Departamento extends Propiedad {

    // Atributos
    private int numeroDepartamento;

    // Constructores
    public Departamento() {
        super();
        this.numeroDepartamento = 0;
    }

    public Departamento(String descripcion, int numHabitaciones, int numBaños, int valorUF, boolean estacionamiento, int numeroDepartamento) {
        super(descripcion, numHabitaciones, numBaños, valorUF, estacionamiento);
        this.numeroDepartamento = numeroDepartamento;
        System.out.println("Departamento N°" + numeroDepartamento + " creado: " + descripcion);
    }

    @Override
    public void setVendido(boolean vendido) throws PropiedadVendidaException {
        if (this.vendido && vendido) {
            System.out.println("Error: el Departamento N°" + numeroDepartamento + " ya estaba vendido.");
            throw new PropiedadVendidaException();
        }
        this.vendido = vendido;
        System.out.println("Departamento N°" + numeroDepartamento + " marcado como " + (vendido ? "VENDIDO" : "DISPONIBLE") + ".");
    }

    // Get/set
    public void setNumeroDepartamento(int numeroDepartamento) {this.numeroDepartamento = numeroDepartamento;}
    public int getNumeroDepartamento() {return numeroDepartamento;}
}
