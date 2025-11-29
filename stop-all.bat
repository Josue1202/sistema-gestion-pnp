@echo off
echo ========================================
echo  SISTEMA PNP - DETENIENDO SERVICIOS
echo ========================================
echo.

echo Cerrando ventanas de servicios Java...
taskkill /FI "WINDOWTITLE eq Eureka*" /F >nul 2>&1
taskkill /FI "WINDOWTITLE eq Auth*" /F >nul 2>&1
taskkill /FI "WINDOWTITLE eq Personal*" /F >nul 2>&1
taskkill /FI "WINDOWTITLE eq API*" /F >nul 2>&1

echo.
echo ========================================
echo Todos los servicios detenidos
echo ========================================
echo.
echo NOTA: MySQL sigue corriendo (no se detiene)
echo.

pause
