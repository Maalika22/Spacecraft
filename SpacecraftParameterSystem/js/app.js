$(document).ready(function() {
    // Global variables
    let currentPage = 1;
    let currentPageId = '';
    let currentSpacecraftName = '';
    let currentSubsystemName = '';
    
    // Initialize the application
    initApp();
    
    // Initialize autocomplete for spacecraft and subsystem fields
    initAutocomplete();
    
    // Attach event handlers
    attachEventHandlers();
    
    /**
     * Initialize the application
     */
    function initApp() {
        // Clear all form fields
        clearForm();
        
        // Set default page number
        updatePageNumber(1);
        
        // Load any required initial data
        loadInitialData();
    }
    
    /**
     * Initialize autocomplete functionality
     */
    function initAutocomplete() {
        // Simple autocomplete for spacecraft names
        setupSimpleAutocomplete("#spacecraftName", "spacecraft");
        
        // Simple autocomplete for subsystem names
        setupSimpleAutocomplete("#subsystemName", "subsystem");
    }
    
    /**
     * Setup simple autocomplete without jQuery UI
     */
    function setupSimpleAutocomplete(selector, type) {
        const input = $(selector);
        const listId = selector.replace("#", "") + "_list";
        
        // Create datalist element for autocomplete
        if ($("#" + listId).length === 0) {
            $("body").append('<datalist id="' + listId + '"></datalist>');
            input.attr("list", listId);
        }
        
        // Load autocomplete data
        loadAutocompleteData(type, listId);
        
        // Also setup input event for dynamic loading
        input.on("input", function() {
            if ($(this).val().length >= 1) {
                loadAutocompleteData(type, listId);
            }
        });
    }
    
    /**
     * Load autocomplete data from server
     */
    function loadAutocompleteData(type, listId) {
        $.ajax({
            url: "autocomplete",
            dataType: "json",
            data: {
                type: type
            },
            success: function(data) {
                const datalist = $("#" + listId);
                datalist.empty();
                
                // Add default data if server returns empty
                if (!data || data.length === 0) {
                    if (type === "spacecraft") {
                        data = [
                            "Aryabhata", "Rohini", "INSAT series", "Chandrayaan series", 
                            "Mars Orbiter Mission (MOM)", "AstroSat", "RISAT series", 
                            "Oceansat series", "GSAT series", "Aditya-L1"
                        ];
                    } else if (type === "subsystem") {
                        data = [
                            "power", "thermal", "attitude and orbit control", "propulsion", 
                            "communication", "data handling", "payload subsystems"
                        ];
                    }
                }
                
                // Populate datalist
                data.forEach(function(item) {
                    datalist.append('<option value="' + item + '">');
                });
            },
            error: function(xhr, status, error) {
                console.error("Error loading autocomplete data for " + type + ": " + error);
                
                // Fallback to default data
                const datalist = $("#" + listId);
                datalist.empty();
                
                let defaultData = [];
                if (type === "spacecraft") {
                    defaultData = [
                        "Aryabhata", "Rohini", "INSAT series", "Chandrayaan series", 
                        "Mars Orbiter Mission (MOM)", "AstroSat", "RISAT series", 
                        "Oceansat series", "GSAT series", "Aditya-L1"
                    ];
                } else if (type === "subsystem") {
                    defaultData = [
                        "power", "thermal", "attitude and orbit control", "propulsion", 
                        "communication", "data handling", "payload subsystems"
                    ];
                }
                
                defaultData.forEach(function(item) {
                    datalist.append('<option value="' + item + '">');
                });
            }
        });
    }
    
    /**
     * Attach event handlers to elements
     */
    function attachEventHandlers() {
        // Save button
        $("#saveBtn").click(function() {
            saveParameter();
        });
        
        // Delete button
        $("#deleteBtn").click(function() {
            deleteParameter();
        });
        
        // Search button
        $("#searchBtn").click(function() {
            searchParameter();
        });
        
        // Previous button
        $("#prevBtn").click(function() {
            navigateToPage(-1);
        });
        
        // Next button
        $("#nextBtn").click(function() {
            navigateToPage(1);
        });
        
        // CSV file upload
        $("#csvFile").change(function() {
            uploadCSV();
        });
    }
    
    /**
     * Save parameter data
     */
    function saveParameter() {
        // Validate required fields
        const spacecraftName = $("#spacecraftName").val().trim();
        const subsystemName = $("#subsystemName").val().trim();
        
        if (!spacecraftName || !subsystemName) {
            console.error("Spacecraft name and subsystem name are required");
            return;
        }
        
        // Collect form data
        const formData = {
            pageId: currentPageId,
            pageNo: currentPage,
            spacecraftName: spacecraftName,
            subsystemName: subsystemName
        };
        
        // Add all parameter values
        for (let i = 1; i <= 38; i++) {
            formData["param" + i] = $("#param" + i).val();
        }
        
        // Send AJAX request
        $.ajax({
            url: "save",
            type: "POST",
            data: formData,
            dataType: "json",
            success: function(response) {
                if (response.success) {
                    // Update page ID
                    currentPageId = response.pageId;
                    $("#pageId").text(currentPageId);
                    
                    // Update current values
                    currentSpacecraftName = spacecraftName;
                    currentSubsystemName = subsystemName;
                    
                    console.log("Parameter saved successfully");
                } else {
                    console.error("Failed to save parameter: " + response.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("Error saving parameter: " + error);
            }
        });
    }
    
    /**
     * Delete parameter data
     */
    function deleteParameter() {
        // Validate required fields
        if (!currentPageId || !currentSpacecraftName || !currentSubsystemName) {
            console.error("No parameter is currently loaded to delete");
            return;
        }
        
        // Send AJAX request
        $.ajax({
            url: "delete",
            type: "POST",
            data: {
                pageId: currentPageId,
                spacecraftName: currentSpacecraftName,
                subsystemName: currentSubsystemName
            },
            dataType: "json",
            success: function(response) {
                if (response.success) {
                    // Clear form after successful deletion
                    clearForm();
                    updatePageNumber(1);
                    console.log("Parameter deleted successfully");
                } else {
                    console.error("Failed to delete parameter: " + response.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("Error deleting parameter: " + error);
            }
        });
    }
    
    /**
     * Search for parameters
     */
    function searchParameter() {
        const searchType = $("#searchType").val();
        const searchTerm = $("#searchTerm").val().trim();
        
        if (!searchTerm) {
            console.error("Search term is required");
            return;
        }
        
        // Send AJAX request
        $.ajax({
            url: "search",
            type: "GET",
            data: {
                type: searchType,
                term: searchTerm
            },
            dataType: "json",
            success: function(response) {
                if (response.success && response.data && response.data.length > 0) {
                    // Load the first result
                    loadParameterData(response.data[0]);
                    console.log("Parameter search successful");
                } else {
                    console.error("No parameters found for the search criteria");
                }
            },
            error: function(xhr, status, error) {
                console.error("Error searching parameter: " + error);
            }
        });
    }
    
    /**
     * Navigate to previous or next page
     */
    function navigateToPage(direction) {
        const newPage = currentPage + direction;
        
        if (newPage < 1) {
            console.error("Already on the first page");
            return;
        }
        
        // First save the current page
        saveParameter();
        
        // Then load the new page
        $.ajax({
            url: "search",
            type: "GET",
            data: {
                type: "pageId",
                term: "PAGE_" + newPage
            },
            dataType: "json",
            success: function(response) {
                if (response.success && response.data && response.data.length > 0) {
                    // Load the parameter data
                    loadParameterData(response.data[0]);
                } else {
                    // No page found, create a new one
                    clearForm();
                    updatePageNumber(newPage);
                }
            },
            error: function(xhr, status, error) {
                console.error("Error navigating to page: " + error);
                
                // If error, still update page number
                clearForm();
                updatePageNumber(newPage);
            }
        });
    }
    
    /**
     * Upload CSV file
     */
    function uploadCSV() {
        const fileInput = $("#csvFile")[0];
        
        if (fileInput.files.length === 0) {
            console.error("Please select a CSV file to upload");
            return;
        }
        
        const file = fileInput.files[0];
        if (!file.name.toLowerCase().endsWith(".csv")) {
            console.error("Please upload a CSV file");
            return;
        }
        
        const formData = new FormData();
        formData.append("csvFile", file);
        
        // Send AJAX request
        $.ajax({
            url: "upload",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            dataType: "json",
            success: function(response) {
                if (response.success) {
                    console.log("CSV uploaded successfully: " + response.message);
                    
                    // Reset file input
                    $("#csvFile").val("");
                } else {
                    console.error("Failed to upload CSV: " + response.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("Error uploading CSV: " + error);
            }
        });
    }
    /**
     * Load parameter data into the form
     */
    function loadParameterData(data) {
        // Set form fields
        $("#spacecraftName").val(data.spacecraftName);
        $("#subsystemName").val(data.subsystemName);
        
        // Set parameters
        for (let i = 1; i <= 38; i++) {
            $("#param" + i).val(data["param" + i] || "");
        }
        
        // Update global variables
        currentPageId = data.pageId;
        currentSpacecraftName = data.spacecraftName;
        currentSubsystemName = data.subsystemName;
        
        // Update page number
        if (data.pageNo) {
            updatePageNumber(data.pageNo);
        } else {
            // Extract page number from page ID if possible
            const pageMatch = data.pageId.match(/PAGE_(\d+)/);
            if (pageMatch) {
                updatePageNumber(parseInt(pageMatch[1]));
            } else {
                updatePageNumber(currentPage);
            }
        }
        
        // Update page ID display
        $("#pageId").text(currentPageId);
    }   $("#pageId").text(currentPageId);
    }
    
    /**
     * Update the page number
     */
    function updatePageNumber(newPage) {
        currentPage = newPage;
        $("#pageNo").text(currentPage);
    }
    
    /**
     * Clear all form fields
     */
    function clearForm() {
        // Clear input fields
        $("#spacecraftName").val("");
        $("#subsystemName").val("");
        
        // Clear parameters
        for (let i = 1; i <= 38; i++) {
            $("#param" + i).val("");
        }
        
        // Clear page ID
        currentPageId = "";
        $("#pageId").text("-");
        
        // Reset current values
        currentSpacecraftName = "";
        currentSubsystemName = "";
    }
    
    /**
     * Load initial data
     */
    function loadInitialData() {
        // Nothing to do here for now
        // This could be used to load any saved state or default values
    }
});
