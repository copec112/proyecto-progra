/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Utilidad para restringir campos de texto a que solo acepten números
 * enteros (no letras, no símbolos, no decimales). Se usa en los IDs y
 * en los campos numéricos de los formularios de la ventana.
 *
 * Uso: FiltrosTexto.soloEnteros(miJTextField);
 *
 * @author luisi
 */
public class FiltrosTexto {

    /**
     * Instala en el campo de texto indicado un filtro de documento que solo
     * permite insertar o reemplazar texto compuesto por dígitos (0-9),
     * bloqueando letras, símbolos y decimales.
     *
     * @param campo el campo de texto al que se le restringe la entrada.
     */
    public static void soloEnteros(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string != null && string.matches("[0-9]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null && text.matches("[0-9]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    // Para campos de NOMBRE (Cliente, Agente, Proyecto, Ubicación): solo letras,
    // tildes, ñ y espacios. Bloquea números y símbolos igual que soloEnteros
    // bloquea letras, pero al revés.
    /**
     * Instala en el campo de texto indicado un filtro de documento que solo
     * permite letras (con tildes, ñ/Ñ y ü/Ü) y espacios, bloqueando números
     * y símbolos. Pensado para campos de nombre (Cliente, Agente, Proyecto,
     * Ubicación).
     *
     * @param campo el campo de texto al que se le restringe la entrada.
     */
    public static void soloLetras(JTextField campo) {
        final String PATRON = "[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]*";
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string != null && string.matches(PATRON)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null && text.matches(PATRON)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
}
