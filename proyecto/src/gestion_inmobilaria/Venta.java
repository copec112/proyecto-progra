/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 * NOTA: esta clase no aparece dibujada como caja en el diagrama UML, pero se
 * usa como tipo de retorno en AgenteInmobiliario.venderPropiedad() y como
 * elemento de la pila "ventas: stack<Venta>" en Inmobiliaria. Se crea una
 * versión mínima; ajusta los atributos según lo que realmente necesites
 * registrar de cada venta.
 *
 * @author jacor
 */
public class Venta {

    // Atributos
    private Propiedad propiedad;
    private Cliente cliente;
    private AgenteInmobiliario agenteEncargado;

    // Constructores
    public Venta() {
        this.propiedad = null;
        this.cliente = null;
        this.agenteEncargado = null;
    }

    public Venta(Propiedad propiedad, Cliente cliente, AgenteInmobiliario agenteEncargado) {
        this.propiedad = propiedad;
        this.cliente = cliente;
        this.agenteEncargado = agenteEncargado;
        System.out.println("Venta registrada: \"" + propiedad.getDescripcion() + "\" a " + cliente.getNombre()
                + " (agente: " + agenteEncargado.getNombre() + ")");
    }

    // Get/set
    public void setPropiedad(Propiedad propiedad) {this.propiedad = propiedad;}
    public Propiedad getPropiedad() {return propiedad;}

    public void setCliente(Cliente cliente) {this.cliente = cliente;}
    public Cliente getCliente() {return cliente;}

    public void setAgenteEncargado(AgenteInmobiliario agenteEncargado) {this.agenteEncargado = agenteEncargado;}
    public AgenteInmobiliario getAgenteEncargado() {return agenteEncargado;}
}