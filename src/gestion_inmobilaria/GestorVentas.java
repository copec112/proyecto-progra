/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.util.ArrayList;
import java.util.List;

/**
 * Registro central de todas las ventas realizadas, para poder mostrar
 * en la ventana qué agente vendió cada propiedad y a qué cliente.
 *
 * No estaba en el UML original como caja aparte, pero es necesario para
 * poder consultar el historial de ventas desde la ventana (antes esa
 * información se perdía apenas se cerraba el programa).
 *
 * @author jacor
 */
public class GestorVentas {

    private List<Venta> ventas;

    public GestorVentas() {
        this.ventas = new ArrayList<>();
    }

    public void agregarVenta(Venta venta) {
        if (venta != null) {
            this.ventas.add(venta);
        }
    }

    // Busca si una propiedad ya tiene una venta registrada (y por lo tanto, un agente)
    public Venta buscarVentaPorPropiedad(Propiedad propiedad) {
        for (Venta v : ventas) {
            if (v.getPropiedad() == propiedad) {
                return v;
            }
        }
        return null;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }
}
