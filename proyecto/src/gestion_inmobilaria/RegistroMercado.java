/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.time.LocalDateTime;

/**
 * Registro inmutable del estado de oferta/demanda de un proyecto en una fecha dada.
 *
 * @author luis
 */
public class RegistroMercado {

    // Atributos (solo lectura, según el diagrama <<get>>)
    private final LocalDateTime fecha;
    private final int ofertaTotal;
    private final int demandaTotal;

    // Constructor
    public RegistroMercado(LocalDateTime fecha, int ofertaTotal, int demandaTotal) {
        this.fecha = fecha;
        this.ofertaTotal = ofertaTotal;
        this.demandaTotal = demandaTotal;
    }

    // Get (sin set, es inmutable)
    public LocalDateTime getFecha() {return fecha;}
    public int getOfertaTotal() {return ofertaTotal;}
    public int getDemandaTotal() {return demandaTotal;}
}