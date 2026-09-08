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
 *
 * @author luisi
 */

/**
 * AgenteInmobiliario
 * 
*/

public class AgenteInmobiliario {
    private String id;
    private String nombre;
    private Map<String, Cliente> clientes;


    // Constructor
    public AgenteInmobiliario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.clientes = new HashMap<>();
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    // Setter

    public void setId(String id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * 
     * Metodos
     * */ 

    // Lógica de compra y venta 

    public void venderPropiedad(){

    }
    public void asignarDepartamento(){
    }
}
