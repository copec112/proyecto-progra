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
    /**
     * Crea un registro inmutable con el estado de oferta/demanda de un
     * proyecto en un instante determinado.
     *
     * @param fecha fecha y hora en que se toma el registro.
     * @param ofertaTotal cantidad total de propiedades ofertadas en ese momento.
     * @param demandaTotal cantidad total de interesados/demanda en ese momento.
     */
    public RegistroMercado(LocalDateTime fecha, int ofertaTotal, int demandaTotal) {
        this.fecha = fecha;
        this.ofertaTotal = ofertaTotal;
        this.demandaTotal = demandaTotal;
    }

    // Get (sin set, es inmutable)
    /** @return la fecha y hora en que se tomó el registro. */
    public LocalDateTime getFecha() {return fecha;}
    /** @return la oferta total registrada en ese momento. */
    public int getOfertaTotal() {return ofertaTotal;}
    /** @return la demanda total registrada en ese momento. */
    public int getDemandaTotal() {return demandaTotal;}
}
