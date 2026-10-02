/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 * NOTA: esta clase no aparece dibujada como caja en el diagrama UML, pero se
 * usa como tipo de retorno en AgenteInmobiliario.venderPropiedad() y como
 * elemento de la pila {@code ventas: stack<Venta>} que aparecía en la clase
 * Inmobiliaria (eliminada del proyecto por no operar en el flujo real del
 * sistema). Se crea una versión mínima; ajusta los atributos según lo que
 * realmente necesites registrar de cada venta.
 *
 * @author luisi
 */
public class Venta {

    // Atributos
    private Propiedad propiedad;
    private Cliente cliente;
    private AgenteInmobiliario agenteEncargado;

    // Constructores
    /**
     * Crea una Venta vacía, sin propiedad, cliente ni agente asociados.
     */
    public Venta() {
        this.propiedad = null;
        this.cliente = null;
        this.agenteEncargado = null;
    }

    /**
     * Crea una Venta con todos sus datos e imprime un mensaje de confirmación
     * por consola.
     *
     * @param propiedad propiedad vendida.
     * @param cliente cliente que compró la propiedad.
     * @param agenteEncargado agente inmobiliario que gestionó la venta.
     */
    public Venta(Propiedad propiedad, Cliente cliente, AgenteInmobiliario agenteEncargado) {
        this.propiedad = propiedad;
        this.cliente = cliente;
        this.agenteEncargado = agenteEncargado;
        System.out.println("Venta registrada: \"" + propiedad.getDescripcion() + "\" a " + cliente.getNombre()
                + " (agente: " + agenteEncargado.getNombre() + ")");
    }

    // Get/set
    /** @param propiedad la propiedad vendida a asociar con esta venta. */
    public void setPropiedad(Propiedad propiedad) {this.propiedad = propiedad;}
    /** @return la propiedad vendida en esta venta. */
    public Propiedad getPropiedad() {return propiedad;}

    /** @param cliente el cliente comprador a asociar con esta venta. */
    public void setCliente(Cliente cliente) {this.cliente = cliente;}
    /** @return el cliente que compró la propiedad. */
    public Cliente getCliente() {return cliente;}

    /** @param agenteEncargado el agente a cargo a asociar con esta venta. */
    public void setAgenteEncargado(AgenteInmobiliario agenteEncargado) {this.agenteEncargado = agenteEncargado;}
    /** @return el agente inmobiliario que gestionó la venta. */
    public AgenteInmobiliario getAgenteEncargado() {return agenteEncargado;}
}