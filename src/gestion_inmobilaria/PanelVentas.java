/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Pestaña de solo lectura con el historial de ventas: qué propiedad,
 * a qué cliente, y qué agente la vendió.
 *
 * @author jacor
 */
public class PanelVentas extends JPanel {

    private final GestorVentas gestorVentas;
    private final JTable tabla;
    private final DefaultTableModel modelo;

    public PanelVentas(GestorVentas gestorVentas) {
        this.gestorVentas = gestorVentas;

        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel(new Object[]{"Propiedad", "Cliente", "Agente"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnRefrescar = new JButton("Refrescar");
        panelBotones.add(btnRefrescar);
        add(panelBotones, BorderLayout.SOUTH);

        btnRefrescar.addActionListener(e -> refrescarTabla());

        refrescarTabla();
    }

    public void refrescarTabla() {
        modelo.setRowCount(0);
        for (Venta v : gestorVentas.getVentas()) {
            modelo.addRow(new Object[]{
                v.getPropiedad().getDescripcion(),
                v.getCliente().getNombre(),
                v.getAgenteEncargado().getNombre()
            });
        }
    }
}
