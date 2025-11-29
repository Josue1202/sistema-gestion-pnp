-- =====================================================
-- SEED: Datos de prueba para desarrollo
-- =====================================================
-- ⚠️ SOLO PARA DESARROLLO - NO EJECUTAR EN PRODUCCIÓN
-- =====================================================

USE bd_policia;

-- Personal de prueba (5 efectivos)
INSERT INTO personal (cip, dni, apellidos, nombres, sexo, id_grado, id_unidad_actual, fecha_nacimiento, fecha_ingreso, estado) VALUES
('TEST001', '12345678', 'García López', 'Juan Carlos', 'M', 8, 5, '1985-05-15', '2005-01-10', 'ACTIVO'),
('TEST002', '23456789', 'Pérez Sánchez', 'María Elena', 'F', 9, 5, '1990-08-22', '2010-03-12', 'ACTIVO'),
('TEST003', '34567890', 'Torres Ramírez', 'Roberto Miguel', 'M', 7, 6, '1987-03-10', '2007-06-20', 'ACTIVO'),
('TEST004', '45678901', 'Mendoza Castro', 'Ana Lucía', 'F', 10, 6, '1992-11-30', '2012-02-15', 'ACTIVO'),
('TEST005', '56789012', 'Silva Vargas', 'Carlos Alberto', 'M', 11, 7, '1983-07-18', '2003-09-08', 'ACTIVO');

-- Familiares de prueba
INSERT INTO familiar (id_personal, nombres_apellidos, parentesco, fecha_nacimiento) VALUES
(1, 'Ana García Mendoza', 'CÓNYUGE', '1987-03-20'),
(1, 'Carlos García García', 'HIJO', '2010-06-15'),
(2, 'Roberto Pérez Díaz', 'CÓNYUGE', '1988-12-10'),
(3, 'Lucía Torres Gómez', 'CÓNYUGE', '1989-05-25');

-- Cursos de prueba
INSERT INTO curso (id_personal, tipo, nombre, institucion, fecha_inicio, fecha_fin, horas) VALUES
(1, 'INSTITUCIONAL', 'Curso de Investigación Criminal Avanzada', 'EO-PNP', '2023-01-15', '2023-03-15', 120),
(2, 'EXTRA_INSTITUCIONAL', 'Diplomado en Derechos Humanos', 'PUCP', '2022-06-01', '2022-12-01', 200),
(3, 'INSTITUCIONAL', 'Curso de Liderazgo Policial', 'EO-PNP', '2023-05-10', '2023-06-30', 80);

-- Ascensos de prueba
INSERT INTO ascenso (id_personal, id_grado_anterior, id_grado_nuevo, fecha_ascenso, rd_documento) VALUES
(1, 9, 8, '2020-01-01', 'RD-2020-001'),
(3, 8, 7, '2019-06-15', 'RD-2019-045');

-- Servicios prestados de prueba
INSERT INTO servicio_prestado (id_personal, id_unidad, cargo, fecha_inicio, fecha_fin) VALUES
(1, 3, 'Investigador Junior', '2005-01-10', '2010-12-31'),
(1, 5, 'Jefe de Grupo', '2011-01-01', NULL),
(2, 5, 'Operador de Radio', '2010-03-12', NULL);

SELECT '✅ Seeds ejecutados exitosamente' AS status;
SELECT '📊 Datos de prueba creados:' AS info;
SELECT COUNT(*) AS personal_test FROM personal WHERE cip LIKE 'TEST%';
SELECT COUNT(*) AS familiares_test FROM familiar WHERE id_personal IN (SELECT id_personal FROM personal WHERE cip LIKE 'TEST%');
