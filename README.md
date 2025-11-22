# Sistema de Gestión de Personal PNP 🚔

Sistema completo de gestión de información del personal de la Policía Nacional del Perú, desarrollado con arquitectura de microservicios.

## 🏗️ Arquitectura

### Backend - Java Spring Boot Microservicios
- **Eureka Server** (Puerto 8761) - Descubrimiento de servicios
- **API Gateway** (Puerto 8080) - Punto de entrada único
- **Auth Service** (Puerto 8081) - Autenticación JWT
- **Personal Service** (Puerto 8082) - Gestión CRUD de personal

### Frontend - Angular 17
- Aplicación moderna con diseño glassmorphism
- Tema oscuro con colores PNP
- Totalmente responsive
- Animaciones suaves

### Base de Datos - PostgreSQL
- `pnp_auth` - Base de datos de autenticación
- `pnp_personal` - Base de datos de personal

## 📋 Requisitos Previos

- **Java 17** o superior
- **Maven 3.8+**
- **Node.js 18+** y npm
- **Angular CLI 17+**
- **PostgreSQL 15+**
- **Docker** (opcional, para base de datos)

## 🚀 Instalación y Ejecución

### 1. Base de Datos

#### Opción A: Usar Docker (Recomendado)
```bash
# Iniciar PostgreSQL con Docker Compose
docker-compose up -d

# Verificar que esté corriendo
docker ps
```

#### Opción B: PostgreSQL Local
```bash
# Conectarse a PostgreSQL
psql -U postgres

# Ejecutar script de inicialización
\i scripts/init-databases.sql

# Conectarse a pnp_personal e insertar datos de prueba
\c pnp_personal
\i scripts/sample-data.sql
```

### 2. Backend - Microservicios

#### Iniciar en orden:

**1. Eureka Server**
```bash
cd backend/eureka-server
mvn clean package
mvn spring-boot:run
# Acceder a: http://localhost:8761
```

**2. Auth Service**
```bash
cd backend/auth-service
mvn clean package
mvn spring-boot:run
```

**3. Personal Service**
```bash
cd backend/personal-service
mvn clean package
mvn spring-boot:run
```

**4. API Gateway**
```bash
cd backend/api-gateway
mvn clean package
mvn spring-boot:run
# API disponible en: http://localhost:8080
```

### 3. Frontend - Angular

```bash
cd frontend/pnp-frontend

# Instalar dependencias
npm install

# Iniciar servidor de desarrollo
npm start
# Acceder a: http://localhost:4200
```

## 📊 Modelo de Datos

### Personal PNP
- **CIP** (Código de Identidad Policial) - Único
- **DNI** - Único
- Datos personales (nombres, apellidos, fecha nacimiento, género)
- Información laboral (rango, especialidad, unidad, estado)
- Datos de contacto (teléfono, email, dirección)
- Fechas (ingreso, creación, actualización)

### Funciones Policiales
- Descripción de la función
- Fecha de asignación
- Fecha de finalización
- Estado (activo/inactivo)

## 🔐 Autenticación

El sistema usa JWT para autenticación. Para probar:

1. Primero registra un usuario:
```bash
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123",
  "cip": "123456789",
  "rol": "ADMIN"
}
```

2. Luego inicia sesión:
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

3. Usa el token JWT recibido en los headers de las demás peticiones:
```
Authorization: Bearer <tu-token-jwt>
```

## 📡 Endpoints Principales

### Auth Service
- `POST /api/auth/register` - Registrar usuario
- `POST /api/auth/login` - Iniciar sesión
- `GET /api/auth/me` - Usuario actual

### Personal Service
- `GET /api/personal` - Listar todo el personal
- `GET /api/personal/{id}` - Obtener por ID
- `GET /api/personal/cip/{cip}` - Buscar por CIP
- `GET /api/personal/dni/{dni}` - Buscar por DNI
- `GET /api/personal/search?q=query` - Búsqueda general
- `POST /api/personal` - Crear nuevo personal
- `PUT /api/personal/{id}` - Actualizar personal
- `DELETE /api/personal/{id}` - Eliminar personal
- `GET /api/personal/stats` - Estadísticas

### Funciones
- `GET /api/funciones/personal/{personalId}` - Funciones de un personal
- `POST /api/funciones/personal/{personalId}` - Asignar función
- `PUT /api/funciones/{id}` - Actualizar función
- `DELETE /api/funciones/{id}` - Eliminar función

## 🎨 Características del Frontend

- ✅ Login con validación
- ✅ Dashboard con estadísticas
- ✅ Listado de personal con búsqueda
- ✅ Filtros por CIP, DNI, nombre
- ✅ Badges de estado (Activo, Inactivo, Licencia)
- ✅ Diseño moderno y responsive
- ✅ Animaciones suaves
- ✅ Tema oscuro premium

## 🛠️ Tecnologías Utilizadas

### Backend
- Spring Boot 3.2.0
- Spring Cloud (Eureka, Gateway)
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven

### Frontend
- Angular 17
- TypeScript
- RxJS
- Angular Material Icons
- CSS3 (Variables, Flexbox, Grid)

## 📝 Datos de Prueba

El sistema incluye 5 registros de personal de prueba con todas sus funciones asignadas. Se cargan automáticamente al ejecutar el script `sample-data.sql`.

## 🔧 Configuración

Todas las configuraciones se encuentran en los archivos `application.yml` de cada microservicio:

- **Puertos**: Modificar `server.port`
- **Base de datos**: Modificar `spring.datasource.*`
- **JWT Secret**: Modificar `jwt.secret` en auth-service
- **Eureka URL**: Modificar `eureka.client.service-url`

## 📱 Responsive Design

La aplicación es completamente responsive y se adapta a:
- 📱 Móviles (320px+)
- 📱 Tablets (768px+)
- 💻 Desktop (1024px+)
- 🖥️ Large screens (1440px+)

## 🚨 Seguridad

> **IMPORTANTE**: Esta es una aplicación de desarrollo. Para producción se recomienda:
> - Cambiar el secreto JWT
> - Implementar HTTPS/SSL
> - Usar variables de entorno para credenciales
- Implementar rate limiting
> - Auditoría de accesos
> - Encriptación de datos sensibles
> - Cumplir con Ley N° 29733 (Protección de Datos Personales - Perú)

## 📄 Licencia

Sistema desarrollado para uso interno de la Policía Nacional del Perú.

## 👨‍💻 Autor

Desarrollado con ❤️ para la PNP

---

**Sistema de Gestión de Personal PNP** - © 2024
