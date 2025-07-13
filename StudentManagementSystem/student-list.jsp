<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student List - Student Management System</title>
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
            <h2>Student Records</h2>
            
            <div class="action-bar">
                <a href="student?action=new" class="btn btn-success">Add New Student</a>
            </div>
            
            <div class="table-container">
                <table class="student-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Roll</th>
                            <th>Grade</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty students}">
                                <tr>
                                    <td colspan="5" class="no-data">No students found. <a href="student?action=new">Add the first student</a></td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="student" items="${students}">
                                    <tr>
                                        <td>${student.id}</td>
                                        <td>${student.name}</td>
                                        <td>${student.roll}</td>
                                        <td><span class="grade-badge">${student.grade}</span></td>
                                        <td class="actions">
                                            <a href="student?action=edit&id=${student.id}" class="btn btn-edit">Edit</a>
                                            <a href="student?action=delete&id=${student.id}" 
                                               class="btn btn-delete" 
                                               onclick="return confirm('Are you sure you want to delete this student?')">Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
            
            <div class="summary">
                <p>Total Students: <strong>${students.size()}</strong></p>
            </div>
        </div>
        
        <div class="footer">
            <p>&copy; 2024 Student Management System</p>
        </div>
    </div>
</body>
</html>