@echo off
cd /d "%~dp0"
if not exist out mkdir out
for /r src\main\java %%f in (*.java) do echo %%f>>sources.tmp
javac --release 17 -encoding UTF-8 -d out @sources.tmp
if errorlevel 1 goto :end
del sources.tmp
java -cp out com.menualdia.tp3.MainConsola
:end
if exist sources.tmp del sources.tmp
