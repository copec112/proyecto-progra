/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.util.Scanner;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada del sistema. Carga los datos guardados en CSV, y le
 * pregunta al usuario si quiere usar el sistema por consola o por ventana
 * (Swing) antes de arrancar -- ambos caminos ofrecen exactamente las mismas
 * funcionalidades, solo cambia la forma de interactuar.
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
        app.cargarDatosGenerales();

        Scanner sc = new Scanner(System.in);
        System.out.println("\n¿Cómo deseas usar el sistema?");
        System.out.println("1. Ventana (interfaz gráfica)");
        System.out.println("2. Consola");
        System.out.print("Elige una opción: ");
        String opcion = sc.nextLine().trim();

        if (opcion.equals("2")) {
            app.iniciarConsola(sc);
        } else {
            app.iniciarVentana();
            // OJO: no se cierra el Scanner acá porque System.in se comparte;
            // si se usó la ventana, este Scanner simplemente ya no se necesita más.
        }
    }

    // Abre la ventana principal (Swing)
    public void iniciarVentana() {
        SwingUtilities.invokeLater(() -> {
            MainWindow ventana = new MainWindow(gestorClientes, gestorAgentes, gestorPropiedades, gestorProyectos, gestorVentas);
            ventana.setVisible(true);
        });
    }

    // Abre el menú de consola (mismo Scanner que se usó para la pregunta inicial)
    public void iniciarConsola(Scanner sc) {
        MenuConsola menu = new MenuConsola(gestorClientes, gestorAgentes, gestorPropiedades, gestorProyectos, gestorVentas, sc);
        menu.iniciar();
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
