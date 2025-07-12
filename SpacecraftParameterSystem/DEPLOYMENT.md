# Spacecraft Parameter Management System - Deployment Guide

## Quick Setup for VS Code + Tomcat

### 1. Prerequisites Installation

#### Install Java JDK
```bash
# Windows (using Chocolatey)
choco install openjdk11

# Or download from: https://adoptopenjdk.net/
```

#### Install Apache Tomcat
```bash
# Download Tomcat 9.0 from: https://tomcat.apache.org/download-90.cgi
# Extract to C:\apache-tomcat-9.0.x (Windows) or /opt/tomcat (Linux)
```

### 2. VS Code Extension Setup

Install the following extensions:
- Extension Pack for Java (Microsoft)
- Tomcat for Java (Wei Shen)
- Community Server Connectors (Red Hat)

### 3. Download Required Libraries

#### SQLite JDBC Driver
```bash
# Download from: https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.42.0.0
# Direct link: https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.42.0.0/sqlite-jdbc-3.42.0.0.jar
# Place in: WebContent/WEB-INF/lib/sqlite-jdbc-3.42.0.0.jar
```

#### Bootstrap CSS
```bash
# Download from: https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css
# Save as: WebContent/css/bootstrap.min.css
```

#### Bootstrap JavaScript
```bash
# Download from: https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js
# Save as: WebContent/js/bootstrap.bundle.min.js
```

#### jQuery
```bash
# Download from: https://code.jquery.com/jquery-3.6.0.min.js
# Save as: WebContent/js/jquery.min.js
```

### 4. VS Code Configuration

#### Step 1: Open Project
1. Launch VS Code
2. File > Open Folder
3. Select the `SpacecraftParameterSystem` folder

#### Step 2: Configure Java
1. Press `Ctrl+Shift+P` (Windows) or `Cmd+Shift+P` (Mac)
2. Type "Java: Configure Java Runtime"
3. Ensure JDK 8+ is selected

#### Step 3: Configure Tomcat Server
1. Press `Ctrl+Shift+P`
2. Type "Servers: Create Server"
3. Select "Apache Tomcat"
4. Browse to your Tomcat installation directory
5. Enter server name (e.g., "Tomcat 9.0")

#### Step 4: Add Project to Server
1. Open the "Servers" view in VS Code
2. Right-click on your Tomcat server
3. Select "Add Deployment"
4. Choose "Package File"
5. Select the project folder

### 5. Build and Deploy

#### Step 1: Build Project
```bash
# In VS Code terminal
Ctrl+Shift+P > "Java: Rebuild Projects"
```

#### Step 2: Start Tomcat
1. In the Servers view, right-click on your Tomcat server
2. Select "Start Server"
3. Wait for server to start (check terminal output)

#### Step 3: Deploy Application
1. Right-click on the server
2. Select "Publish Server (Full)" or "Publish Server (Incremental)"

### 6. Access Application

Open your browser and navigate to:
```
http://localhost:8080/SpacecraftParameterSystem
```

### 7. Testing the Application

#### Test Basic Functionality
1. Enter a spacecraft name (e.g., "Voyager 1")
2. Enter a subsystem name (e.g., "Communications")
3. Fill in some parameter values
4. Click "Save"
5. Verify the record is saved

#### Test CSV Upload
1. Use the provided `sample_data.csv` file
2. Click "Choose File" in the CSV Upload section
3. Select the CSV file
4. Click "Upload CSV"
5. Verify records are imported

#### Test Search
1. Select search type (Page ID, Spacecraft, Subsystem)
2. Enter search term
3. Click "Search"
4. Verify results appear

### 8. Troubleshooting

#### Common Issues and Solutions

**Issue**: ServletException - Class not found
**Solution**: 
- Ensure all required JARs are in WEB-INF/lib
- Restart Tomcat server
- Check Java build path configuration

**Issue**: CSS/JS files not loading
**Solution**:
- Verify file paths in index.jsp
- Ensure Bootstrap and jQuery files are properly downloaded
- Check browser developer tools for 404 errors

**Issue**: Database connection error
**Solution**:
- Verify SQLite JDBC driver is in WEB-INF/lib
- Check file permissions on project directory
- Ensure database file can be created in project root

**Issue**: Autocomplete not working
**Solution**:
- Ensure jQuery is loaded properly
- Check browser console for JavaScript errors
- Verify servlet mappings in web.xml

### 9. Development Workflow

#### Hot Reload
VS Code supports hot reload for Java classes:
1. Make changes to Java files
2. Save the file
3. Changes are automatically reloaded

#### JSP Changes
JSP files are reloaded automatically:
1. Make changes to JSP files
2. Save the file
3. Refresh browser to see changes

#### Static Resource Changes
CSS/JS files are served directly:
1. Make changes to CSS/JS files
2. Save the file
3. Hard refresh browser (Ctrl+F5)

### 10. Project Structure Verification

Ensure your project structure looks like this:
```
SpacecraftParameterSystem/
├── WebContent/
│   ├── WEB-INF/
│   │   ├── lib/
│   │   │   └── sqlite-jdbc-3.42.0.0.jar ✓
│   │   └── web.xml ✓
│   ├── css/
│   │   ├── bootstrap.min.css ✓ (download required)
│   │   └── custom.css ✓
│   ├── js/
│   │   ├── jquery.min.js ✓ (download required)
│   │   ├── bootstrap.bundle.min.js ✓ (download required)
│   │   └── app.js ✓
│   └── index.jsp ✓
├── src/
│   ├── model/
│   │   └── Parameter.java ✓
│   ├── dao/
│   │   └── ParameterDAO.java ✓
│   ├── servlet/
│   │   ├── SaveParameterServlet.java ✓
│   │   ├── DeleteParameterServlet.java ✓
│   │   ├── LoadParameterServlet.java ✓
│   │   ├── AutocompleteServlet.java ✓
│   │   ├── UploadCSVServlet.java ✓
│   │   └── SearchServlet.java ✓
│   └── util/
│       └── DatabaseUtil.java ✓
├── sample_data.csv ✓
└── README.md ✓
```

### 11. Alternative: Using CDN (Quick Test)

If you want to test quickly without downloading files, modify `index.jsp`:

Replace:
```html
<link href="css/bootstrap.min.css" rel="stylesheet">
```

With:
```html
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
```

Replace:
```html
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
```

With:
```html
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
```

### 12. Database File Location

The SQLite database file `spacecraft_parameters.db` will be created in the project root directory when you first run the application.

### 13. Next Steps

After successful deployment:
1. Test all functionality
2. Import sample data using the CSV upload
3. Customize parameter labels in the JSP file
4. Add additional validation as needed
5. Enhance styling in custom.css

For production deployment, consider:
- Setting up proper logging
- Adding input validation
- Implementing user authentication
- Using a production database
- Adding SSL/TLS support
