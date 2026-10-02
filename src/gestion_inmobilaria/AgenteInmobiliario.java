/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 * Representa a un agente inmobiliario, encargado de asignar propiedades a
 * clientes interesados y de concretar ventas. Cada agente mantiene su propio
 * gestor de clientes.
 *
 * @author jacor
 * tengo un problema con subir el comit xd
 */
public class AgenteInmobiliario {

    // Atributos
    private String id;
    private String nombre;
    private GestorClientes gestorClientes;

    // Constructores
    /**
     * Crea un agente vacío, con id y nombre en blanco y un gestor de
     * clientes propio recién inicializado.
     */
    public AgenteInmobiliario() {
        this.id = "";
        this.nombre = "";
        this.gestorClientes = new GestorClientes();
    }

    /**
     * Crea un agente con los datos indicados y un gestor de clientes propio
     * recién inicializado.
     *
     * @param id identificador único del agente.
     * @param nombre nombre del agente.
     */
    public AgenteInmobiliario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.gestorClientes = new GestorClientes();
        System.out.println("Agente creado: [" + id + "] " + nombre);
    }

    // <<Gestión de la coleccion>>
    /** @return el gestor de clientes propio de este agente. */
    public GestorClientes getGestorClientes() {
        return gestorClientes;
    }

    // <<Lógica de compra y venta>>
    /**
     * Marca una propiedad como de interés de un cliente, registrando al
     * cliente como interesado en ella. Si la propiedad o el cliente son
     * nulos, no hace nada y solo informa el error.
     *
     * @param propiedad la propiedad en la que el cliente muestra interés.
     * @param cliente el cliente interesado en la propiedad.
     */
    public void asignarPropiedad(Propiedad propiedad, Cliente cliente) {
        if (propiedad != null && cliente != null) {
            propiedad.registrarInteresados();
            System.out.println("Agente " + nombre + " asignó la propiedad \"" + propiedad.getDescripcion()
                    + "\" como interés del cliente " + cliente.getNombre() + ".");
        } else {
            System.out.println("Error: no se pudo asignar la propiedad (propiedad o cliente nulo).");
        }
    }

    /**
     * Concreta la venta de una propiedad a un cliente: marca la propiedad
     * como vendida y se la agrega a la lista de propiedades adquiridas del
     * cliente, generando el registro de la venta.
     *
     * @param propiedad la propiedad que se vende.
     * @param cliente el cliente que adquiere la propiedad.
     * @return la {@link Venta} generada con la propiedad, el cliente y este
     *         agente como encargado.
     * @throws PropiedadVendidaException si la propiedad ya estaba vendida.
     */
    public Venta venderPropiedad(Propiedad propiedad, Cliente cliente) throws PropiedadVendidaException {
        try {
            propiedad.setVendido(true);
        } catch (PropiedadVendidaException e) {
            System.out.println("Venta fallida: " + e.getMessage());
            throw e;
        }
        cliente.agregarPropiedad(propiedad);
        System.out.println("Venta realizada por el agente " + nombre + ": \"" + propiedad.getDescripcion()
                + "\" -> cliente " + cliente.getNombre() + ".");
        return new Venta(propiedad, cliente, this);
    }

    // Get/set
    /** @param id el nuevo id del agente. */
    public void setId(String id) {this.id = id;}
    /** @return el id del agente. */
    public String getId() {return id;}

    /** @param nombre el nuevo nombre del agente. */
    public void setNombre(String nombre) {this.nombre = nombre;}
    /** @return el nombre del agente. */
    public String getNombre() {return nombre;}
}
