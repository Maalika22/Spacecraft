# Tomcat Server Setup Guide for Spacecraft Parameter Management System

This guide will help you set up an Apache Tomcat server to run the Spacecraft Parameter Management System web application.

## Prerequisites

1. Java JDK 8 or higher installed
2. Apache Tomcat 9.x downloaded from https://tomcat.apache.org/download-90.cgi

## Installation Steps

### Step 1: Install Apache Tomcat

1. Download Apache Tomcat 9.x from the official website
2. Extract the downloaded zip/tar.gz file to a directory of your choice (e.g., `C:\tomcat` or `/opt/tomcat`)
3. Set the `CATALINA_HOME` environment variable to point to your Tomcat installation directory

### Step 2: Configure Tomcat

1. In the Tomcat directory, navigate to the `conf` folder
2. Edit `tomcat-users.xml` and add the following lines inside the `<tomcat-users>` tag:

```xml
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<user username="admin" password="password" roles="manager-gui, manager-script"/>
```

3. Save the file

### Step 3: Deploy the Spacecraft Parameter Management System

#### Option 1: WAR File Deployment

1. Build the WAR file for the Spacecraft Parameter Management System
2. Copy the WAR file to the `webapps` directory in your Tomcat installation
3. Start Tomcat by running the appropriate script:
   - Windows: `%CATALINA_HOME%\bin\startup.bat`
   - Linux/Mac: `$CATALINA_HOME/bin/startup.sh`

#### Option 2: Direct Deployment

1. Copy the entire `SpacecraftParameterSystem` directory to the `webapps` directory in your Tomcat installation
2. Start Tomcat using the appropriate script as mentioned above

### Step 4: Access the Application

1. Open a web browser and navigate to: `http://localhost:8080/SpacecraftParameterSystem/`
2. You should see the Spacecraft Parameter Management System interface

## Troubleshooting

### Database Initialization Issues

- If you encounter database initialization errors, ensure the SQLite JDBC driver is in the `WEB-INF/lib` directory
- Check that the application has write permissions in the directory where the SQLite database file is being created

### Connection Refused Errors

- Verify that Tomcat is running by checking if the process is active
- Ensure no other application is using port 8080
- If needed, change Tomcat's port by editing the `conf/server.xml` file

### ClassNotFoundException

- Ensure all required JAR files are in the `WEB-INF/lib` directory
- Verify that the compile-time classes match the runtime JDK version

## Additional Resources

- [Apache Tomcat Documentation](https://tomcat.apache.org/tomcat-9.0-doc/index.html)
- [Java Servlet Specification](https://javaee.github.io/servlet-spec/)
- [SQLite Documentation](https://www.sqlite.org/docs.html)

## Support

For additional support, please refer to the project documentation or contact the system administrator.
