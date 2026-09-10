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
 * @author jacor
 */

public class GestorClientes implements GestorColeccion<Cliente, String> {
    
    private Map<String, Cliente> mapaClientes;

    // Constructor
    public GestorClientes() {
        this.mapaClientes = new HashMap<>();
    }

    // Sobreescritura de metodos

    @Override
    public void agregar(Cliente cliente){
        mapaClientes.put(cliente.getId(), cliente);
    }

    @Override
    public void mostrarTodos(){
        for (Cliente cliente : mapaClientes.values()) {
            System.out.println("ID: " + cliente.getId() + " - Nombre: " + cliente.getNombre());
        }
    }

    @Override
    public void editar(String id){
        Cliente clienteAux = mapaClientes.get(id);
        
        if (clienteAux == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        Scanner buffer = new Scanner(System.in);
        System.out.println("Selecciona qué valor editar:");
        System.out.println("1: ID");
        System.out.println("2: NOMBRE");
        System.out.println("Apretar cualquier otra tecla para cancelar");
        String opcion = buffer.nextLine();
        
        switch(opcion){
            case "1":
                System.out.println("Escribe el nuevo ID: ");
                String nuevoId = buffer.nextLine();
                
                // Si cambiamos el ID, debemos actualizar la clave en el mapa
                mapaClientes.remove(id); 
                clienteAux.setId(nuevoId);
                mapaClientes.put(nuevoId, clienteAux); 
                
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

    @Override
    public void eliminar(String id){
        Scanner buffer = new Scanner(System.in);
        
        if (!mapaClientes.containsKey(id)){
            System.out.println("El elemento con esa clave no existe.");
            return;
        }
        
        Cliente clienteAux = mapaClientes.get(id);
        System.out.println("¿Estás seguro que quieres eliminar a " + clienteAux.getNombre() + "? (S/n)");

        // 7. Comparación correcta de Strings en Java
        if (buffer.nextLine().equalsIgnoreCase("S")){
            mapaClientes.remove(id); // Eliminar del mapa
            System.out.println("Cliente eliminado.");
            return;
        }

        System.out.println("Operación cancelada.");
    }

    @Override
    public Cliente buscar(String id){
        return mapaClientes.get(id);
    }
    
    // Método adicional
    public Cliente buscarPorNombre(String nombre){
        for (Cliente cliente : mapaClientes.values()) {
            if (cliente.getNombre().equalsIgnoreCase(nombre)) {
                return cliente;
            }
        }
        return null; // Retorna null si no lo encuentra
    }
}