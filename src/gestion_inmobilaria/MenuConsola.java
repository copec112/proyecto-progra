/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion_inmobilaria;

import java.util.Map;
import java.util.Scanner;

/**
 * Menú de consola con las mismas funcionalidades que la ventana Swing
 * (MainWindow): Clientes, Agentes, Propiedades, Proyectos y Ventas.
 * Usa los mismos Gestor* y el mismo CsvManager, así que cualquier cambio
 * hecho por consola queda igual de persistido que uno hecho por ventana.
 *
 * IDs y campos numéricos (habitaciones, baños, valor UF, número) solo
 * aceptan enteros NO negativos, igual que FiltrosTexto en la ventana
 * (que directamente bloquea el signo "-" al escribir).
 *
 * @author jacor
 */
public class MenuConsola {

    private final Scanner sc;
    private final GestorClientes gestorClientes;
    private final GestorAgentes gestorAgentes;
    private final GestorPropiedades gestorPropiedades;
    private final GestorProyectos gestorProyectos;
    private final GestorVentas gestorVentas;

    public MenuConsola(GestorClientes gestorClientes, GestorAgentes gestorAgentes,
            GestorPropiedades gestorPropiedades, GestorProyectos gestorProyectos,
            GestorVentas gestorVentas, Scanner sc) {
        this.gestorClientes = gestorClientes;
        this.gestorAgentes = gestorAgentes;
        this.gestorPropiedades = gestorPropiedades;
        this.gestorProyectos = gestorProyectos;
        this.gestorVentas = gestorVentas;
        this.sc = sc;
    }

    // ============ MENÚ PRINCIPAL ============

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== GESTIÓN INMOBILIARIA (CONSOLA) ===");
            System.out.println("1. Gestionar Clientes");
            System.out.println("2. Gestionar Agentes");
            System.out.println("3. Gestionar Propiedades");
            System.out.println("4. Gestionar Proyectos");
            System.out.println("5. Ver Historial de Ventas");
            System.out.println("6. Proyección de Precios");
            System.out.println("7. Guardar y Salir");
            int opcion = leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1: menuClientes(); break;
                case 2: menuAgentes(); break;
                case 3: menuPropiedades(); break;
                case 4: menuProyectos(); break;
                case 5: verVentas(); break;
                case 6: proyectarPrecioConsola(); break;
                case 7:
                    CsvManager.guardarTodo(gestorClientes, gestorAgentes, gestorPropiedades, gestorProyectos, gestorVentas);
                    System.out.println("Datos guardados. ¡Hasta luego!");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    // ============ HELPERS DE LECTURA ============

    // Entero sin restricción de signo: se usa SOLO para navegar menús (elegir opción 1, 2, 3...),
    // donde no corresponde al equivalente de ningún JTextField con FiltrosTexto.
    private int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingresa solo números enteros.");
            }
        }
    }

    // Entero NO negativo: este es el equivalente real a FiltrosTexto.soloEnteros()
    // de la ventana (que bloquea el símbolo "-" directamente al escribir).
    // Se usa para TODOS los IDs y campos numéricos de formularios (habitaciones,
    // baños, valor UF, número de casa/depto) -- igual que en los Panel*.java.
    private int leerEnteroNoNegativo(String prompt) {
        while (true) {
            int valor = leerEntero(prompt);
            if (valor < 0) {
                System.out.println("Este campo no admite números negativos (igual que en la ventana). Intenta de nuevo.");
                continue;
            }
            return valor;
        }
    }

    // Igual que leerEnteroNoNegativo, pero para ediciones donde dejar vacío
    // significa "no cambiar este campo" (equivalente a dejar el JTextField
    // con su valor precargado sin tocarlo en la ventana).
    private Integer leerEnteroOpcionalNoNegativo(String prompt) {
        while (true) {
            String texto = leerTexto(prompt);
            if (texto.isEmpty()) return null;
            try {
                int valor = Integer.parseInt(texto);
                if (valor < 0) {
                    System.out.println("Este campo no admite números negativos. Intenta de nuevo.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Ingresa solo números enteros, o deja vacío para no cambiar.");
            }
        }
    }

    private String leerTexto(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    // Igual que leerTexto, pero solo acepta letras, tildes, ñ y espacios
    // (equivalente a FiltrosTexto.soloLetras() en la ventana). Se usa en
    // TODOS los campos de nombre (Cliente, Agente, Proyecto, Ubicación).
    private String leerTextoSoloLetras(String prompt) {
        final String PATRON = "[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]*";
        while (true) {
            String texto = leerTexto(prompt);
            if (texto.matches(PATRON)) {
                return texto;
            }
            System.out.println("Este campo solo admite letras y espacios (sin números ni símbolos). Intenta de nuevo.");
        }
    }

    private boolean leerSiNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (S/N): ");
            String r = sc.nextLine().trim().toUpperCase();
            if (r.equals("S")) return true;
            if (r.equals("N")) return false;
            System.out.println("Responde S o N.");
        }
    }

    // ============ CLIENTES ============

    private void menuClientes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Clientes ---");
            System.out.println("1. Agregar  2. Editar  3. Eliminar  4. Buscar  5. Mostrar todos  0. Volver");
            int op = leerEntero("Elige una opción: ");
            switch (op) {
                case 1: agregarClienteConsola(); break;
                case 2: editarClienteConsola(); break;
                case 3: eliminarClienteConsola(); break;
                case 4: buscarClienteConsola(); break;
                case 5: gestorClientes.mostrarCliente(); break;
                case 0: volver = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    private void agregarClienteConsola() {
        int id = leerEnteroNoNegativo("ID del cliente (número, sin negativos): ");
        if (gestorClientes.getClientes().containsKey(String.valueOf(id))) {
            System.out.println("Ya existe un cliente con ese ID.");
            return;
        }
        String nombre = leerTextoSoloLetras("Nombre: ");
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        gestorClientes.agregarCliente(new Cliente(String.valueOf(id), nombre));
        CsvManager.guardarClientes(gestorClientes);
    }

    private void editarClienteConsola() {
        int id = leerEnteroNoNegativo("ID del cliente a editar: ");
        try {
            Cliente c = gestorClientes.buscarCliente(String.valueOf(id));
            String nuevoNombre = leerTextoSoloLetras("Nuevo nombre (vacío = no cambiar, actual: " + c.getNombre() + "): ");
            if (!nuevoNombre.isEmpty()) {
                c.setNombre(nuevoNombre);
                CsvManager.guardarClientes(gestorClientes);
            }
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void eliminarClienteConsola() {
        int id = leerEnteroNoNegativo("ID del cliente a eliminar: ");
        try {
            Cliente c = gestorClientes.buscarCliente(String.valueOf(id));
            if (!c.getPropiedadesAdquiridas().isEmpty()) {
                System.out.println("No se puede eliminar: este cliente tiene " + c.getPropiedadesAdquiridas().size()
                        + " propiedad(es) adquirida(s) registrada(s).");
                return;
            }
            gestorClientes.eliminarCliente(String.valueOf(id));
            CsvManager.guardarClientes(gestorClientes);
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void buscarClienteConsola() {
        int id = leerEnteroNoNegativo("ID del cliente a buscar: ");
        try {
            gestorClientes.buscarCliente(String.valueOf(id));
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Cliente no encontrado.");
        }
    }

    // ============ AGENTES ============

    private void menuAgentes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Agentes ---");
            System.out.println("1. Agregar  2. Editar  3. Eliminar  4. Buscar  5. Mostrar todos  0. Volver");
            int op = leerEntero("Elige una opción: ");
            switch (op) {
                case 1: agregarAgenteConsola(); break;
                case 2: editarAgenteConsola(); break;
                case 3: eliminarAgenteConsola(); break;
                case 4: buscarAgenteConsola(); break;
                case 5: gestorAgentes.mostrarAgentes(); break;
                case 0: volver = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    private void agregarAgenteConsola() {
        int id = leerEnteroNoNegativo("ID del agente (número, sin negativos): ");
        if (gestorAgentes.getAgentes().containsKey(String.valueOf(id))) {
            System.out.println("Ya existe un agente con ese ID.");
            return;
        }
        String nombre = leerTextoSoloLetras("Nombre: ");
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        gestorAgentes.agregarAgentes(new AgenteInmobiliario(String.valueOf(id), nombre));
        CsvManager.guardarAgentes(gestorAgentes);
    }

    private void editarAgenteConsola() {
        int id = leerEnteroNoNegativo("ID del agente a editar: ");
        try {
            AgenteInmobiliario a = gestorAgentes.buscarAgentes(String.valueOf(id));
            String nuevoNombre = leerTextoSoloLetras("Nuevo nombre (vacío = no cambiar, actual: " + a.getNombre() + "): ");
            if (!nuevoNombre.isEmpty()) {
                a.setNombre(nuevoNombre);
                CsvManager.guardarAgentes(gestorAgentes);
            }
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Agente no encontrado.");
        }
    }

    private void eliminarAgenteConsola() {
        int id = leerEnteroNoNegativo("ID del agente a eliminar: ");
        try {
            AgenteInmobiliario a = gestorAgentes.buscarAgentes(String.valueOf(id));
            long ventasDelAgente = gestorVentas.getVentas().stream()
                    .filter(v -> v.getAgenteEncargado() == a)
                    .count();
            if (ventasDelAgente > 0) {
                System.out.println("No se puede eliminar: este agente tiene " + ventasDelAgente + " venta(s) registrada(s).");
                return;
            }
            gestorAgentes.eliminarAgentes(String.valueOf(id));
            CsvManager.guardarAgentes(gestorAgentes);
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Agente no encontrado.");
        }
    }

    private void buscarAgenteConsola() {
        int id = leerEnteroNoNegativo("ID del agente a buscar: ");
        try {
            gestorAgentes.buscarAgentes(String.valueOf(id));
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Agente no encontrado.");
        }
    }

    // ============ PROPIEDADES ============

    private void menuPropiedades() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Propiedades ---");
            System.out.println("1. Agregar  2. Editar  3. Eliminar  4. Vender  5. Registrar Interesado  6. Mostrar todas  0. Volver");
            int op = leerEntero("Elige una opción: ");
            switch (op) {
                case 1: agregarPropiedadConsola(); break;
                case 2: editarPropiedadConsola(); break;
                case 3: eliminarPropiedadConsola(); break;
                case 4: venderPropiedadConsola(); break;
                case 5: registrarInteresadoConsola(); break;
                case 6: mostrarPropiedadesConsola(); break;
                case 0: volver = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    private String leerTipoPropiedad() {
        while (true) {
            String tipo = leerTexto("Tipo (CASA / DEPARTAMENTO): ").toUpperCase();
            if (tipo.equals("CASA") || tipo.equals("DEPARTAMENTO")) return tipo;
            System.out.println("Escribe exactamente CASA o DEPARTAMENTO.");
        }
    }

    private void agregarPropiedadConsola() {
        int id = leerEnteroNoNegativo("ID de la propiedad (número único, sin negativos): ");
        if (gestorPropiedades.getPropiedades().containsKey(id)) {
            System.out.println("Ya existe una propiedad con ese ID.");
            return;
        }
        String tipo = leerTipoPropiedad();
        String descripcion = leerTexto("Descripción: ");
        int habitaciones = leerEnteroNoNegativo("N° Habitaciones: ");
        int banos = leerEnteroNoNegativo("N° Baños: ");
        int valorUF = leerEnteroNoNegativo("Valor (UF): ");
        boolean estacionamiento = leerSiNo("¿Tiene estacionamiento?");
        int numero = leerEnteroNoNegativo("N° Casa/Depto: ");

        Propiedad p = tipo.equals("CASA")
                ? new Casa(descripcion, habitaciones, banos, valorUF, estacionamiento, numero)
                : new Departamento(descripcion, habitaciones, banos, valorUF, estacionamiento, numero);

        gestorPropiedades.agregarPropiedad(id, p);
        CsvManager.guardarPropiedades(gestorPropiedades, gestorClientes);
    }

    private void editarPropiedadConsola() {
        int id = leerEnteroNoNegativo("ID de la propiedad a editar: ");
        try {
            Propiedad p = gestorPropiedades.buscarPropiedad(id);

            String descripcion = leerTexto("Nueva descripción (vacío = no cambiar, actual: " + p.getDescripcion() + "): ");
            if (!descripcion.isEmpty()) p.setDescripcion(descripcion);

            Integer hab = leerEnteroOpcionalNoNegativo("Nuevo N° habitaciones (vacío = no cambiar, actual: " + p.getNumHabitaciones() + "): ");
            if (hab != null) p.setNumHabitaciones(hab);

            Integer ban = leerEnteroOpcionalNoNegativo("Nuevo N° baños (vacío = no cambiar, actual: " + p.getNumBaños() + "): ");
            if (ban != null) p.setNumBaños(ban);

            Integer valor = leerEnteroOpcionalNoNegativo("Nuevo valor UF (vacío = no cambiar, actual: " + p.getValorUF() + "): ");
            if (valor != null) p.setValorUF(valor);

            p.setEstacionamiento(leerSiNo("¿Tiene estacionamiento?"));

            CsvManager.guardarPropiedades(gestorPropiedades, gestorClientes);
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Propiedad no encontrada.");
        }
    }

    private void eliminarPropiedadConsola() {
        int id = leerEnteroNoNegativo("ID de la propiedad a eliminar: ");
        try {
            Propiedad p = gestorPropiedades.buscarPropiedad(id);

            if (p.isVendido()) {
                System.out.println("No se puede eliminar: esta propiedad ya está vendida y tiene un cliente asociado.");
                return;
            }
            ProyectoInmobiliario proyectoQueLaTiene = gestorProyectos.buscarProyectoDePropiedad(p);
            if (proyectoQueLaTiene != null) {
                System.out.println("No se puede eliminar: está asignada al proyecto \"" + proyectoQueLaTiene.getNombre()
                        + "\". Quítala de ese proyecto primero (menú Proyectos → Quitar Propiedad del Proyecto).");
                return;
            }

            gestorPropiedades.eliminarPropiedad(id);
            CsvManager.guardarPropiedades(gestorPropiedades, gestorClientes);
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Propiedad no encontrada.");
        }
    }

    // Mismo comportamiento de 3 casos que en PanelPropiedades.venderPropiedad():
    // no vendida -> pide cliente + agente / vendida sin agente -> pide solo agente / vendida con agente -> avisa
    private void venderPropiedadConsola() {
        int id = leerEnteroNoNegativo("ID de la propiedad a vender: ");
        try {
            Propiedad p = gestorPropiedades.buscarPropiedad(id);

            if (p.isVendido() && gestorVentas.buscarVentaPorPropiedad(p) == null) {
                asignarAgenteAVentaExistenteConsola(p);
                return;
            }

            if (p.isVendido()) {
                Venta ventaExistente = gestorVentas.buscarVentaPorPropiedad(p);
                System.out.println("Esta propiedad ya fue vendida por el agente " + ventaExistente.getAgenteEncargado().getNombre() + ".");
                return;
            }

            if (gestorClientes.getClientes().isEmpty()) {
                System.out.println("No hay clientes registrados todavía. Agrega uno primero.");
                return;
            }
            if (gestorAgentes.getAgentes().isEmpty()) {
                System.out.println("No hay agentes registrados todavía. Agrega uno primero.");
                return;
            }

            int idCliente = leerEnteroNoNegativo("ID del cliente comprador: ");
            int idAgente = leerEnteroNoNegativo("ID del agente que vende: ");

            Cliente c = gestorClientes.buscarCliente(String.valueOf(idCliente));
            AgenteInmobiliario agente = gestorAgentes.buscarAgentes(String.valueOf(idAgente));

            Venta venta = agente.venderPropiedad(p, c);
            gestorVentas.agregarVenta(venta);

            CsvManager.guardarPropiedades(gestorPropiedades, gestorClientes);
            CsvManager.guardarVentas(gestorVentas, gestorPropiedades);
        } catch (PropiedadVendidaException e) {
            System.out.println("Esa propiedad ya estaba vendida.");
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Propiedad, cliente o agente no encontrado.");
        }
    }

    private void asignarAgenteAVentaExistenteConsola(Propiedad p) {
        Cliente clienteDueño = buscarClientePorPropiedad(p);
        if (clienteDueño == null) {
            System.out.println("Esta propiedad figura como vendida pero no está asociada a ningún cliente. Revisa clienteId en propiedades.csv.");
            return;
        }
        if (gestorAgentes.getAgentes().isEmpty()) {
            System.out.println("No hay agentes registrados todavía. Agrega uno primero.");
            return;
        }
        System.out.println("Esta propiedad ya está vendida a: " + clienteDueño.getNombre());
        int idAgente = leerEnteroNoNegativo("¿Qué agente la vendió? (ID): ");
        try {
            AgenteInmobiliario agente = gestorAgentes.buscarAgentes(String.valueOf(idAgente));
            Venta venta = new Venta(p, clienteDueño, agente);
            gestorVentas.agregarVenta(venta);
            CsvManager.guardarVentas(gestorVentas, gestorPropiedades);
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Agente no encontrado.");
        }
    }

    private void registrarInteresadoConsola() {
        int id = leerEnteroNoNegativo("ID de la propiedad: ");
        try {
            Propiedad p = gestorPropiedades.buscarPropiedad(id);
            p.registrarInteresados();
            CsvManager.guardarPropiedades(gestorPropiedades, gestorClientes);
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Propiedad no encontrada.");
        }
    }

    private void mostrarPropiedadesConsola() {
        System.out.println("--- Lista de propiedades (" + gestorPropiedades.getPropiedades().size() + ") ---");
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
            String cliente = buscarNombreClientePorPropiedad(p);
            String agente = buscarNombreAgentePorPropiedad(p);
            System.out.println("[" + id + "] " + tipo + " N°" + numero + " - " + p.getDescripcion()
                    + " | " + p.getNumHabitaciones() + " hab, " + p.getNumBaños() + " baños, " + p.getValorUF() + " UF"
                    + " | Interesados: " + p.getNumInteresados()
                    + " | Vendido: " + p.isVendido()
                    + (cliente.isEmpty() ? "" : " | Cliente: " + cliente)
                    + (agente.isEmpty() ? "" : " | Agente: " + agente));
        }
    }

    private String buscarNombreClientePorPropiedad(Propiedad p) {
        Cliente c = buscarClientePorPropiedad(p);
        return c == null ? "" : c.getNombre();
    }

    private Cliente buscarClientePorPropiedad(Propiedad p) {
        for (Cliente c : gestorClientes.getClientes().values()) {
            if (c.getPropiedadesAdquiridas().contains(p)) {
                return c;
            }
        }
        return null;
    }

    private String buscarNombreAgentePorPropiedad(Propiedad p) {
        Venta v = gestorVentas.buscarVentaPorPropiedad(p);
        return v == null ? "" : v.getAgenteEncargado().getNombre();
    }

    // ============ PROYECTOS ============

    private void menuProyectos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Proyectos ---");
            System.out.println("1. Agregar  2. Editar  3. Eliminar  4. Asignar Propiedad Existente  5. Crear y Asignar Propiedad  6. Quitar Propiedad del Proyecto  7. Mostrar todos  0. Volver");
            int op = leerEntero("Elige una opción: ");
            switch (op) {
                case 1: agregarProyectoConsola(); break;
                case 2: editarProyectoConsola(); break;
                case 3: eliminarProyectoConsola(); break;
                case 4: asignarPropiedadConsola(); break;
                case 5: crearYAsignarPropiedadConsola(); break;
                case 6: quitarPropiedadConsola(); break;
                case 7: mostrarProyectosConsola(); break;
                case 0: volver = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    private void agregarProyectoConsola() {
        int id = leerEnteroNoNegativo("ID del proyecto (número, sin negativos): ");
        if (gestorProyectos.getProyectos().containsKey(String.valueOf(id))) {
            System.out.println("Ya existe un proyecto con ese ID.");
            return;
        }
        String nombre = leerTextoSoloLetras("Nombre: ");
        String ubicacion = leerTextoSoloLetras("Ubicación: ");
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        gestorProyectos.agregarProyecto(new ProyectoInmobiliario(String.valueOf(id), nombre, ubicacion));
        CsvManager.guardarProyectos(gestorProyectos);
    }

    private void editarProyectoConsola() {
        int id = leerEnteroNoNegativo("ID del proyecto a editar: ");
        try {
            ProyectoInmobiliario pr = gestorProyectos.buscarProyecto(String.valueOf(id));
            String nombre = leerTextoSoloLetras("Nuevo nombre (vacío = no cambiar, actual: " + pr.getNombre() + "): ");
            if (!nombre.isEmpty()) pr.setNombre(nombre);
            String ubicacion = leerTextoSoloLetras("Nueva ubicación (vacío = no cambiar, actual: " + pr.getUbicacion() + "): ");
            if (!ubicacion.isEmpty()) pr.setUbicacion(ubicacion);
            CsvManager.guardarProyectos(gestorProyectos);
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Proyecto no encontrado.");
        }
    }

    private void eliminarProyectoConsola() {
        int id = leerEnteroNoNegativo("ID del proyecto a eliminar: ");
        try {
            gestorProyectos.eliminarProyecto(String.valueOf(id));
            CsvManager.guardarProyectos(gestorProyectos);
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Proyecto no encontrado.");
        }
    }

    private void asignarPropiedadConsola() {
        int idProyecto = leerEnteroNoNegativo("ID del proyecto: ");
        if (gestorPropiedades.getPropiedades().isEmpty()) {
            System.out.println("No hay propiedades registradas todavía.");
            return;
        }
        int idProp = leerEnteroNoNegativo("ID de la propiedad a asignar: ");
        try {
            ProyectoInmobiliario pr = gestorProyectos.buscarProyecto(String.valueOf(idProyecto));
            Propiedad prop = gestorPropiedades.buscarPropiedad(idProp);

            ProyectoInmobiliario proyectoActual = gestorProyectos.buscarProyectoDePropiedad(prop);
            if (proyectoActual != null && proyectoActual != pr) {
                System.out.println("Esta propiedad ya está asignada al proyecto \"" + proyectoActual.getNombre()
                        + "\". Una propiedad física solo puede pertenecer a un proyecto a la vez.");
                return;
            }
            if (proyectoActual == pr) {
                System.out.println("Esta propiedad ya está asignada a este mismo proyecto.");
                return;
            }

            pr.getPropiedades().put(idProp, prop);
            CsvManager.guardarProyectos(gestorProyectos);
            System.out.println("Propiedad " + idProp + " asignada al proyecto " + idProyecto + ".");
            if (prop.isVendido()) {
                System.out.println("Nota: esta propiedad ya está vendida, por lo tanto NO suma a la oferta disponible del proyecto.");
            }
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Proyecto o propiedad no encontrada.");
        }
    }

    private void crearYAsignarPropiedadConsola() {
        int idProyecto = leerEnteroNoNegativo("ID del proyecto: ");
        try {
            ProyectoInmobiliario pr = gestorProyectos.buscarProyecto(String.valueOf(idProyecto));

            int idProp = leerEnteroNoNegativo("ID de la nueva propiedad (número único, sin negativos): ");
            if (gestorPropiedades.getPropiedades().containsKey(idProp)) {
                System.out.println("Ya existe una propiedad con ese ID.");
                return;
            }
            String tipo = leerTipoPropiedad();
            String descripcion = leerTexto("Descripción: ");
            int habitaciones = leerEnteroNoNegativo("N° Habitaciones: ");
            int banos = leerEnteroNoNegativo("N° Baños: ");
            int valorUF = leerEnteroNoNegativo("Valor (UF): ");
            boolean estacionamiento = leerSiNo("¿Tiene estacionamiento?");
            int numero = leerEnteroNoNegativo("N° Casa/Depto: ");

            Propiedad prop = tipo.equals("CASA")
                    ? new Casa(descripcion, habitaciones, banos, valorUF, estacionamiento, numero)
                    : new Departamento(descripcion, habitaciones, banos, valorUF, estacionamiento, numero);

            gestorPropiedades.agregarPropiedad(idProp, prop);
            pr.getPropiedades().put(idProp, prop);

            CsvManager.guardarPropiedades(gestorPropiedades, gestorClientes);
            CsvManager.guardarProyectos(gestorProyectos);
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Proyecto no encontrado.");
        }
    }

    // Desasigna una propiedad del proyecto (NO la elimina de GestorPropiedades,
    // solo la saca de la lista de este proyecto).
    private void quitarPropiedadConsola() {
        int idProyecto = leerEnteroNoNegativo("ID del proyecto: ");
        try {
            ProyectoInmobiliario pr = gestorProyectos.buscarProyecto(String.valueOf(idProyecto));
            if (pr.getPropiedades().isEmpty()) {
                System.out.println("Este proyecto no tiene propiedades asignadas.");
                return;
            }
            int idProp = leerEnteroNoNegativo("ID de la propiedad a quitar: ");
            if (pr.getPropiedades().remove(idProp) != null) {
                CsvManager.guardarProyectos(gestorProyectos);
                System.out.println("Propiedad " + idProp + " desasignada del proyecto " + idProyecto + ".");
            } else {
                System.out.println("Esa propiedad no estaba asignada a este proyecto.");
            }
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Proyecto no encontrado.");
        }
    }

    private void mostrarProyectosConsola() {
        System.out.println("--- Lista de proyectos (" + gestorProyectos.getProyectos().size() + ") ---");
        for (ProyectoInmobiliario pr : gestorProyectos.getProyectos().values()) {
            System.out.println("[" + pr.getIdProyecto() + "] " + pr.getNombre() + " (" + pr.getUbicacion() + ")"
                    + " | Propiedades: " + pr.getPropiedades().size()
                    + " | Oferta disp.: " + pr.calcularOfertaDisponible()
                    + " | Demanda: " + pr.calcularDemandaTotal());
        }
    }

    // ============ VENTAS ============

    private void verVentas() {
        System.out.println("\n--- Historial de Ventas (" + gestorVentas.getVentas().size() + ") ---");
        for (Venta v : gestorVentas.getVentas()) {
            System.out.println(v.getPropiedad().getDescripcion() + " -> Cliente: " + v.getCliente().getNombre()
                    + " | Agente: " + v.getAgenteEncargado().getNombre());
        }
    }

    // ============ PROYECCIÓN DE PRECIOS ============

    private void proyectarPrecioConsola() {
        if (gestorProyectos.getProyectos().isEmpty()) {
            System.out.println("No hay proyectos registrados todavía.");
            return;
        }

        System.out.println("--- Proyectos disponibles ---");
        for (ProyectoInmobiliario pr : gestorProyectos.getProyectos().values()) {
            System.out.println("[" + pr.getIdProyecto() + "] " + pr.getNombre()
                    + " | Oferta: " + pr.calcularOfertaDisponible()
                    + " | Demanda: " + pr.calcularDemandaTotal()
                    + " | Ratio tensión: " + String.format("%.2f", pr.calcularRatioTension())
                    + " | Precio prom. actual: " + String.format("%.1f", pr.calcularPrecioPromedioActual()) + " UF");
        }

        int idProyecto = leerEnteroNoNegativo("ID del proyecto a proyectar: ");
        try {
            ProyectoInmobiliario pr = gestorProyectos.buscarProyecto(String.valueOf(idProyecto));
            int meses = leerEnteroNoNegativo("¿A cuántos meses proyectar? (mayor a 0): ");
            if (meses <= 0) {
                System.out.println("Los meses a proyectar deben ser mayores a 0.");
                return;
            }
            System.out.println(pr.proyectarOfertaDemanda(meses));
            pr.registrarEstadoMercado();
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Proyecto no encontrado.");
        }
    }
}
