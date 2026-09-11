/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Pestaña de gestión de propiedades: tabla + Agregar/Editar/Eliminar/Vender.
 * Cada operación guarda automáticamente en propiedades.csv.
 *
 * NOTA: para cambiar el tipo (Casa <-> Departamento) de una propiedad ya
 * creada, hay que eliminarla y volver a crearla con el tipo correcto,
 * ya que en Java no se puede "mutar" la clase de un objeto existente.
 *
 * @author jacor
 */
public class PanelPropiedades extends JPanel {

    private final GestorPropiedades gestorPropiedades;
    private final GestorClientes gestorClientes;
    private final JTable tabla;
    private final DefaultTableModel modelo;

    public PanelPropiedades(GestorPropiedades gestorPropiedades, GestorClientes gestorClientes) {
        this.gestorPropiedades = gestorPropiedades;
        this.gestorClientes = gestorClientes;

        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel(new Object[]{
            "ID", "Tipo", "Descripción", "Habit.", "Baños", "Valor UF",
            "Interesados", "Vendido", "Estac.", "N°", "Cliente"
        }, 0) {
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
        JButton btnVender = new JButton("Vender a Cliente");
        JButton btnInteresado = new JButton("Registrar Interesado");
        JButton btnRefrescar = new JButton("Refrescar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVender);
        panelBotones.add(btnInteresado);
        panelBotones.add(btnRefrescar);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarPropiedad());
        btnEditar.addActionListener(e -> editarPropiedad());
        btnEliminar.addActionListener(e -> eliminarPropiedad());
        btnVender.addActionListener(e -> venderPropiedad());
        btnInteresado.addActionListener(e -> registrarInteresado());
        btnRefrescar.addActionListener(e -> refrescarTabla());

        refrescarTabla();
    }

    public void refrescarTabla() {
        modelo.setRowCount(0);
        for (Map.Entry<Integer, Propiedad> entry : gestorPropiedades.getPropiedades().entrySet()) {
            int id = entry.getKey();
            Propiedad p = entry.getValue();
            String tipo;
            int numero;
            if (p instanceof Casa) {
                tipo = "CASA";
                numero = ((Casa) p).getNumeroCasa();
            } else {
                tipo = "DEPARTAMENTO";
                numero = ((Departamento) p).getNumeroDepartamento();
            }
            String clienteNombre = buscarNombreClientePorPropiedad(p);
            modelo.addRow(new Object[]{
                id, tipo, p.getDescripcion(), p.getNumHabitaciones(), p.getNumBaños(), p.getValorUF(),
                p.getNumInteresados(), p.isVendido(), p.isEstacionamiento(), numero, clienteNombre
            });
        }
    }

    private String buscarNombreClientePorPropiedad(Propiedad p) {
        for (Cliente c : gestorClientes.getClientes().values()) {
            if (c.getPropiedadesAdquiridas().contains(p)) {
                return c.getNombre();
            }
        }
        return "";
    }

    private void agregarPropiedad() {
        JTextField txtId = new JTextField();
        FiltrosTexto.soloEnteros(txtId);
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"CASA", "DEPARTAMENTO"});
        JTextField txtDescripcion = new JTextField();
        JTextField txtHabitaciones = new JTextField("0");
        FiltrosTexto.soloEnteros(txtHabitaciones);
        JTextField txtBanos = new JTextField("0");
        FiltrosTexto.soloEnteros(txtBanos);
        JTextField txtValorUF = new JTextField("0");
        FiltrosTexto.soloEnteros(txtValorUF);
        JCheckBox chkEstacionamiento = new JCheckBox();
        JTextField txtNumero = new JTextField("0");
        FiltrosTexto.soloEnteros(txtNumero);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("ID (número único):")); panel.add(txtId);
        panel.add(new JLabel("Tipo:")); panel.add(comboTipo);
        panel.add(new JLabel("Descripción:")); panel.add(txtDescripcion);
        panel.add(new JLabel("N° Habitaciones:")); panel.add(txtHabitaciones);
        panel.add(new JLabel("N° Baños:")); panel.add(txtBanos);
        panel.add(new JLabel("Valor (UF):")); panel.add(txtValorUF);
        panel.add(new JLabel("Estacionamiento:")); panel.add(chkEstacionamiento);
        panel.add(new JLabel("N° Casa/Depto:")); panel.add(txtNumero);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Agregar Propiedad", JOptionPane.OK_CANCEL_OPTION);
        if (resultado != JOptionPane.OK_OPTION) return;

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            if (gestorPropiedades.getPropiedades().containsKey(id)) {
                JOptionPane.showMessageDialog(this, "Ya existe una propiedad con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String descripcion = txtDescripcion.getText().trim();
            int habitaciones = Integer.parseInt(txtHabitaciones.getText().trim());
            int banos = Integer.parseInt(txtBanos.getText().trim());
            int valorUF = Integer.parseInt(txtValorUF.getText().trim());
            boolean estacionamiento = chkEstacionamiento.isSelected();
            int numero = Integer.parseInt(txtNumero.getText().trim());

            Propiedad p;
            if (comboTipo.getSelectedItem().equals("CASA")) {
                p = new Casa(descripcion, habitaciones, banos, valorUF, estacionamiento, numero);
            } else {
                p = new Departamento(descripcion, habitaciones, banos, valorUF, estacionamiento, numero);
            }
            gestorPropiedades.agregarPropiedad(id, p);
            CsvManager.guardarPropiedades(gestorPropiedades, gestorClientes);
            refrescarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID, habitaciones, baños, valor y número deben ser números enteros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarPropiedad() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una propiedad de la tabla.");
            return;
        }
        int id = (int) modelo.getValueAt(fila, 0);
        try {
            Propiedad p = gestorPropiedades.buscarPropiedad(id);

            JTextField txtDescripcion = new JTextField(p.getDescripcion());
            JTextField txtHabitaciones = new JTextField(String.valueOf(p.getNumHabitaciones()));
            FiltrosTexto.soloEnteros(txtHabitaciones);
            JTextField txtBanos = new JTextField(String.valueOf(p.getNumBaños()));
            FiltrosTexto.soloEnteros(txtBanos);
            JTextField txtValorUF = new JTextField(String.valueOf(p.getValorUF()));
            FiltrosTexto.soloEnteros(txtValorUF);
            JCheckBox chkEstacionamiento = new JCheckBox("", p.isEstacionamiento());

            JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
            panel.add(new JLabel("Descripción:")); panel.add(txtDescripcion);
            panel.add(new JLabel("N° Habitaciones:")); panel.add(txtHabitaciones);
            panel.add(new JLabel("N° Baños:")); panel.add(txtBanos);
            panel.add(new JLabel("Valor (UF):")); panel.add(txtValorUF);
            panel.add(new JLabel("Estacionamiento:")); panel.add(chkEstacionamiento);

            int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Propiedad " + id, JOptionPane.OK_CANCEL_OPTION);
            if (resultado != JOptionPane.OK_OPTION) return;

            p.setDescripcion(txtDescripcion.getText().trim());
            p.setNumHabitaciones(Integer.parseInt(txtHabitaciones.getText().trim()));
            p.setNumBaños(Integer.parseInt(txtBanos.getText().trim()));
            p.setValorUF(Integer.parseInt(txtValorUF.getText().trim()));
            p.setEstacionamiento(chkEstacionamiento.isSelected());

            CsvManager.guardarPropiedades(gestorPropiedades, gestorClientes);
            refrescarTabla();
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Propiedad no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Habitaciones, baños y valor deben ser números enteros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPropiedad() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una propiedad de la tabla.");
            return;
        }
        int id = (int) modelo.getValueAt(fila, 0);
        int confirmar = JOptionPane.showConfirmDialog(this, "¿Eliminar propiedad " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) return;

        try {
            gestorPropiedades.eliminarPropiedad(id);
            CsvManager.guardarPropiedades(gestorPropiedades, gestorClientes);
            refrescarTabla();
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Propiedad no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void venderPropiedad() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una propiedad de la tabla.");
            return;
        }
        int id = (int) modelo.getValueAt(fila, 0);

        if (gestorClientes.getClientes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay clientes registrados todavía. Agrega uno primero en la pestaña Clientes.");
            return;
        }

        String[] idsClientes = gestorClientes.getClientes().keySet().toArray(new String[0]);
        String idCliente = (String) JOptionPane.showInputDialog(this, "Selecciona el cliente comprador:",
                "Vender Propiedad", JOptionPane.PLAIN_MESSAGE, null, idsClientes, idsClientes[0]);
        if (idCliente == null) return;

        try {
            Propiedad p = gestorPropiedades.buscarPropiedad(id);
            Cliente c = gestorClientes.buscarCliente(idCliente);
            p.setVendido(true);
            c.agregarPropiedad(p);
            CsvManager.guardarPropiedades(gestorPropiedades, gestorClientes);
            refrescarTabla();
        } catch (PropiedadVendidaException ex) {
            JOptionPane.showMessageDialog(this, "Esa propiedad ya estaba vendida.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Propiedad o cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarInteresado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una propiedad de la tabla.");
            return;
        }
        int id = (int) modelo.getValueAt(fila, 0);
        try {
            Propiedad p = gestorPropiedades.buscarPropiedad(id);
            p.registrarInteresados();
            CsvManager.guardarPropiedades(gestorPropiedades, gestorClientes);
            refrescarTabla();
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, "Propiedad no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
