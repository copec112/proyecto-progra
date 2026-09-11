/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Encargada de leer y escribir los datos del sistema en archivos CSV,
 * ubicados en la carpeta "data/" (se crea automáticamente junto al .jar
 * o dentro del proyecto, dependiendo de dónde se ejecute).
 *
 * Se guarda todo cada vez que se agrega/edita/elimina algo desde la
 * ventana, así los datos quedan persistidos aunque se cierre el programa.
 *
 * @author jacor
 */
public class CsvManager {

    private static final String CARPETA = "data";

    // ============ CARGA / GUARDADO GENERAL ============

    public static void cargarTodo(GestorClientes gc, GestorAgentes ga, GestorPropiedades gp, GestorProyectos gpr, GestorVentas gv) {
        crearCarpetaSiNoExiste();
        cargarClientes(gc);
        cargarAgentes(ga);
        cargarPropiedades(gp, gc);
        cargarProyectos(gpr, gp);
        cargarVentas(gv, gp, gc, ga); // ok, misma firma
        System.out.println("=== Carga de datos CSV completa ===");
    }

    public static void guardarTodo(GestorClientes gc, GestorAgentes ga, GestorPropiedades gp, GestorProyectos gpr, GestorVentas gv) {
        crearCarpetaSiNoExiste();
        guardarClientes(gc);
        guardarAgentes(ga);
        guardarPropiedades(gp, gc);
        guardarProyectos(gpr);
        guardarVentas(gv, gp);
        System.out.println("=== Guardado de datos CSV completo ===");
    }

    private static void crearCarpetaSiNoExiste() {
        File carpeta = new File(CARPETA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    // ============ CLIENTES ============

    public static void guardarClientes(GestorClientes gc) {
        crearCarpetaSiNoExiste();
        try (PrintWriter pw = new PrintWriter(new FileWriter(CARPETA + "/clientes.csv"))) {
            pw.println("id,nombre");
            for (Cliente c : gc.getClientes().values()) {
                pw.println(escapar(c.getId()) + "," + escapar(c.getNombre()));
            }
        } catch (IOException e) {
            System.out.println("Error al guardar clientes.csv: " + e.getMessage());
        }
    }

    public static void cargarClientes(GestorClientes gc) {
        File archivo = new File(CARPETA + "/clientes.csv");
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine(); // salta el header
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(",", -1);
                if (p.length < 2) continue;
                gc.agregarCliente(new Cliente(desescapar(p[0]), desescapar(p[1])));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar clientes.csv: " + e.getMessage());
        }
    }

    // ============ AGENTES ============

    public static void guardarAgentes(GestorAgentes ga) {
        crearCarpetaSiNoExiste();
        try (PrintWriter pw = new PrintWriter(new FileWriter(CARPETA + "/agentes.csv"))) {
            pw.println("id,nombre");
            for (AgenteInmobiliario a : ga.getAgentes().values()) {
                pw.println(escapar(a.getId()) + "," + escapar(a.getNombre()));
            }
        } catch (IOException e) {
            System.out.println("Error al guardar agentes.csv: " + e.getMessage());
        }
    }

    public static void cargarAgentes(GestorAgentes ga) {
        File archivo = new File(CARPETA + "/agentes.csv");
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine();
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(",", -1);
                if (p.length < 2) continue;
                ga.agregarAgentes(new AgenteInmobiliario(desescapar(p[0]), desescapar(p[1])));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar agentes.csv: " + e.getMessage());
        }
    }

    // ============ PROPIEDADES ============

    public static void guardarPropiedades(GestorPropiedades gp, GestorClientes gc) {
        crearCarpetaSiNoExiste();
        try (PrintWriter pw = new PrintWriter(new FileWriter(CARPETA + "/propiedades.csv"))) {
            pw.println("id,tipo,descripcion,numHabitaciones,numBanos,valorUF,numInteresados,vendido,estacionamiento,numero,clienteId");
            for (Map.Entry<Integer, Propiedad> entry : gp.getPropiedades().entrySet()) {
                int id = entry.getKey();
                Propiedad prop = entry.getValue();
                String tipo;
                int numero;
                if (prop instanceof Casa) {
                    tipo = "CASA";
                    numero = ((Casa) prop).getNumeroCasa();
                } else if (prop instanceof Departamento) {
                    tipo = "DEPARTAMENTO";
                    numero = ((Departamento) prop).getNumeroDepartamento();
                } else {
                    continue; // tipo desconocido, no debería pasar
                }
                String clienteId = buscarIdClientePorPropiedad(prop, gc);
                pw.println(id + "," + tipo + "," + escapar(prop.getDescripcion()) + ","
                        + prop.getNumHabitaciones() + "," + prop.getNumBaños() + "," + prop.getValorUF() + ","
                        + prop.getNumInteresados() + "," + prop.isVendido() + "," + prop.isEstacionamiento() + ","
                        + numero + "," + escapar(clienteId));
            }
        } catch (IOException e) {
            System.out.println("Error al guardar propiedades.csv: " + e.getMessage());
        }
    }

    private static String buscarIdClientePorPropiedad(Propiedad prop, GestorClientes gc) {
        for (Cliente c : gc.getClientes().values()) {
            if (c.getPropiedadesAdquiridas().contains(prop)) {
                return c.getId();
            }
        }
        return "";
    }

    public static void cargarPropiedades(GestorPropiedades gp, GestorClientes gc) {
        File archivo = new File(CARPETA + "/propiedades.csv");
        if (!archivo.exists()) return;
        boolean seRepararonDatos = false;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine();
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(",", -1);
                if (p.length < 11) continue;

                int id = Integer.parseInt(p[0]);
                String tipo = p[1];
                String descripcion = desescapar(p[2]);
                int numHabitaciones = Integer.parseInt(p[3]);
                int numBanos = Integer.parseInt(p[4]);
                int valorUF = Integer.parseInt(p[5]);
                int numInteresados = Integer.parseInt(p[6]);
                boolean vendido = Boolean.parseBoolean(p[7]);
                boolean estacionamiento = Boolean.parseBoolean(p[8]);
                int numero = Integer.parseInt(p[9]);
                String clienteId = desescapar(p[10]);

                Propiedad prop;
                if (tipo.equals("CASA")) {
                    prop = new Casa(descripcion, numHabitaciones, numBanos, valorUF, estacionamiento, numero);
                } else {
                    prop = new Departamento(descripcion, numHabitaciones, numBanos, valorUF, estacionamiento, numero);
                }
                prop.setNumInteresados(numInteresados);
                gp.agregarPropiedad(id, prop);

                // Primero se intenta reconectar con el cliente dueño (si corresponde).
                boolean clienteEncontrado = false;
                if (!clienteId.isEmpty()) {
                    try {
                        Cliente c = gc.buscarCliente(clienteId);
                        c.agregarPropiedad(prop);
                        clienteEncontrado = true;
                    } catch (ElementoNoEncontradoException e) {
                        System.out.println("Aviso: cliente " + clienteId + " (dueño de propiedad " + id + ") no existe en clientes.csv");
                    }
                }

                // AUTO-REPARACIÓN: si el CSV dice vendido=true pero no se pudo
                // reconectar con un cliente real (venta "fantasma" de datos viejos,
                // ej. de antes de que existiera el bloqueo al eliminar clientes),
                // se libera la propiedad en vez de dejarla vendida sin dueño.
                if (vendido && clienteEncontrado) {
                    try {
                        prop.setVendido(true);
                    } catch (PropiedadVendidaException e) {
                        // no debería pasar al cargar un dato fresco
                    }
                } else if (vendido && !clienteEncontrado) {
                    System.out.println("Reparando propiedad " + id + ": figuraba vendida pero sin un cliente válido. Se marca como DISPONIBLE.");
                    seRepararonDatos = true;
                    // prop.vendido ya nace en false por el constructor, no hace falta tocarlo.
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar propiedades.csv: " + e.getMessage());
        }

        if (seRepararonDatos) {
            guardarPropiedades(gp, gc);
            System.out.println("propiedades.csv actualizado con las reparaciones automáticas.");
        }
    }

    // ============ PROYECTOS ============

    public static void guardarProyectos(GestorProyectos gpr) {
        crearCarpetaSiNoExiste();
        try (PrintWriter pw = new PrintWriter(new FileWriter(CARPETA + "/proyectos.csv"))) {
            pw.println("idProyecto,nombre,ubicacion,propiedadesIds");
            for (ProyectoInmobiliario pr : gpr.getProyectos().values()) {
                StringBuilder ids = new StringBuilder();
                for (Integer idProp : pr.getPropiedades().keySet()) {
                    if (ids.length() > 0) ids.append("|");
                    ids.append(idProp);
                }
                pw.println(escapar(pr.getIdProyecto()) + "," + escapar(pr.getNombre()) + ","
                        + escapar(pr.getUbicacion()) + "," + ids);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar proyectos.csv: " + e.getMessage());
        }
    }

    public static void cargarProyectos(GestorProyectos gpr, GestorPropiedades gp) {
        File archivo = new File(CARPETA + "/proyectos.csv");
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine();
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(",", -1);
                if (p.length < 3) continue;

                String idProyecto = desescapar(p[0]);
                String nombre = desescapar(p[1]);
                String ubicacion = desescapar(p[2]);
                ProyectoInmobiliario proyecto = new ProyectoInmobiliario(idProyecto, nombre, ubicacion);
                gpr.agregarProyecto(proyecto);

                if (p.length >= 4 && !p[3].trim().isEmpty()) {
                    String[] ids = p[3].split("\\|");
                    for (String idStr : ids) {
                        try {
                            int idProp = Integer.parseInt(idStr.trim());
                            Propiedad prop = gp.buscarPropiedad(idProp);
                            proyecto.getPropiedades().put(idProp, prop);
                        } catch (NumberFormatException | ElementoNoEncontradoException e) {
                            System.out.println("Aviso: propiedad " + idStr + " del proyecto " + idProyecto + " no encontrada.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar proyectos.csv: " + e.getMessage());
        }
    }

    // ============ VENTAS ============

    // Necesita GestorPropiedades para poder resolver el id numérico de cada
    // propiedad vendida (Venta solo guarda la referencia al objeto, no su id).
    public static void guardarVentas(GestorVentas gv, GestorPropiedades gp) {
        crearCarpetaSiNoExiste();
        try (PrintWriter pw = new PrintWriter(new FileWriter(CARPETA + "/ventas.csv"))) {
            pw.println("propiedadId,clienteId,agenteId");
            for (Venta v : gv.getVentas()) {
                Integer idProp = null;
                for (Map.Entry<Integer, Propiedad> entry : gp.getPropiedades().entrySet()) {
                    if (entry.getValue() == v.getPropiedad()) {
                        idProp = entry.getKey();
                        break;
                    }
                }
                if (idProp == null) continue; // propiedad no encontrada, se omite
                pw.println(idProp + "," + escapar(v.getCliente().getId()) + "," + escapar(v.getAgenteEncargado().getId()));
            }
        } catch (IOException e) {
            System.out.println("Error al guardar ventas.csv: " + e.getMessage());
        }
    }

    public static void cargarVentas(GestorVentas gv, GestorPropiedades gp, GestorClientes gc, GestorAgentes ga) {
        File archivo = new File(CARPETA + "/ventas.csv");
        if (!archivo.exists()) return;
        boolean seRepararonDatos = false;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine();
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(",", -1);
                if (p.length < 3) continue;
                try {
                    int idProp = Integer.parseInt(p[0]);
                    String idCliente = desescapar(p[1]);
                    String idAgente = desescapar(p[2]);

                    Propiedad prop = gp.buscarPropiedad(idProp);
                    Cliente cliente = gc.buscarCliente(idCliente);
                    AgenteInmobiliario agente = ga.buscarAgentes(idAgente);

                    // Solo reconstruimos el registro (para mostrar el agente en pantalla).
                    // El vendido=true y la relación cliente-propiedad ya se reconstruyen
                    // al cargar propiedades.csv, así que esto no duplica nada, solo
                    // recrea el "recibo" de la venta con su agente asociado.
                    gv.agregarVenta(new Venta(prop, cliente, agente));
                } catch (NumberFormatException | ElementoNoEncontradoException e) {
                    // AUTO-REPARACIÓN: venta "fantasma" que apunta a un cliente/agente/
                    // propiedad que ya no existe (ej. de antes del bloqueo al eliminar).
                    // Se descarta y ventas.csv se reescribe limpio al final de la carga.
                    System.out.println("Reparando ventas.csv: se descarta un registro inválido (" + linea + ")");
                    seRepararonDatos = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar ventas.csv: " + e.getMessage());
        }

        if (seRepararonDatos) {
            guardarVentas(gv, gp);
            System.out.println("ventas.csv actualizado con las reparaciones automáticas.");
        }
    }

    // ============ UTILIDADES ============

    // Reemplaza las comas del texto para no romper el formato CSV (simple, sin librerías externas)
    private static String escapar(String s) {
        return s == null ? "" : s.replace(",", "%2C");
    }

    private static String desescapar(String s) {
        return s == null ? "" : s.replace("%2C", ",");
    }
}
