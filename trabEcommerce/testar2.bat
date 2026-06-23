@echo off
setlocal enabledelayedexpansion
set "OUT=resultado2.txt"
echo TESTE2 - %date% %time% > "%OUT%"
echo. >> "%OUT%"

echo === HEALTH CHECKS (timeout 30s) === >> "%OUT%"
for %%P in (8080 8081 8082 8083 8084 8085 8086 8087 8088 8089) do (
    echo | set /p="[%%P] " >> "%OUT%"
    curl -s --connect-timeout 15 --max-time 30 http://localhost:%%P/actuator/health >> "%OUT%" 2>&1
    echo. >> "%OUT%"
)

echo. >> "%OUT%"
echo === AUTH REGISTER === >> "%OUT%"
echo {"firstName":"Matheus","lastName":"Teixeira","email":"matheus.teste@udesc.br","password":"senha@123","phone":"47999990001","userType":"COMPRADOR"} > "%TEMP%\reg.json"
curl -s --connect-timeout 15 --max-time 30 -X POST http://localhost:8081/auth/register -H "Content-Type: application/json" -d @"%TEMP%\reg.json" >> "%OUT%" 2>&1
echo. >> "%OUT%"

echo === AUTH LOGIN === >> "%OUT%"
echo {"email":"matheus.teste@udesc.br","password":"senha@123"} > "%TEMP%\login.json"
curl -s --connect-timeout 15 --max-time 30 -X POST http://localhost:8081/auth/login -H "Content-Type: application/json" -d @"%TEMP%\login.json" > "%TEMP%\loginresp.json" 2>&1
type "%TEMP%\loginresp.json" >> "%OUT%"
echo. >> "%OUT%"

powershell -command "try{$t=(Get-Content '%TEMP%\loginresp.json'|ConvertFrom-Json).accessToken;if($t){'TOKEN_OK: '+$t.Substring(0,[Math]::Min(80,$t.Length))+'...'}else{'TOKEN_VAZIO'}}catch{'PARSE_ERROR'}" >> "%OUT%" 2>&1
set /p TOKEN= < "%TEMP%\loginresp.json"

echo. >> "%OUT%"
echo === GATEWAY LOGIN === >> "%OUT%"
curl -s --connect-timeout 15 --max-time 30 -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d @"%TEMP%\login.json" >> "%OUT%" 2>&1
echo. >> "%OUT%"

echo. >> "%OUT%"
echo === SWAGGER STATUS CODES === >> "%OUT%"
for %%P in (8081 8082 8083 8084 8085 8086 8087 8088 8089) do (
    for /f "usebackq" %%C in (`curl -s --connect-timeout 10 --max-time 20 -o nul -w "%%{http_code}" http://localhost:%%P/swagger-ui/index.html 2^>nul`) do (
        echo [%%P] HTTP %%C >> "%OUT%"
    )
)

echo. >> "%OUT%"
echo === CONCLUIDO - %time% === >> "%OUT%"
echo.
echo Resultado em: %CD%\resultado2.txt
type "%OUT%"
echo.
pause
