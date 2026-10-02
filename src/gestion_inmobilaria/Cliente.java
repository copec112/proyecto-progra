/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 * Representa a un cliente del sistema inmobiliario, identificado por un id
 * único y un nombre, que puede ir adquiriendo propiedades a lo largo del
 * tiempo.
 *
 * @author jacor
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente {
    // Atributos
    private String id;
    private String nombre;
    private List<Propiedad> propiedadesAdquiridas;

    // Constructores
    /**
     * Crea un cliente vacío, con id y nombre en blanco y sin propiedades
     * adquiridas.
     */
    public Cliente(){
        this.id = "";
        this.nombre = "";
        propiedadesAdquiridas = new ArrayList<>();
    }
    /**
     * Crea un cliente con los datos indicados.
     *
     * @param id identificador único del cliente.
     * @param nombre nombre del cliente.
     */
    public Cliente(String id, String nombre){
        this.id = id;
        this.nombre = nombre;
        propiedadesAdquiridas = new ArrayList<>();
        System.out.println("Cliente creado: [" + id + "] " + nombre);
    }

    // Solo se puede agregar para no complejizar por el scope del prouyecto
    /**
     * Agrega una propiedad a la lista de propiedades adquiridas por este
     * cliente. Si la propiedad es nula, no hace nada y solo informa el error.
     *
     * @param propiedad la propiedad adquirida por el cliente.
     */
    public void agregarPropiedad(Propiedad propiedad){
        if (propiedad != null) {
            this.propiedadesAdquiridas.add(propiedad);
            System.out.println("Cliente " + nombre + " adquirió la propiedad \"" + propiedad.getDescripcion()
                    + "\". Total propiedades: " + propiedadesAdquiridas.size());
        } else {
            System.out.println("Error: no se pudo agregar la propiedad (es nula).");
        }
    }
    
    // Get/set
    // getter: public type get(){return x;}
    // setter: public void set(type x){this.x = x;}
    /** @param id el nuevo id del cliente. */
    public void setId(String id){this.id = id;}
    /** @return el id del cliente. */
    public String getId(){return id;}

    /** @param nombre el nuevo nombre del cliente. */
    public void setNombre(String nombre){this.nombre = nombre;}
    /** @return el nombre del cliente. */
    public String getNombre(){return nombre;}

    // Vista de solo lectura: evita que código externo agregue propiedades
    // saltándose agregarPropiedad() (rompería el encapsulamiento).
    /**
     * Entrega una vista de solo lectura de las propiedades adquiridas por
     * el cliente, para evitar que se modifique la colección interna sin
     * pasar por {@link #agregarPropiedad(Propiedad)}.
     *
     * @return lista inmodificable de propiedades adquiridas.
     */
    public List<Propiedad> getPropiedadesAdquiridas() {return Collections.unmodifiableList(propiedadesAdquiridas);}
    /** @param propiedadesAdquiridas la nueva lista de propiedades adquiridas. */
    public void setPropiedadesAdquiridas(List<Propiedad> propiedadesAdquiridas) {this.propiedadesAdquiridas = propiedadesAdquiridas;}
}
