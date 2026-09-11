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
        System.out.println("Proyecto creado: [" + idProyecto + "] " + nombre + " (" + ubicacion + ")");
    }

    // <<Gestión de la coleccion>>
    // TODO: aquí normalmente se delegaría en un GestorPropiedades sobre "propiedades".
    public void gestionarPropiedades() {
        System.out.println("Gestionando propiedades del proyecto " + nombre + "... (lógica pendiente)");
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
        System.out.println("Oferta disponible en " + nombre + ": " + oferta);
        return oferta;
    }

    // Demanda total = suma de interesados en todas las propiedades
    public int calcularDemandaTotal() {
        int demanda = 0;
        for (Propiedad p : propiedades.values()) {
            demanda += p.getNumInteresados();
        }
        System.out.println("Demanda total en " + nombre + ": " + demanda);
        return demanda;
    }

    // <<Funcionalidad Única>>
    // TODO: definir la lógica real de proyección.
    public String proyectarOfertaDemanda() {
        return proyectarOfertaDemanda(1);
    }

    public String proyectarOfertaDemanda(int mesesFuturo) {
        // placeholder: reemplazar con el modelo de proyección real
        String resultado = "Proyección a " + mesesFuturo + " mes(es) - oferta actual: "
                + calcularOfertaDisponible() + ", demanda actual: " + calcularDemandaTotal();
        System.out.println(resultado);
        return resultado;
    }

    // <<Historial oferta/demanda>>
    public void registrarEstadoMercado() {
        registrarEstadoMercado(LocalDateTime.now().toString(), calcularOfertaDisponible(), calcularDemandaTotal());
    }

    public void registrarEstadoMercado(String fecha, int oferta, int demanda) {
        // NOTA: RegistroMercado espera LocalDateTime; se parsea el string recibido.
        LocalDateTime fechaRegistro = LocalDateTime.parse(fecha);
        this.historialMercado.add(new RegistroMercado(fechaRegistro, oferta, demanda));
        System.out.println("Estado de mercado registrado en " + nombre + " (" + fecha + "): oferta=" + oferta + ", demanda=" + demanda);
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
