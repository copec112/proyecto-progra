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
 * Los cambios se guardan en agentes.csv recién al cerrar la ventana
 * (ver MainWindow.windowClosing), no después de cada operación.
 *
 * @author jacor
 */
public class PanelAgentes extends JPanel {

    private final GestorAgentes gestorAgentes;
    private final GestorVentas gestorVentas;
    private final JTable tabla;
    private final DefaultTableModel modelo;

    /**
     * Construye el panel de gestión de agentes: crea la tabla, los botones
     * de Agregar/Editar/Eliminar/Refrescar, conecta sus acciones y carga la
     * tabla con los agentes existentes.
     *
     * @param gestorAgentes gestor de agentes a mostrar y modificar.
     * @param gestorVentas gestor de ventas, usado para verificar si un
     *        agente tiene ventas registradas antes de eliminarlo.
     */
    public PanelAgentes(GestorAgentes gestorAgentes, GestorVentas gestorVentas) {
        this.gestorAgentes = gestorAgentes;
        this.gestorVentas = gestorVentas;

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

    /**
     * Recarga la tabla con el id y nombre de todos los agentes del gestor.
     */
    public void refrescarTabla() {
        modelo.setRowCount(0);
        for (AgenteInmobiliario a : gestorAgentes.getAgentes().values()) {
            modelo.addRow(new Object[]{a.getId(), a.getNombre()});
        }
    }

    /**
     * Maneja el botón "Agregar": pide id y nombre en un diálogo, valida que
     * no estén vacíos y que el id no esté repetido, y agrega el nuevo
     * agente al gestor.
     */
    private void agregarAgente() {
        JTextField txtId = new JTextField();
        FiltrosTexto.soloEnteros(txtId);
        JTextField txtNombre = new JTextField();
        FiltrosTexto.soloLetras(txtNombre);
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
        refrescarTabla();
    }

    /**
     * Maneja el botón "Editar": toma el agente seleccionado en la tabla y
     * permite modificar su nombre mediante un diálogo.
     */
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
            FiltrosTexto.soloLetras(txtNombre);
            JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
            panel.add(new JLabel("Nombre:"));
            panel.add(txtNombre);

            int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Agente " + id, JOptionPane.OK_CANCEL_OPTION);
            if (resultado == JOptionPane.OK_OPTION) {
                String nuevoNombre = txtNombre.getText().trim();
                if (!nuevoNombre.isEmpty()) {
                    a.setNombre(nuevoNombre);
                    refrescarTabla();
                }
            }
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Agente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Maneja el botón "Eliminar": toma el agente seleccionado en la tabla,
     * verifica que no tenga ventas registradas a su nombre (en cuyo caso se
     * bloquea la eliminación) y, tras confirmar con el usuario, lo elimina
     * del gestor.
     */
    private void eliminarAgente() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un agente de la tabla.");
            return;
        }
        String id = (String) modelo.getValueAt(fila, 0);
        try {
            AgenteInmobiliario a = gestorAgentes.buscarAgentes(id);

            long ventasDelAgente = gestorVentas.getVentas().stream()
                    .filter(v -> v.getAgenteEncargado() == a)
                    .count();
            if (ventasDelAgente > 0) {
                JOptionPane.showMessageDialog(this,
                        "No se puede eliminar: este agente tiene " + ventasDelAgente
                        + " venta(s) registrada(s) a su nombre en el historial.",
                        "No se puede eliminar", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmar = JOptionPane.showConfirmDialog(this, "¿Eliminar agente " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmar != JOptionPane.YES_OPTION) return;

            gestorAgentes.eliminarAgentes(id);
            refrescarTabla();
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Agente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
