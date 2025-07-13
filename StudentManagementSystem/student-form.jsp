<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${student != null}">Edit Student</c:when>
            <c:otherwise>Add New Student</c:otherwise>
        </c:choose>
        - Student Management System
    </title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Student Management System</h1>
            <nav>
                <a href="index.jsp">Home</a>
                <a href="student?action=list">Student List</a>
                <a href="student?action=new">Add Student</a>
            </nav>
        </div>
        
        <div class="main-content">
            <div class="form-container">
                <h2>
                    <c:choose>
                        <c:when test="${student != null}">Edit Student</c:when>
                        <c:otherwise>Add New Student</c:otherwise>
                    </c:choose>
                </h2>
                
                <form action="student" method="post" class="student-form">
                    <c:choose>
                        <c:when test="${student != null}">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="${student.id}">
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="action" value="insert">
                        </c:otherwise>
                    </c:choose>
                    
                    <div class="form-group">
                        <label for="name">Student Name:</label>
                        <input type="text" 
                               id="name" 
                               name="name" 
                               value="${student.name}" 
                               required 
                               placeholder="Enter student's full name">
                    </div>
                    
                    <div class="form-group">
                        <label for="roll">Roll Number:</label>
                        <input type="text" 
                               id="roll" 
                               name="roll" 
                               value="${student.roll}" 
                               required 
                               placeholder="Enter roll number (e.g., 2024001)">
                    </div>
                    
                    <div class="form-group">
                        <label for="grade">Grade:</label>
                        <select id="grade" name="grade" required>
                            <option value="">Select Grade</option>
                            <option value="A+" ${student.grade == 'A+' ? 'selected' : ''}>A+</option>
                            <option value="A" ${student.grade == 'A' ? 'selected' : ''}>A</option>
                            <option value="A-" ${student.grade == 'A-' ? 'selected' : ''}>A-</option>
                            <option value="B+" ${student.grade == 'B+' ? 'selected' : ''}>B+</option>
                            <option value="B" ${student.grade == 'B' ? 'selected' : ''}>B</option>
                            <option value="B-" ${student.grade == 'B-' ? 'selected' : ''}>B-</option>
                            <option value="C+" ${student.grade == 'C+' ? 'selected' : ''}>C+</option>
                            <option value="C" ${student.grade == 'C' ? 'selected' : ''}>C</option>
                            <option value="C-" ${student.grade == 'C-' ? 'selected' : ''}>C-</option>
                            <option value="D" ${student.grade == 'D' ? 'selected' : ''}>D</option>
                            <option value="F" ${student.grade == 'F' ? 'selected' : ''}>F</option>
                        </select>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <c:choose>
                                <c:when test="${student != null}">Update Student</c:when>
                                <c:otherwise>Add Student</c:otherwise>
                            </c:choose>
                        </button>
                        <a href="student?action=list" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
        
        <div class="footer">
            <p>&copy; 2024 Student Management System</p>
        </div>
    </div>
    
    <script>
        // Form validation
        document.querySelector('.student-form').addEventListener('submit', function(e) {
            const name = document.getElementById('name').value.trim();
            const roll = document.getElementById('roll').value.trim();
            const grade = document.getElementById('grade').value;
            
            if (!name || !roll || !grade) {
                e.preventDefault();
                alert('Please fill in all fields.');
                return false;
            }
            
            // Validate roll number format (basic validation)
            if (!/^[A-Za-z0-9]+$/.test(roll)) {
                e.preventDefault();
                alert('Roll number should contain only letters and numbers.');
                return false;
            }
        });
    </script>
</body>
</html>