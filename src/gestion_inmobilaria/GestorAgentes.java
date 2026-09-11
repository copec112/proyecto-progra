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
 * @author luisi
 */
public class GestorAgentes {

    // Atributos
    private Map<String, AgenteInmobiliario> agentes;

    // Constructores
    public GestorAgentes() {
        this.agentes = new HashMap<>();
    }

    // <<Gestión de la Colección AGENTES>>
    public void agregarAgentes(AgenteInmobiliario agente) {
        if (agente != null) {
            this.agentes.put(agente.getId(), agente);
            System.out.println("Agente agregado al gestor: [" + agente.getId() + "] " + agente.getNombre());
        } else {
            System.out.println("Error: no se pudo agregar el agente (es nulo).");
        }
    }

    public void mostrarAgentes() {
        System.out.println("--- Lista de agentes (" + agentes.size() + ") ---");
        for (AgenteInmobiliario a : agentes.values()) {
            System.out.println(a.getId() + " - " + a.getNombre());
        }
    }

    // TODO: definir qué campos son editables según tu interfaz.
    public void editarAgentes(String id) throws ElementoNoEncontradoException {
        AgenteInmobiliario agente = buscarAgentes(id);
        System.out.println("Editando agente: " + agente.getNombre() + " (lógica de edición pendiente)");
    }

    public void eliminarAgentes(String id) throws ElementoNoEncontradoException {
        if (!agentes.containsKey(id)) {
            System.out.println("Error: no se encontró un agente con id " + id);
            throw new ElementoNoEncontradoException();
        }
        AgenteInmobiliario eliminado = agentes.remove(id);
        System.out.println("Agente eliminado: " + eliminado.getNombre());
    }

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
    public void setAgentes(Map<String, AgenteInmobiliario> agentes) {this.agentes = agentes;}
    public Map<String, AgenteInmobiliario> getAgentes() {return agentes;}
}