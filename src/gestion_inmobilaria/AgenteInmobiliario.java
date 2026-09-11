/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
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
    public AgenteInmobiliario() {
        this.id = "";
        this.nombre = "";
        this.gestorClientes = new GestorClientes();
    }

    public AgenteInmobiliario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.gestorClientes = new GestorClientes();
        System.out.println("Agente creado: [" + id + "] " + nombre);
    }

    // <<Gestión de la coleccion>>
    public GestorClientes getGestorClientes() {
        return gestorClientes;
    }

    // <<Lógica de compra y venta>>
    public void asignarPropiedad(Propiedad propiedad, Cliente cliente) {
        if (propiedad != null && cliente != null) {
            propiedad.registrarInteresados();
            System.out.println("Agente " + nombre + " asignó la propiedad \"" + propiedad.getDescripcion()
                    + "\" como interés del cliente " + cliente.getNombre() + ".");
        } else {
            System.out.println("Error: no se pudo asignar la propiedad (propiedad o cliente nulo).");
        }
    }

    public Venta venderPropiedad(Propiedad propiedad, Cliente cliente) throws PropiedadVendidaException {
        try {
            propiedad.setVendido(true);
        } catch (PropiedadVendidaException e) {
            System.out.println("Venta fallida: la propiedad ya estaba vendida.");
            throw e;
        }
        cliente.agregarPropiedad(propiedad);
        System.out.println("Venta realizada por el agente " + nombre + ": \"" + propiedad.getDescripcion()
                + "\" -> cliente " + cliente.getNombre() + ".");
        return new Venta(propiedad, cliente, this);
    }

    // Get/set
    public void setId(String id) {this.id = id;}
    public String getId() {return id;}

    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getNombre() {return nombre;}
}
