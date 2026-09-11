/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada del sistema. Carga los datos guardados en CSV,
 * y abre la ventana principal (Swing) para gestionar todo desde ahí.
 *
 * @author jacor
 */
public class App {

    // Atributos: un gestor por cada colección del sistema
    private GestorClientes gestorClientes;
    private GestorAgentes gestorAgentes;
    private GestorPropiedades gestorPropiedades;
    private GestorProyectos gestorProyectos;
    private GestorVentas gestorVentas;

    // Constructor
    public App() {
        this.gestorClientes = new GestorClientes();
        this.gestorAgentes = new GestorAgentes();
        this.gestorPropiedades = new GestorPropiedades();
        this.gestorProyectos = new GestorProyectos();
        this.gestorVentas = new GestorVentas();
    }

    public static void main(String[] args) {
        System.out.println("=== Iniciando Gestión Inmobiliaria ===");
        App app = new App();
        app.iniciarApp();
    }

    // Carga los datos y abre la ventana principal
    public void iniciarApp() {
        cargarDatosGenerales();
        SwingUtilities.invokeLater(() -> {
            MainWindow ventana = new MainWindow(gestorClientes, gestorAgentes, gestorPropiedades, gestorProyectos, gestorVentas);
            ventana.setVisible(true);
        });
    }

    // <<Lectura y escritura de datos>>
    public void cargarDatosGenerales() {
        CsvManager.cargarTodo(gestorClientes, gestorAgentes, gestorPropiedades, gestorProyectos, gestorVentas);
    }

    public void guardarDatosGenerales() {
        CsvManager.guardarTodo(gestorClientes, gestorAgentes, gestorPropiedades, gestorProyectos, gestorVentas);
    }

    // Get/set
    public GestorClientes getGestorClientes() {return gestorClientes;}
    public void setGestorClientes(GestorClientes gestorClientes) {this.gestorClientes = gestorClientes;}

    public GestorAgentes getGestorAgentes() {return gestorAgentes;}
    public void setGestorAgentes(GestorAgentes gestorAgentes) {this.gestorAgentes = gestorAgentes;}

    public GestorPropiedades getGestorPropiedades() {return gestorPropiedades;}
    public void setGestorPropiedades(GestorPropiedades gestorPropiedades) {this.gestorPropiedades = gestorPropiedades;}

    public GestorProyectos getGestorProyectos() {return gestorProyectos;}
    public void setGestorProyectos(GestorProyectos gestorProyectos) {this.gestorProyectos = gestorProyectos;}

    public GestorVentas getGestorVentas() {return gestorVentas;}
    public void setGestorVentas(GestorVentas gestorVentas) {this.gestorVentas = gestorVentas;}
}
