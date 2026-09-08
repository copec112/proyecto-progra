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
    private Map<String, Cliente> mapaClientes;


    // Constructor
    public AgenteInmobiliario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.mapaClientes = new HashMap<>();
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Retorna una vista de solo lectura para proteger la colección interna
    public Map<String, Cliente> getClientes() {
        return Collections.unmodifiableMap(mapaClientes);
    }

    /**
     * 
     * Metodos
     * */ 

    // Lógica de compra y venta 

    
    public void asignarDepartamento(Propiedad propiedad, Cliente cliente){
        if (!propiedad.setVendido()){
            cliente.agregarPropiedad(propiedad)
            System.out.println("La propiedad fue vendida exitosamente.");
            return;
        }
        System.out.println("Error, la propiedad ya esta vendida");
        return;
    }
    
    public void venderPropiedad(Propiedad propiedad){
        propiedad.setVendida(true);
        return;
    }

    

    // Manejo de coleccion de clientes

    void agregarCliente(Cliente cliente){
        mapaClientes.put(cliente.getId(), cliente.getNombre());
    }
    void mostrarCliente(){
        for (String i : mapaClientes.values()) {
            System.out.println(i);
        }
    }
    void editarCliente(String id){}
    void eliminarCliente(String id){}
    Cliente buscarCliente(String id){}
    Cliente buscarCliente(String nombre){}


}
