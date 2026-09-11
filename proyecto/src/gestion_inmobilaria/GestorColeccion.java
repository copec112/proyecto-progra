/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 *
 * @author jacor
 */

/**
 * Gestor Coleccion (interface)
 * @param <T>
 * @param <K>
 * Interfaz genérica para la gestión de colecciones
 * T = El tipo de objeto (Cliente, Propiedad, etc.)
 * K = El tipo de dato de la clave o ID (String, Integer, etc.)
 */
/**
 * 
 * EJEMPLO DE USO
 * 
 * ´´´
 * AgenteInmobiliario agente = new AgenteInmobiliario("001", "Juan Perez");
 * agente.getGestorClientes().agregar(nuevoCliente);
 * agente.getGestorClientes().mostrarTodos();
 * agente.getGestorClientes().editar("ID-123");
 * ´´´
 */

public interface GestorColeccion<T, K> {
    void agregar(T elemento);
    T buscar(K id);
    void eliminar(K id);
    void mostrarTodos();
    void editar(K id);
}

