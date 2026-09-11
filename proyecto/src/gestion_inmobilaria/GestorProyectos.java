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
public class GestorProyectos {

    // Atributos
    private Map<String, ProyectoInmobiliario> proyectos;

    // Constructores
    public GestorProyectos() {
        this.proyectos = new HashMap<>();
    }

    // <<Gestión de la Colección PROYECTOS>>
    public void agregarProyecto(ProyectoInmobiliario proyecto) {
        if (proyecto != null) {
            this.proyectos.put(proyecto.getIdProyecto(), proyecto);
            System.out.println("Proyecto agregado al gestor: [" + proyecto.getIdProyecto() + "] " + proyecto.getNombre());
        } else {
            System.out.println("Error: no se pudo agregar el proyecto (es nulo).");
        }
    }

    public void mostrarProyectos() {
        System.out.println("--- Lista de proyectos (" + proyectos.size() + ") ---");
        for (ProyectoInmobiliario p : proyectos.values()) {
            System.out.println(p.getIdProyecto() + " - " + p.getNombre());
        }
    }

    // TODO: definir qué campos son editables según tu interfaz.
    public void editarProyecto(String id) throws ElementoNoEncontradoException {
        ProyectoInmobiliario proyecto = buscarProyecto(id);
        System.out.println("Editando proyecto: " + proyecto.getNombre() + " (lógica de edición pendiente)");
    }

    public void eliminarProyecto(String id) throws ElementoNoEncontradoException {
        if (!proyectos.containsKey(id)) {
            System.out.println("Error: no se encontró un proyecto con id " + id);
            throw new ElementoNoEncontradoException();
        }
        ProyectoInmobiliario eliminado = proyectos.remove(id);
        System.out.println("Proyecto eliminado: " + eliminado.getNombre());
    }

    public ProyectoInmobiliario buscarProyecto(String id) throws ElementoNoEncontradoException {
        ProyectoInmobiliario proyecto = proyectos.get(id);
        if (proyecto == null) {
            System.out.println("Error: no se encontró un proyecto con id " + id);
            throw new ElementoNoEncontradoException();
        }
        System.out.println("Proyecto encontrado: " + proyecto.getNombre());
        return proyecto;
    }

    public ProyectoInmobiliario buscarProyecto(String nombre, String id) throws ElementoNoEncontradoException {
        ProyectoInmobiliario proyecto = proyectos.get(id);
        if (proyecto == null || !proyecto.getNombre().equals(nombre)) {
            System.out.println("Error: no se encontró un proyecto con nombre " + nombre + " e id " + id);
            throw new ElementoNoEncontradoException();
        }
        System.out.println("Proyecto encontrado: " + proyecto.getNombre());
        return proyecto;
    }

    // Get/set
    public void setProyectos(Map<String, ProyectoInmobiliario> proyectos) {this.proyectos = proyectos;}
    public Map<String, ProyectoInmobiliario> getProyectos() {return proyectos;}
}
