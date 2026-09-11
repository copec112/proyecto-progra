/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jacor
 */
public class GestorClientes {

    // Atributos
    private Map<String, Cliente> clientes;

    // Constructores
    public GestorClientes() {
        this.clientes = new HashMap<>();
    }

    // <<Gestión de la Colección CLIENTES>>
    public void agregarCliente(Cliente cliente) {
        if (cliente != null) {
            this.clientes.put(cliente.getId(), cliente);
            System.out.println("Cliente agregado al gestor: [" + cliente.getId() + "] " + cliente.getNombre());
        } else {
            System.out.println("Error: no se pudo agregar el cliente (es nulo).");
        }
    }

    public void mostrarCliente() {
        System.out.println("--- Lista de clientes (" + clientes.size() + ") ---");
        for (Cliente c : clientes.values()) {
            System.out.println(c.getId() + " - " + c.getNombre());
        }
    }

    // TODO: definir qué campos son editables y pedirlos/recibirlos según tu interfaz.
    public void editarCliente(String id) throws ElementoNoEncontradoException {
        Cliente cliente = buscarCliente(id);
        System.out.println("Editando cliente: " + cliente.getNombre() + " (lógica de edición pendiente)");
    }

    public void eliminarCliente(String id) throws ElementoNoEncontradoException {
        if (!clientes.containsKey(id)) {
            System.out.println("Error: no se encontró un cliente con id " + id);
            throw new ElementoNoEncontradoException();
        }
        Cliente eliminado = clientes.remove(id);
        System.out.println("Cliente eliminado: " + eliminado.getNombre());
    }

    public Cliente buscarCliente(String id) throws ElementoNoEncontradoException {
        Cliente cliente = clientes.get(id);
        if (cliente == null) {
            System.out.println("Error: no se encontró un cliente con id " + id);
            throw new ElementoNoEncontradoException();
        }
        System.out.println("Cliente encontrado: " + cliente.getNombre());
        return cliente;
    }

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
    public void setClientes(Map<String, Cliente> clientes) {this.clientes = clientes;}
    public Map<String, Cliente> getClientes() {return clientes;}
}