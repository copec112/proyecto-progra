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
 * Pestaña de gestión de clientes: tabla + Agregar/Editar/Eliminar.
 * Cada operación guarda automáticamente en clientes.csv.
 *
 * @author jacor
 */
public class PanelClientes extends JPanel {

    private final GestorClientes gestorClientes;
    private final JTable tabla;
    private final DefaultTableModel modelo;

    public PanelClientes(GestorClientes gestorClientes) {
        this.gestorClientes = gestorClientes;

        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "N° Propiedades"}, 0) {
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

        btnAgregar.addActionListener(e -> agregarCliente());
        btnEditar.addActionListener(e -> editarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnRefrescar.addActionListener(e -> refrescarTabla());

        refrescarTabla();
    }

    public void refrescarTabla() {
        modelo.setRowCount(0);
        for (Cliente c : gestorClientes.getClientes().values()) {
            modelo.addRow(new Object[]{c.getId(), c.getNombre(), c.getPropiedadesAdquiridas().size()});
        }
    }

    private void agregarCliente() {
        JTextField txtId = new JTextField();
        FiltrosTexto.soloEnteros(txtId);
        JTextField txtNombre = new JTextField();
        FiltrosTexto.soloLetras(txtNombre);
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("ID (solo números):"));
        panel.add(txtId);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Agregar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (resultado != JOptionPane.OK_OPTION) return;

        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        if (id.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El ID y el nombre no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (gestorClientes.getClientes().containsKey(id)) {
            JOptionPane.showMessageDialog(this, "Ya existe un cliente con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        gestorClientes.agregarCliente(new Cliente(id, nombre));
        CsvManager.guardarClientes(gestorClientes);
        refrescarTabla();
    }

    private void editarCliente() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla.");
            return;
        }
        String id = (String) modelo.getValueAt(fila, 0);
        try {
            Cliente c = gestorClientes.buscarCliente(id);
            JTextField txtNombre = new JTextField(c.getNombre());
            FiltrosTexto.soloLetras(txtNombre);
            JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
            panel.add(new JLabel("Nombre:"));
            panel.add(txtNombre);

            int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Cliente " + id, JOptionPane.OK_CANCEL_OPTION);
            if (resultado == JOptionPane.OK_OPTION) {
                String nuevoNombre = txtNombre.getText().trim();
                if (!nuevoNombre.isEmpty()) {
                    c.setNombre(nuevoNombre);
                    CsvManager.guardarClientes(gestorClientes);
                    refrescarTabla();
                }
            }
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla.");
            return;
        }
        String id = (String) modelo.getValueAt(fila, 0);
        try {
            Cliente c = gestorClientes.buscarCliente(id);
            if (!c.getPropiedadesAdquiridas().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No se puede eliminar: este cliente tiene " + c.getPropiedadesAdquiridas().size()
                        + " propiedad(es) adquirida(s) registrada(s).\n"
                        + "Eliminarlo dejaría esas ventas con datos inconsistentes (la propiedad seguiría\n"
                        + "marcada como vendida, pero sin dueño).",
                        "No se puede eliminar", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmar = JOptionPane.showConfirmDialog(this, "¿Eliminar cliente " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmar != JOptionPane.YES_OPTION) return;

            gestorClientes.eliminarCliente(id);
            CsvManager.guardarClientes(gestorClientes);
            refrescarTabla();
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
