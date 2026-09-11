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
        }
    }

    public void mostrarProyectos() {
        for (ProyectoInmobiliario p : proyectos.values()) {
            System.out.println(p.getIdProyecto() + " - " + p.getNombre());
        }
    }

    // TODO: definir qué campos son editables según tu interfaz.
    public void editarProyecto(String id) throws ElementoNoEncontradoException {
        buscarProyecto(id);
        // lógica de edición pendiente
    }

    public void eliminarProyecto(String id) throws ElementoNoEncontradoException {
        if (!proyectos.containsKey(id)) {
            throw new ElementoNoEncontradoException();
        }
        proyectos.remove(id);
    }

    public ProyectoInmobiliario buscarProyecto(String id) throws ElementoNoEncontradoException {
        ProyectoInmobiliario proyecto = proyectos.get(id);
        if (proyecto == null) {
            throw new ElementoNoEncontradoException();
        }
        return proyecto;
    }

    public ProyectoInmobiliario buscarProyecto(String nombre, String id) throws ElementoNoEncontradoException {
        ProyectoInmobiliario proyecto = proyectos.get(id);
        if (proyecto == null || !proyecto.getNombre().equals(nombre)) {
            throw new ElementoNoEncontradoException();
        }
        return proyecto;
    }

    // Una propiedad física solo puede pertenecer a UN proyecto a la vez.
    // Recorre todos los proyectos y devuelve cuál (si alguno) ya tiene
    // asignada esta propiedad, o null si no está en ninguno.
    public ProyectoInmobiliario buscarProyectoDePropiedad(Propiedad propiedad) {
        for (ProyectoInmobiliario pr : proyectos.values()) {
            if (pr.getPropiedades().containsValue(propiedad)) {
                return pr;
            }
        }
        return null;
    }

    // Get/set
    public void setProyectos(Map<String, ProyectoInmobiliario> proyectos) {this.proyectos = proyectos;}
    public Map<String, ProyectoInmobiliario> getProyectos() {return proyectos;}
}
