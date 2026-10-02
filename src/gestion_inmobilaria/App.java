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
    /**
     * Crea la aplicación, inicializando un gestor vacío por cada colección
     * del sistema (clientes, agentes, propiedades, proyectos, ventas).
     */
    public App() {
        this.gestorClientes = new GestorClientes();
        this.gestorAgentes = new GestorAgentes();
        this.gestorPropiedades = new GestorPropiedades();
        this.gestorProyectos = new GestorProyectos();
        this.gestorVentas = new GestorVentas();
    }

    /**
     * Punto de entrada del programa. Carga los datos guardados en CSV y le
     * pregunta al usuario si desea usar el sistema por consola o por
     * ventana (Swing) antes de arrancar.
     *
     * @param args argumentos de línea de comandos (no se utilizan).
     */
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
    /**
     * Abre la ventana principal (interfaz gráfica Swing) en el hilo de
     * eventos de Swing, usando los gestores ya cargados de esta aplicación.
     */
    public void iniciarVentana() {
        SwingUtilities.invokeLater(() -> {
            MainWindow ventana = new MainWindow(gestorClientes, gestorAgentes, gestorPropiedades, gestorProyectos, gestorVentas);
            ventana.setVisible(true);
        });
    }

    // Abre el menú de consola (mismo Scanner que se usó para la pregunta inicial)
    /**
     * Abre el menú de consola, reutilizando el mismo {@code Scanner} que
     * se usó para preguntar al usuario el modo de uso del sistema.
     *
     * @param sc scanner ya abierto sobre la entrada estándar.
     */
    public void iniciarConsola(Scanner sc) {
        MenuConsola menu = new MenuConsola(gestorClientes, gestorAgentes, gestorPropiedades, gestorProyectos, gestorVentas, sc);
        menu.iniciar();
    }

    // <<Lectura y escritura de datos>>
    /**
     * Carga en los gestores de esta aplicación los datos guardados en los
     * archivos CSV del sistema.
     */
    public void cargarDatosGenerales() {
        CsvManager.cargarTodo(gestorClientes, gestorAgentes, gestorPropiedades, gestorProyectos, gestorVentas);
    }

    /**
     * Guarda en los archivos CSV del sistema los datos actuales de todos
     * los gestores de esta aplicación.
     */
    public void guardarDatosGenerales() {
        CsvManager.guardarTodo(gestorClientes, gestorAgentes, gestorPropiedades, gestorProyectos, gestorVentas);
    }

    // Get/set
    /** @return el gestor de clientes de esta aplicación. */
    public GestorClientes getGestorClientes() {return gestorClientes;}
    /** @param gestorClientes nuevo gestor de clientes a usar. */
    public void setGestorClientes(GestorClientes gestorClientes) {this.gestorClientes = gestorClientes;}

    /** @return el gestor de agentes inmobiliarios de esta aplicación. */
    public GestorAgentes getGestorAgentes() {return gestorAgentes;}
    /** @param gestorAgentes nuevo gestor de agentes a usar. */
    public void setGestorAgentes(GestorAgentes gestorAgentes) {this.gestorAgentes = gestorAgentes;}

    /** @return el gestor de propiedades de esta aplicación. */
    public GestorPropiedades getGestorPropiedades() {return gestorPropiedades;}
    /** @param gestorPropiedades nuevo gestor de propiedades a usar. */
    public void setGestorPropiedades(GestorPropiedades gestorPropiedades) {this.gestorPropiedades = gestorPropiedades;}

    /** @return el gestor de proyectos inmobiliarios de esta aplicación. */
    public GestorProyectos getGestorProyectos() {return gestorProyectos;}
    /** @param gestorProyectos nuevo gestor de proyectos a usar. */
    public void setGestorProyectos(GestorProyectos gestorProyectos) {this.gestorProyectos = gestorProyectos;}

    /** @return el gestor de ventas de esta aplicación. */
    public GestorVentas getGestorVentas() {return gestorVentas;}
    /** @param gestorVentas nuevo gestor de ventas a usar. */
    public void setGestorVentas(GestorVentas gestorVentas) {this.gestorVentas = gestorVentas;}
}
