# Spacecraft Parameter Management System

A comprehensive JSP-based web application for managing spacecraft parameters with SQLite database backend.

## Tech Stack

- **Backend**: JSP + Java Servlets
- **Database**: SQLite
- **Frontend**: HTML5, CSS3, Bootstrap 5
- **JavaScript**: jQuery with AJAX
- **Server**: Apache Tomcat

## Features

- **Parameter Management**: Handle 38 different spacecraft parameters
- **Autocomplete**: Smart suggestions for spacecraft and subsystem names
- **CSV Upload**: Batch import functionality with AJAX
- **Search**: Multi-criteria search (Page ID, Spacecraft Name, Subsystem Name)
- **Real-time Operations**: Save, delete, and modify entries without page refresh
- **Responsive Design**: Bootstrap 5 based UI with offline support
- **Page Navigation**: Next and Previous buttons for moving between records

## Installation and Setup

1. **Prerequisites**:
   - Java JDK 8 or higher
   - Apache Tomcat 9.x (See TOMCAT_SETUP.md for detailed instructions)
   - All required libraries (included in the project)

2. **Deploy the Application**:
   - Copy the SpacecraftParameterSystem directory to Tomcat's webapps folder
   - Start Tomcat server
   - Access the application at http://localhost:8080/SpacecraftParameterSystem/

3. **Initial Setup**:
   - The database will be automatically initialized on first run
   - Default spacecraft and subsystem values will be pre-loaded

## Usage Guide

### Main Interface

The interface is divided into three main sections:

1. **Header**: Displays the title and current page information (Page No and Page ID)
2. **Left Sidebar**: Contains input fields for spacecraft and subsystem names, CSV upload field, and action buttons
3. **Main Content Area**: Black background with 38 parameter input fields

### Basic Operations

- **Save Parameters**: Fill in the spacecraft name, subsystem name, and parameter values, then click Save
- **Delete Parameters**: Load a record, then click Delete to remove it
- **Search**: Select search criteria, enter search term, and click Search
- **Navigate**: Use Next and Previous buttons to move between records
- **Upload CSV**: Select a CSV file with the proper format and upload it to batch import data

## Project Structure

```
SpacecraftParameterSystem/
├── WebContent/
│   ├── WEB-INF/
│   │   ├── lib/
│   │   │   └── sqlite-jdbc-3.42.0.0.jar
│   │   └── web.xml
│   ├── css/
│   │   ├── bootstrap.min.css
│   │   └── custom.css
│   ├── js/
│   │   ├── jquery.min.js
│   │   ├── bootstrap.bundle.min.js
│   │   └── app.js
│   └── index.jsp
├── src/
│   ├── model/
│   │   └── Parameter.java
│   ├── dao/
│   │   └── ParameterDAO.java
│   ├── servlet/
│   │   ├── SaveParameterServlet.java
│   │   ├── DeleteParameterServlet.java
│   │   ├── LoadParameterServlet.java
│   │   ├── AutocompleteServlet.java
│   │   ├── UploadCSVServlet.java
│   │   └── SearchServlet.java
│   └── util/
│       └── DatabaseUtil.java
├── DEPLOYMENT.md
├── TOMCAT_SETUP.md
└── sample_data.csv
```

## Database Schema

The application uses a single table `parameters` with the following structure:

```sql
CREATE TABLE parameters (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    page_id TEXT NOT NULL,
    spacecraft_name TEXT NOT NULL,
    subsystem_name TEXT NOT NULL,
    param1 TEXT, param2 TEXT, ..., param38 TEXT,
    UNIQUE(page_id, spacecraft_name, subsystem_name)
);
```

**Candidate Keys**: (page_id, spacecraft_name, subsystem_name)

## Setup Instructions

### Prerequisites

1. **Java JDK 8 or higher**
2. **Apache Tomcat 9.0 or higher**
3. **VS Code** with Java extensions
4. **Internet connection** (for downloading dependencies)

### Step 1: Download Required Libraries

1. **SQLite JDBC Driver**:
   - Download from: https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
   - Place `sqlite-jdbc-x.x.x.jar` in `WebContent/WEB-INF/lib/`

2. **Bootstrap CSS**:
   - Download from: https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css
   - Replace the placeholder `WebContent/css/bootstrap.min.css`

3. **Bootstrap JavaScript**:
   - Download from: https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js
   - Replace the placeholder `WebContent/js/bootstrap.bundle.min.js`

4. **jQuery**:
   - Download from: https://code.jquery.com/jquery-3.6.0.min.js
   - Replace the placeholder `WebContent/js/jquery.min.js`

### Step 2: Setup VS Code Environment

1. **Install Extensions**:
   - Extension Pack for Java
   - Tomcat for Java
   - Community Server Connectors

2. **Configure Tomcat**:
   - Download Apache Tomcat 9.0+
   - Extract to a directory (e.g., `C:\apache-tomcat-9.0.x`)
   - In VS Code, open Command Palette (Ctrl+Shift+P)
   - Run "Java: Configure Java Runtime"
   - Add Tomcat server

### Step 3: Setup Project in VS Code

1. **Open Project**:
   ```
   File > Open Folder > Select SpacecraftParameterSystem
   ```

2. **Configure Build Path**:
   - Right-click project > "Configure Classpath"
   - Add External JARs: Include Tomcat's servlet-api.jar
   - Add the SQLite JDBC jar from WEB-INF/lib

3. **Configure Server**:
   - Open VS Code Command Palette
   - "Servers: Create Server"
   - Select Tomcat
   - Add project to server

### Step 4: Build and Deploy

1. **Build Project**:
   ```
   Ctrl+Shift+P > Java: Rebuild Projects
   ```

2. **Deploy to Tomcat**:
   - Right-click on Tomcat server in VS Code
   - "Add Deployment" > Select project folder
   - Start server

3. **Access Application**:
   ```
   http://localhost:8080/SpacecraftParameterSystem
   ```

## Usage Guide

### Basic Operations

1. **Create New Entry**:
   - Enter Spacecraft Name and Subsystem Name
   - Fill in parameter values
   - Click "Save"

2. **Load Existing Entry**:
   - Select spacecraft and subsystem
   - Click "Load Parameters"
   - Modify values as needed
   - Click "Save" to update

3. **Search Records**:
   - Select search type (Page ID, Spacecraft, Subsystem)
   - Enter search term
   - Click "Search"
   - Click "Load" on desired result

4. **CSV Upload**:
   - Prepare CSV with format: pageId, spacecraftName, subsystemName, param1, ..., param38
   - Select CSV file
   - Click "Upload CSV"

### CSV Format Example

```csv
page_id,spacecraft_name,subsystem_name,param1,param2,param3,...,param38
PAGE_ABC123,Voyager 1,Communications,12.5,3.2,45.0,...,0.95
PAGE_DEF456,Hubble,Optical,8.7,2.1,67.3,...,1.23
```

## API Endpoints

- `POST /save` - Save parameter data
- `POST /delete` - Delete parameter record
- `GET /load` - Load parameter data
- `GET /autocomplete` - Get autocomplete suggestions
- `POST /upload` - Upload CSV file
- `GET /search` - Search parameters

## Troubleshooting

### Common Issues

1. **ClassNotFoundException for SQLite**:
   - Ensure sqlite-jdbc.jar is in WEB-INF/lib
   - Restart Tomcat server

2. **Servlet compilation errors**:
   - Add servlet-api.jar to build path
   - Ensure proper Java version

3. **CSS/JS not loading**:
   - Check file paths in index.jsp
   - Ensure Bootstrap and jQuery files are downloaded

4. **Database connection issues**:
   - Check if database file has write permissions
   - Verify SQLite JDBC driver version

### Development Tips

1. **Hot Reload**:
   - Use VS Code's Java hot reload feature
   - Modify JSP files without restart

2. **Debugging**:
   - Use VS Code debugger with Tomcat
   - Add breakpoints in servlet code

3. **Database Inspection**:
   - Use SQLite browser tools
   - Check `spacecraft_parameters.db` file

## Future Enhancements

1. **Authentication**: Add user login system
2. **Validation**: Enhanced client/server-side validation
3. **Export**: Generate PDF/Excel reports
4. **History**: Track parameter change history
5. **Charts**: Visualize parameter trends
6. **REST API**: Expose RESTful endpoints

## License

This project is developed for educational/demonstration purposes.

## Support

For issues and questions, please check the troubleshooting section above or review the code comments for implementation details.
