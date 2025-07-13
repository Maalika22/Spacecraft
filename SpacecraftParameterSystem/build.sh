#!/bin/bash
echo "Compiling Spacecraft Parameter Management System..."

# Create classes directory if it doesn't exist
mkdir -p WEB-INF/classes

# Compile utility classes first
echo "Compiling utility classes..."
javac -cp "WEB-INF/lib/*" -d WEB-INF/classes src/util/DatabaseUtil.java
if [ $? -ne 0 ]; then
    echo "Error compiling DatabaseUtil.java"
    exit 1
fi

# Compile model classes
echo "Compiling model classes..."
javac -cp "WEB-INF/lib/*" -d WEB-INF/classes src/model/Parameter.java
if [ $? -ne 0 ]; then
    echo "Error compiling Parameter.java"
    exit 1
fi

# Compile DAO classes
echo "Compiling DAO classes..."
javac -cp "WEB-INF/lib/*:WEB-INF/classes" -d WEB-INF/classes src/dao/ParameterDAO.java
if [ $? -ne 0 ]; then
    echo "Error compiling ParameterDAO.java"
    exit 1
fi

# Compile servlet classes
echo "Compiling servlet classes..."
javac -cp "WEB-INF/lib/*:WEB-INF/classes" -d WEB-INF/classes src/servlet/*.java
if [ $? -ne 0 ]; then
    echo "Error compiling servlet classes"
    exit 1
fi

# Compile test classes
echo "Compiling test classes..."
javac -cp "WEB-INF/lib/*:WEB-INF/classes" -d WEB-INF/classes src/test/*.java
if [ $? -ne 0 ]; then
    echo "Error compiling test classes"
    exit 1
fi

echo "Compilation completed successfully!"
echo "All class files are in WEB-INF/classes/"