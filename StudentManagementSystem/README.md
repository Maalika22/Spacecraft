# Student Management System - JSP Project

A complete web-based Student Management System built with JSP, Servlets, and SQLite database. This application allows you to manage student records with Name, Roll Number, and Grade information.

## Features

- **Student Registration**: Add new students with Name, Roll Number, and Grade
- **View All Students**: Display all students in a tabular format
- **Edit Student Information**: Update existing student records
- **Delete Students**: Remove student records with confirmation
- **Grade Management**: Support for standard grades (A+, A, A-, B+, B, B-, C+, C, C-, D, F)
- **Roll Number Validation**: Ensures unique roll numbers
- **Responsive Design**: Works on desktop and mobile devices
- **SQLite Database**: Lightweight, file-based database

## Project Structure

```
StudentManagementSystem/
├── WEB-INF/
│   ├── web.xml                 # Web application configuration
│   ├── classes/                # Compiled Java classes
│   └── lib/                    # JAR dependencies
│       ├── sqlite-jdbc-3.44.1.0.jar
│       ├── servlet-api.jar
│       └── jstl-1.2.jar
├── src/
│   └── com/student/
│       ├── model/
│       │   └── Student.java    # Student entity class
│       ├── dao/
│       │   └── StudentDAO.java # Database access object
│       └── servlet/
│           └── StudentServlet.java # Main servlet controller
├── css/
│   └── style.css              # Stylesheet
├── index.jsp                  # Main page
├── student-list.jsp           # Student list page
├── student-form.jsp           # Add/Edit student form
├── build.sh                   # Linux build script
├── build.bat                  # Windows build script
└── README.md                  # This file
```

## Database Schema

The application uses SQLite with the following table structure:

```sql
CREATE TABLE students (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    roll TEXT UNIQUE NOT NULL,
    grade TEXT NOT NULL
);
```

## Requirements

- Java Development Kit (JDK) 8 or higher
- Apache Tomcat 8.5 or higher (or any servlet container)
- Web browser (Chrome, Firefox, Safari, Edge)

## Installation & Setup

### Quick Setup (Automatic)

1. **Clone or Download** this project
2. **Run the build script**:
   - On Linux/Mac: `./build.sh`
   - On Windows: `build.bat`
3. **Deploy to Tomcat**:
   - Copy the entire `StudentManagementSystem` folder to your Tomcat `webapps` directory
   - Start Tomcat server
4. **Access the application**:
   - Open browser and go to: `http://localhost:8080/StudentManagementSystem`

### Manual Setup

1. **Download Dependencies**:
   - SQLite JDBC Driver: [sqlite-jdbc-3.44.1.0.jar](https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar)
   - Servlet API: [javax.servlet-api-4.0.1.jar](https://repo1.maven.org/maven2/javax/servlet/javax.servlet-api/4.0.1/javax.servlet-api-4.0.1.jar)
   - JSTL: [jstl-1.2.jar](https://repo1.maven.org/maven2/javax/servlet/jstl/1.2/jstl-1.2.jar)

2. **Place JAR files** in `WEB-INF/lib/` directory

3. **Compile Java files**:
   ```bash
   javac -cp "WEB-INF/lib/*" -d "WEB-INF/classes" src/com/student/model/*.java src/com/student/dao/*.java src/com/student/servlet/*.java
   ```

4. **Deploy to Tomcat** as described above

## Usage

### Adding a New Student

1. Click "Add New Student" from the main page or student list
2. Fill in the form:
   - **Name**: Student's full name
   - **Roll Number**: Unique identifier (letters and numbers only)
   - **Grade**: Select from dropdown (A+ to F)
3. Click "Add Student"

### Viewing Students

- The student list displays all registered students in a table
- Shows ID, Name, Roll Number, Grade, and action buttons
- Students are sorted alphabetically by name

### Editing a Student

1. Click "Edit" button next to a student in the list
2. Modify the information in the form
3. Click "Update Student"

### Deleting a Student

1. Click "Delete" button next to a student in the list
2. Confirm the deletion in the popup dialog

## Technical Details

### Architecture

- **MVC Pattern**: Model-View-Controller architecture
- **Model**: `Student.java` - Data entity
- **View**: JSP pages for user interface
- **Controller**: `StudentServlet.java` - Handles all requests

### Database Operations

- **CRUD Operations**: Create, Read, Update, Delete
- **Connection Management**: Automatic connection handling
- **SQLite Integration**: File-based database (no server required)

### Security Features

- **SQL Injection Prevention**: Uses PreparedStatements
- **Input Validation**: Client and server-side validation
- **Unique Constraints**: Roll number uniqueness enforced

## Customization

### Adding New Fields

1. Update `Student.java` model class
2. Modify database table creation in `StudentDAO.java`
3. Update JSP forms and display pages
4. Adjust CSS styling as needed

### Changing Grades

Modify the grade options in `student-form.jsp`:

```html
<option value="A+">A+</option>
<option value="A">A</option>
<!-- Add or modify grade options -->
```

### Styling

- Edit `css/style.css` to customize the appearance
- Responsive design using CSS Grid and Flexbox
- Color scheme and animations can be modified

## Troubleshooting

### Common Issues

1. **Compilation Errors**:
   - Ensure all JAR files are in `WEB-INF/lib/`
   - Check Java version compatibility

2. **Database Connection Issues**:
   - Verify SQLite JDBC driver is loaded
   - Check file permissions for database creation

3. **Page Not Found (404)**:
   - Verify Tomcat is running
   - Check application deployment path
   - Ensure web.xml configuration is correct

4. **Styling Issues**:
   - Check CSS file path in JSP pages
   - Verify CSS file is accessible

### Debug Mode

Enable debug logging by adding this to your servlet:
```java
System.out.println("Debug: " + debugMessage);
```

## Browser Compatibility

- ✅ Chrome 60+
- ✅ Firefox 55+
- ✅ Safari 11+
- ✅ Edge 79+
- ✅ Mobile browsers

## Performance

- **Database**: SQLite provides excellent performance for small to medium datasets
- **Memory**: Minimal memory footprint
- **Concurrent Users**: Suitable for small teams/classrooms

## Future Enhancements

- User authentication and authorization
- File upload for student photos
- Export functionality (PDF, Excel)
- Advanced search and filtering
- Email notifications
- Backup and restore features

## License

This project is open source and available under the [MIT License](LICENSE).

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## Support

For issues and questions:
1. Check the troubleshooting section
2. Review the code comments
3. Create an issue in the repository

---

**Note**: This project was built based on the handwritten diagram specifications, implementing a complete student management system with Name, Roll, and Grade functionality as requested.