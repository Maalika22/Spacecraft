package com.student.dao;

import com.student.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String DB_URL = "jdbc:sqlite:students.db";
    private static final String DB_DRIVER = "org.sqlite.JDBC";
    
    // Initialize database and create table
    public void initializeDatabase() {
        try {
            Class.forName(DB_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);
            
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS students (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    roll TEXT UNIQUE NOT NULL,
                    grade TEXT NOT NULL
                )
                """;
            
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSQL);
            
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Add a new student
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, roll, grade) VALUES (?, ?, ?)";
        
        try {
            Class.forName(DB_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getRoll());
            pstmt.setString(3, student.getGrade());
            
            int result = pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();
            
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY name";
        
        try {
            Class.forName(DB_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setRoll(rs.getString("roll"));
                student.setGrade(rs.getString("grade"));
                students.add(student);
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return students;
    }
    
    // Get student by ID
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = null;
        
        try {
            Class.forName(DB_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setRoll(rs.getString("roll"));
                student.setGrade(rs.getString("grade"));
            }
            
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return student;
    }
    
    // Update student
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, roll = ?, grade = ? WHERE id = ?";
        
        try {
            Class.forName(DB_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getRoll());
            pstmt.setString(3, student.getGrade());
            pstmt.setInt(4, student.getId());
            
            int result = pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();
            
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Delete student
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        
        try {
            Class.forName(DB_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            int result = pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();
            
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}