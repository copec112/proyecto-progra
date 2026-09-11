/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author luisi
 */
public class Inmobiliaria {

    // Atributos
    private Set<AgenteInmobiliario> agentes;
    private Set<ProyectoInmobiliario> proyectos;
    private Stack<Venta> ventas;

    // Constructores
    public Inmobiliaria() {
        this.agentes = new HashSet<>();
        this.proyectos = new HashSet<>();
        this.ventas = new Stack<>();
        System.out.println("Inmobiliaria inicializada.");
    }

    // <<Gestión de las colecciones>>
    // TODO: aquí normalmente se delega en GestorAgentes / GestorProyectos.
    public void gestionarAgentes() {
        System.out.println("Gestionando agentes... (lógica pendiente)");
    }

    public void gestionarProyectos() {
        System.out.println("Gestionando proyectos... (lógica pendiente)");
    }

    // <<Registro de ventas>>
    // NOTA: el diagrama define agregarVenta(agenteEncargado: AgenteInmobiliario),
    // se deja el parámetro tal cual, aunque probablemente lo correcto sea recibir
    // la Venta ya generada por AgenteInmobiliario.venderPropiedad(...).
    public void agregarVenta(AgenteInmobiliario agenteEncargado) {
        // placeholder: ajustar para recibir/crear la Venta real
        System.out.println("Registrando venta a cargo del agente " + agenteEncargado.getNombre() + "... (lógica pendiente)");
    }

    public void mostrarVentas() {
        System.out.println("--- Historial de ventas (" + ventas.size() + ") ---");
        for (Venta v : ventas) {
            System.out.println(v.getPropiedad().getDescripcion() + " -> " + v.getCliente().getNombre());
        }
    }

    // Get/set
    public void setAgentes(Set<AgenteInmobiliario> agentes) {this.agentes = agentes;}
    public Set<AgenteInmobiliario> getAgentes() {return agentes;}

    public void setProyectos(Set<ProyectoInmobiliario> proyectos) {this.proyectos = proyectos;}
    public Set<ProyectoInmobiliario> getProyectos() {return proyectos;}

    public void setVentas(Stack<Venta> ventas) {this.ventas = ventas;}
    public Stack<Venta> getVentas() {return ventas;}
}