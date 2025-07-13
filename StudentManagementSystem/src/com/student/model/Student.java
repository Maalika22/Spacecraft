package com.student.model;

public class Student {
    private int id;
    private String name;
    private String roll;
    private String grade;
    
    // Default constructor
    public Student() {
    }
    
    // Constructor with parameters
    public Student(String name, String roll, String grade) {
        this.name = name;
        this.roll = roll;
        this.grade = grade;
    }
    
    // Constructor with all fields
    public Student(int id, String name, String roll, String grade) {
        this.id = id;
        this.name = name;
        this.roll = roll;
        this.grade = grade;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getRoll() {
        return roll;
    }
    
    public void setRoll(String roll) {
        this.roll = roll;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roll='" + roll + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}