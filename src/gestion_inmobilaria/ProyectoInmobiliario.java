/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jacor
 */
public class ProyectoInmobiliario {

    // Atributos
    private String idProyecto;
    private String nombre;
    private String ubicacion;
    private Map<Integer, Propiedad> propiedades;
    private List<RegistroMercado> historialMercado;

    // Constructores
    public ProyectoInmobiliario() {
        this.idProyecto = "";
        this.nombre = "";
        this.ubicacion = "";
        this.propiedades = new HashMap<>();
        this.historialMercado = new ArrayList<>();
    }

    public ProyectoInmobiliario(String idProyecto, String nombre, String ubicacion) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.propiedades = new HashMap<>();
        this.historialMercado = new ArrayList<>();
    }

    // <<Gestión de la coleccion>>
    // TODO: aquí normalmente se delegaría en un GestorPropiedades sobre "propiedades".
    public void gestionarPropiedades() {
        // lógica pendiente (agregar/editar/eliminar/mostrar propiedades del proyecto)
    }

    // <<Lógica del Negocio (Oferta y Demanda)>>
    // Oferta disponible = propiedades no vendidas
    public int calcularOfertaDisponible() {
        int oferta = 0;
        for (Propiedad p : propiedades.values()) {
            if (!p.isVendido()) {
                oferta++;
            }
        }
        return oferta;
    }

    // Demanda total = suma de interesados en todas las propiedades
    public int calcularDemandaTotal() {
        int demanda = 0;
        for (Propiedad p : propiedades.values()) {
            demanda += p.getNumInteresados();
        }
        return demanda;
    }

    // <<Funcionalidad Única>>
    // Modelo de proyección: mientras más tensionado esté el mercado (demanda
    // alta respecto a la oferta disponible), más rápido sube el precio
    // promedio proyectado. Se usa una tasa mensual base (supuesto de mercado
    // estable) ajustada por la razón demanda/oferta, aplicada como interés
    // compuesto sobre los meses futuros.
    private static final double TASA_MENSUAL_BASE = 0.005; // 0.5% mensual en un mercado equilibrado

    // Precio promedio actual de las propiedades del proyecto (vendidas o no),
    // usado como punto de partida de la proyección.
    public double calcularPrecioPromedioActual() {
        if (propiedades.isEmpty()) return 0;
        double suma = 0;
        for (Propiedad p : propiedades.values()) {
            suma += p.getValorUF();
        }
        return suma / propiedades.size();
    }

    // Razón demanda/oferta: >1 significa más interesados que propiedades
    // disponibles (mercado tensionado, sube más rápido); <1 significa que
    // sobra oferta respecto al interés actual (sube más lento).
    public double calcularRatioTension() {
        int oferta = calcularOfertaDisponible();
        int demanda = calcularDemandaTotal();
        if (oferta == 0) {
            return demanda == 0 ? 1.0 : 3.0; // sin oferta disponible: tope de tensión asumido
        }
        return (double) demanda / oferta;
    }

    // Precio promedio proyectado a N meses, usando interés compuesto con
    // la tasa base ajustada por el ratio de tensión del mercado.
    public double proyectarPrecioPromedio(int mesesFuturo) {
        double tasaAjustada = TASA_MENSUAL_BASE * calcularRatioTension();
        double precioActual = calcularPrecioPromedioActual();
        return precioActual * Math.pow(1 + tasaAjustada, mesesFuturo);
    }

    // Cuánto sube el precio promedio en UF (positivo = sube).
    public double calcularAumentoEstimado(int mesesFuturo) {
        return proyectarPrecioPromedio(mesesFuturo) - calcularPrecioPromedioActual();
    }

    // Lo mismo pero en porcentaje respecto al precio actual.
    public double calcularAumentoPorcentual(int mesesFuturo) {
        double actual = calcularPrecioPromedioActual();
        if (actual == 0) return 0;
        return (calcularAumentoEstimado(mesesFuturo) / actual) * 100;
    }

    public String proyectarOfertaDemanda() {
        return proyectarOfertaDemanda(1);
    }

    public String proyectarOfertaDemanda(int mesesFuturo) {
        int oferta = calcularOfertaDisponible();
        int demanda = calcularDemandaTotal();
        double precioActual = calcularPrecioPromedioActual();
        double precioProyectado = proyectarPrecioPromedio(mesesFuturo);
        double aumentoUF = calcularAumentoEstimado(mesesFuturo);
        double aumentoPorcentual = calcularAumentoPorcentual(mesesFuturo);

        return String.format(
                "Proyecto \"%s\" - Proyección a %d mes(es):%n"
                + "  Oferta disponible: %d | Demanda: %d | Ratio de tensión: %.2f%n"
                + "  Precio promedio ACTUAL: %.1f UF%n"
                + "  Precio promedio PROYECTADO: %.1f UF%n"
                + "  >>> SUBE %.1f UF (%.1f%%) en %d mes(es) <<<",
                nombre, mesesFuturo, oferta, demanda, calcularRatioTension(),
                precioActual, precioProyectado, aumentoUF, aumentoPorcentual, mesesFuturo);
    }

    // <<Historial oferta/demanda>>
    public void registrarEstadoMercado() {
        registrarEstadoMercado(LocalDateTime.now().toString(), calcularOfertaDisponible(), calcularDemandaTotal());
    }

    public void registrarEstadoMercado(String fecha, int oferta, int demanda) {
        // NOTA: RegistroMercado espera LocalDateTime; se parsea el string recibido.
        LocalDateTime fechaRegistro = LocalDateTime.parse(fecha);
        this.historialMercado.add(new RegistroMercado(fechaRegistro, oferta, demanda));
    }

    // Get/set
    public void setIdProyecto(String idProyecto) {this.idProyecto = idProyecto;}
    public String getIdProyecto() {return idProyecto;}

    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getNombre() {return nombre;}

    public void setUbicacion(String ubicacion) {this.ubicacion = ubicacion;}
    public String getUbicacion() {return ubicacion;}

    public Map<Integer, Propiedad> getPropiedades() {return propiedades;}
    public void setPropiedades(Map<Integer, Propiedad> propiedades) {this.propiedades = propiedades;}

    public List<RegistroMercado> getHistorialMercado() {return historialMercado;}
    public void setHistorialMercado(List<RegistroMercado> historialMercado) {this.historialMercado = historialMercado;}
}
