@echo off
echo Building Student Management System...
echo.

REM Create directories if they don't exist
if not exist "WEB-INF\classes" mkdir "WEB-INF\classes"
if not exist "WEB-INF\lib" mkdir "WEB-INF\lib"

REM Download dependencies if they don't exist
echo Checking dependencies...

if not exist "WEB-INF\lib\sqlite-jdbc-3.44.1.0.jar" (
    echo Downloading SQLite JDBC driver...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar' -OutFile 'WEB-INF\lib\sqlite-jdbc-3.44.1.0.jar'"
)

if not exist "WEB-INF\lib\servlet-api.jar" (
    echo Downloading Servlet API...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/javax/servlet/javax.servlet-api/4.0.1/javax.servlet-api-4.0.1.jar' -OutFile 'WEB-INF\lib\servlet-api.jar'"
)

if not exist "WEB-INF\lib\jstl-1.2.jar" (
    echo Downloading JSTL...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/javax/servlet/jstl/1.2/jstl-1.2.jar' -OutFile 'WEB-INF\lib\jstl-1.2.jar'"
)

REM Compile Java files
echo Compiling Java files...
javac -cp "WEB-INF\lib\*" -d "WEB-INF\classes" src\com\student\model\*.java src\com\student\dao\*.java src\com\student\servlet\*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Build successful!
    echo.
    echo To deploy:
    echo 1. Copy this entire folder to your Tomcat webapps directory
    echo 2. Rename the folder to 'StudentManagementSystem'
    echo 3. Start Tomcat
    echo 4. Access http://localhost:8080/StudentManagementSystem
    echo.
) else (
    echo.
    echo Build failed! Check for compilation errors.
    echo.
)

pause