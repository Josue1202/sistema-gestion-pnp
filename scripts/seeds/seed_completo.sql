-- =====================================================
-- SEED COMPLETO: Datos en TODAS las tablas
-- =====================================================
USE bd_policia;

-- Limpiar datos anteriores de prueba
DELETE FROM vacacion WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
DELETE FROM asignacion_diaria WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
DELETE FROM armamento WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
DELETE FROM destaque_comision WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
DELETE FROM licencia_medica WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
DELETE FROM sancion_merito WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
DELETE FROM curso WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
DELETE FROM servicio_prestado WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
DELETE FROM ascenso WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
DELETE FROM familiar WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
DELETE FROM usuario WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
DELETE FROM personal WHERE cip LIKE 'TEST%';

-- Personal de prueba
INSERT INTO personal (cip, dni, apellidos, nombres, sexo, id_grado, id_unidad_actual, id_ubigeo_nacimiento, id_ubigeo_domicilio, fecha_nacimiento, fecha_ingreso, estado, especialidad, grupo_sanguineo, telefono, celular, correo, estatura, peso, n_hijos) VALUES
('TEST001', '12345678', 'García López', 'Juan Carlos', 'M', 8, 5, 1, 1, '1985-05-15', '2005-01-10', 'ACTIVO', 'Investigación Criminal', 'O+', '014567890', '987654321', 'juan.garcia@test.com', 1.75, 78.5, 2),
('TEST002', '23456789', 'Pérez Sánchez', 'María Elena', 'F', 9, 5, 2, 3, '1990-08-22', '2010-03-12', 'ACTIVO', 'Criminalística', 'A+', '014567891', '987654322', 'maria.perez@test.com', 1.62, 58.0, 1),
('TEST003', '34567890', 'Torres Ramírez', 'Roberto Miguel', 'M', 7, 6, 3, 4, '1987-03-10', '2007-06-20', 'ACTIVO', 'Operaciones', 'B+', '014567892', '987654323', 'roberto.torres@test.com', 1.80, 85.0, 0),
('TEST004', '45678901', 'Mendoza Castro', 'Ana Lucía', 'F', 10, 6, 4, 5, '1992-11-30', '2012-02-15', 'ACTIVO', 'Administrativa', 'AB+', '014567893', '987654324', 'ana.mendoza@test.com', 1.65, 60.0, 0),
('TEST005', '56789012', 'Silva Vargas', 'Carlos Alberto', 'M', 11, 7, 5, 1, '1983-07-18', '2003-09-08', 'ACTIVO', 'Patrullaje', 'O-', '014567894', '987654325', 'carlos.silva@test.com', 1.78, 82.0, 3);

-- Usuarios para login (password: test123)
INSERT INTO usuario (id_personal, id_rol, username, password_hash, estado) VALUES
(1, 2, 'test001', '$2a$10$N9qo8uLOickgx2ZMRZoMye', 1),
(2, 3, 'test002', '$2a$10$N9qo8uLOickgx2ZMRZoMye', 1),
(3, 3, 'test003', '$2a$10$N9qo8uLOickgx2ZMRZoMye', 1);

-- Familiares
INSERT INTO familiar (id_personal, nombres_apellidos, parentesco, fecha_nacimiento, dni, vive_con_efectivo, es_dependiente) VALUES
(1, 'Ana García Mendoza', 'CÓNYUGE', '1987-03-20', '23456780', 1, 0),
(1, 'Carlos García García', 'HIJO', '2010-06-15', '78901234', 1, 1),
(1, 'Laura García García', 'HIJA', '2012-08-20', '78901235', 1, 1),
(2, 'Roberto Pérez Díaz', 'CÓNYUGE', '1988-12-10', '34567890', 1, 0),
(5, 'María Silva Torres', 'CÓNYUGE', '1985-05-12', '45678901', 1, 0),
(5, 'Pedro Silva Silva', 'HIJO', '2008-03-15', '56789012', 1, 1);

-- Ascensos
INSERT INTO ascenso (id_personal, id_grado_anterior, id_grado_nuevo, fecha_ascenso, rd_documento, motivo) VALUES
(1, 9, 8, '2020-01-01', 'RD-2020-001', 'Tiempo de servicio y méritos'),
(3, 8, 7, '2019-06-15', 'RD-2019-045', 'Acción distinguida'),
(5, 12, 11, '2018-03-20', 'RD-2018-023', 'Antigüedad');

-- Servicios prestados
INSERT INTO servicio_prestado (id_personal, id_unidad, cargo, fecha_inicio, fecha_fin, motivo_salida) VALUES
(1, 3, 'Investigador Junior', '2005-01-10', '2010-12-31', 'Ascenso'),
(1, 5, 'Jefe de Grupo', '2011-01-01', NULL, NULL),
(2, 5, 'Técnico Forense', '2010-03-12', NULL, NULL),
(3, 4, 'Operador Táctico', '2007-06-20', '2015-08-30', 'Traslado'),
(3, 6, 'Jefe de Escuadrón', '2015-09-01', NULL, NULL);

-- Cursos
INSERT INTO curso (id_personal, tipo, nombre, institucion, fecha_inicio, fecha_fin, horas) VALUES
(1, 'INSTITUCIONAL', 'Curso de Investigación Criminal Avanzada', 'EO-PNP', '2023-01-15', '2023-03-15', 120),
(1, 'EXTRA_INSTITUCIONAL', 'Diplomado en Criminología', 'UNMSM', '2022-04-01', '2022-10-01', 200),
(2, 'INSTITUCIONAL', 'Curso de Criminalística', 'EO-PNP', '2021-06-01', '2021-08-30', 100),
(3, 'INSTITUCIONAL', 'Operaciones Especiales', 'DIROES', '2020-01-10', '2020-03-20', 150),
(5, 'INSTITUCIONAL', 'Patrullaje Moderno', 'EO-PNP', '2019-05-15', '2019-07-15', 80);

-- Armamento
INSERT INTO armamento (id_personal, tipo, propiedad, marca, serie, calibre, estado_arma, fecha_asignacion) VALUES
(1, 'PISTOLA', 'ESTADO', 'Glock', 'GLK12345', '9mm', 'OPERATIVO', '2020-01-15'),
(3, 'PISTOLA', 'ESTADO', 'Beretta', 'BRT67890', '9mm', 'OPERATIVO', '2019-06-20'),
(5, 'REVOLVER', 'ESTADO', 'Taurus', 'TAU11223', '.38', 'OPERATIVO', '2018-03-25');

-- Sanciones y Méritos
INSERT INTO sancion_merito (id_personal, tipo, motivo, rd_documento, fecha, dias_suspension) VALUES
(1, 'FELICITACION', 'Destacada labor en caso de corrupción', 'RD-FEL-2023-045', '2023-05-10', NULL),
(2, 'MERITO', 'Excelencia en análisis forense', 'RD-MER-2022-089', '2022-08-15', NULL),
(3, 'SANCION', 'Llegada tarde injustificada', 'RD-SAN-2021-012', '2021-03-20', 3);

-- Licencias médicas
INSERT INTO licencia_medica (id_personal, fecha_inicio, fecha_fin, diagnostico, centro_medico) VALUES
(2, '2023-02-10', '2023-02-15', 'Gripe estacional', 'Hospital PNP'),
(5, '2022-11-20', '2022-11-25', 'Lesión en servicio - esguince tobillo', 'Hospital PNP');

-- Destacamentos
INSERT INTO destaque_comision (id_personal, id_unidad_destino, tipo, fecha_inicio, fecha_fin, motivo, rd_documento) VALUES
(1, 4, 'COMISION', '2021-06-01', '2021-06-30', 'Apoyo en operativo antiterrorista', 'RD-COM-2021-034');

-- Asignaciones diarias
INSERT INTO asignacion_diaria (id_personal, id_cargo, fecha, tipo_documento, nro_documento, descripcion) VALUES
(1, 1, '2024-01-15', 'MEMORANDUM', 'MEM-2024-001', 'Turno mañana - sector norte'),
(2, 4, '2024-01-15', 'CARTILLA_FUNCIONAL', 'CF-2024-001', 'Operador central de comunicaciones'),
(3, 2, '2024-01-15', 'MEMORANDUM', 'MEM-2024-002', 'Supervisión patrullaje zona este'),
(5, 5, '2024-01-15', 'CARTILLA_FUNCIONAL', 'CF-2024-002', 'Patrullaje motorizado - turno tarde');

-- Vacaciones
INSERT INTO vacacion (id_personal, fecha_inicio, fecha_fin, tipo, observaciones, estado) VALUES
(1, '2024-02-01', '2024-02-30', 'PROGRAMADA', 'Vacaciones anuales 2024', 'APROBADA'),
(2, '2024-03-15', '2024-03-29', 'PROGRAMADA', 'Vacaciones primer semestre', 'PENDIENTE'),
(5, '2024-01-20', '2024-02-03', 'ADELANTADA', 'Motivos familiares', 'APROBADA');

-- Resumen
SELECT '✅ SEED COMPLETO EJECUTADO' AS status;
SELECT '📊 DATOS INSERTADOS:' AS info;
SELECT 'Personal' AS tabla, COUNT(*) AS cantidad FROM personal WHERE cip LIKE 'TEST%'
UNION ALL SELECT 'Usuarios', COUNT(*) FROM usuario WHERE username LIKE 'test%'
UNION ALL SELECT 'Familiares', COUNT(*) FROM familiar WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')
UNION ALL SELECT 'Ascensos', COUNT(*) FROM ascenso WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')
UNION ALL SELECT 'Servicios', COUNT(*) FROM servicio_prestado WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')
UNION ALL SELECT 'Cursos', COUNT(*) FROM curso WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')
UNION ALL SELECT 'Armamento', COUNT(*) FROM armamento WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')
UNION ALL SELECT 'Sanciones/Méritos', COUNT(*) FROM sancion_merito WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')
UNION ALL SELECT 'Licencias', COUNT(*) FROM licencia_medica WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')
UNION ALL SELECT 'Destacamentos', COUNT(*) FROM destaque_comision WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')
UNION ALL SELECT 'Asignaciones', COUNT(*) FROM asignacion_diaria WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')
UNION ALL SELECT 'Vacaciones', COUNT(*) FROM vacacion WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');

SELECT '🔐 USUARIOS DE PRUEBA PARA LOGIN:' AS login_info;
SELECT username AS usuario, 'test123' AS password, 'JEFE_PERSONAL' AS rol FROM usuario WHERE username = 'test001'
UNION ALL SELECT 'test002', 'test123', 'OPERADOR'
UNION ALL SELECT 'test003', 'test123', 'OPERADOR';
