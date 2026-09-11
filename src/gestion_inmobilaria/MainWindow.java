/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * Ventana principal del sistema. Contiene una pestaña por cada entidad
 * (Clientes, Agentes, Propiedades, Proyectos). Al cerrarla, se hace un
 * guardado final en CSV como respaldo (aunque cada operación ya guarda
 * por su cuenta apenas ocurre).
 *
 * @author jacor
 */
public class MainWindow extends JFrame {

    private final GestorClientes gestorClientes;
    private final GestorAgentes gestorAgentes;
    private final GestorPropiedades gestorPropiedades;
    private final GestorProyectos gestorProyectos;

    public MainWindow(GestorClientes gestorClientes, GestorAgentes gestorAgentes,
            GestorPropiedades gestorPropiedades, GestorProyectos gestorProyectos) {

        this.gestorClientes = gestorClientes;
        this.gestorAgentes = gestorAgentes;
        this.gestorPropiedades = gestorPropiedades;
        this.gestorProyectos = gestorProyectos;

        setTitle("Gestión Inmobiliaria");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JTabbedPane pestañas = new JTabbedPane();
        PanelClientes panelClientes = new PanelClientes(gestorClientes);
        PanelAgentes panelAgentes = new PanelAgentes(gestorAgentes);
        PanelPropiedades panelPropiedades = new PanelPropiedades(gestorPropiedades, gestorClientes);
        PanelProyectos panelProyectos = new PanelProyectos(gestorProyectos, gestorPropiedades);

        pestañas.addTab("Clientes", panelClientes);
        pestañas.addTab("Agentes", panelAgentes);
        pestañas.addTab("Propiedades", panelPropiedades);
        pestañas.addTab("Proyectos", panelProyectos);

        // Al cambiar de pestaña se refresca la tabla, por si otra pestaña
        // modificó datos relacionados (ej: vender una propiedad cambia
        // el N° de propiedades de un cliente).
        pestañas.addChangeListener(e -> {
            panelClientes.refrescarTabla();
            panelAgentes.refrescarTabla();
            panelPropiedades.refrescarTabla();
            panelProyectos.refrescarTabla();
        });

        add(pestañas);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Cerrando ventana, guardando datos finales en CSV...");
                CsvManager.guardarTodo(gestorClientes, gestorAgentes, gestorPropiedades, gestorProyectos);
                dispose();
                System.exit(0);
            }
        });
    }
}
