/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    // TODO: procesarPago
    public boolean venderPropiedad(Propiedad propiedad){}

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

    void editarCliente(String id){
        Cliente clienteAux = mapaClientes.get(id);
        Scanner buffer = new Scanner(System.in);
        
        System.out.println("Selecciona que que valor editar ");
        System.out.println("1: ID ");
        System.out.println("2: NOMBRE ");
        System.out.println("Apretar cualquier otra tecla para cancelar ");
        String opcion = buffer.nextLine();
        
        switch(expression){
            case "1":
                System.out.println("Escribe el nuevo ID: ");
                clienteAux.setId(buffer.nextLine());
                System.out.print("\033[H\033[2J");
                System.out.flush();


                break;
            case "2":
                System.out.println("Escribe el nuevo NOMBRE: ");
                clienteAux.setNombre(buffer.nextLine());
                
                System.out.print("\033[H\033[2J");
                System.out.flush();

                break;
            default:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                break;
        }

    }

    void eliminarCliente(String id){
        if (!containsKey(id)){
            System.out.println("El elemento con esa clave no existe.");
            return;
        }
        Cliente clienteAux = mapaClientes.get(id);
        System.out.println("¿Estas seguro que quieres eliminar " + cliente.getNombre() + "?");
        System.out.println("S/n");

        if (buffer.nextLine() == "S"){
            clienteAux.remove(id);
            return;
        }

        System.out.println("Operacion cancelada");

    }

    // TODO
    Cliente buscarCliente(String id){}
    Cliente buscarCliente(String nombre){}


}
