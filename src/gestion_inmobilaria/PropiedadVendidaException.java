/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 * Se lanza al intentar vender una propiedad que ya fue vendida (vendido == true).
 * Debe capturarse con try-catch.
 *
 * @author luis
 */
public class PropiedadVendidaException extends Exception {

    /**
     * Crea la excepción con el mensaje genérico por defecto, indicando que
     * la propiedad ya se encuentra vendida.
     */
    public PropiedadVendidaException() {
        super("La propiedad ya se encuentra vendida.");
    }

    // Sobrecarga: permite un mensaje específico (ej. para distinguir el motivo
    // de rechazo entre Casa y Departamento) sin necesidad de crear una
    // excepción nueva para cada caso.
    /**
     * Crea la excepción con un mensaje específico, permitiendo indicar el
     * motivo exacto del rechazo (por ejemplo, para distinguir entre Casa y
     * Departamento) sin necesidad de una excepción nueva para cada caso.
     *
     * @param mensaje detalle del motivo por el que se rechaza la venta.
     */
    public PropiedadVendidaException(String mensaje) {
        super(mensaje);
    }
}
