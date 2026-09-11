/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
package gestion_inmobilaria;
 
/**
 *
 * @author luisi
 */
public class Casa extends Propiedad {
 
    // Atributos
    private int numeroCasa;
 
    // Constructores
    public Casa() {
        super();
        this.numeroCasa = 0;
    }
 
    public Casa(String descripcion, int numHabitaciones, int numBaños, int valorUF, boolean estacionamiento, int numeroCasa) {
        super(descripcion, numHabitaciones, numBaños, valorUF, estacionamiento);
        this.numeroCasa = numeroCasa;
        System.out.println("Casa N°" + numeroCasa + " creada: " + descripcion);
    }
 
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
    public void setNumeroCasa(int numeroCasa) {this.numeroCasa = numeroCasa;}
    public int getNumeroCasa() {return numeroCasa;}
}
