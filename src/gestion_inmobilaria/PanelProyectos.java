/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Pestaña de gestión de proyectos inmobiliarios: tabla + Agregar/Editar/Eliminar,
 * más un botón para asignar propiedades existentes a un proyecto.
 * Cada operación guarda automáticamente en proyectos.csv.
 *
 * @author jacor
 */
public class PanelProyectos extends JPanel {

    private final GestorProyectos gestorProyectos;
    private final GestorPropiedades gestorPropiedades;
    private final JTable tabla;
    private final DefaultTableModel modelo;

    public PanelProyectos(GestorProyectos gestorProyectos, GestorPropiedades gestorPropiedades) {
        this.gestorProyectos = gestorProyectos;
        this.gestorPropiedades = gestorPropiedades;

        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel(new Object[]{"ID Proyecto", "Nombre", "Ubicación", "N° Propiedades", "Oferta Disp.", "Demanda"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnAsignar = new JButton("Asignar Propiedad");
        JButton btnRefrescar = new JButton("Refrescar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnAsignar);
        panelBotones.add(btnRefrescar);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarProyecto());
        btnEditar.addActionListener(e -> editarProyecto());
        btnEliminar.addActionListener(e -> eliminarProyecto());
        btnAsignar.addActionListener(e -> asignarPropiedad());
        btnRefrescar.addActionListener(e -> refrescarTabla());

        refrescarTabla();
    }

    public void refrescarTabla() {
        modelo.setRowCount(0);
        for (ProyectoInmobiliario pr : gestorProyectos.getProyectos().values()) {
            modelo.addRow(new Object[]{
                pr.getIdProyecto(), pr.getNombre(), pr.getUbicacion(), pr.getPropiedades().size(),
                pr.calcularOfertaDisponible(), pr.calcularDemandaTotal()
            });
        }
    }

    private void agregarProyecto() {
        JTextField txtId = new JTextField();
        FiltrosTexto.soloEnteros(txtId);
        JTextField txtNombre = new JTextField();
        JTextField txtUbicacion = new JTextField();
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("ID Proyecto (solo números):")); panel.add(txtId);
        panel.add(new JLabel("Nombre:")); panel.add(txtNombre);
        panel.add(new JLabel("Ubicación:")); panel.add(txtUbicacion);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Agregar Proyecto", JOptionPane.OK_CANCEL_OPTION);
        if (resultado != JOptionPane.OK_OPTION) return;

        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String ubicacion = txtUbicacion.getText().trim();
        if (id.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El ID y el nombre no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (gestorProyectos.getProyectos().containsKey(id)) {
            JOptionPane.showMessageDialog(this, "Ya existe un proyecto con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        gestorProyectos.agregarProyecto(new ProyectoInmobiliario(id, nombre, ubicacion));
        CsvManager.guardarProyectos(gestorProyectos);
        refrescarTabla();
    }

    private void editarProyecto() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un proyecto de la tabla.");
            return;
        }
        String id = (String) modelo.getValueAt(fila, 0);
        try {
            ProyectoInmobiliario pr = gestorProyectos.buscarProyecto(id);

            JTextField txtNombre = new JTextField(pr.getNombre());
            JTextField txtUbicacion = new JTextField(pr.getUbicacion());
            JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
            panel.add(new JLabel("Nombre:")); panel.add(txtNombre);
            panel.add(new JLabel("Ubicación:")); panel.add(txtUbicacion);

            int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Proyecto " + id, JOptionPane.OK_CANCEL_OPTION);
            if (resultado == JOptionPane.OK_OPTION) {
                pr.setNombre(txtNombre.getText().trim());
                pr.setUbicacion(txtUbicacion.getText().trim());
                CsvManager.guardarProyectos(gestorProyectos);
                refrescarTabla();
            }
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Proyecto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProyecto() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un proyecto de la tabla.");
            return;
        }
        String id = (String) modelo.getValueAt(fila, 0);
        int confirmar = JOptionPane.showConfirmDialog(this, "¿Eliminar proyecto " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) return;

        try {
            gestorProyectos.eliminarProyecto(id);
            CsvManager.guardarProyectos(gestorProyectos);
            refrescarTabla();
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Proyecto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void asignarPropiedad() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un proyecto de la tabla.");
            return;
        }
        String idProyecto = (String) modelo.getValueAt(fila, 0);

        if (gestorPropiedades.getPropiedades().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay propiedades registradas todavía. Agrega una primero en la pestaña Propiedades.");
            return;
        }

        Integer[] idsPropiedades = gestorPropiedades.getPropiedades().keySet().toArray(new Integer[0]);
        Integer idProp = (Integer) JOptionPane.showInputDialog(this, "Selecciona la propiedad a asignar a este proyecto:",
                "Asignar Propiedad", JOptionPane.PLAIN_MESSAGE, null, idsPropiedades, idsPropiedades[0]);
        if (idProp == null) return;

        try {
            ProyectoInmobiliario pr = gestorProyectos.buscarProyecto(idProyecto);
            Propiedad prop = gestorPropiedades.buscarPropiedad(idProp);
            pr.getPropiedades().put(idProp, prop);
            CsvManager.guardarProyectos(gestorProyectos);
            refrescarTabla();
            JOptionPane.showMessageDialog(this, "Propiedad " + idProp + " asignada al proyecto " + idProyecto + ".");
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Proyecto o propiedad no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
