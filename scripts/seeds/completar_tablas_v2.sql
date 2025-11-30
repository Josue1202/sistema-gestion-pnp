-- =====================================================
-- COMPLETAR TABLAS FALTANTES - VERSION CORREGIDA
-- =====================================================
USE bd_policia;

-- Primero obtenemos los IDs reales
SET @id1 = (SELECT id_personal FROM personal WHERE cip = 'TEST001');
SET @id2 = (SELECT id_personal FROM personal WHERE cip = 'TEST002');
SET @id3 = (SELECT id_personal FROM personal WHERE cip = 'TEST003');
SET @id4 = (SELECT id_personal FROM personal WHERE cip = 'TEST004');
SET @id5 = (SELECT id_personal FROM personal WHERE cip = 'TEST005');

-- USUARIOS PARA LOGIN
INSERT IGNORE INTO usuario (id_personal, id_rol, username, password_hash, estado) VALUES
(@id1, 2, 'test001', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 1),
(@id2, 3, 'test002', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 1),
(@id3, 3, 'test003', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 1);

-- ARMAMENTO
INSERT INTO armamento (id_personal, tipo, propiedad, marca, serie, calibre, estado_arma, fecha_asignacion, observaciones) VALUES
(@id1, 'PISTOLA', 'ESTADO', 'Glock 19', 'GLK-TEST-001', '9mm', 'OPERATIVO', '2020-01-15', 'Arma de dotación estándar'),
(@id3, 'PISTOLA', 'ESTADO', 'Beretta 92', 'BRT-TEST-002', '9mm', 'OPERATIVO', '2019-06-20', 'Para operaciones especiales'),
(@id5, 'REVOLVER', 'ESTADO', 'Taurus 85', 'TAU-TEST-003', '.38 Special', 'OPERATIVO', '2018-03-25', 'Patrullaje estándar');

-- SANCIONES Y MÉRITOS
INSERT INTO sancion_merito (id_personal, tipo, motivo, rd_documento, fecha, dias_suspension) VALUES
(@id1, 'FELICITACION', 'Destacada labor en caso de corrupción municipal', 'RD-FEL-2023-045', '2023-05-10', NULL),
(@id2, 'MERITO', 'Excelencia en análisis forense - 15 casos resueltos', 'RD-MER-2022-089', '2022-08-15', NULL),
(@id3, 'SANCION', 'Llegada tarde injustificada en 3 ocasiones', 'RD-SAN-2021-012', '2021-03-20', 3);

-- LICENCIAS MÉDICAS
INSERT INTO licencia_medica (id_personal, fecha_inicio, fecha_fin, diagnostico, centro_medico) VALUES
(@id2, '2023-02-10', '2023-02-15', 'Gripe estacional - reposo médico', 'Hospital PNP Luis N. Sáenz'),
(@id5, '2022-11-20', '2022-11-25', 'Lesión en servicio - esguince tobillo', 'Hospital PNP Luis N. Sáenz');

-- DESTACAMENTOS
INSERT INTO destaque_comision (id_personal, id_unidad_destino, tipo, fecha_inicio, fecha_fin, motivo, rd_documento) VALUES
(@id1, 4, 'COMISION', '2021-06-01', '2021-06-30', 'Apoyo operativo antiterrorista', 'RD-COM-2021-034'),
(@id3, 3, 'DESTAQUE', '2020-08-15', '2020-12-15', 'Refuerzo en investigación criminal', 'RD-DES-2020-078');

-- ASIGNACIONES DIARIAS
INSERT INTO asignacion_diaria (id_personal, id_cargo, fecha, tipo_documento, nro_documento, descripcion) VALUES
(@id1, 1, CURDATE(), 'MEMORANDUM', CONCAT('MEM-', YEAR(CURDATE()), '-001'), 'Jefe de turno mañana'),
(@id2, 4, CURDATE(), 'CARTILLA_FUNCIONAL', CONCAT('CF-', YEAR(CURDATE()), '-001'), 'Operador de comunicaciones'),
(@id3, 2, CURDATE(), 'MEMORANDUM', CONCAT('MEM-', YEAR(CURDATE()), '-002'), 'Supervisión patrullaje'),
(@id5, 5, CURDATE(), 'CARTILLA_FUNCIONAL', CONCAT('CF-', YEAR(CURDATE()), '-002'), 'Patrullaje motorizado');

-- VACACIONES
INSERT INTO vacacion (id_personal, fecha_inicio, fecha_fin, tipo, observaciones, estado) VALUES
(@id1, '2024-02-01', '2024-02-15', 'PROGRAMADA', 'Vacaciones anuales 2024', 'APROBADA'),
(@id2, '2024-03-15', '2024-03-29', 'PROGRAMADA', 'Vacaciones primer semestre', 'PENDIENTE'),
(@id5, '2024-01-20', '2024-02-03', 'ADELANTADA', 'Motivos familiares', 'APROBADA');

-- RESUMEN
SELECT '✅ TABLAS COMPLETADAS EXITOSAMENTE' AS status;
