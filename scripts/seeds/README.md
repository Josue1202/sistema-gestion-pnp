# 🌱 Directorio de Seeds (Datos de Prueba)

## ¿Qué son los seeds?

Los **seeds** son datos de prueba para desarrollo y testing.

**NO son datos de producción.**

## 📋 Uso Recomendado

### ✅ Usa seeds para:
- Datos de prueba en desarrollo
- Testing de funcionalidades
- Demos del sistema
- Capacitación de usuarios

### ❌ NO uses seeds para:
- Datos de producción (usuarios reales)
- Datos sensibles
- Datos que cambiarán constantemente

## 🚀 Cómo Usar

### Ejecutar seeds:
```bash
# Solo en entorno de DESARROLLO
mysql -u root -p123456 bd_policia < scripts/seeds/personal_test_data.sql
```

### Limpiar datos de prueba:
```bash
# Antes de desplegar a producción, NUNCA ejecutes seeds
# Los seeds son solo para desarrollo local
```

## 📝 Ejemplo de Seed

```sql
-- =====================================================
-- SEED: Datos de prueba para personal PNP
-- =====================================================
-- SOLO PARA DESARROLLO - NO EJECUTAR EN PRODUCCIÓN
-- =====================================================

-- Insertar 5 policías de prueba
INSERT INTO personal (cip, dni, apellidos, nombres, sexo, id_grado, estado) VALUES
('TEST001', '12345678', 'García López', 'Juan Carlos', 'M', 8, 'ACTIVO'),
('TEST002', '23456789', 'Pérez Sánchez', 'María Elena', 'F', 9, 'ACTIVO'),
('TEST003', '34567890', 'Torres Ramírez', 'Roberto Miguel', 'M', 7, 'ACTIVO');

-- Insertar familiares de prueba
INSERT INTO familiar (id_personal, nombres_apellidos, parentesco) VALUES
(1, 'Ana García Mendoza', 'CÓNYUGE'),
(1, 'Carlos García García', 'HIJO');

SELECT 'Seeds ejecutados - Datos de prueba creados' AS status;
```

## ⚠️ Importante

- **NUNCA** ejecutar seeds en producción
- Usar CIPs con prefijo `TEST` para identificarlos
- Incluir comentario `-- SOLO DESARROLLO` en la primera línea
- Datos deben ser ficticios (no reales)

## 🗑️ Limpiar Seeds

```sql
-- Script para limpiar datos de prueba
DELETE FROM personal WHERE cip LIKE 'TEST%';
DELETE FROM familiar WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
```
