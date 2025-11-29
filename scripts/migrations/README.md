# 📁 Directorio de Migraciones de Base de Datos

## ¿Qué son las migraciones?

Las migraciones son **cambios incrementales** a la base de datos después de la creación inicial.

## 📋 Reglas de Uso

### 1. **Numeración secuencial**
```
001_descripcion_clara.sql
002_otra_modificacion.sql
003_agregar_indice.sql
```

### 2. **Nunca editar migraciones ejecutadas**
❌ MALO: Editar `001_crear_tabla.sql` después de ejecutarla
✅ BUENO: Crear `002_corregir_tabla.sql` con la corrección

### 3. **Una migración = Un cambio lógico**
✅ BUENO: `001_add_foto_column.sql` - Solo agrega columna foto
❌ MALO: `001_cambios_varios.sql` - Agrega columna, índice, tabla, etc.

## 🚀 Cómo Usar

### Crear nueva migración:
```bash
# 1. Crea el archivo con número siguiente
# Ejemplo: Ya existe 001, 002, 003 → Crear 004_agregar_campo_email.sql

# 2. Escribe SOLO el cambio
ALTER TABLE personal ADD COLUMN email_secundario VARCHAR(100);

# 3. Ejecuta
mysql -u root -p123456 bd_policia < scripts/migrations/004_agregar_campo_email.sql

# 4. Commit a Git
git add scripts/migrations/004_agregar_campo_email.sql
git commit -m "Migration: Agregar email secundario a personal"
```

## 📝 Plantilla de Migración

```sql
-- =====================================================
-- MIGRATION: [Número] - [Descripción]
-- =====================================================
-- Autor: [Tu nombre]
-- Fecha: [YYYY-MM-DD]
-- Descripción: [Qué hace este cambio y por qué]
-- =====================================================

-- Cambio 1
ALTER TABLE ...;

-- Cambio 2 (si es parte del mismo cambio lógico)
CREATE INDEX ...;

-- Verificación
SELECT 'Migration ejecutada exitosamente' AS status;
```

## ⚠️ Importante

- Las migraciones se ejecutan **UNA VEZ**
- Orden de ejecución: 001 → 002 → 003 → ...
- **NUNCA** borres o edites migraciones ya ejecutadas
- Si hay error, crea una migración de **rollback** o **corrección**

## 📊 Log de Migraciones Ejecutadas

Mantén registro en archivo o tabla:

```sql
-- Opcional: Tabla para trackear migraciones
CREATE TABLE schema_migrations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    version VARCHAR(50) UNIQUE,
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Al ejecutar migración 001:
INSERT INTO schema_migrations (version) VALUES ('001_initial_schema');
```
