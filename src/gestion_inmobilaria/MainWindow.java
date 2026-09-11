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
 * (Clientes, Agentes, Propiedades, Proyectos, Ventas). Al cerrarla, se hace
 * un guardado final en CSV como respaldo (aunque cada operación ya guarda
 * por su cuenta apenas ocurre).
 *
 * @author jacor
 */
public class MainWindow extends JFrame {

    private final GestorClientes gestorClientes;
    private final GestorAgentes gestorAgentes;
    private final GestorPropiedades gestorPropiedades;
    private final GestorProyectos gestorProyectos;
    private final GestorVentas gestorVentas;

    public MainWindow(GestorClientes gestorClientes, GestorAgentes gestorAgentes,
            GestorPropiedades gestorPropiedades, GestorProyectos gestorProyectos, GestorVentas gestorVentas) {

        this.gestorClientes = gestorClientes;
        this.gestorAgentes = gestorAgentes;
        this.gestorPropiedades = gestorPropiedades;
        this.gestorProyectos = gestorProyectos;
        this.gestorVentas = gestorVentas;

        setTitle("Gestión Inmobiliaria");
        setSize(1050, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JTabbedPane pestañas = new JTabbedPane();
        PanelClientes panelClientes = new PanelClientes(gestorClientes);
        PanelAgentes panelAgentes = new PanelAgentes(gestorAgentes, gestorVentas);
        PanelPropiedades panelPropiedades = new PanelPropiedades(gestorPropiedades, gestorClientes, gestorAgentes, gestorVentas, gestorProyectos);
        PanelProyectos panelProyectos = new PanelProyectos(gestorProyectos, gestorPropiedades, gestorClientes);
        PanelVentas panelVentas = new PanelVentas(gestorVentas);
        PanelProyeccion panelProyeccion = new PanelProyeccion(gestorProyectos);

        pestañas.addTab("Clientes", panelClientes);
        pestañas.addTab("Agentes", panelAgentes);
        pestañas.addTab("Propiedades", panelPropiedades);
        pestañas.addTab("Proyectos", panelProyectos);
        pestañas.addTab("Ventas", panelVentas);
        pestañas.addTab("Proyección de Precios", panelProyeccion);

        // Al cambiar de pestaña se refrescan todas las tablas, por si otra
        // pestaña modificó datos relacionados (ej: vender una propiedad
        // cambia el N° de propiedades de un cliente y aparece en Ventas).
        pestañas.addChangeListener(e -> {
            panelClientes.refrescarTabla();
            panelAgentes.refrescarTabla();
            panelPropiedades.refrescarTabla();
            panelProyectos.refrescarTabla();
            panelVentas.refrescarTabla();
            panelProyeccion.refrescarTabla();
        });

        add(pestañas);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Cerrando ventana, guardando datos finales en CSV...");
                CsvManager.guardarTodo(gestorClientes, gestorAgentes, gestorPropiedades, gestorProyectos, gestorVentas);
                dispose();
                System.exit(0);
            }
        });
    }
}
