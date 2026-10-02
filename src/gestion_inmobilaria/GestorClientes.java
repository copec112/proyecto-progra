/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Administra la colección de clientes del sistema, indexados por su id,
 * permitiendo agregarlos, buscarlos, listarlos y eliminarlos.
 *
 * @author luisi
 */
public class GestorClientes {

    // Atributos
    private Map<String, Cliente> clientes;

    // Constructores
    /**
     * Crea un gestor de clientes con la colección interna vacía.
     */
    public GestorClientes() {
        this.clientes = new HashMap<>();
    }

    // <<Gestión de la Colección CLIENTES>>
    /**
     * Agrega un cliente a la colección, usando su id como llave. Si el
     * cliente es nulo, no hace nada y solo informa el error.
     *
     * @param cliente el cliente a agregar.
     */
    public void agregarCliente(Cliente cliente) {
        if (cliente != null) {
            this.clientes.put(cliente.getId(), cliente);
            System.out.println("Cliente agregado al gestor: [" + cliente.getId() + "] " + cliente.getNombre());
        } else {
            System.out.println("Error: no se pudo agregar el cliente (es nulo).");
        }
    }

    /**
     * Imprime en consola la lista completa de clientes registrados, con su
     * id y nombre.
     */
    public void mostrarCliente() {
        System.out.println("--- Lista de clientes (" + clientes.size() + ") ---");
        for (Cliente c : clientes.values()) {
            System.out.println(c.getId() + " - " + c.getNombre());
        }
    }

    // TODO: definir qué campos son editables y pedirlos/recibirlos según tu interfaz.
    /**
     * Busca el cliente con el id indicado para editarlo (la lógica de
     * edición concreta queda pendiente).
     *
     * @param id id del cliente a editar.
     * @throws ElementoNoEncontradoException si no existe un cliente con ese id.
     */
    public void editarCliente(String id) throws ElementoNoEncontradoException {
        Cliente cliente = buscarCliente(id);
        System.out.println("Editando cliente: " + cliente.getNombre() + " (lógica de edición pendiente)");
    }

    /**
     * Elimina de la colección el cliente con el id indicado.
     *
     * @param id id del cliente a eliminar.
     * @throws ElementoNoEncontradoException si no existe un cliente con ese id.
     */
    public void eliminarCliente(String id) throws ElementoNoEncontradoException {
        if (!clientes.containsKey(id)) {
            System.out.println("Error: no se encontró un cliente con id " + id);
            throw new ElementoNoEncontradoException();
        }
        Cliente eliminado = clientes.remove(id);
        System.out.println("Cliente eliminado: " + eliminado.getNombre());
    }

    /**
     * Busca un cliente por su id.
     *
     * @param id id del cliente a buscar.
     * @return el cliente encontrado.
     * @throws ElementoNoEncontradoException si no existe un cliente con ese id.
     */
    public Cliente buscarCliente(String id) throws ElementoNoEncontradoException {
        Cliente cliente = clientes.get(id);
        if (cliente == null) {
            System.out.println("Error: no se encontró un cliente con id " + id);
            throw new ElementoNoEncontradoException();
        }
        System.out.println("Cliente encontrado: " + cliente.getNombre());
        return cliente;
    }

    /**
     * Busca un cliente verificando tanto su id como su nombre.
     *
     * @param nombre nombre esperado del cliente.
     * @param id id del cliente a buscar.
     * @return el cliente encontrado.
     * @throws ElementoNoEncontradoException si no existe un cliente con ese
     *         id, o si el nombre no coincide con el registrado.
     */
    public Cliente buscarCliente(String nombre, String id) throws ElementoNoEncontradoException {
        Cliente cliente = clientes.get(id);
        if (cliente == null || !cliente.getNombre().equals(nombre)) {
            System.out.println("Error: no se encontró un cliente con nombre " + nombre + " e id " + id);
            throw new ElementoNoEncontradoException();
        }
        System.out.println("Cliente encontrado: " + cliente.getNombre());
        return cliente;
    }

    // Get/set
    /** @param clientes la nueva colección de clientes. */
    public void setClientes(Map<String, Cliente> clientes) {this.clientes = clientes;}

    // Vista de solo lectura: evita que el código externo agregue/quite
    // clientes directamente sobre la colección interna, saltándose
    // agregarCliente()/eliminarCliente() (rompería el encapsulamiento).
    /**
     * Entrega una vista de solo lectura de la colección de clientes, para
     * evitar que se modifique directamente saltándose los métodos del
     * gestor.
     *
     * @return mapa inmodificable de clientes indexados por id.
     */
    public Map<String, Cliente> getClientes() {return Collections.unmodifiableMap(clientes);}
}
