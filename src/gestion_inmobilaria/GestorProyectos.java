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
 * Gestiona la colección de proyectos inmobiliarios del sistema: permite
 * agregar, buscar, editar y eliminar proyectos, además de ubicar en qué
 * proyecto (si alguno) está asignada una propiedad determinada.
 *
 * @author jacor
 */
public class GestorProyectos {

    // Atributos
    private Map<String, ProyectoInmobiliario> proyectos;

    // Constructores
    /**
     * Crea un gestor de proyectos vacío, listo para recibir proyectos.
     */
    public GestorProyectos() {
        this.proyectos = new HashMap<>();
    }

    // <<Gestión de la Colección PROYECTOS>>
    /**
     * Agrega un proyecto a la colección, usando su ID como clave. Si el
     * proyecto recibido es {@code null}, no hace nada.
     *
     * @param proyecto proyecto inmobiliario a agregar.
     */
    public void agregarProyecto(ProyectoInmobiliario proyecto) {
        if (proyecto != null) {
            this.proyectos.put(proyecto.getIdProyecto(), proyecto);
        }
    }

    /**
     * Imprime en consola el ID y nombre de todos los proyectos registrados.
     */
    public void mostrarProyectos() {
        for (ProyectoInmobiliario p : proyectos.values()) {
            System.out.println(p.getIdProyecto() + " - " + p.getNombre());
        }
    }

    // TODO: definir qué campos son editables según tu interfaz.
    /**
     * Edita un proyecto existente. Actualmente solo valida que el proyecto
     * exista; la lógica de edición de campos está pendiente.
     *
     * @param id identificador del proyecto a editar.
     * @throws ElementoNoEncontradoException si no existe un proyecto con ese id.
     */
    public void editarProyecto(String id) throws ElementoNoEncontradoException {
        buscarProyecto(id);
        // lógica de edición pendiente
    }

    /**
     * Elimina de la colección el proyecto con el id indicado.
     *
     * @param id identificador del proyecto a eliminar.
     * @throws ElementoNoEncontradoException si no existe un proyecto con ese id.
     */
    public void eliminarProyecto(String id) throws ElementoNoEncontradoException {
        if (!proyectos.containsKey(id)) {
            throw new ElementoNoEncontradoException();
        }
        proyectos.remove(id);
    }

    /**
     * Busca un proyecto por su id.
     *
     * @param id identificador del proyecto buscado.
     * @return el proyecto encontrado.
     * @throws ElementoNoEncontradoException si no existe un proyecto con ese id.
     */
    public ProyectoInmobiliario buscarProyecto(String id) throws ElementoNoEncontradoException {
        ProyectoInmobiliario proyecto = proyectos.get(id);
        if (proyecto == null) {
            throw new ElementoNoEncontradoException();
        }
        return proyecto;
    }

    /**
     * Busca un proyecto verificando id y nombre a la vez, para confirmar
     * que el nombre corresponde al id antes de dar por válido el resultado.
     *
     * @param nombre nombre esperado del proyecto.
     * @param id identificador del proyecto buscado.
     * @return el proyecto encontrado, si el id y el nombre coinciden.
     * @throws ElementoNoEncontradoException si no existe un proyecto con ese id
     *         o si el nombre no coincide con el del proyecto encontrado.
     */
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
    /**
     * Recorre todos los proyectos y devuelve cuál (si alguno) ya tiene
     * asignada la propiedad indicada.
     *
     * @param propiedad propiedad que se busca entre los proyectos.
     * @return el proyecto que contiene la propiedad, o {@code null} si
     *         no está asignada a ninguno.
     */
    public ProyectoInmobiliario buscarProyectoDePropiedad(Propiedad propiedad) {
        for (ProyectoInmobiliario pr : proyectos.values()) {
            if (pr.getPropiedades().containsValue(propiedad)) {
                return pr;
            }
        }
        return null;
    }

    // Get/set
    /**
     * Reemplaza la colección completa de proyectos.
     *
     * @param proyectos nuevo mapa de proyectos (clave: id de proyecto).
     */
    public void setProyectos(Map<String, ProyectoInmobiliario> proyectos) {this.proyectos = proyectos;}

    // Vista de solo lectura (ver GestorClientes.getClientes() para el motivo).
    /**
     * Devuelve una vista de solo lectura de los proyectos registrados.
     *
     * @return mapa inmutable de proyectos (clave: id de proyecto).
     */
    public Map<String, ProyectoInmobiliario> getProyectos() {return Collections.unmodifiableMap(proyectos);}
}
