@echo off
echo ========================================
echo  SUBIR PROYECTO A GITHUB
echo ========================================
echo.

REM Refrescar PATH
for /f "tokens=2*" %%a in ('reg query "HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v Path') do set "SystemPath=%%b"
for /f "tokens=2*" %%a in ('reg query "HKCU\Environment" /v Path') do set "UserPath=%%b"
set "PATH=%SystemPath%;%UserPath%"

echo [1/6] Configurando Git...
git config --global user.name "Josue"
git config --global user.email "josuegery@gmail.com"

echo [2/6] Inicializando repositorio...
git init

echo [3/6] Agregando archivos...
git add .

echo [4/6] Creando commit...
git commit -m "Sistema PNP: Backend microservicios Java + Frontend Angular + PostgreSQL"

echo.
echo ========================================
echo  CASI LISTO!
echo ========================================
echo.
echo Ahora necesitas:
echo 1. Crear un repositorio en GitHub (https://github.com/new)
echo 2. Copiar la URL del repositorio
echo 3. Ejecutar estos comandos:
echo.
echo    git remote add origin https://github.com/TU_USUARIO/TU_REPOSITORIO.git
echo    git branch -M main
echo    git push -u origin main
echo.
pause
