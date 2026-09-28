CREATE DATABASE IF NOT EXISTS menu_al_dia;
USE menu_al_dia;

CREATE TABLE IF NOT EXISTS categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS plato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    categoria_id INT NOT NULL,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255),
    precio_base DECIMAL(10,2) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_plato_categoria
        FOREIGN KEY (categoria_id) REFERENCES categoria(id),
    CONSTRAINT chk_precio_base CHECK (precio_base >= 0)
);

CREATE TABLE IF NOT EXISTS etiqueta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS plato_etiqueta (
    plato_id INT NOT NULL,
    etiqueta_id INT NOT NULL,
    PRIMARY KEY (plato_id, etiqueta_id),
    FOREIGN KEY (plato_id) REFERENCES plato(id) ON DELETE CASCADE,
    FOREIGN KEY (etiqueta_id) REFERENCES etiqueta(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL UNIQUE,
    estado ENUM('BORRADOR','PUBLICADO','CERRADO') NOT NULL DEFAULT 'BORRADOR',
    fecha_publicacion DATETIME
);

CREATE TABLE IF NOT EXISTS menu_plato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    menu_id INT NOT NULL,
    plato_id INT NOT NULL,
    precio_dia DECIMAL(10,2) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_menu_plato UNIQUE (menu_id, plato_id),
    FOREIGN KEY (menu_id) REFERENCES menu(id) ON DELETE CASCADE,
    FOREIGN KEY (plato_id) REFERENCES plato(id),
    CONSTRAINT chk_precio_dia CHECK (precio_dia >= 0)
);

CREATE TABLE IF NOT EXISTS promocion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255),
    fecha_desde DATE NOT NULL,
    fecha_hasta DATE NOT NULL,
    CONSTRAINT chk_fechas_promocion CHECK (fecha_hasta >= fecha_desde)
);

CREATE TABLE IF NOT EXISTS promocion_plato (
    promocion_id INT NOT NULL,
    plato_id INT NOT NULL,
    precio_promocional DECIMAL(10,2),
    PRIMARY KEY (promocion_id, plato_id),
    FOREIGN KEY (promocion_id) REFERENCES promocion(id) ON DELETE CASCADE,
    FOREIGN KEY (plato_id) REFERENCES plato(id),
    CONSTRAINT chk_precio_promocional CHECK (
        precio_promocional IS NULL OR precio_promocional >= 0
    )
);

INSERT IGNORE INTO categoria (id, nombre) VALUES
(1, 'Platos principales'),
(2, 'Entradas'),
(3, 'Bebidas');
