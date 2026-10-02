/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 * Interfaz genérica para la gestión de colecciones (agentes, proyectos,
 * clientes, propiedades, etc.).
 * <p>
 * T = El tipo de objeto (Cliente, Propiedad, etc.)
 * K = El tipo de dato de la clave o ID (String, Integer, etc.)
 * <p>
 * EJEMPLO DE USO
 * <pre>
 * AgenteInmobiliario agente = new AgenteInmobiliario("001", "Juan Perez");
 * agente.getGestorClientes().agregar(nuevoCliente);
 * agente.getGestorClientes().mostrarTodos();
 * agente.getGestorClientes().editar("ID-123");
 * </pre>
 *
 * @param <T> tipo de objeto gestionado por la colección.
 * @param <K> tipo de dato de la clave o ID que identifica a cada objeto.
 * @author luis
 */
public interface GestorColeccion<T, K> {

    /**
     * Agrega un nuevo elemento a la colección.
     *
     * @param elemento elemento a agregar.
     */
    void agregar(T elemento);

    /**
     * Busca un elemento dentro de la colección a partir de su clave/ID.
     *
     * @param id clave o identificador del elemento buscado.
     * @return el elemento encontrado, o {@code null} si no existe.
     */
    T buscar(K id);

    /**
     * Elimina de la colección el elemento asociado a la clave/ID indicada.
     *
     * @param id clave o identificador del elemento a eliminar.
     */
    void eliminar(K id);

    /**
     * Muestra (por consola u otro medio) todos los elementos de la colección.
     */
    void mostrarTodos();

    /**
     * Permite editar el elemento asociado a la clave/ID indicada.
     *
     * @param id clave o identificador del elemento a editar.
     */
    void editar(K id);
}

