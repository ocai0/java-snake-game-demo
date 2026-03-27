@echo off
CLS
if not exist bin mkdir bin
del /q bin\*.class
echo Compilando o jogo...
javac -d bin src/*.java
if %errorlevel% neq 0 (
    echo.
    echo [ERRO] A compilacao falhou. Verifique o codigo.
    pause
    exit /b %errorlevel%
)
CLS
echo Iniciando "SnakeGame"
java -cp bin SnakeGame