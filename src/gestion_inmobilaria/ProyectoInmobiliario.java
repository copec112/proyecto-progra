/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa un proyecto inmobiliario: un conjunto de propiedades ubicadas
 * en un mismo lugar, con lógica de negocio para calcular oferta y demanda,
 * y proyectar cómo podría evolucionar su precio promedio a futuro según
 * qué tan tensionado esté el mercado.
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
    /**
     * Crea un proyecto inmobiliario vacío, sin propiedades ni historial de
     * mercado, con los campos de texto inicializados en cadena vacía.
     */
    public ProyectoInmobiliario() {
        this.idProyecto = "";
        this.nombre = "";
        this.ubicacion = "";
        this.propiedades = new HashMap<>();
        this.historialMercado = new ArrayList<>();
    }

    /**
     * Crea un proyecto inmobiliario con sus datos básicos, sin propiedades
     * ni historial de mercado todavía.
     *
     * @param idProyecto identificador único del proyecto.
     * @param nombre nombre del proyecto.
     * @param ubicacion ubicación del proyecto.
     */
    public ProyectoInmobiliario(String idProyecto, String nombre, String ubicacion) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.propiedades = new HashMap<>();
        this.historialMercado = new ArrayList<>();
    }

    // <<Gestión de la coleccion>>
    // TODO: aquí normalmente se delegaría en un GestorPropiedades sobre "propiedades".
    /**
     * Punto de extensión para la gestión de propiedades del proyecto
     * (agregar/editar/eliminar/mostrar). Actualmente no hace nada; la
     * lógica está pendiente.
     */
    public void gestionarPropiedades() {
        // lógica pendiente (agregar/editar/eliminar/mostrar propiedades del proyecto)
    }

    // Asigna (o reasigna) una propiedad a este proyecto. Reemplaza la mutación
    // directa que antes se hacía desde afuera con pr.getPropiedades().put(...),
    // la cual rompía el encapsulamiento al modificar la colección interna
    // a través del getter.
    /**
     * Asigna (o reasigna) una propiedad a este proyecto, guardándola bajo
     * el id indicado.
     *
     * @param id identificador de la propiedad dentro del proyecto.
     * @param propiedad propiedad a asignar.
     */
    public void asignarPropiedad(int id, Propiedad propiedad) {
        this.propiedades.put(id, propiedad);
    }

    // Quita una propiedad de este proyecto (sin eliminarla de GestorPropiedades).
    // Devuelve la propiedad quitada, o null si no estaba asignada a este
    // proyecto -- mismo contrato que Map.remove(), para no romper el código
    // que ya revisaba ese valor de retorno.
    /**
     * Quita una propiedad de este proyecto (sin eliminarla de
     * GestorPropiedades).
     *
     * @param id identificador de la propiedad a quitar.
     * @return la propiedad quitada, o {@code null} si no estaba asignada a
     *         este proyecto.
     */
    public Propiedad quitarPropiedad(int id) {
        return this.propiedades.remove(id);
    }

    // <<Lógica del Negocio (Oferta y Demanda)>>
    // Oferta disponible = propiedades no vendidas
    /**
     * Calcula la oferta disponible del proyecto, es decir, la cantidad de
     * propiedades aún no vendidas.
     *
     * @return cantidad de propiedades no vendidas.
     */
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
    /**
     * Calcula la demanda total del proyecto, sumando la cantidad de
     * interesados registrados en todas sus propiedades.
     *
     * @return suma de interesados de todas las propiedades del proyecto.
     */
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
    /**
     * Calcula el precio promedio actual de las propiedades del proyecto
     * (vendidas o no), usado como punto de partida de la proyección.
     *
     * @return precio promedio en UF, o 0 si el proyecto no tiene propiedades.
     */
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
    /**
     * Calcula la razón demanda/oferta del proyecto: mayor a 1 significa
     * más interesados que propiedades disponibles (mercado tensionado,
     * sube más rápido); menor a 1 significa que sobra oferta respecto al
     * interés actual (sube más lento).
     *
     * @return ratio de tensión del mercado (demanda dividida por oferta,
     *         o un tope de tensión asumido si no hay oferta disponible).
     */
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
    /**
     * Proyecta el precio promedio del proyecto a N meses, usando interés
     * compuesto con la tasa base ajustada por el ratio de tensión del
     * mercado.
     *
     * @param mesesFuturo cantidad de meses a proyectar hacia el futuro.
     * @return precio promedio proyectado en UF.
     */
    public double proyectarPrecioPromedio(int mesesFuturo) {
        double tasaAjustada = TASA_MENSUAL_BASE * calcularRatioTension();
        double precioActual = calcularPrecioPromedioActual();
        return precioActual * Math.pow(1 + tasaAjustada, mesesFuturo);
    }

    // Cuánto sube el precio promedio en UF (positivo = sube).
    /**
     * Calcula cuánto sube el precio promedio en UF a N meses (positivo
     * significa que sube).
     *
     * @param mesesFuturo cantidad de meses a proyectar hacia el futuro.
     * @return aumento estimado en UF.
     */
    public double calcularAumentoEstimado(int mesesFuturo) {
        return proyectarPrecioPromedio(mesesFuturo) - calcularPrecioPromedioActual();
    }

    // Lo mismo pero en porcentaje respecto al precio actual.
    /**
     * Calcula el mismo aumento que {@link #calcularAumentoEstimado(int)}
     * pero expresado en porcentaje respecto al precio actual.
     *
     * @param mesesFuturo cantidad de meses a proyectar hacia el futuro.
     * @return aumento estimado en porcentaje, o 0 si el precio actual es 0.
     */
    public double calcularAumentoPorcentual(int mesesFuturo) {
        double actual = calcularPrecioPromedioActual();
        if (actual == 0) return 0;
        return (calcularAumentoEstimado(mesesFuturo) / actual) * 100;
    }

    /**
     * Proyecta la oferta y demanda del proyecto a 1 mes.
     *
     * @return texto descriptivo con el resultado de la proyección a 1 mes.
     */
    public String proyectarOfertaDemanda() {
        return proyectarOfertaDemanda(1);
    }

    /**
     * Proyecta la oferta, demanda y evolución del precio promedio del
     * proyecto a N meses, y arma un texto descriptivo con el resultado.
     *
     * @param mesesFuturo cantidad de meses a proyectar hacia el futuro.
     * @return texto descriptivo con oferta, demanda, ratio de tensión,
     *         precio actual, precio proyectado y el aumento estimado.
     */
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
    /**
     * Registra en el historial de mercado del proyecto el estado actual
     * de oferta y demanda, usando la fecha y hora actuales.
     */
    public void registrarEstadoMercado() {
        registrarEstadoMercado(LocalDateTime.now().toString(), calcularOfertaDisponible(), calcularDemandaTotal());
    }

    /**
     * Registra en el historial de mercado del proyecto un estado de
     * oferta y demanda con la fecha indicada.
     *
     * @param fecha fecha y hora del registro, en formato aceptado por
     *        {@link LocalDateTime#parse(CharSequence)}.
     * @param oferta oferta disponible en el momento del registro.
     * @param demanda demanda total en el momento del registro.
     */
    public void registrarEstadoMercado(String fecha, int oferta, int demanda) {
        // NOTA: RegistroMercado espera LocalDateTime; se parsea el string recibido.
        LocalDateTime fechaRegistro = LocalDateTime.parse(fecha);
        this.historialMercado.add(new RegistroMercado(fechaRegistro, oferta, demanda));
    }

    // Get/set
    /** @param idProyecto nuevo identificador del proyecto. */
    public void setIdProyecto(String idProyecto) {this.idProyecto = idProyecto;}
    /** @return el identificador del proyecto. */
    public String getIdProyecto() {return idProyecto;}

    /** @param nombre nuevo nombre del proyecto. */
    public void setNombre(String nombre) {this.nombre = nombre;}
    /** @return el nombre del proyecto. */
    public String getNombre() {return nombre;}

    /** @param ubicacion nueva ubicación del proyecto. */
    public void setUbicacion(String ubicacion) {this.ubicacion = ubicacion;}
    /** @return la ubicación del proyecto. */
    public String getUbicacion() {return ubicacion;}

    // Devuelve una vista de solo lectura: quien llame no puede agregar/quitar
    // propiedades saltándose asignarPropiedad()/quitarPropiedad() (rompería
    // el encapsulamiento de la colección interna).
    /**
     * Devuelve una vista de solo lectura de las propiedades del proyecto;
     * para modificar la colección se debe usar {@link #asignarPropiedad}
     * o {@link #quitarPropiedad}.
     *
     * @return mapa inmutable de propiedades (clave: id de propiedad).
     */
    public Map<Integer, Propiedad> getPropiedades() {return Collections.unmodifiableMap(propiedades);}
    /** @param propiedades nuevo mapa de propiedades del proyecto. */
    public void setPropiedades(Map<Integer, Propiedad> propiedades) {this.propiedades = propiedades;}

    /** @return el historial de estados de mercado del proyecto. */
    public List<RegistroMercado> getHistorialMercado() {return historialMercado;}
    /** @param historialMercado nuevo historial de estados de mercado. */
    public void setHistorialMercado(List<RegistroMercado> historialMercado) {this.historialMercado = historialMercado;}
}
