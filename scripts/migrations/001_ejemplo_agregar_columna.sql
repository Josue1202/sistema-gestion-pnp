-- =====================================================
-- MIGRATION EJEMPLO: Agregar columna foto_url
-- =====================================================
-- Autor: Sistema PNP
-- Fecha: 2025-11-29
-- Descripción: Ejemplo de migración para agregar soporte
--              de foto de perfil a personal
-- =====================================================

USE bd_policia;

-- Verificar que la columna no exista (idempotencia)
SET @col_exists = 0;
SELECT COUNT(*) INTO @col_exists 
FROM information_schema.COLUMNS 
WHERE TABLE_SCHEMA = 'bd_policia' 
AND TABLE_NAME = 'personal' 
AND COLUMN_NAME = 'foto_perfil';

-- Solo agregar si no existe
SET @query = IF(@col_exists = 0,
    'ALTER TABLE personal ADD COLUMN foto_perfil VARCHAR(255) COMMENT "URL de la foto de perfil"',
    'SELECT "Columna foto_perfil ya existe, saltando..." AS info'
);

PREPARE stmt FROM @query;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Confirmación
SELECT 'Migration 001 ejecutada exitosamente' AS status;
