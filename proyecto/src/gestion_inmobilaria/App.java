/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 *
 * @author jacor
 */
public class App {

    // Atributos
    private Inmobiliaria inmobiliaria;
    private boolean usarInterfazGrafica;

    // Constructores
    public App() {
        this.inmobiliaria = new Inmobiliaria();
        this.usarInterfazGrafica = false;
    }

    public static void main(String[] args) {
        System.out.println("=== Iniciando Gestión Inmobiliaria ===");
        App app = new App();
        app.iniciarApp();
    }

    // Pregunta si se maneja por consola o UI y arranca el flujo correspondiente
    public void iniciarApp() {
        // TODO: preguntar usarInterfazGrafica y derivar a consola o UI
        System.out.println("Iniciando aplicación...");
        cargarDatosGenerales();
        mostrarMenuPrincipal();
    }

    // <<Lectura y escritura de datos>>
    public void cargarDatosGenerales() {
        // TODO: cargar datos persistidos (archivo, BD, etc.)
        System.out.println("Cargando datos generales... (lógica pendiente)");
    }

    public void guardarDatosGenerales() {
        // TODO: guardar datos antes de cerrar la app
        System.out.println("Guardando datos generales... (lógica pendiente)");
    }

    public void ejecutarProyeccionMercado() {
        // TODO: recorrer proyectos y llamar a proyectarOfertaDemanda()
        System.out.println("Ejecutando proyección de mercado... (lógica pendiente)");
    }

    public void mostrarMenuPrincipal() {
        // TODO: menú de consola o disparo de la UI
        System.out.println("Mostrando menú principal... (lógica pendiente)");
    }

    // Get/set
    public void setInmobiliaria(Inmobiliaria inmobiliaria) {this.inmobiliaria = inmobiliaria;}
    public Inmobiliaria getInmobiliaria() {return inmobiliaria;}

    public void setUsarInterfazGrafica(boolean usarInterfazGrafica) {this.usarInterfazGrafica = usarInterfazGrafica;}
    public boolean isUsarInterfazGrafica() {return usarInterfazGrafica;}
}