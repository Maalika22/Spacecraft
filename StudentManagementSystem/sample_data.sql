-- Sample data for Student Management System
-- This file can be used to populate the database with test data

-- Create table (already handled by the application)
CREATE TABLE IF NOT EXISTS students (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    roll TEXT UNIQUE NOT NULL,
    grade TEXT NOT NULL
);

-- Insert sample student records
INSERT OR IGNORE INTO students (name, roll, grade) VALUES
('John Smith', '2024001', 'A'),
('Emily Johnson', '2024002', 'A+'),
('Michael Brown', '2024003', 'B+'),
('Sarah Davis', '2024004', 'A-'),
('David Wilson', '2024005', 'B'),
('Lisa Miller', '2024006', 'A+'),
('James Moore', '2024007', 'B-'),
('Jennifer Taylor', '2024008', 'A'),
('Robert Anderson', '2024009', 'C+'),
('Amanda Thomas', '2024010', 'B+'),
('Christopher Jackson', '2024011', 'A-'),
('Jessica White', '2024012', 'A'),
('Matthew Harris', '2024013', 'B'),
('Ashley Martin', '2024014', 'A+'),
('Daniel Thompson', '2024015', 'B-');

-- Verify data insertion
SELECT COUNT(*) as total_students FROM students;
SELECT * FROM students ORDER BY name;