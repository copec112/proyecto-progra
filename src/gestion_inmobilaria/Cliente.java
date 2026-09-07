/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

/**
 *
 * @author jacor
 */

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    // Atributos
    private String id;
    private String nombre;
    private List<Propiedad> propiedadesAdquiridas;
    
    // Constructores
    public Cliente(){
        this.id = "";
        this.nombre = "";
        propiedadesAdquiridas = new ArrayList<>();
    }
    public Cliente(String id, String nombre){
        this.id = id;
        this.nombre = nombre;
        propiedadesAdquiridas = new ArrayList<>();
    }
    
    // Solo se puede agregar para no complejizar por el scope del prouyecto
    public void agregarPropiedad(Propiedad propiedad){
        if (propiedad != null) {
            this.propiedadesAdquiridas.add(propiedad);
        }
    }
    
    // Get/set
    // getter: public type get(){return x;}
    // setter: public void set(type x){this.x = x;}
    public void setId(String id){this.id = id;}
    public String getId(){return id;}

    public void setNombre(String nombre){this.nombre = nombre;}
    public String getNombre(){return nombre;}
    
    public List<Propiedad> getPropiedadesAdquiridas() {return propiedadesAdquiridas;}
    public void setPropiedadesAdquiridas(List<Propiedad> propiedadesAdquiridas) {this.propiedadesAdquiridas = propiedadesAdquiridas;}
}
