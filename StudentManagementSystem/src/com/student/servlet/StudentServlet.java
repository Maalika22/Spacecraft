package com.student.servlet;

import com.student.dao.StudentDAO;
import com.student.model.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO;
    
    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAO();
        studentDAO.initializeDatabase();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                listStudents(request, response);
                break;
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            default:
                listStudents(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("insert".equals(action)) {
            insertStudent(request, response);
        } else if ("update".equals(action)) {
            updateStudent(request, response);
        }
    }
    
    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Student> students = studentDAO.getAllStudents();
        request.setAttribute("students", students);
        request.getRequestDispatcher("student-list.jsp").forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("student-form.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.getStudentById(id);
        request.setAttribute("student", student);
        request.getRequestDispatcher("student-form.jsp").forward(request, response);
    }
    
    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String roll = request.getParameter("roll");
        String grade = request.getParameter("grade");
        
        Student student = new Student(name, roll, grade);
        
        if (studentDAO.addStudent(student)) {
            request.setAttribute("message", "Student added successfully!");
        } else {
            request.setAttribute("error", "Failed to add student. Roll number might already exist.");
        }
        
        response.sendRedirect("student?action=list");
    }
    
    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String roll = request.getParameter("roll");
        String grade = request.getParameter("grade");
        
        Student student = new Student(id, name, roll, grade);
        
        if (studentDAO.updateStudent(student)) {
            request.setAttribute("message", "Student updated successfully!");
        } else {
            request.setAttribute("error", "Failed to update student.");
        }
        
        response.sendRedirect("student?action=list");
    }
    
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        if (studentDAO.deleteStudent(id)) {
            request.setAttribute("message", "Student deleted successfully!");
        } else {
            request.setAttribute("error", "Failed to delete student.");
        }
        
        response.sendRedirect("student?action=list");
    }
}