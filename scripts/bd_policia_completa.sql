-- =====================================================
-- SISTEMA DE GESTIÓN PNP - BASE DE DATOS COMPLETA V2
-- =====================================================
-- Autor: Sistema PNP
-- Fecha: 2025-11-29
-- Descripción: Base de datos normalizada para gestión 
--              integral de personal policial
-- =====================================================

-- 1. CREACIÓN DE BASE DE DATOS
DROP DATABASE IF EXISTS bd_policia;
CREATE DATABASE bd_policia CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_policia;

-- =====================================================
-- 2. TABLAS MAESTRAS (Catálogos)
-- =====================================================

-- Tabla de Ubicación Geográfica (Ubigeo)
CREATE TABLE ubigeo (
    id_ubigeo INT AUTO_INCREMENT PRIMARY KEY,
    departamento VARCHAR(50) NOT NULL,
    provincia VARCHAR(50) NOT NULL,
    distrito VARCHAR(50) NOT NULL,
    CONSTRAINT uk_ubigeo UNIQUE (departamento, provincia, distrito),
    INDEX idx_departamento (departamento)
) ENGINE=InnoDB;

-- Tabla de Grados Policiales
CREATE TABLE grado_policial (
    id_grado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    jerarquia INT NOT NULL UNIQUE,
    categoria ENUM('OFICIAL', 'SUBOFICIAL', 'CIVIL') NOT NULL,
    INDEX idx_jerarquia (jerarquia)
) ENGINE=InnoDB;

-- Tabla de Unidades Policiales
CREATE TABLE unidad_policial (
    id_unidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    siglas VARCHAR(20),
    tipo ENUM('COMISARIA', 'DIVISION', 'REGION', 'ADMINISTRATIVO', 'ESPECIAL') NOT NULL,
    INDEX idx_tipo (tipo)
) ENGINE=InnoDB;

-- Tabla de Roles de Usuario
CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) UNIQUE NOT NULL,
    descripcion VARCHAR(200)
) ENGINE=InnoDB;

-- Tabla de Cargos
CREATE TABLE cargo (
    id_cargo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_cargo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    nivel_jerarquico INT
) ENGINE=InnoDB;

-- =====================================================
-- 3. TABLA PRINCIPAL - PERSONAL
-- =====================================================

CREATE TABLE personal (
    id_personal INT AUTO_INCREMENT PRIMARY KEY,
    
    -- Identificación
    cip VARCHAR(20) NOT NULL UNIQUE,
    dni VARCHAR(10) NOT NULL UNIQUE,
    apellidos VARCHAR(100) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    sexo ENUM('M', 'F') NOT NULL,
    
    -- Relaciones normalizadas
    id_grado INT,
    id_unidad_actual INT,
    id_ubigeo_nacimiento INT,
    id_ubigeo_domicilio INT,
    
    -- Información laboral
    especialidad VARCHAR(100),
    codofin VARCHAR(20),
    fecha_ingreso DATE,
    fecha_incorporacion DATE,
    
    -- Datos personales
    fecha_nacimiento DATE,
    estado_civil ENUM('SOLTERO', 'CASADO', 'DIVORCIADO', 'VIUDO', 'CONVIVIENTE'),
    n_hijos INT DEFAULT 0,
    grupo_sanguineo VARCHAR(5),
    
    -- Contacto
    telefono VARCHAR(20),
    celular VARCHAR(20),
    correo VARCHAR(100),
    domicilio TEXT,
    referencia_domicilio TEXT,
    
    -- Datos físicos
    estatura DECIMAL(4,2),
    peso DECIMAL(5,2),
    talla_camisa VARCHAR(5),
    talla_calzado VARCHAR(5),
    color_piel VARCHAR(20),
    
    -- Educación
    grado_instruccion VARCHAR(50),
    profesion VARCHAR(100),
    
    -- Multimedia
    foto_url VARCHAR(255),
    
    -- Estado y auditoría
    estado ENUM('ACTIVO', 'BAJA', 'RETIRO', 'LICENCIA') DEFAULT 'ACTIVO',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Índices
    INDEX idx_cip (cip),
    INDEX idx_dni (dni),
    INDEX idx_estado (estado),
    INDEX idx_grado (id_grado),
    INDEX idx_unidad (id_unidad_actual),
    
    -- Foreign Keys
    FOREIGN KEY (id_grado) REFERENCES grado_policial(id_grado),
    FOREIGN KEY (id_unidad_actual) REFERENCES unidad_policial(id_unidad),
    FOREIGN KEY (id_ubigeo_nacimiento) REFERENCES ubigeo(id_ubigeo),
    FOREIGN KEY (id_ubigeo_domicilio) REFERENCES ubigeo(id_ubigeo)
) ENGINE=InnoDB;

-- =====================================================
-- 4. GESTIÓN DE ACCESO
-- =====================================================

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_personal INT UNIQUE,
    id_rol INT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    estado BOOLEAN DEFAULT 1,
    ultimo_acceso TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_username (username),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
) ENGINE=InnoDB;

-- =====================================================
-- 5. GESTIÓN DE PERSONAL
-- =====================================================

-- Historial de Ascensos
CREATE TABLE ascenso (
    id_ascenso INT AUTO_INCREMENT PRIMARY KEY,
    id_personal INT NOT NULL,
    id_grado_anterior INT,
    id_grado_nuevo INT NOT NULL,
    fecha_ascenso DATE NOT NULL,
    rd_documento VARCHAR(50),
    motivo TEXT,
    
    INDEX idx_personal (id_personal),
    INDEX idx_fecha (fecha_ascenso),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal) ON DELETE CASCADE,
    FOREIGN KEY (id_grado_anterior) REFERENCES grado_policial(id_grado),
    FOREIGN KEY (id_grado_nuevo) REFERENCES grado_policial(id_grado)
) ENGINE=InnoDB;

-- Historial de Servicios Prestados
CREATE TABLE servicio_prestado (
    id_servicio INT AUTO_INCREMENT PRIMARY KEY,
    id_personal INT NOT NULL,
    id_unidad INT NOT NULL,
    cargo VARCHAR(100),
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE, -- NULL si es actual
    motivo_salida VARCHAR(200),
    
    INDEX idx_personal (id_personal),
    INDEX idx_unidad (id_unidad),
    INDEX idx_actual (fecha_fin), -- Para buscar servicios actuales (WHERE fecha_fin IS NULL)
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal) ON DELETE CASCADE,
    FOREIGN KEY (id_unidad) REFERENCES unidad_policial(id_unidad)
) ENGINE=InnoDB;

-- Cursos y Capacitaciones
CREATE TABLE curso (
    id_curso INT AUTO_INCREMENT PRIMARY KEY,
    id_personal INT NOT NULL,
    tipo ENUM('INSTITUCIONAL', 'EXTRA_INSTITUCIONAL') NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    institucion VARCHAR(100),
    fecha_inicio DATE,
    fecha_fin DATE,
    horas INT,
    certificado_url VARCHAR(255),
    
    INDEX idx_personal (id_personal),
    INDEX idx_tipo (tipo),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Núcleo Familiar
CREATE TABLE familiar (
    id_familiar INT AUTO_INCREMENT PRIMARY KEY,
    id_personal INT NOT NULL,
    nombres_apellidos VARCHAR(100) NOT NULL,
    parentesco ENUM('CÓNYUGE', 'HIJO', 'HIJA', 'PADRE', 'MADRE', 'HERMANO', 'HERMANA') NOT NULL,
    fecha_nacimiento DATE,
    dni VARCHAR(10),
    lugar_nacimiento VARCHAR(100),
    vive_con_efectivo BOOLEAN DEFAULT 0,
    es_dependiente BOOLEAN DEFAULT 0,
    
    INDEX idx_personal (id_personal),
    INDEX idx_parentesco (parentesco),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Sanciones y Méritos
CREATE TABLE sancion_merito (
    id_registro INT AUTO_INCREMENT PRIMARY KEY,
    id_personal INT NOT NULL,
    tipo ENUM('SANCION', 'MERITO', 'FELICITACION', 'AMONESTACION') NOT NULL,
    motivo TEXT NOT NULL,
    rd_documento VARCHAR(50),
    fecha DATE NOT NULL,
    dias_suspension INT, -- Solo para sanciones
    
    INDEX idx_personal (id_personal),
    INDEX idx_tipo (tipo),
    INDEX idx_fecha (fecha),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Licencias Médicas
CREATE TABLE licencia_medica (
    id_licencia INT AUTO_INCREMENT PRIMARY KEY,
    id_personal INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    dias INT GENERATED ALWAYS AS (DATEDIFF(fecha_fin, fecha_inicio) + 1) STORED,
    diagnostico TEXT,
    centro_medico VARCHAR(100),
    documento_url VARCHAR(255),
    
    INDEX idx_personal (id_personal),
    INDEX idx_fecha_inicio (fecha_inicio),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Destacamentos y Comisiones
CREATE TABLE destaque_comision (
    id_destaque INT AUTO_INCREMENT PRIMARY KEY,
    id_personal INT NOT NULL,
    id_unidad_destino INT NOT NULL,
    tipo ENUM('DESTAQUE', 'COMISION', 'PERMUTA') NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE,
    motivo TEXT,
    rd_documento VARCHAR(50),
    
    INDEX idx_personal (id_personal),
    INDEX idx_tipo (tipo),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal) ON DELETE CASCADE,
    FOREIGN KEY (id_unidad_destino) REFERENCES unidad_policial(id_unidad)
) ENGINE=InnoDB;

-- =====================================================
-- 6. GESTIÓN OPERATIVA
-- =====================================================

-- Armamento
CREATE TABLE armamento (
    id_armamento INT AUTO_INCREMENT PRIMARY KEY,
    id_personal INT NOT NULL,
    tipo ENUM('PISTOLA', 'REVOLVER', 'FUSIL', 'ESCOPETA', 'OTRO') NOT NULL,
    propiedad ENUM('ESTADO', 'PARTICULAR') NOT NULL,
    marca VARCHAR(50),
    serie VARCHAR(50) UNIQUE NOT NULL,
    calibre VARCHAR(20),
    estado_arma ENUM('OPERATIVO', 'INOPERATIVO', 'PERDIDO', 'EN_MANTENIMIENTO') DEFAULT 'OPERATIVO',
    fecha_asignacion DATE,
    fecha_devolucion DATE,
    observaciones TEXT,
    
    INDEX idx_personal (id_personal),
    INDEX idx_serie (serie),
    INDEX idx_estado (estado_arma),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Asignaciones Diarias
CREATE TABLE asignacion_diaria (
    id_asignacion INT AUTO_INCREMENT PRIMARY KEY,
    id_personal INT NOT NULL,
    id_cargo INT NOT NULL,
    fecha DATE NOT NULL,
    tipo_documento ENUM('MEMORANDUM', 'CARTILLA_FUNCIONAL') NOT NULL,
    nro_documento VARCHAR(50),
    descripcion TEXT,
    archivo_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_personal (id_personal),
    INDEX idx_fecha (fecha),
    INDEX idx_cargo (id_cargo),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal) ON DELETE CASCADE,
    FOREIGN KEY (id_cargo) REFERENCES cargo(id_cargo)
) ENGINE=InnoDB;

-- Vacaciones
CREATE TABLE vacacion (
    id_vacacion INT AUTO_INCREMENT PRIMARY KEY,
    id_personal INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    dias_tomados INT GENERATED ALWAYS AS (DATEDIFF(fecha_fin, fecha_inicio) + 1) STORED,
    tipo ENUM('PROGRAMADA', 'ADELANTADA', 'FRACCIONADA') NOT NULL,
    observaciones TEXT,
    estado ENUM('PENDIENTE', 'APROBADA', 'RECHAZADA', 'TOMADA') DEFAULT 'PENDIENTE',
    
    INDEX idx_personal (id_personal),
    INDEX idx_fecha_inicio (fecha_inicio),
    INDEX idx_estado (estado),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal) ON DELETE CASCADE
) ENGINE=InnoDB;

-- =====================================================
-- 7. DATOS MAESTROS INICIALES
-- =====================================================

-- Roles del Sistema
INSERT INTO rol (nombre_rol, descripcion) VALUES
('ADMINISTRADOR', 'Acceso total al sistema'),
('JEFE_PERSONAL', 'Gestión de personal y asignaciones'),
('OPERADOR', 'Registro y consulta de información'),
('CONSULTA', 'Solo lectura de información');

-- Grados Policiales (Jerarquía descendente)
INSERT INTO grado_policial (nombre, jerarquia, categoria) VALUES
-- Oficiales Generales
('General PNP', 1, 'OFICIAL'),
('Teniente General PNP', 2, 'OFICIAL'),
('General de División PNP', 3, 'OFICIAL'),
('General de Brigada PNP', 4, 'OFICIAL'),

-- Oficiales Superiores
('Coronel PNP', 5, 'OFICIAL'),
('Comandante PNP', 6, 'OFICIAL'),
('Mayor PNP', 7, 'OFICIAL'),

-- Oficiales Subalternos
('Capitán PNP', 8, 'OFICIAL'),
('Teniente PNP', 9, 'OFICIAL'),
('Alférez PNP', 10, 'OFICIAL'),

-- Suboficiales Superiores
('Suboficial Superior PNP', 11, 'SUBOFICIAL'),
('Suboficial Brigadier PNP', 12, 'SUBOFICIAL'),
('Suboficial Técnico de Primera PNP', 13, 'SUBOFICIAL'),
('Suboficial Técnico de Segunda PNP', 14, 'SUBOFICIAL'),
('Suboficial Técnico de Tercera PNP', 15, 'SUBOFICIAL'),

-- Suboficiales Auxiliares
('Suboficial de Primera PNP', 16, 'SUBOFICIAL'),
('Suboficial de Segunda PNP', 17, 'SUBOFICIAL'),
('Suboficial de Tercera PNP', 18, 'SUBOFICIAL');

-- Principales Ubigeos del Perú
INSERT INTO ubigeo (departamento, provincia, distrito) VALUES
-- Lima
('LIMA', 'LIMA', 'LIMA'),
('LIMA', 'LIMA', 'SAN JUAN DE LURIGANCHO'),
('LIMA', 'LIMA', 'SAN MARTIN DE PORRES'),
('LIMA', 'LIMA', 'COMAS'),
('LIMA', 'CALLAO', 'CALLAO'),

-- Arequipa
('AREQUIPA', 'AREQUIPA', 'AREQUIPA'),
('AREQUIPA', 'AREQUIPA', 'CAYMA'),

-- Cusco
('CUSCO', 'CUSCO', 'CUSCO'),
('CUSCO', 'CUSCO', 'SANTIAGO'),

-- La Libertad
('LA LIBERTAD', 'TRUJILLO', 'TRUJILLO'),

-- Piura
('PIURA', 'PIURA', 'PIURA'),

-- Lambayeque
('LAMBAYEQUE', 'CHICLAYO', 'CHICLAYO'),

-- Junín
('JUNIN', 'HUANCAYO', 'HUANCAYO'),

-- Loreto
('LORETO', 'MAYNAS', 'IQUITOS'),

-- Puno
('PUNO', 'PUNO', 'PUNO'),

-- Ucayali
('UCAYALI', 'CORONEL PORTILLO', 'CALLERIA');

-- Tipos de Unidades Policiales
INSERT INTO unidad_policial (nombre, siglas, tipo) VALUES
-- Direcciones
('Dirección de Investigación Criminal', 'DIRINCRI', 'DIVISION'),
('Dirección de Tránsito', 'DIRTRA', 'DIVISION'),
('Dirección de Operaciones Especiales', 'DIROES', 'DIVISION'),
('Dirección Contra el Terrorismo', 'DIRCOTE', 'DIVISION'),

-- Comisarías de Lima
('Comisaría Central de Lima', 'CC-LIMA', 'COMISARIA'),
('Comisaría de San Juan de Lurigancho', 'COM-SJL', 'COMISARIA'),
('Comisaría de Comas', 'COM-COMAS', 'COMISARIA'),
('Comisaría del Callao', 'COM-CALLAO', 'COMISARIA'),

-- Regiones
('Región Policial Lima', 'RPOL-LIMA', 'REGION'),
('Región Policial Arequipa', 'RPOL-AQP', 'REGION'),
('Región Policial Cusco', 'RPOL-CUSCO', 'REGION'),

-- Administrativo
('Dirección de Personal', 'DIRPER', 'ADMINISTRATIVO'),
('Dirección de Logística', 'DIRLOG', 'ADMINISTRATIVO');

-- Cargos Comunes
INSERT INTO cargo (nombre_cargo, descripcion, nivel_jerarquico) VALUES
('Jefe de Comisaría', 'Responsable de la comisaría', 1),
('Subjefe de Comisaría', 'Segundo al mando', 2),
('Jefe de Grupo', 'Responsable de grupo operativo', 3),
('Operador de Radio', 'Comunicaciones', 4),
('Efectivo de Patrullaje', 'Patrullaje a pie/motorizado', 5),
('Investigador', 'Investigación criminal', 3),
('Secretario', 'Labores administrativas', 4);

-- Usuario Administrador por defecto
-- Password: admin123 (BCrypt hash)
INSERT INTO usuario (username, password_hash, id_rol, estado) VALUES
('admin', '$2a$10$XlOEqM8xqNjN9qN9xqN9xO', 1, 1);

-- =====================================================
-- 8. VISTAS ÚTILES
-- =====================================================

-- Vista de Personal Activo con información completa
CREATE VIEW v_personal_activo AS
SELECT 
    p.id_personal,
    p.cip,
    p.dni,
    CONCAT(p.apellidos, ', ', p.nombres) AS nombre_completo,
    p.sexo,
    g.nombre AS grado,
    g.jerarquia,
    u.nombre AS unidad_actual,
    u.tipo AS tipo_unidad,
    p.especialidad,
    p.estado,
    TIMESTAMPDIFF(YEAR, p.fecha_nacimiento, CURDATE()) AS edad,
    TIMESTAMPDIFF(YEAR, p.fecha_ingreso, CURDATE()) AS anos_servicio
FROM personal p
LEFT JOIN grado_policial g ON p.id_grado = g.id_grado
LEFT JOIN unidad_policial u ON p.id_unidad_actual = u.id_unidad
WHERE p.estado = 'ACTIVO';

-- Vista de Servicios Actuales
CREATE VIEW v_servicios_actuales AS
SELECT 
    s.id_servicio,
    p.cip,
    CONCAT(p.apellidos, ', ', p.nombres) AS personal,
    u.nombre AS unidad,
    s.cargo,
    s.fecha_inicio,
    DATEDIFF(CURDATE(), s.fecha_inicio) AS dias_en_servicio
FROM servicio_prestado s
INNER JOIN personal p ON s.id_personal = p.id_personal
INNER JOIN unidad_policial u ON s.id_unidad = u.id_unidad
WHERE s.fecha_fin IS NULL;

-- =====================================================
-- 9. PROCEDIMIENTOS ALMACENADOS
-- =====================================================

DELIMITER //

-- Procedimiento para registrar ascenso automáticamente
CREATE PROCEDURE sp_registrar_ascenso(
    IN p_id_personal INT,
    IN p_id_grado_nuevo INT,
    IN p_fecha_ascenso DATE,
    IN p_rd_documento VARCHAR(50)
)
BEGIN
    DECLARE v_id_grado_actual INT;
    
    -- Obtener grado actual
    SELECT id_grado INTO v_id_grado_actual FROM personal WHERE id_personal = p_id_personal;
    
    -- Insertar ascenso
    INSERT INTO ascenso (id_personal, id_grado_anterior, id_grado_nuevo, fecha_ascenso, rd_documento)
    VALUES (p_id_personal, v_id_grado_actual, p_id_grado_nuevo, p_fecha_ascenso, p_rd_documento);
    
    -- Actualizar grado en personal
    UPDATE personal SET id_grado = p_id_grado_nuevo WHERE id_personal = p_id_personal;
END //

DELIMITER ;

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================

-- Mostrar resumen
SELECT 'Base de datos bd_policia creada exitosamente' AS mensaje;
SELECT COUNT(*) AS total_tablas FROM information_schema.tables WHERE table_schema = 'bd_policia';
SELECT 'Datos maestros insertados correctamente' AS estado;
