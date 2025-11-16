-- CREACIÓN DE LA BASE DE DATOS
CREATE DATABASE IF NOT EXISTS app_usuarios
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE app_usuarios;

-- TABLA USUARIO (A)
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- TABLA CREDENCIAL_ACCESO (B)
CREATE TABLE credencial_acceso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(150) NOT NULL,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,

    -- Relación 1 a 1 unidireccional usando FK única
    usuario_id BIGINT NOT NULL UNIQUE,

    CONSTRAINT fk_cred_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
