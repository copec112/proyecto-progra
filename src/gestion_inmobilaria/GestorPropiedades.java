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
 * Administra la colección de propiedades del sistema, indexadas por un id
 * numérico, permitiendo agregarlas, buscarlas, listarlas y eliminarlas.
 *
 * @author luis
 */
public class GestorPropiedades {

    // Atributos
    private Map<Integer, Propiedad> propiedades;

    // Constructores
    /**
     * Crea un gestor de propiedades con la colección interna vacía.
     */
    public GestorPropiedades() {
        this.propiedades = new HashMap<>();
    }

    // <<Gestión de la Colección Propiedades>>
    /**
     * Agrega una propiedad a la colección, usando el id indicado como
     * llave. Si la propiedad es nula, no hace nada y solo informa el error.
     *
     * @param id id con el que se registra la propiedad.
     * @param p la propiedad a agregar.
     */
    public void agregarPropiedad(int id, Propiedad p) {
        if (p != null) {
            this.propiedades.put(id, p);
            System.out.println("Propiedad agregada al gestor [" + id + "]: " + p.getDescripcion());
        } else {
            System.out.println("Error: no se pudo agregar la propiedad (es nula).");
        }
    }

    /**
     * Busca una propiedad por su id.
     *
     * @param id id de la propiedad a buscar.
     * @return la propiedad encontrada.
     * @throws ElementoNoEncontradoException si no existe una propiedad con ese id.
     */
    public Propiedad buscarPropiedad(int id) throws ElementoNoEncontradoException {
        Propiedad p = propiedades.get(id);
        if (p == null) {
            System.out.println("Error: no se encontró una propiedad con id " + id);
            throw new ElementoNoEncontradoException();
        }
        System.out.println("Propiedad encontrada [" + id + "]: " + p.getDescripcion());
        return p;
    }

    /**
     * Elimina de la colección la propiedad con el id indicado.
     *
     * @param id id de la propiedad a eliminar.
     * @throws ElementoNoEncontradoException si no existe una propiedad con ese id.
     */
    public void eliminarPropiedad(int id) throws ElementoNoEncontradoException {
        if (!propiedades.containsKey(id)) {
            System.out.println("Error: no se encontró una propiedad con id " + id);
            throw new ElementoNoEncontradoException();
        }
        Propiedad eliminada = propiedades.remove(id);
        System.out.println("Propiedad eliminada [" + id + "]: " + eliminada.getDescripcion());
    }

    /**
     * Imprime en consola la lista completa de propiedades registradas, con
     * su id y descripción.
     */
    public void mostrarPropiedades() {
        System.out.println("--- Lista de propiedades (" + propiedades.size() + ") ---");
        for (Map.Entry<Integer, Propiedad> entry : propiedades.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescripcion());
        }
    }

    // TODO: definir qué campos son editables según tu interfaz.
    /**
     * Busca la propiedad con el id indicado para editarla (la lógica de
     * edición concreta queda pendiente).
     *
     * @param id id de la propiedad a editar.
     * @throws ElementoNoEncontradoException si no existe una propiedad con ese id.
     */
    public void editarPropiedades(int id) throws ElementoNoEncontradoException {
        Propiedad p = buscarPropiedad(id);
        System.out.println("Editando propiedad: " + p.getDescripcion() + " (lógica de edición pendiente)");
    }

    // Get/set
    /** @param propiedades la nueva colección de propiedades. */
    public void setPropiedades(Map<Integer, Propiedad> propiedades) {this.propiedades = propiedades;}

    // Vista de solo lectura (ver GestorClientes.getClientes() para el motivo).
    /**
     * Entrega una vista de solo lectura de la colección de propiedades,
     * para evitar que se modifique directamente saltándose los métodos del
     * gestor.
     *
     * @return mapa inmodificable de propiedades indexadas por id.
     */
    public Map<Integer, Propiedad> getPropiedades() {return Collections.unmodifiableMap(propiedades);}
}
