# TecnoStore

Sistema de consola en Java para la gestión de ventas, inventario y clientes de una tienda minorista de celulares.

## Descripción del proyecto

TecnoStore automatiza el control de ventas, inventario y clientes de una tienda de celulares, reemplazando el manejo manual en hojas de cálculo. Permite:

- Gestionar el catálogo de celulares (registrar, actualizar, eliminar, listar, reponer stock).
- Gestionar clientes (registrar, actualizar, eliminar, listar), con validación de correo e identificación única.
- Registrar ventas de uno o varios celulares por cliente, calculando el total con IVA (19%) y aplicando descuentos según la gama del celular.
- Generar reportes de stock bajo, celulares más vendidos y ventas totales por mes.
- Exportar un resumen de todas las ventas a un archivo de texto (`reporte_ventas.txt`).

El proyecto aplica Programación Orientada a Objetos (encapsulamiento, herencia, polimorfismo, composición), colecciones, manejo de excepciones, persistencia con JDBC y patrones de diseño (Factory y Singleton).

## Estructura de clases

El proyecto está organizado en capas, cada una con una responsabilidad única:

```
MODELO       -> Entidades del negocio (Celular, Cliente, Venta, ItemVenta)
DAO          -> Persistencia con JDBC (solo acceso a datos, sin reglas de negocio)
CONTROLADOR  -> Reglas de negocio, validaciones y orquestación
UTILIDADES   -> Validaciones de entrada, reportes (Stream API), generación de archivos, Factory
VISTA        -> Menús de consola, uno por módulo
```

### MODELO
- `Celular` (clase abstracta) — atributos comunes de un celular.
- `CelularGamaAlta`, `CelularGamaMedia`, `CelularGamaBaja` — subclases que heredan de `Celular` y definen su propio descuento (0%, 5%, 10%).
- `Cliente` — datos del cliente.
- `Venta` — cabecera de una venta, compuesta por un `Cliente` y una lista de `ItemVenta`.
- `ItemVenta` — un celular vendido dentro de una venta, con su cantidad y subtotal.

### DAO
- `CelularDAO`, `ClienteDAO`, `VentasDAO` — operaciones CRUD contra MySQL usando `PreparedStatement` y `try-with-resources`.

### CONTROLADOR
- `ConexionDB` — conexión a la base de datos (patrón **Singleton**).
- `CelularController`, `ClienteController` — validan datos antes de persistir.
- `VentaController` — valida stock, calcula el total con descuento por gama e IVA, y coordina la persistencia de la venta, sus detalles y la actualización de stock.

### UTILIDADES
- `Validador` — validaciones de negocio (correo, identificación única, precio, stock) y de entrada por consola.
- `FactoryCelular` — patrón **Factory**: decide qué subclase de `Celular` instanciar según la gama.
- `ReporteUtils` — reportes con Stream API (stock bajo, top 3 más vendidos, ventas por mes).
- `ArchivoUtils` — genera `reporte_ventas.txt`.

### VISTA
- `MenuPrincipal` — menú principal, delega en las siguientes vistas.
- `VistaCelulares`, `VistaClientes`, `VistaVentas`, `VistaReportes` — un menú de consola por módulo.

### Diagrama de capas

```
VISTA  -->  CONTROLADOR  -->  DAO  -->  Base de datos MySQL
                |
           UTILIDADES
```

## Indicaciones para conexión MySQL

1. Tener MySQL instalado y corriendo localmente (puerto por defecto `3306`).
2. Ejecutar el script de creación de la base de datos (tablas `celulares`, `clientes`, `ventas`, `detalle_ventas`).
3. Verificar que las credenciales en `CONTROLADOR/ConexionDB.java` coincidan con tu instalación:

```java
DriverManager.getConnection("jdbc:mysql://localhost:3306/tecnostore_db", "root", "TU_CONTRASEÑA");
```

4. Asegurarse de que el proyecto tenga como dependencia el conector de MySQL (`mysql-connector-j`) en el `pom.xml`:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
```

5. Compilar y ejecutar `TecnoStore.java` (clase con el método `main`).

## Ejemplo de ejecución

### Menú principal

<!-- imagen: menu principal -->

### Gestión de celulares

<!-- imagen: registrar celular -->

<!-- imagen: listar celulares -->

<!-- imagen: reponer stock -->

### Gestión de clientes

<!-- imagen: registrar cliente -->

<!-- imagen: listar clientes -->

### Gestión de ventas

<!-- imagen: registrar venta -->

<!-- imagen: listar ventas -->

### Reportes

<!-- imagen: stock bajo -->

<!-- imagen: top 3 mas vendidos -->

<!-- imagen: ventas por mes -->

### Archivo generado (reporte_ventas.txt)

<!-- imagen: contenido de reporte_ventas.txt -->
