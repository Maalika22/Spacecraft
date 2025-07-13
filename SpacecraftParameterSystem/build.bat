@echo off
echo Compiling Spacecraft Parameter Management System...

REM Create classes directory if it doesn't exist
if not exist "WEB-INF\classes" mkdir "WEB-INF\classes"

REM Compile utility classes first
echo Compiling utility classes...
javac -cp "WEB-INF\lib\*" -d WEB-INF\classes src\util\DatabaseUtil.java
if %ERRORLEVEL% neq 0 (
    echo Error compiling DatabaseUtil.java
    exit /b 1
)

REM Compile model classes
echo Compiling model classes...
javac -cp "WEB-INF\lib\*" -d WEB-INF\classes src\model\Parameter.java
if %ERRORLEVEL% neq 0 (
    echo Error compiling Parameter.java
    exit /b 1
)

REM Compile DAO classes
echo Compiling DAO classes...
javac -cp "WEB-INF\lib\*;WEB-INF\classes" -d WEB-INF\classes src\dao\ParameterDAO.java
if %ERRORLEVEL% neq 0 (
    echo Error compiling ParameterDAO.java
    exit /b 1
)

REM Compile servlet classes
echo Compiling servlet classes...
javac -cp "WEB-INF\lib\*;WEB-INF\classes" -d WEB-INF\classes src\servlet\*.java
if %ERRORLEVEL% neq 0 (
    echo Error compiling servlet classes
    exit /b 1
)

REM Compile test classes
echo Compiling test classes...
javac -cp "WEB-INF\lib\*;WEB-INF\classes" -d WEB-INF\classes src\test\*.java
if %ERRORLEVEL% neq 0 (
    echo Error compiling test classes
    exit /b 1
)

echo Compilation completed successfully!
echo All class files are in WEB-INF\classes\
pause
