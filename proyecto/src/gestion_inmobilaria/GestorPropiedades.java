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
 * @author luis
 */
public class GestorPropiedades {

    // Atributos
    private Map<Integer, Propiedad> propiedades;

    // Constructores
    public GestorPropiedades() {
        this.propiedades = new HashMap<>();
    }

    // <<Gestión de la Colección Propiedades>>
    public void agregarPropiedad(int id, Propiedad p) {
        if (p != null) {
            this.propiedades.put(id, p);
            System.out.println("Propiedad agregada al gestor [" + id + "]: " + p.getDescripcion());
        } else {
            System.out.println("Error: no se pudo agregar la propiedad (es nula).");
        }
    }

    public Propiedad buscarPropiedad(int id) throws ElementoNoEncontradoException {
        Propiedad p = propiedades.get(id);
        if (p == null) {
            System.out.println("Error: no se encontró una propiedad con id " + id);
            throw new ElementoNoEncontradoException();
        }
        System.out.println("Propiedad encontrada [" + id + "]: " + p.getDescripcion());
        return p;
    }

    public void eliminarPropiedad(int id) throws ElementoNoEncontradoException {
        if (!propiedades.containsKey(id)) {
            System.out.println("Error: no se encontró una propiedad con id " + id);
            throw new ElementoNoEncontradoException();
        }
        Propiedad eliminada = propiedades.remove(id);
        System.out.println("Propiedad eliminada [" + id + "]: " + eliminada.getDescripcion());
    }

    public void mostrarPropiedades() {
        System.out.println("--- Lista de propiedades (" + propiedades.size() + ") ---");
        for (Map.Entry<Integer, Propiedad> entry : propiedades.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescripcion());
        }
    }

    // TODO: definir qué campos son editables según tu interfaz.
    public void editarPropiedades(int id) throws ElementoNoEncontradoException {
        Propiedad p = buscarPropiedad(id);
        System.out.println("Editando propiedad: " + p.getDescripcion() + " (lógica de edición pendiente)");
    }

    // Get/set
    public void setPropiedades(Map<Integer, Propiedad> propiedades) {this.propiedades = propiedades;}
    public Map<Integer, Propiedad> getPropiedades() {return propiedades;}
}
