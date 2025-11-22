@echo off
echo ========================================
echo  DETENIENDO SISTEMA PNP
echo ========================================
echo.

echo Cerrando todas las ventanas de Java...
taskkill /F /FI "WINDOWTITLE eq *PNP*" >nul 2>&1

echo Deteniendo contenedores Docker...
docker-compose down

echo.
echo Sistema detenido completamente.
echo.
pause
