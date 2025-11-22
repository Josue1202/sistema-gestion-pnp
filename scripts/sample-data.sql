-- Datos de prueba para Personal PNP
-- Conectarse a la base de datos pnp_personal antes de ejecutar

-- Insertar personal de prueba
INSERT INTO personal_pnp (cip, dni, apellido_paterno, apellido_materno, nombres, fecha_nacimiento, genero, telefono, email, direccion, rango, especialidad, unidad, estado, fecha_ingreso, created_at, updated_at)
VALUES
('123456789', '12345678', 'García', 'Rodríguez', 'Juan Carlos', '1985-03-15', 'M', '987654321', 'juan.garcia@pnp.gob.pe', 'Av. Principal 123, Lima', 'Capitán', 'Investigación Criminal', 'Comisaría Central Lima', 'ACTIVO', '2005-01-10', NOW(), NOW()),
('234567890', '23456789', 'López', 'Martínez', 'María Elena', '1990-07-22', 'F', '987123456', 'maria.lopez@pnp.gob.pe', 'Jr. Los Pinos 456, Lima', 'Teniente', 'Criminalística', 'Comisaría Central Lima', 'ACTIVO', '2010-06-15', NOW(), NOW()),
('345678901', '34567890', 'Pérez', 'Sánchez', 'Roberto', '1988-11-30', 'M', '987234567', 'roberto.perez@pnp.gob.pe', 'Calle Las Flores 789, Callao', 'Mayor', 'Tránsito', 'Comisaría del Callao', 'ACTIVO', '2008-03-20', NOW(), NOW()),
('456789012', '45678901', 'Torres', 'Ramírez', 'Ana Lucía', '1992-05-18', 'F', '987345678', 'ana.torres@pnp.gob.pe', 'Av. Los Héroes 321, Arequipa', 'Suboficial 1ra', 'Patrullaje', 'Comisaría Arequipa', 'ACTIVO', '2012-09-05', NOW(), NOW()),
('567890123', '56789012', 'Fernández', 'Castro', 'Diego Alejandro', '1987-09-12', 'M', '987456789', 'diego.fernandez@pnp.gob.pe', 'Jr. Comercio 654, Cusco', 'Comandante', 'Orden Público', 'Comisaría Cusco', 'LICENCIA', '2007-11-18', NOW(), NOW());

-- Insertar funciones de prueba
INSERT INTO funciones_policial (personal_id, funcion, fecha_asignacion, fecha_fin, activo)
VALUES
(1, 'Jefe de Investigaciones de la unidad', '2020-01-01', NULL, true),
(1, 'Instructor en Academia PNP', '2018-06-01', '2019-12-31', false),
(2, 'Análisis de escenas del crimen', '2015-03-15', NULL, true),
(2, 'Coordinadora de laboratorio forense', '2021-07-01', NULL, true),
(3, 'Supervisor de control de tráfico vehicular', '2019-05-10', NULL, true),
(4, 'Patrullaje motorizado zona urbana', '2012-09-05', NULL, true),
(5, 'Coordinación de operativos de seguridad ciudadana', '2015-02-20', '2024-10-31', false),
(5, 'Jefe de operaciones especiales', '2021-01-10', NULL, true);

-- Insertar usuario de prueba en pnp_auth
-- Nota: Ejecutar esto en la base de datos pnp_auth
-- \c pnp_auth
-- INSERT INTO usuarios (username, password, cip, rol, activo, created_at, updated_at)
-- VALUES ('admin', '$2a$10$XlOEqM8xqNjN9qN9xqN9xO', '123456789', 'ADMIN', true, NOW(), NOW());
-- Password: admin123 (hasheado con BCrypt)

\echo 'Datos de prueba insertados exitosamente!'
\echo 'Personal de prueba: 5 registros'
\echo 'Funciones de prueba: 8 registros'
