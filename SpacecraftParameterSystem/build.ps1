# PowerShell build script for Spacecraft Parameter Management System
Write-Host "Compiling Spacecraft Parameter Management System..." -ForegroundColor Green

# Create classes directory if it doesn't exist
if (!(Test-Path "WebContent\WEB-INF\classes")) {
    New-Item -ItemType Directory -Path "WebContent\WEB-INF\classes" -Force
    Write-Host "Created classes directory" -ForegroundColor Yellow
}

# Function to check compilation result
function Check-Compilation {
    param($ProcessName)
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Error compiling $ProcessName" -ForegroundColor Red
        exit 1
    }
    Write-Host "$ProcessName compiled successfully" -ForegroundColor Green
}

try {
    # Compile utility classes first
    Write-Host "Compiling utility classes..." -ForegroundColor Cyan
    javac -cp "WebContent\WEB-INF\lib\*" -d WebContent\WEB-INF\classes src\util\DatabaseUtil.java
    Check-Compilation "DatabaseUtil.java"

    # Compile model classes
    Write-Host "Compiling model classes..." -ForegroundColor Cyan
    javac -cp "WebContent\WEB-INF\lib\*" -d WebContent\WEB-INF\classes src\model\Parameter.java
    Check-Compilation "Parameter.java"

    # Compile DAO classes
    Write-Host "Compiling DAO classes..." -ForegroundColor Cyan
    javac -cp "WebContent\WEB-INF\lib\*;WebContent\WEB-INF\classes" -d WebContent\WEB-INF\classes src\dao\ParameterDAO.java
    Check-Compilation "ParameterDAO.java"

    # Compile servlet classes
    Write-Host "Compiling servlet classes..." -ForegroundColor Cyan
    javac -cp "WebContent\WEB-INF\lib\*;WebContent\WEB-INF\classes" -d WebContent\WEB-INF\classes src\servlet\*.java
    Check-Compilation "Servlet classes"

    Write-Host "`nCompilation completed successfully!" -ForegroundColor Green
    Write-Host "All class files are in WebContent\WEB-INF\classes\" -ForegroundColor Yellow
    
    # List compiled classes
    Write-Host "`nCompiled classes:" -ForegroundColor Cyan
    Get-ChildItem -Path "WebContent\WEB-INF\classes" -Recurse -Filter "*.class" | ForEach-Object {
        Write-Host "  $($_.FullName.Replace((Get-Location).Path + '\WebContent\WEB-INF\classes\', ''))" -ForegroundColor White
    }
}
catch {
    Write-Host "An error occurred during compilation: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host "`nPress any key to continue..." -ForegroundColor Yellow
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
