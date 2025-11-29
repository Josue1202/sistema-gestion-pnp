@echo off
echo ========================================
echo  SISTEMA PNP - INICIANDO BACKEND
echo ========================================
echo.

REM NO SE USA DOCKER/POSTGRESQL
REM Sistema usa MySQL instalado localmente en bd_policia

echo [1/4] Verificando servicios...
echo MySQL debe estar corriendo en localhost:3306
echo Base de datos: bd_policia
echo.

echo [2/4] Iniciando Eureka Server...
start "Eureka Server" cmd /k "cd backend\eureka-server && mvn spring-boot:run"
timeout /t 15 /nobreak >nul

echo.
echo [3/4] Iniciando Auth Service...
start "Auth Service" cmd /k "cd backend\auth-service && mvn spring-boot:run"
timeout /t 10 /nobreak >nul

echo.
echo [4/4] Iniciando Personal Service...
start "Personal Service" cmd /k "cd backend\personal-service && mvn spring-boot:run"
timeout /t 10 /nobreak >nul

echo.
echo [5/4] Iniciando API Gateway...
start "API Gateway" cmd /k "cd backend\api-gateway && mvn spring-boot:run"

echo.
echo ========================================
echo Backend iniciado exitosamente!
echo ========================================
echo.
echo Servicios disponibles:
echo - Eureka Server:   http://localhost:8761
echo - Auth Service:    http://localhost:8081  
echo - Personal Service: http://localhost:8082
echo - API Gateway:     http://localhost:8080
echo.
echo NOTA: MySQL debe estar corriendo (bd_policia)
echo.

pause
