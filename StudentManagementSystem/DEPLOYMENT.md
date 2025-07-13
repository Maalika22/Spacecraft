# Deployment Guide - Student Management System

This guide provides detailed instructions for deploying the Student Management System JSP application to various environments.

## Quick Deploy (Tomcat)

### Prerequisites
- Apache Tomcat 8.5+ installed
- Java 8+ installed
- Built project (run `build.sh` or `build.bat`)

### Steps
1. **Stop Tomcat** (if running)
2. **Copy the project folder** to `$TOMCAT_HOME/webapps/`
3. **Rename folder** to `StudentManagementSystem` (if different)
4. **Start Tomcat**
5. **Access**: http://localhost:8080/StudentManagementSystem

## Detailed Deployment Instructions

### 1. Apache Tomcat Deployment

#### Option A: WAR File Deployment
```bash
# Create WAR file
cd StudentManagementSystem
jar -cvf StudentManagementSystem.war *

# Deploy WAR file
cp StudentManagementSystem.war $TOMCAT_HOME/webapps/
```

#### Option B: Directory Deployment
```bash
# Copy entire directory
cp -r StudentManagementSystem $TOMCAT_HOME/webapps/
```

#### Tomcat Configuration
Add to `$TOMCAT_HOME/conf/server.xml` if needed:
```xml
<Context path="/StudentManagementSystem" 
         docBase="StudentManagementSystem" 
         reloadable="true" />
```

### 2. Jetty Deployment

```bash
# Copy to Jetty webapps
cp -r StudentManagementSystem $JETTY_HOME/webapps/

# Start Jetty
java -jar $JETTY_HOME/start.jar
```

### 3. GlassFish Deployment

```bash
# Deploy using asadmin
asadmin deploy --contextroot /StudentManagementSystem StudentManagementSystem.war
```

### 4. WildFly/JBoss Deployment

```bash
# Copy to deployments directory
cp StudentManagementSystem.war $JBOSS_HOME/standalone/deployments/
```

## Environment-Specific Configurations

### Development Environment

```xml
<!-- web.xml - Development settings -->
<context-param>
    <param-name>debug</param-name>
    <param-value>true</param-value>
</context-param>
```

### Production Environment

1. **Remove debug parameters**
2. **Configure connection pooling** (if needed)
3. **Set appropriate error pages**
4. **Enable compression**

```xml
<!-- web.xml - Production settings -->
<error-page>
    <error-code>404</error-code>
    <location>/error/404.jsp</location>
</error-page>
<error-page>
    <error-code>500</error-code>
    <location>/error/500.jsp</location>
</error-page>
```

## Database Configuration

### Default Configuration
- **Database File**: `students.db` (auto-created)
- **Location**: Application root directory
- **Driver**: SQLite JDBC

### Custom Database Location
Modify `StudentDAO.java`:
```java
private static final String DB_URL = "jdbc:sqlite:/path/to/your/database/students.db";
```

### Database Backup
```bash
# Backup SQLite database
cp students.db students_backup_$(date +%Y%m%d).db
```

## Security Considerations

### 1. Database Security
- Place database file outside web-accessible directory
- Set appropriate file permissions:
```bash
chmod 600 students.db
```

### 2. Application Security
- Update `web.xml` with security constraints if needed:
```xml
<security-constraint>
    <web-resource-collection>
        <web-resource-name>Admin Area</web-resource-name>
        <url-pattern>/admin/*</url-pattern>
    </web-resource-collection>
    <auth-constraint>
        <role-name>admin</role-name>
    </auth-constraint>
</security-constraint>
```

### 3. HTTPS Configuration
Configure HTTPS in your servlet container for production use.

## Performance Optimization

### 1. Database Optimization
```sql
-- Add indexes for better performance
CREATE INDEX idx_student_roll ON students(roll);
CREATE INDEX idx_student_name ON students(name);
```

### 2. Connection Pooling
For production, consider using connection pooling:
```xml
<!-- context.xml -->
<Resource name="jdbc/StudentDB"
          auth="Container"
          type="javax.sql.DataSource"
          maxTotal="20"
          maxIdle="10"
          maxWaitMillis="-1"
          username=""
          password=""
          driverClassName="org.sqlite.JDBC"
          url="jdbc:sqlite:students.db"/>
```

### 3. Compression
Enable compression in Tomcat:
```xml
<!-- server.xml -->
<Connector port="8080"
           compression="on"
           compressionMinSize="1024"
           compressableMimeType="text/html,text/xml,text/plain,text/css,text/javascript,application/javascript"/>
```

## Monitoring and Logging

### 1. Application Logs
Add logging configuration to `web.xml`:
```xml
<context-param>
    <param-name>log4j-config-location</param-name>
    <param-value>/WEB-INF/log4j.properties</param-value>
</context-param>
```

### 2. Health Check Endpoint
Create a simple health check servlet:
```java
@WebServlet("/health")
public class HealthCheckServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().write("OK");
    }
}
```

## Troubleshooting Deployment Issues

### Common Problems

1. **ClassNotFoundException**
   - Verify all JAR files are in `WEB-INF/lib/`
   - Check Java version compatibility

2. **Database Connection Failed**
   - Verify SQLite JDBC driver is loaded
   - Check database file permissions
   - Ensure write permissions for database directory

3. **404 Not Found**
   - Check application context path
   - Verify servlet mapping in `web.xml`
   - Ensure Tomcat is running

4. **CSS/JS Not Loading**
   - Check file paths in JSP pages
   - Verify files are in correct directories
   - Check servlet container static content configuration

### Debug Commands

```bash
# Check if Tomcat is running
ps aux | grep tomcat

# Check port usage
netstat -tulpn | grep :8080

# Check Tomcat logs
tail -f $TOMCAT_HOME/logs/catalina.out

# Check application-specific logs
tail -f $TOMCAT_HOME/logs/localhost.*.log
```

## Backup and Recovery

### 1. Application Backup
```bash
#!/bin/bash
# backup_app.sh
DATE=$(date +%Y%m%d_%H%M%S)
tar -czf "student_app_backup_$DATE.tar.gz" StudentManagementSystem/
```

### 2. Database Backup
```bash
#!/bin/bash
# backup_db.sh
DATE=$(date +%Y%m%d_%H%M%S)
cp students.db "students_backup_$DATE.db"
```

### 3. Recovery
```bash
# Restore application
tar -xzf student_app_backup_YYYYMMDD_HHMMSS.tar.gz

# Restore database
cp students_backup_YYYYMMDD_HHMMSS.db students.db
```

## Scaling Considerations

### 1. Horizontal Scaling
For multiple instances:
- Use external database (MySQL, PostgreSQL)
- Implement session clustering
- Use load balancer

### 2. Vertical Scaling
- Increase JVM heap size: `-Xmx512m`
- Tune garbage collection
- Optimize database queries

## Docker Deployment

### Dockerfile
```dockerfile
FROM tomcat:9.0-jdk11

# Copy application
COPY StudentManagementSystem/ /usr/local/tomcat/webapps/StudentManagementSystem/

# Expose port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
```

### Build and Run
```bash
# Build Docker image
docker build -t student-management-system .

# Run container
docker run -p 8080:8080 student-management-system
```

---

For additional deployment support, refer to your servlet container's documentation or contact the development team.