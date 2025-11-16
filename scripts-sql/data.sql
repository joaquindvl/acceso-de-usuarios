USE app_usuarios;

-- Usuarios de prueba
INSERT INTO usuario (nombre, email, eliminado)
VALUES
('Juan Pérez', 'juan@example.com', FALSE),
('Ana López', 'ana@example.com', FALSE);

-- Credenciales asociadas a cada usuario
INSERT INTO credencial_acceso (username, password, eliminado, usuario_id)
VALUES
('juanp', '1234', FALSE, 1),
('analo', 'abcd', FALSE, 2);
