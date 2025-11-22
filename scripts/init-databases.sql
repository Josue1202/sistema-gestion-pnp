-- Inicialización de bases de datos para Sistema PNP
-- Ejecutar este script como superusuario de PostgreSQL

-- Base de datos para Auth Service
DROP DATABASE IF EXISTS pnp_auth;
CREATE DATABASE pnp_auth;

-- Base de datos para Personal Service
DROP DATABASE IF EXISTS pnp_personal;
CREATE DATABASE pnp_personal;

-- Crear usuario si no existe
DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'pnp_admin') THEN
      CREATE ROLE pnp_admin LOGIN PASSWORD 'pnp_password';
   END IF;
END
$do$;

-- Otorgar privilegios
GRANT ALL PRIVILEGES ON DATABASE pnp_auth TO pnp_admin;
GRANT ALL PRIVILEGES ON DATABASE pnp_personal TO pnp_admin;

\echo 'Bases de datos creadas exitosamente!'
\echo 'Usuario: pnp_admin'
\echo 'Bases de datos: pnp_auth, pnp_personal'
