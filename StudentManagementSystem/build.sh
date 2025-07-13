#!/bin/bash

echo "Building Student Management System..."
echo

# Create directories if they don't exist
mkdir -p "WEB-INF/classes"
mkdir -p "WEB-INF/lib"

# Download dependencies if they don't exist
echo "Checking dependencies..."

if [ ! -f "WEB-INF/lib/sqlite-jdbc-3.44.1.0.jar" ]; then
    echo "Downloading SQLite JDBC driver..."
    wget -O "WEB-INF/lib/sqlite-jdbc-3.44.1.0.jar" "https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar"
fi

if [ ! -f "WEB-INF/lib/servlet-api.jar" ]; then
    echo "Downloading Servlet API..."
    wget -O "WEB-INF/lib/servlet-api.jar" "https://repo1.maven.org/maven2/javax/servlet/javax.servlet-api/4.0.1/javax.servlet-api-4.0.1.jar"
fi

if [ ! -f "WEB-INF/lib/jstl-1.2.jar" ]; then
    echo "Downloading JSTL..."
    wget -O "WEB-INF/lib/jstl-1.2.jar" "https://repo1.maven.org/maven2/javax/servlet/jstl/1.2/jstl-1.2.jar"
fi

# Compile Java files
echo "Compiling Java files..."
javac -cp "WEB-INF/lib/*" -d "WEB-INF/classes" src/com/student/model/*.java src/com/student/dao/*.java src/com/student/servlet/*.java

if [ $? -eq 0 ]; then
    echo
    echo "Build successful!"
    echo
    echo "To deploy:"
    echo "1. Copy this entire folder to your Tomcat webapps directory"
    echo "2. Rename the folder to 'StudentManagementSystem'"
    echo "3. Start Tomcat"
    echo "4. Access http://localhost:8080/StudentManagementSystem"
    echo
else
    echo
    echo "Build failed! Check for compilation errors."
    echo
fi