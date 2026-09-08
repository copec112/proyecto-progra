/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;
import java.time.*;

/**
 *
 * @author jacor
 */

/**
* 
* RegistroMercado.
* Propósito   :   Crear una caputura de la oferta y demanda actual de un proyecto
*                 inmobiliario
* Parámetros  :   fecha(LocalDateTime): captura del dia y hora del sistema.
*                 ofertaTotal(int): Cantidad de propiedades sin vender del proyecto.
*                 demandaTotal(int): Suma de total de interesados de las propiedades del
*                 proyecto.
*/


public class RegistroMercado {
    private LocalDateTime fecha;
    private int ofertaTotal;
    private int demandaTotal;

    public RegistroMercado() {
        this.fecha = LocalDateTime.now();
        this.ofertaTotal = 0;
        this.demandaTotal = 0;
    }

    public RegistroMercado(LocalDateTime fecha, int ofertaTotal, int demandaTotal) {
        this.fecha = LocalDateTime.now();
        this.ofertaTotal = ofertaTotal;
        this.demandaTotal = demandaTotal;
    }

}
