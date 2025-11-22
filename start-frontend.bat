@echo off
echo ========================================
echo  SISTEMA PNP - INICIANDO FRONTEND
echo ========================================
echo.

cd frontend\pnp-frontend

REM Refrescar PATH
for /f "tokens=2*" %%a in ('reg query "HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v Path') do set "SystemPath=%%b"
for /f "tokens=2*" %%a in ('reg query "HKCU\Environment" /v Path') do set "UserPath=%%b"
set "PATH=%SystemPath%;%UserPath%"

echo [1/2] Instalando dependencias de Angular...
call npm install

echo.
echo [2/2] Iniciando servidor Angular...
echo.
echo  Frontend estara disponible en: http://localhost:4200
echo.

start "Frontend Angular - PNP" cmd /k "npm start"

echo.
echo ========================================
echo  SISTEMA COMPLETO INICIADO
echo ========================================
echo.
echo  Backend:
echo   - Eureka Dashboard: http://localhost:8761
echo   - API Gateway:      http://localhost:8080
echo.
echo  Frontend:
echo   - Angular App:      http://localhost:4200
echo.
echo Abre tu navegador en http://localhost:4200
echo.
pause
