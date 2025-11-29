-- Script para crear usuario admin en MySQL bd_policia
-- Base de datos: bd_policia
-- Tabla: usuarios (debe existir en auth-service)

USE bd_policia;

-- Crear tabla usuarios si no existe
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    cip VARCHAR(20),
    rol VARCHAR(20) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insertar usuario admin
-- Username: admin
-- Password: admin123 (BCrypt hash)
INSERT INTO usuarios (username, password, cip, rol, activo)
VALUES ('admin', '$2a$10$XlOEqM8xqNjN9qN9xqN9xO', '123456789', 'ADMIN', true);

SELECT 'Usuario admin creado exitosamente!' AS mensaje;
SELECT '==================================' AS separador;
SELECT 'Username: admin' AS credencial1;
SELECT 'Password: admin123' AS credencial2;
SELECT '==================================' AS separador2;
