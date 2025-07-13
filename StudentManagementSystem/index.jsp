<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Management System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Student Management System</h1>
            <p>Welcome to the Student Management Portal</p>
        </div>
        
        <div class="main-content">
            <div class="welcome-box">
                <h2>Manage Student Records</h2>
                <p>Keep track of student information including Name, Roll Number, and Grade.</p>
                
                <div class="action-buttons">
                    <a href="student?action=list" class="btn btn-primary">View All Students</a>
                    <a href="student?action=new" class="btn btn-secondary">Add New Student</a>
                </div>
            </div>
        </div>
        
        <div class="footer">
            <p>&copy; 2024 Student Management System</p>
        </div>
    </div>
</body>
</html>