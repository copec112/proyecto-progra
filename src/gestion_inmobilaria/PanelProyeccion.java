/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Pestaña de Proyección de Precios: muestra la oferta/demanda y el precio
 * promedio actual de cada proyecto, y permite proyectar cómo podría subir
 * ese precio a N meses según qué tan tensionado esté el mercado
 * (ProyectoInmobiliario.proyectarPrecioPromedio / calcularRatioTension).
 *
 * @author jacor
 */
public class PanelProyeccion extends JPanel {

    private final GestorProyectos gestorProyectos;
    private final JTable tabla;
    private final DefaultTableModel modelo;
    private final JTextField txtMeses;

    public PanelProyeccion(GestorProyectos gestorProyectos) {
        this.gestorProyectos = gestorProyectos;

        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel(new Object[]{
            "Proyecto", "Ubicación", "Oferta Disp.", "Demanda", "Ratio Tensión", "Precio Prom. Actual (UF)"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.add(new JLabel("Meses a proyectar:"));
        txtMeses = new JTextField("6", 4);
        FiltrosTexto.soloEnteros(txtMeses);
        panelBotones.add(txtMeses);
        JButton btnProyectar = new JButton("Proyectar Precio");
        JButton btnRefrescar = new JButton("Refrescar");
        panelBotones.add(btnProyectar);
        panelBotones.add(btnRefrescar);
        add(panelBotones, BorderLayout.SOUTH);

        btnProyectar.addActionListener(e -> proyectarPrecio());
        btnRefrescar.addActionListener(e -> refrescarTabla());

        refrescarTabla();
    }

    public void refrescarTabla() {
        modelo.setRowCount(0);
        for (ProyectoInmobiliario pr : gestorProyectos.getProyectos().values()) {
            modelo.addRow(new Object[]{
                pr.getNombre(),
                pr.getUbicacion(),
                pr.calcularOfertaDisponible(),
                pr.calcularDemandaTotal(),
                String.format("%.2f", pr.calcularRatioTension()),
                String.format("%.1f", pr.calcularPrecioPromedioActual())
            });
        }
    }

    private void proyectarPrecio() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un proyecto de la tabla.");
            return;
        }
        String nombreProyecto = (String) modelo.getValueAt(fila, 0);

        String mesesTexto = txtMeses.getText().trim();
        if (mesesTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa cuántos meses proyectar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int meses = Integer.parseInt(mesesTexto);
        if (meses <= 0) {
            JOptionPane.showMessageDialog(this, "Los meses a proyectar deben ser mayores a 0.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Se busca el proyecto por nombre entre los que están cargados en la tabla actual.
        ProyectoInmobiliario proyecto = null;
        for (ProyectoInmobiliario pr : gestorProyectos.getProyectos().values()) {
            if (pr.getNombre().equals(nombreProyecto)) {
                proyecto = pr;
                break;
            }
        }
        if (proyecto == null) {
            JOptionPane.showMessageDialog(this, "Proyecto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resultado = proyecto.proyectarOfertaDemanda(meses);
        proyecto.registrarEstadoMercado(); // deja registro en el historial del proyecto
        JOptionPane.showMessageDialog(this, resultado, "Proyección de Precio", JOptionPane.INFORMATION_MESSAGE);
    }
}
