@echo off
echo ========================================
echo  SISTEMA PNP - INICIANDO BACKEND
echo ========================================
echo.

REM Verificar que Docker este corriendo
echo [1/5] Verificando Docker...
docker ps >nul 2>&1
if errorlevel 1 (
    echo ERROR: Docker no esta corriendo. Inicia Docker Desktop primero.
    pause
    exit /b 1
)
echo OK - Docker esta corriendo

REM Verificar PostgreSQL
echo [2/5] Verificando PostgreSQL...
docker ps | findstr pnp-postgres >nul
if errorlevel 1 (
    echo Iniciando PostgreSQL...
    docker-compose up -d
    timeout /t 10 /nobreak >nul
)
echo OK - PostgreSQL corriendo

echo.
echo [3/5] Iniciando microservicios...
echo.

REM Configurar JAVA_HOME
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

REM Iniciar Eureka Server
echo Iniciando Eureka Server (Puerto 8761)...
start "Eureka Server - PNP" cmd /k "cd backend\eureka-server && C:\apache-maven-3.9.11\bin\mvn spring-boot:run"
timeout /t 5 /nobreak >nul

REM Iniciar Auth Service
echo Iniciando Auth Service (Puerto 8081)...
start "Auth Service - PNP" cmd /k "cd backend\auth-service && C:\apache-maven-3.9.11\bin\mvn spring-boot:run"
timeout /t 3 /nobreak >nul

REM Iniciar Personal Service
echo Iniciando Personal Service (Puerto 8082)...
start "Personal Service - PNP" cmd /k "cd backend\personal-service && C:\apache-maven-3.9.11\bin\mvn spring-boot:run"
timeout /t 3 /nobreak >nul

REM Iniciar API Gateway
echo Iniciando API Gateway (Puerto 8080)...
start "API Gateway - PNP" cmd /k "cd backend\api-gateway && C:\apache-maven-3.9.11\bin\mvn spring-boot:run"

echo.
echo ========================================
echo  BACKEND INICIADO
echo ========================================
echo.
echo Se abrieron 4 ventanas con los servicios:
echo  - Eureka Server:     http://localhost:8761
echo  - Auth Service:      http://localhost:8081
echo  - Personal Service:  http://localhost:8082
echo  - API Gateway:       http://localhost:8080
echo.
echo Espera 1-2 minutos a que todos los servicios inicien
echo Luego ejecuta: start-frontend.bat
echo.
pause
