-- =====================================================
-- COMPLETAR TABLAS FALTANTES
-- =====================================================
USE bd_policia;

-- USUARIOS PARA LOGIN (test001, test002, test003)
-- Password para todos: test123
INSERT INTO usuario (id_personal, id_rol, username, password_hash, estado) VALUES
(1, 2, 'test001', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 1),
(2, 3, 'test002', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 1),
(3, 3, 'test003', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 1);

-- ARMAMENTO (3 armas asignadas)
INSERT INTO armamento (id_personal, tipo, propiedad, marca, serie, calibre, estado_arma, fecha_asignacion, observaciones) VALUES
(1, 'PISTOLA', 'ESTADO', 'Glock 19', 'GLK-TEST-001', '9mm', 'OPERATIVO', '2020-01-15', 'Arma de dotación estándar'),
(3, 'PISTOLA', 'ESTADO', 'Beretta 92', 'BRT-TEST-002', '9mm', 'OPERATIVO', '2019-06-20', 'Para operaciones especiales'),
(5, 'REVOLVER', 'ESTADO', 'Taurus 85', 'TAU-TEST-003', '.38 Special', 'OPERATIVO', '2018-03-25', 'Patrullaje estándar');

-- SANCIONES Y MÉRITOS (3 registros)
INSERT INTO sancion_merito (id_personal, tipo, motivo, rd_documento, fecha, dias_suspension) VALUES
(1, 'FELICITACION', 'Destacada labor en caso de corrupción municipal. Recuperación de S/. 2 millones', 'RD-FEL-2023-045', '2023-05-10', NULL),
(2, 'MERITO', 'Excelencia en análisis forense - 15 casos resueltos en 1 mes', 'RD-MER-2022-089', '2022-08-15', NULL),
(3, 'SANCION', 'Llegada tarde injustificada en 3 ocasiones', 'RD-SAN-2021-012', '2021-03-20', 3);

-- LICENCIAS MÉDICAS (2 registros)
INSERT INTO licencia_medica (id_personal, fecha_inicio, fecha_fin, diagnostico, centro_medico, documento_url) VALUES
(2, '2023-02-10', '2023-02-15', 'Gripe estacional - reposo médico', 'Hospital PNP Luis N. Sáenz', NULL),
(5, '2022-11-20', '2022-11-25', 'Lesión en servicio - esguince de tobillo derecho grado II', 'Hospital PNP Luis N. Sáenz', NULL);

-- DESTACAMENTOS Y COMISIONES (2 registros)
INSERT INTO destaque_comision (id_personal, id_unidad_destino, tipo, fecha_inicio, fecha_fin, motivo, rd_documento) VALUES
(1, 4, 'COMISION', '2021-06-01', '2021-06-30', 'Apoyo en operativo antiterrorista - zona VRAEM', 'RD-COM-2021-034'),
(3, 3, 'DESTAQUE', '2020-08-15', '2020-12-15', 'Refuerzo en división de investigación criminal', 'RD-DES-2020-078');

-- ASIGNACIONES DIARIAS (4 registros)
INSERT INTO asignacion_diaria (id_personal, id_cargo, fecha, tipo_documento, nro_documento, descripcion, archivo_url) VALUES
(1, 1, CURDATE(), 'MEMORANDUM', CONCAT('MEM-', YEAR(CURDATE()), '-001'), 'Jefe de turno mañana - sector norte', NULL),
(2, 4, CURDATE(), 'CARTILLA_FUNCIONAL', CONCAT('CF-', YEAR(CURDATE()), '-001'), 'Operador de central de comunicaciones - turno tarde', NULL),
(3, 2, CURDATE(), 'MEMORANDUM', CONCAT('MEM-', YEAR(CURDATE()), '-002'), 'Supervisión de patrullaje - zona este', NULL),
(5, 5, CURDATE(), 'CARTILLA_FUNCIONAL', CONCAT('CF-', YEAR(CURDATE()), '-002'), 'Patrullaje motorizado - sector comercial', NULL);

-- VACACIONES (3 registros)
INSERT INTO vacacion (id_personal, fecha_inicio, fecha_fin, tipo, observaciones, estado) VALUES
(1, '2024-02-01', '2024-02-15', 'PROGRAMADA', 'Vacaciones anuales 2024 - primer periodo', 'APROBADA'),
(2, '2024-03-15', '2024-03-29', 'PROGRAMADA', 'Vacaciones primer semestre', 'PENDIENTE'),
(5, '2024-01-20', '2024-02-03', 'ADELANTADA', 'Motivos familiares - solicitud especial', 'APROBADA');

-- RESUMEN FINAL
SELECT '✅ TODAS LAS TABLAS COMPLETADAS' AS status;
SELECT '' AS separador;
SELECT 'RESUMEN DE DATOS DE PRUEBA:' AS titulo;
SELECT '' AS separador2;

SELECT 
    'Personal' AS tabla, 
    COUNT(*) AS registros,
    'Policías de prueba con CIP TEST001-TEST005' AS descripcion
FROM personal WHERE cip LIKE 'TEST%'

UNION ALL SELECT 'Usuarios Login', COUNT(*), 'admin + test001, test002, test003'
FROM usuario WHERE username IN ('admin', 'test001', 'test002', 'test003')

UNION ALL SELECT 'Familiares', COUNT(*), 'Cónyuges e hijos'
FROM familiar WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')

UNION ALL SELECT 'Ascensos', COUNT(*), 'Historial de promociones'
FROM ascenso WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')

UNION ALL SELECT 'Servicios', COUNT(*), 'Historial de unidades'
FROM servicio_prestado WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')

UNION ALL SELECT 'Cursos', COUNT(*), 'Capacitaciones institucionales y extra'
FROM curso WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')

UNION ALL SELECT 'Armamento', COUNT(*), 'Armas del Estado asignadas'
FROM armamento WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')

UNION ALL SELECT 'Sanciones/Méritos', COUNT(*), 'Felicitaciones, méritos y sanciones'
FROM sancion_merito WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')

UNION ALL SELECT 'Licencias Médicas', COUNT(*), 'Permisos por salud'
FROM licencia_medica WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')

UNION ALL SELECT 'Destacamentos', COUNT(*), 'Comisiones y destacamentos'
FROM destaque_comision WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')

UNION ALL SELECT 'Asignaciones Diarias', COUNT(*), CONCAT('Turnos del día ', CURDATE())
FROM asignacion_diaria WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%')

UNION ALL SELECT 'Vacaciones', COUNT(*), 'Programadas y adelantadas'
FROM vacacion WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');

SELECT '' AS separador3;
SELECT '🔐 CREDENCIALES PARA LOGIN:' AS login_titulo;
SELECT '' AS separador4;
SELECT 'admin' AS usuario, 'admin123' AS password, 'ADMINISTRADOR' AS rol
UNION ALL SELECT 'test001', 'test123', 'JEFE_PERSONAL'
UNION ALL SELECT 'test002', 'test123', 'OPERADOR'
UNION ALL SELECT 'test003', 'test123', 'OPERADOR';
