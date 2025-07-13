<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spacecraft Parameter Management System</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/custom.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container-fluid">
        <!-- Centered Header -->
        <header class="row header-section">
            <div class="col-12 text-center">
                <h1>Spacecraft Parameter Management System</h1>
            </div>
        </header>

        <!-- Top Row - Inputs (Horizontally Aligned) -->
        <div class="row top-inputs-row mb-4">
            <div class="col-md-4 mb-3">
                <label for="csvFile" class="form-label">Upload CSV:</label>
                <input type="file" class="form-control" id="csvFile" accept=".csv">
            </div>
            <div class="col-md-4 mb-3">
                <label for="spacecraftName" class="form-label">Spacecraft Name:</label>
                <input type="text" class="form-control" id="spacecraftName" autocomplete="off" placeholder="Enter spacecraft name">
            </div>
            <div class="col-md-4 mb-3">
                <label for="subsystemName" class="form-label">Subsystem Name:</label>
                <input type="text" class="form-control" id="subsystemName" autocomplete="off" placeholder="Enter subsystem name">
            </div>
        </div>

        <div class="row main-content">
            <!-- Left Sidebar -->
            <div class="col-md-3 sidebar">
                <div class="greeting">
                    <h3>Hello, User!</h3>
                </div>
                
                <div class="search-section mt-4">
                    <div class="form-group">
                        <label for="searchType">Search By:</label>
                        <select class="form-select" id="searchType">
                            <option value="spacecraft">Spacecraft Name</option>
                            <option value="subsystem">Subsystem Name</option>
                            <option value="pageId">Page ID</option>
                        </select>
                    </div>
                    <div class="form-group mt-2">
                        <input type="text" class="form-control" id="searchTerm" placeholder="Search...">
                    </div>
                    <button id="searchBtn" class="btn btn-info mt-2">Search</button>
                </div>
            </div>

            <!-- Main Content Area -->
            <div class="col-md-9 parameter-section">
                <!-- Page Info Display (Inside Parameter Table) -->
                <div class="page-info-container mb-3">
                    <div class="page-info">
                        <span class="page-label">Page No: </span><span id="pageNo">1</span> | 
                        <span class="page-label">Page ID: </span><span id="pageId">-</span>
                    </div>
                    <!-- Next/Previous Navigation Buttons -->
                    <div class="navigation-buttons">
                        <button id="prevBtn" class="btn btn-secondary">Previous</button>
                        <button id="nextBtn" class="btn btn-secondary">Next</button>
                    </div>
                </div>

                <!-- Parameter Grid -->
                <div class="parameter-grid">
                    <% for(int i = 1; i <= 38; i++) { %>
                        <div class="parameter-item">
                            <label for="param<%= i %>">Parameter <%= i %>:</label>
                            <input type="text" class="form-control" id="param<%= i %>" name="param<%= i %>">
                        </div>
                    <% } %>
                </div>

                <!-- Save and Delete Buttons -->
                <div class="action-buttons mt-4">
                    <button id="saveBtn" class="btn btn-primary btn-lg me-3">Save</button>
                    <button id="deleteBtn" class="btn btn-danger btn-lg me-3">Delete</button>
                    <button id="dashboardBtn" class="btn btn-success btn-lg" onclick="window.location.href='spacecraft-dashboard.jsp'">
                        <i class="fas fa-tachometer-alt"></i> Dashboard
                    </button>
                </div>
            </div>
        </div>

        <footer class="row">
            <div class="col-12 text-center">
                <p>Spacecraft Parameter Management System</p>
            </div>
        </footer>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="js/app.js"></script>
</body>
</html>
