USE dbBecas;
-- ============================================================
-- 1. ROLES
-- ============================================================
INSERT INTO rol (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO rol (nombre) VALUES ('ROLE_ESTUDIANTE');

-- ============================================================
-- 2. BECAS
-- ============================================================
INSERT INTO beca (nombre, descripcion, fecha_inicio, fecha_fin, activa) 
VALUES ('Beca Excelencia 2025', 'Promedio > 16', '2025-01-01', '2025-12-31', 1);

INSERT INTO beca (nombre, descripcion, fecha_inicio, fecha_fin, activa) 
VALUES ('Beca Pasada 2024', 'Cerrada', '2024-01-01', '2024-12-31', 0);

-- ============================================================
-- 3. USUARIO ADMIN
-- ============================================================
-- Usamos TRIM() por seguridad para eliminar espacios invisibles al copiar/pegar 12345
INSERT INTO usuario (username, password, enabled, nombre_completo) 
VALUES (
    'admin', 
    TRIM('$2a$10$l39e/ElCkoy/NUUMYHvFP.bcGuyG5ZTDCQg2XpzqqeJX4x8IuEeNe'), 
    1, 
    'Super Administrador'
);

-- ============================================================
-- 4. ASIGNAR ROL (A prueba de fallos de ID)
-- ============================================================
-- Busca dinámicamente el ID del usuario 'admin' y del rol 'ROLE_ADMIN'
INSERT INTO usuario_rol (usuario_id, rol_id) 
VALUES (
    (SELECT id FROM usuario WHERE username = 'admin'),
    (SELECT id FROM rol WHERE nombre = 'ROLE_ADMIN')
);