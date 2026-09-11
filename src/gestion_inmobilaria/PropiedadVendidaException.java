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

    public PropiedadVendidaException() {
        super("La propiedad ya se encuentra vendida.");
    }
}
