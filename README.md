# Sistema de Gestión de Inmobiliaria

## Descripción

Este sistema de información permite al usuario gestionar las ventas de una inmobiliaria a través de los siguientes recursos: agentes, clientes, proyectos inmobiliarios y propiedades. Cuando se venda una propiedad, se registra el agente encargado y su cliente. Además, el sistema es capaz de registrar la oferta y demanda de un proyecto a través del tiempo y formular una proyección a futuro para decidir la mejor accion.

## Cómo compilar y ejecutar

### Requisitos previos:

- Tener instalado JAVA 8
- Tener instalado una versión del IDE NetBeans 12 (o superior)

### Pasos para compilar y ejecutar:

#### Metodo 1:

 Correr el programa mediante el clonado del repositorio.

1. Copiar el siguiente link de github https://github.com/copec112/proyecto-progra.git
2. Seleccionar el menú de `teams` en NetBeans
3. Clonar el repositorio y crear un nuevo proyecto `ant`
4. Correr el proyecto
5. EXTRA: En caso de que el nuevo poryecto tenga problemas para encontrar el codigo fuente, se sugiere mover los archivos src del proyecto al src de la capreta del proyecto, u probar con el segundo metodo.

#### Metodo 2:

Mediante el zip presente en la entrega o descargado del siguiente link, se puede importar como proyecto de NetBeans.

link: https://drive.google.com/drive/folders/1XnUthsVDPzZbcgqg02aSMbgMRY4VRhyW?usp=sharing

1. Descargar el `.zip` 
2. Desde el menu `file` de NetBeans, seleccione el submenu de `import` 
3. Seleccione la opcion `from ZIP`
4. Seleccione el archivo y carpeta ruta
5. Una vez con el proyecto iniciado, seleccionar la opción de run proyect (flecha verde) o seleccionar con click derecho la clase `App` y  `run file`

## Funcionalidades

### Funcionando correctamente:

- Gestión de colecciones (Agentes, propiedades, proyectos, clientes) con operaciones **CRUD.**
- Manejo de menús a través de consola (TUI) o por interfaz grafica (GUI).
- Vista de demanda/oferta de un proyecto a través del tiempo

### Problemas conocidos:

- Lógica de compra y venta confusa para el usuario primerizo.

### A mejorar:

- Incluir métodos de ordenamiento (alfabético, sector, cantidad, etc.) para los datos.
- Mejorar el proceso de compra y venta (ej: validación de pago).
