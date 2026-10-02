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
 * Administra la colección de agentes inmobiliarios del sistema, indexados
 * por su id, permitiendo agregarlos, buscarlos, listarlos y eliminarlos.
 *
 * @author luisi
 */
public class GestorAgentes {

    // Atributos
    private Map<String, AgenteInmobiliario> agentes;

    // Constructores
    /**
     * Crea un gestor de agentes con la colección interna vacía.
     */
    public GestorAgentes() {
        this.agentes = new HashMap<>();
    }

    // <<Gestión de la Colección AGENTES>>
    /**
     * Agrega un agente a la colección, usando su id como llave. Si el
     * agente es nulo, no hace nada y solo informa el error.
     *
     * @param agente el agente a agregar.
     */
    public void agregarAgentes(AgenteInmobiliario agente) {
        if (agente != null) {
            this.agentes.put(agente.getId(), agente);
            System.out.println("Agente agregado al gestor: [" + agente.getId() + "] " + agente.getNombre());
        } else {
            System.out.println("Error: no se pudo agregar el agente (es nulo).");
        }
    }

    /**
     * Imprime en consola la lista completa de agentes registrados, con su
     * id y nombre.
     */
    public void mostrarAgentes() {
        System.out.println("--- Lista de agentes (" + agentes.size() + ") ---");
        for (AgenteInmobiliario a : agentes.values()) {
            System.out.println(a.getId() + " - " + a.getNombre());
        }
    }

    // TODO: definir qué campos son editables según tu interfaz.
    /**
     * Busca el agente con el id indicado para editarlo (la lógica de
     * edición concreta queda pendiente).
     *
     * @param id id del agente a editar.
     * @throws ElementoNoEncontradoException si no existe un agente con ese id.
     */
    public void editarAgentes(String id) throws ElementoNoEncontradoException {
        AgenteInmobiliario agente = buscarAgentes(id);
        System.out.println("Editando agente: " + agente.getNombre() + " (lógica de edición pendiente)");
    }

    /**
     * Elimina de la colección el agente con el id indicado.
     *
     * @param id id del agente a eliminar.
     * @throws ElementoNoEncontradoException si no existe un agente con ese id.
     */
    public void eliminarAgentes(String id) throws ElementoNoEncontradoException {
        if (!agentes.containsKey(id)) {
            System.out.println("Error: no se encontró un agente con id " + id);
            throw new ElementoNoEncontradoException();
        }
        AgenteInmobiliario eliminado = agentes.remove(id);
        System.out.println("Agente eliminado: " + eliminado.getNombre());
    }

    /**
     * Busca un agente por su id.
     *
     * @param id id del agente a buscar.
     * @return el agente encontrado.
     * @throws ElementoNoEncontradoException si no existe un agente con ese id.
     */
    public AgenteInmobiliario buscarAgentes(String id) throws ElementoNoEncontradoException {
        AgenteInmobiliario agente = agentes.get(id);
        if (agente == null) {
            System.out.println("Error: no se encontró un agente con id " + id);
            throw new ElementoNoEncontradoException();
        }
        System.out.println("Agente encontrado: " + agente.getNombre());
        return agente;
    }

    // Get/set
    /** @param agentes la nueva colección de agentes. */
    public void setAgentes(Map<String, AgenteInmobiliario> agentes) {this.agentes = agentes;}

    // Vista de solo lectura (ver GestorClientes.getClientes() para el motivo).
    /**
     * Entrega una vista de solo lectura de la colección de agentes, para
     * evitar que se modifique directamente saltándose los métodos del
     * gestor.
     *
     * @return mapa inmodificable de agentes indexados por id.
     */
    public Map<String, AgenteInmobiliario> getAgentes() {return Collections.unmodifiableMap(agentes);}
}