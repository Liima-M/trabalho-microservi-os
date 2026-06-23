@echo off
setlocal enabledelayedexpansion
set "OUT=resultado-testes.txt"

echo TESTE TRABCOMMERCE - %date% %time% > "%OUT%"
echo. >> "%OUT%"

echo === 1. HEALTH CHECKS === >> "%OUT%"
for %%P in (8080 8081 8082 8083 8084 8085 8086 8087 8088 8089) do (
    set "RESP="
    for /f "usebackq delims=" %%R in (`curl -s --max-time 5 http://localhost:%%P/actuator/health 2^>nul`) do set "RESP=%%R"
    echo [%%P] !RESP! >> "%OUT%"
)

echo. >> "%OUT%"
echo === 2. REGISTRO DE USUARIO === >> "%OUT%"
echo {"firstName":"Matheus","lastName":"Teixeira","email":"matheus.teste@udesc.br","password":"senha@123","phone":"47999990001","userType":"COMPRADOR"} > "%TEMP%\reg.json"
curl -s --max-time 10 -X POST http://localhost:8081/auth/register -H "Content-Type: application/json" -d @"%TEMP%\reg.json" >> "%OUT%" 2>&1
echo. >> "%OUT%"

echo. >> "%OUT%"
echo === 3. LOGIN (direto auth-service:8081) === >> "%OUT%"
echo {"email":"matheus.teste@udesc.br","password":"senha@123"} > "%TEMP%\login.json"
curl -s --max-time 10 -X POST http://localhost:8081/auth/login -H "Content-Type: application/json" -d @"%TEMP%\login.json" > "%TEMP%\loginresp.json" 2>&1
type "%TEMP%\loginresp.json" >> "%OUT%"
echo. >> "%OUT%"

rem Extract token via PowerShell
set "TOKEN="
for /f "usebackq delims=" %%T in (`powershell -command "try{(Get-Content '%TEMP%\loginresp.json' ^| ConvertFrom-Json).accessToken}catch{''}" 2^>nul`) do set "TOKEN=%%T"
echo TOKEN_EXTRAIDO: !TOKEN:~0,60!... >> "%OUT%"

echo. >> "%OUT%"
echo === 4. LOGIN VIA GATEWAY (porta 8080) === >> "%OUT%"
curl -s --max-time 10 -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d @"%TEMP%\login.json" >> "%OUT%" 2>&1
echo. >> "%OUT%"

echo. >> "%OUT%"
echo === 5. CATALOG via GATEWAY com token === >> "%OUT%"
curl -s --max-time 10 "http://localhost:8080/catalog/products/search" -H "Authorization: Bearer !TOKEN!" >> "%OUT%" 2>&1
echo. >> "%OUT%"

echo. >> "%OUT%"
echo === 6. SWAGGER UIs === >> "%OUT%"
for %%P in (8081 8082 8083 8084 8085 8086 8087 8088 8089) do (
    for /f "usebackq" %%C in (`curl -s --max-time 5 -o nul -w "%%{http_code}" http://localhost:%%P/swagger-ui/index.html 2^>nul`) do (
        echo [%%P] HTTP %%C >> "%OUT%"
    )
)

echo. >> "%OUT%"
echo === CONCLUIDO === >> "%OUT%"
echo Resultado salvo em: %CD%\resultado-testes.txt
echo.
type "%OUT%"
echo.
pause
