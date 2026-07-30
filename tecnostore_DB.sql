drop database if exists tecnostore_db;
CREATE DATABASE IF NOT EXISTS tecnostore_db;
USE tecnostore_db;

CREATE TABLE celulares (
    id INT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    sistema_operativo VARCHAR(30) NOT NULL,
    gama ENUM('Alta', 'Media', 'Baja') NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    CONSTRAINT chk_precio_positivo CHECK (precio > 0),
    CONSTRAINT chk_stock_positivo CHECK (stock >= 0)
);

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

CREATE TABLE ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_venta_cliente FOREIGN KEY (id_cliente)
        REFERENCES clientes(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE detalle_ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_celular INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (id_venta)
        REFERENCES ventas(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_detalle_celular FOREIGN KEY (id_celular)
        REFERENCES celulares(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT chk_cantidad_positiva CHECK (cantidad > 0)
);