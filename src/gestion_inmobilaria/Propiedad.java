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
    public Propiedad() {
        this.descripcion = "";
        this.numHabitaciones = 0;
        this.numBaños = 0;
        this.valorUF = 0;
        this.numInteresados = 0;
        this.vendido = false;
        this.estacionamiento = false;
    }

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
    public void registrarInteresados() {
        this.numInteresados++;
        System.out.println("Se registró un nuevo interesado en \"" + descripcion + "\". Total interesados: " + numInteresados);
    }

    // Cada subclase (Casa, Departamento) decide cómo marcar la venta,
    // y debe lanzar PropiedadVendidaException si ya estaba vendida.
    public abstract void setVendido(boolean vendido) throws PropiedadVendidaException;

    // TODO: ajustar la fórmula real según la lógica de negocio del proyecto.
    // Se deja un cálculo simple como base (a modificar).
    public int calcularPrecioActual(int valorUF, int numInteresados) {
        int precio = valorUF + (numInteresados * 1); // placeholder
        System.out.println("Precio actual calculado para \"" + descripcion + "\": " + precio + " UF");
        return precio;
    }

    // Get/set
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public String getDescripcion() {return descripcion;}

    public void setNumHabitaciones(int numHabitaciones) {this.numHabitaciones = numHabitaciones;}
    public int getNumHabitaciones() {return numHabitaciones;}

    public void setNumBaños(int numBaños) {this.numBaños = numBaños;}
    public int getNumBaños() {return numBaños;}

    public void setValorUF(int valorUF) {this.valorUF = valorUF;}
    public int getValorUF() {return valorUF;}

    public void setNumInteresados(int numInteresados) {this.numInteresados = numInteresados;}
    public int getNumInteresados() {return numInteresados;}

    // Solo getter: el diagrama marca <<get>> para vendido (el set real es setVendido())
    public boolean isVendido() {return vendido;}

    public void setEstacionamiento(boolean estacionamiento) {this.estacionamiento = estacionamiento;}
    public boolean isEstacionamiento() {return estacionamiento;}
}