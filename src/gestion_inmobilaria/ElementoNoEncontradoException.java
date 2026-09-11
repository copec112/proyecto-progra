/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 * Se lanza cuando no se encuentra un elemento buscado en una colección
 * (agente, proyecto, cliente o propiedad).
 *
 * @author jacor
 */
public class ElementoNoEncontradoException extends Exception {

    public ElementoNoEncontradoException() {
        super("El elemento solicitado no fue encontrado.");
    }
}