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
 * Pestaña de gestión de agentes inmobiliarios: tabla + Agregar/Editar/Eliminar.
 * Cada operación guarda automáticamente en agentes.csv.
 *
 * @author jacor
 */
public class PanelAgentes extends JPanel {

    private final GestorAgentes gestorAgentes;
    private final JTable tabla;
    private final DefaultTableModel modelo;

    public PanelAgentes(GestorAgentes gestorAgentes) {
        this.gestorAgentes = gestorAgentes;

        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0) {
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
        JButton btnRefrescar = new JButton("Refrescar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarAgente());
        btnEditar.addActionListener(e -> editarAgente());
        btnEliminar.addActionListener(e -> eliminarAgente());
        btnRefrescar.addActionListener(e -> refrescarTabla());

        refrescarTabla();
    }

    public void refrescarTabla() {
        modelo.setRowCount(0);
        for (AgenteInmobiliario a : gestorAgentes.getAgentes().values()) {
            modelo.addRow(new Object[]{a.getId(), a.getNombre()});
        }
    }

    private void agregarAgente() {
        JTextField txtId = new JTextField();
        FiltrosTexto.soloEnteros(txtId);
        JTextField txtNombre = new JTextField();
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("ID (solo números):"));
        panel.add(txtId);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Agregar Agente", JOptionPane.OK_CANCEL_OPTION);
        if (resultado != JOptionPane.OK_OPTION) return;

        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        if (id.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El ID y el nombre no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (gestorAgentes.getAgentes().containsKey(id)) {
            JOptionPane.showMessageDialog(this, "Ya existe un agente con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        gestorAgentes.agregarAgentes(new AgenteInmobiliario(id, nombre));
        CsvManager.guardarAgentes(gestorAgentes);
        refrescarTabla();
    }

    private void editarAgente() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un agente de la tabla.");
            return;
        }
        String id = (String) modelo.getValueAt(fila, 0);
        try {
            AgenteInmobiliario a = gestorAgentes.buscarAgentes(id);
            JTextField txtNombre = new JTextField(a.getNombre());
            JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
            panel.add(new JLabel("Nombre:"));
            panel.add(txtNombre);

            int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Agente " + id, JOptionPane.OK_CANCEL_OPTION);
            if (resultado == JOptionPane.OK_OPTION) {
                String nuevoNombre = txtNombre.getText().trim();
                if (!nuevoNombre.isEmpty()) {
                    a.setNombre(nuevoNombre);
                    CsvManager.guardarAgentes(gestorAgentes);
                    refrescarTabla();
                }
            }
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Agente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarAgente() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un agente de la tabla.");
            return;
        }
        String id = (String) modelo.getValueAt(fila, 0);
        int confirmar = JOptionPane.showConfirmDialog(this, "¿Eliminar agente " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) return;

        try {
            gestorAgentes.eliminarAgentes(id);
            CsvManager.guardarAgentes(gestorAgentes);
            refrescarTabla();
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Agente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
