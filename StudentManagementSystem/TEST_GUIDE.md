# Testing Guide - Student Management System

This guide provides comprehensive testing instructions to verify all functionality of the Student Management System.

## Pre-Testing Setup

### 1. Build and Deploy
```bash
# Build the project
./build.sh

# Deploy to Tomcat
cp -r StudentManagementSystem $TOMCAT_HOME/webapps/

# Start Tomcat
$TOMCAT_HOME/bin/startup.sh
```

### 2. Access Application
- URL: http://localhost:8080/StudentManagementSystem
- Expected: Homepage loads with welcome message

## Functional Testing

### Test Case 1: Homepage Navigation
**Objective**: Verify homepage loads and navigation works

**Steps**:
1. Open http://localhost:8080/StudentManagementSystem
2. Verify page title: "Student Management System"
3. Click "View All Students"
4. Click "Add New Student"
5. Navigate back to home

**Expected Results**:
- Homepage displays welcome message
- Navigation buttons work correctly
- All pages load without errors

### Test Case 2: Add New Student
**Objective**: Test student creation functionality

**Steps**:
1. Navigate to "Add New Student"
2. Fill form with test data:
   - Name: "John Doe"
   - Roll: "TEST001"
   - Grade: "A"
3. Click "Add Student"
4. Verify redirect to student list
5. Confirm student appears in list

**Expected Results**:
- Form accepts valid input
- Student is created successfully
- Student appears in the list
- Success feedback is shown

### Test Case 3: View All Students
**Objective**: Test student list display

**Steps**:
1. Navigate to "Student List"
2. Verify table headers: ID, Name, Roll, Grade, Actions
3. Check if students are displayed
4. Verify sorting (alphabetical by name)

**Expected Results**:
- Table displays all students
- Headers are correctly labeled
- Data is properly formatted
- Grade badges are styled correctly

### Test Case 4: Edit Student
**Objective**: Test student update functionality

**Steps**:
1. From student list, click "Edit" for any student
2. Modify student information:
   - Change name to "Jane Doe"
   - Change grade to "A+"
3. Click "Update Student"
4. Verify changes are reflected in the list

**Expected Results**:
- Edit form pre-populates with existing data
- Changes are saved successfully
- Updated information appears in list
- Success feedback is shown

### Test Case 5: Delete Student
**Objective**: Test student deletion functionality

**Steps**:
1. From student list, click "Delete" for any student
2. Confirm deletion in popup dialog
3. Verify student is removed from list

**Expected Results**:
- Confirmation dialog appears
- Student is deleted upon confirmation
- Student no longer appears in list
- Success feedback is shown

## Data Validation Testing

### Test Case 6: Form Validation
**Objective**: Test input validation

**Test Data**:
```
Valid Inputs:
- Name: "Alice Johnson"
- Roll: "STU123"
- Grade: "B+"

Invalid Inputs:
- Name: "" (empty)
- Roll: "" (empty)
- Grade: "" (not selected)
- Roll: "TEST@#$" (special characters)
```

**Steps**:
1. Try submitting form with empty fields
2. Try submitting with invalid roll number
3. Submit with valid data

**Expected Results**:
- Empty fields show validation errors
- Invalid characters in roll number are rejected
- Valid data is accepted and processed

### Test Case 7: Duplicate Roll Number
**Objective**: Test unique constraint validation

**Steps**:
1. Add student with roll "DUP001"
2. Try adding another student with same roll "DUP001"
3. Verify error handling

**Expected Results**:
- First student is created successfully
- Second attempt shows error message
- Database maintains data integrity

## User Interface Testing

### Test Case 8: Responsive Design
**Objective**: Test mobile and desktop layouts

**Steps**:
1. Test on desktop browser (1920x1080)
2. Test on tablet simulation (768x1024)
3. Test on mobile simulation (375x667)
4. Verify all elements are accessible

**Expected Results**:
- Layout adapts to different screen sizes
- All buttons and links are clickable
- Text remains readable
- Navigation remains functional

### Test Case 9: Browser Compatibility
**Objective**: Test cross-browser functionality

**Browsers to Test**:
- Chrome (latest)
- Firefox (latest)
- Safari (if available)
- Edge (latest)

**Expected Results**:
- All functionality works across browsers
- Styling appears consistent
- No JavaScript errors in console

## Database Testing

### Test Case 10: Database Operations
**Objective**: Verify database persistence

**Steps**:
1. Add several students
2. Restart Tomcat server
3. Access application again
4. Verify data persists

**Expected Results**:
- Data survives server restart
- Database file is created (students.db)
- All CRUD operations work correctly

### Test Case 11: Database Integrity
**Objective**: Test data integrity constraints

**Tests**:
- Primary key auto-increment
- Unique roll number constraint
- NOT NULL constraints
- Data type validation

**Expected Results**:
- All constraints are enforced
- Appropriate error messages for violations
- Data remains consistent

## Performance Testing

### Test Case 12: Load Testing
**Objective**: Test with multiple students

**Steps**:
1. Add 50+ students using the form
2. Navigate through student list
3. Perform edit/delete operations
4. Monitor response times

**Expected Results**:
- Application remains responsive
- Page load times under 2 seconds
- No memory leaks or crashes

## Error Handling Testing

### Test Case 13: Error Pages
**Objective**: Test error handling

**Tests**:
1. Access non-existent URL (404 error)
2. Cause server error (500 error)
3. Test database connection failure

**Expected Results**:
- Appropriate error messages displayed
- User can navigate back to working pages
- Application doesn't crash

## Security Testing

### Test Case 14: Input Security
**Objective**: Test against common attacks

**Test Inputs**:
```
SQL Injection:
- Name: "'; DROP TABLE students; --"
- Roll: "1' OR '1'='1"

XSS:
- Name: "<script>alert('XSS')</script>"
- Roll: "<img src=x onerror=alert('XSS')>"
```

**Expected Results**:
- Malicious input is sanitized
- No SQL injection occurs
- XSS attempts are prevented
- Database remains intact

## Automated Testing Script

### Quick Test Script
```bash
#!/bin/bash
# quick_test.sh

echo "Starting Student Management System Tests..."

# Test 1: Check if application is accessible
echo "Test 1: Application Accessibility"
curl -s http://localhost:8080/StudentManagementSystem > /dev/null
if [ $? -eq 0 ]; then
    echo "✓ Application is accessible"
else
    echo "✗ Application is not accessible"
    exit 1
fi

# Test 2: Check if CSS loads
echo "Test 2: Static Resource Loading"
curl -s http://localhost:8080/StudentManagementSystem/css/style.css > /dev/null
if [ $? -eq 0 ]; then
    echo "✓ CSS file loads correctly"
else
    echo "✗ CSS file not loading"
fi

# Test 3: Check if servlet responds
echo "Test 3: Servlet Response"
curl -s http://localhost:8080/StudentManagementSystem/student?action=list > /dev/null
if [ $? -eq 0 ]; then
    echo "✓ Servlet responds correctly"
else
    echo "✗ Servlet not responding"
fi

echo "Basic tests completed."
```

## Test Data Cleanup

### Cleanup Script
```sql
-- Reset database for fresh testing
DELETE FROM students;
DELETE FROM sqlite_sequence WHERE name='students';

-- Optional: Drop and recreate table
DROP TABLE IF EXISTS students;
CREATE TABLE students (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    roll TEXT UNIQUE NOT NULL,
    grade TEXT NOT NULL
);
```

## Test Reporting

### Test Results Template
```
Test Execution Report
=====================
Date: [DATE]
Tester: [NAME]
Environment: [DETAILS]

Test Case Results:
- TC1: Homepage Navigation: [PASS/FAIL]
- TC2: Add New Student: [PASS/FAIL]
- TC3: View All Students: [PASS/FAIL]
- TC4: Edit Student: [PASS/FAIL]
- TC5: Delete Student: [PASS/FAIL]
- TC6: Form Validation: [PASS/FAIL]
- TC7: Duplicate Roll Number: [PASS/FAIL]
- TC8: Responsive Design: [PASS/FAIL]
- TC9: Browser Compatibility: [PASS/FAIL]
- TC10: Database Operations: [PASS/FAIL]
- TC11: Database Integrity: [PASS/FAIL]
- TC12: Load Testing: [PASS/FAIL]
- TC13: Error Pages: [PASS/FAIL]
- TC14: Input Security: [PASS/FAIL]

Issues Found:
[List any issues discovered]

Overall Status: [PASS/FAIL]
```

## Continuous Testing

### Regression Test Checklist
Before any code changes:
- [ ] Run all functional tests
- [ ] Verify database operations
- [ ] Check UI responsiveness
- [ ] Test error handling
- [ ] Validate security measures

---

**Note**: Always test in a development environment before deploying to production. Keep test data separate from production data.