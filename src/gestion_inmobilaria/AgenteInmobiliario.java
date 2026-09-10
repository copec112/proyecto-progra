/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

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
    private GestorClientes gestorClientes;
    
    // Constructor
    public AgenteInmobiliario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.gestorClientes = new GestorClientes();
    }


    // Acceso al gestor de clientes
    public GestorClientes getGestorClientes() {
        return gestorClientes;
    }

    public void asignarDepartamento(Propiedad propiedad, Cliente cliente){
        if (!propiedad.setVendido()){ // Asumo que devuelve true si ya estaba vendido
            cliente.agregarPropiedad(propiedad);
            System.out.println("La propiedad fue vendida exitosamente.");
            return;
        }
        System.out.println("Error, la propiedad ya está vendida");
    }
    
    public void venderPropiedad(Propiedad propiedad){
        propiedad.setVendida(true);
    }


}
