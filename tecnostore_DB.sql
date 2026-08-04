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

INSERT INTO celulares (marca, modelo, sistema_operativo, gama, precio, stock) VALUES
('Apple', 'iPhone 15 Pro', 'IOS', 'Alta', 1199.99, 15),
('Samsung', 'Galaxy S24', 'ANDROID', 'Alta', 999.50, 20),
('Xiaomi', 'Redmi Note 13', 'ANDROID', 'Media', 250.00, 40),
('Motorola', 'Moto G54', 'ANDROID', 'Media', 180.00, 35),
('Nokia', '110', 'PROPRIETARY', 'Baja', 30.00, 50);

INSERT INTO clientes (nombre, identificacion, correo, telefono) VALUES
('Juan Pérez', '10203040', 'juan.perez@email.com', '+573001234567'),
('Maria López', '50607080', 'maria.lopez@email.com', '+573119876543'),
('Carlos Mendoza', '90101112', 'carlos.m@email.com', '+573204567890');

INSERT INTO ventas (id_cliente, total) VALUES
(1, 1199.99),
(2, 500.00),
(3, 30.00);

INSERT INTO detalle_ventas (id_venta, id_celular, cantidad, subtotal) VALUES
(1, 1, 1, 1199.99),
(2, 3, 2, 500.00),
(3, 5, 1, 30.00);
