@echo off
echo ========================================
echo  SISTEMA PNP - INICIO COMPLETO
echo ========================================
echo.

REM Iniciar backend
call start-backend.bat

REM Esperar a que los servicios inicien
echo.
echo Esperando 90 segundos a que los microservicios inicien...
timeout /t 90 /nobreak

REM Iniciar frontend
call start-frontend.bat
