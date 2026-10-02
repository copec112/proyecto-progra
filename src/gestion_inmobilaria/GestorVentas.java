/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Registro central de todas las ventas realizadas, para poder mostrar
 * en la ventana qué agente vendió cada propiedad y a qué cliente.
 *
 * No estaba en el UML original como caja aparte, pero es necesario para
 * poder consultar el historial de ventas desde la ventana (antes esa
 * información se perdía apenas se cerraba el programa).
 *
 * @author luisi
 */
public class GestorVentas {

    private List<Venta> ventas;

    /**
     * Crea un GestorVentas con la lista de ventas vacía.
     */
    public GestorVentas() {
        this.ventas = new ArrayList<>();
    }

    /**
     * Agrega una venta al registro, ignorando el valor si es {@code null}.
     *
     * @param venta la venta a registrar.
     */
    public void agregarVenta(Venta venta) {
        if (venta != null) {
            this.ventas.add(venta);
        }
    }

    // Busca si una propiedad ya tiene una venta registrada (y por lo tanto, un agente)
    /**
     * Busca, entre las ventas registradas, si una propiedad dada ya fue
     * vendida (y por lo tanto tiene un agente y cliente asociados).
     *
     * @param propiedad propiedad a buscar entre las ventas registradas.
     * @return la Venta asociada a esa propiedad, o {@code null} si no se
     *         encuentra ninguna venta registrada para ella.
     */
    public Venta buscarVentaPorPropiedad(Propiedad propiedad) {
        for (Venta v : ventas) {
            if (v.getPropiedad() == propiedad) {
                return v;
            }
        }
        return null;
    }

    // Vista de solo lectura (ver GestorClientes.getClientes() para el motivo).
    /**
     * Entrega una vista de solo lectura de todas las ventas registradas.
     *
     * @return lista inmodificable con todas las ventas registradas.
     */
    public List<Venta> getVentas() {
        return Collections.unmodifiableList(ventas);
    }

    /**
     * Reemplaza por completo la lista de ventas registradas.
     *
     * @param ventas nueva lista de ventas a utilizar.
     */
    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }
}
