/**
 * Spacecraft Dashboard JavaScript
 * Handles real-time data updates, mission timer, and interactive controls
 */

$(document).ready(function() {
    // Initialize dashboard
    initializeDashboard();
    
    // Start mission timer
    startMissionTimer();
    
    // Start real-time data updates
    startDataUpdates();
    
    // Setup control panel event handlers
    setupControlPanel();
});

// Global variables for mission tracking
let missionStartTime = new Date();
let missionTimer;
let dataUpdateInterval;
let alertCounter = 0;

// Spacecraft data simulation
let spacecraftData = {
    altitude: 423.7,
    velocity: 7.66,
    orbitalPeriod: 92.8,
    inclination: 51.6,
    latitude: -12.45,
    longitude: 167.23,
    heading: 245.7,
    groundTrack: "NOMINAL",
    batteryLevel: 87,
    powerConsumption: 1.47,
    solarArray: 2.15,
    cabinTemp: 22.4,
    cabinPressure: 101.3,
    o2Level: 20.9,
    co2Level: 0.04,
    signalStrength: -78,
    dataRate: 2.4,
    nextPass: "14:32:15",
    groundStation: "MADRID"
};

// System status tracking
let systemStatus = {
    overall: "NOMINAL",
    propulsion: "ONLINE",
    lifeSupport: "NOMINAL",
    communication: "ACTIVE",
    navigation: "LOCKED",
    thermal: "MONITOR"
};

function initializeDashboard() {
    console.log("Spacecraft Dashboard Initializing...");
    
    // Update all display values
    updateAllDisplays();
    
    // Initialize system status
    updateSystemStatus();
    
    console.log("Dashboard Ready - All Systems Nominal");
}

function startMissionTimer() {
    let missionDuration = 0;
    
    missionTimer = setInterval(function() {
        missionDuration++;
        
        const hours = Math.floor(missionDuration / 3600);
        const minutes = Math.floor((missionDuration % 3600) / 60);
        const seconds = missionDuration % 60;
        
        const timeString = 
            String(hours).padStart(2, '0') + ':' +
            String(minutes).padStart(2, '0') + ':' +
            String(seconds).padStart(2, '0');
        
        $('#missionTime').text(timeString);
    }, 1000);
}

function startDataUpdates() {
    dataUpdateInterval = setInterval(function() {
        simulateDataChanges();
        updateAllDisplays();
        checkSystemAlerts();
    }, 2000); // Update every 2 seconds
}

function simulateDataChanges() {
    // Simulate realistic spacecraft parameter changes
    
    // Orbital mechanics (small variations)
    spacecraftData.altitude += (Math.random() - 0.5) * 0.5;
    spacecraftData.velocity += (Math.random() - 0.5) * 0.01;
    spacecraftData.latitude += (Math.random() - 0.5) * 0.1;
    spacecraftData.longitude += (Math.random() - 0.5) * 0.1;
    spacecraftData.heading += (Math.random() - 0.5) * 2;
    
    // Power system variations
    spacecraftData.batteryLevel += (Math.random() - 0.5) * 0.5;
    spacecraftData.powerConsumption += (Math.random() - 0.5) * 0.05;
    spacecraftData.solarArray += (Math.random() - 0.5) * 0.1;
    
    // Environmental system variations
    spacecraftData.cabinTemp += (Math.random() - 0.5) * 0.2;
    spacecraftData.cabinPressure += (Math.random() - 0.5) * 0.5;
    spacecraftData.o2Level += (Math.random() - 0.5) * 0.1;
    spacecraftData.co2Level += (Math.random() - 0.5) * 0.005;
    
    // Communication system variations
    spacecraftData.signalStrength += (Math.random() - 0.5) * 2;
    spacecraftData.dataRate += (Math.random() - 0.5) * 0.1;
    
    // Keep values within realistic bounds
    spacecraftData.altitude = Math.max(400, Math.min(450, spacecraftData.altitude));
    spacecraftData.velocity = Math.max(7.5, Math.min(7.8, spacecraftData.velocity));
    spacecraftData.batteryLevel = Math.max(70, Math.min(100, spacecraftData.batteryLevel));
    spacecraftData.powerConsumption = Math.max(1.0, Math.min(2.0, spacecraftData.powerConsumption));
    spacecraftData.solarArray = Math.max(1.8, Math.min(2.5, spacecraftData.solarArray));
    spacecraftData.cabinTemp = Math.max(20, Math.min(25, spacecraftData.cabinTemp));
    spacecraftData.cabinPressure = Math.max(95, Math.min(105, spacecraftData.cabinPressure));
    spacecraftData.o2Level = Math.max(20, Math.min(22, spacecraftData.o2Level));
    spacecraftData.co2Level = Math.max(0.02, Math.min(0.08, spacecraftData.co2Level));
    spacecraftData.signalStrength = Math.max(-85, Math.min(-70, spacecraftData.signalStrength));
    spacecraftData.dataRate = Math.max(2.0, Math.min(3.0, spacecraftData.dataRate));
    
    // Ensure heading stays within 0-360 degrees
    if (spacecraftData.heading < 0) spacecraftData.heading += 360;
    if (spacecraftData.heading >= 360) spacecraftData.heading -= 360;
    
    // Ensure latitude/longitude stay within valid ranges
    spacecraftData.latitude = Math.max(-90, Math.min(90, spacecraftData.latitude));
    spacecraftData.longitude = Math.max(-180, Math.min(180, spacecraftData.longitude));
}

function updateAllDisplays() {
    // Flight Dynamics
    $('#altitude').text(spacecraftData.altitude.toFixed(1));
    $('#velocity').text(spacecraftData.velocity.toFixed(2));
    $('#orbitalPeriod').text(spacecraftData.orbitalPeriod.toFixed(1));
    $('#inclination').text(spacecraftData.inclination.toFixed(1));
    
    // Navigation
    $('#latitude').text(spacecraftData.latitude.toFixed(2));
    $('#longitude').text(spacecraftData.longitude.toFixed(2));
    $('#heading').text(spacecraftData.heading.toFixed(1));
    $('#groundTrack').text(spacecraftData.groundTrack);
    
    // Power Management
    const batteryLevel = Math.round(spacecraftData.batteryLevel);
    $('#batteryGauge').css('width', batteryLevel + '%');
    $('.gauge-value').text(batteryLevel + '%');
    $('#powerConsumption').text(spacecraftData.powerConsumption.toFixed(2));
    $('#solarArray').text(spacecraftData.solarArray.toFixed(2));
    
    // Environmental
    $('#cabinTemp').text(spacecraftData.cabinTemp.toFixed(1));
    $('#cabinPressure').text(spacecraftData.cabinPressure.toFixed(1));
    $('#o2Level').text(spacecraftData.o2Level.toFixed(1));
    $('#co2Level').text(spacecraftData.co2Level.toFixed(2));
    
    // Communications
    $('#signalStrength').text(spacecraftData.signalStrength.toFixed(0));
    $('#dataRate').text(spacecraftData.dataRate.toFixed(1));
    $('#nextPass').text(spacecraftData.nextPass);
    $('#groundStation').text(spacecraftData.groundStation);
}

function updateSystemStatus() {
    // Update individual system status indicators
    $('.system-item').each(function() {
        const systemName = $(this).find('.system-name').text().toLowerCase().replace(' ', '');
        const statusElement = $(this).find('.status-indicator');
        
        if (systemStatus[systemName]) {
            statusElement.text(systemStatus[systemName]);
            
            // Update status indicator classes
            statusElement.removeClass('status-active status-warning status-critical');
            
            switch(systemStatus[systemName]) {
                case 'ONLINE':
                case 'NOMINAL':
                case 'ACTIVE':
                case 'LOCKED':
                    statusElement.addClass('status-active');
                    break;
                case 'MONITOR':
                case 'CAUTION':
                    statusElement.addClass('status-warning');
                    break;
                case 'OFFLINE':
                case 'CRITICAL':
                case 'ERROR':
                    statusElement.addClass('status-critical');
                    break;
            }
        }
    });
    
    // Update overall system status
    $('#systemStatus').text('SYSTEMS ' + systemStatus.overall);
}

function checkSystemAlerts() {
    const alerts = [];
    
    // Check for critical conditions
    if (spacecraftData.batteryLevel < 75) {
        alerts.push({
            level: 'warning',
            message: 'Battery level below 75% - monitor power consumption'
        });
    }
    
    if (spacecraftData.cabinTemp > 24 || spacecraftData.cabinTemp < 21) {
        alerts.push({
            level: 'warning',
            message: 'Cabin temperature outside nominal range - thermal system monitoring'
        });
        systemStatus.thermal = 'MONITOR';
    } else {
        systemStatus.thermal = 'NOMINAL';
    }
    
    if (spacecraftData.signalStrength < -82) {
        alerts.push({
            level: 'info',
            message: 'Signal strength degraded - adjusting antenna orientation'
        });
    }
    
    if (spacecraftData.co2Level > 0.06) {
        alerts.push({
            level: 'warning',
            message: 'CO2 levels elevated - activating scrubber system'
        });
    }
    
    // Add random operational alerts occasionally
    if (Math.random() < 0.1) { // 10% chance every update cycle
        const operationalAlerts = [
            { level: 'info', message: 'Routine system diagnostic completed successfully' },
            { level: 'info', message: 'Solar array auto-tracking adjustment performed' },
            { level: 'success', message: 'Data downlink to ground station completed' },
            { level: 'info', message: 'Attitude control system micro-adjustment' }
        ];
        
        const randomAlert = operationalAlerts[Math.floor(Math.random() * operationalAlerts.length)];
        alerts.push(randomAlert);
    }
    
    // Add new alerts to the display
    alerts.forEach(alert => {
        addAlert(alert.level, alert.message);
    });
    
    // Update system status display
    updateSystemStatus();
}

function addAlert(level, message) {
    const currentTime = new Date();
    const timeString = String(currentTime.getHours()).padStart(2, '0') + ':' +
                      String(currentTime.getMinutes()).padStart(2, '0') + ':' +
                      String(currentTime.getSeconds()).padStart(2, '0');
    
    const alertHtml = `
        <div class="alert-item alert-${level}">
            <span class="alert-time">${timeString}</span>
            <span class="alert-message">${message}</span>
        </div>
    `;
    
    $('#alertsContainer').prepend(alertHtml);
    
    // Keep only the last 10 alerts
    const alerts = $('#alertsContainer .alert-item');
    if (alerts.length > 10) {
        alerts.slice(10).remove();
    }
    
    alertCounter++;
}

function setupControlPanel() {
    // Emergency Stop button
    $('#emergencyStop').click(function() {
        if (confirm('WARNING: This will initiate emergency stop procedures. Are you sure?')) {
            addAlert('critical', 'EMERGENCY STOP INITIATED - All non-essential systems shutting down');
            systemStatus.overall = 'EMERGENCY';
            updateSystemStatus();
            
            // Simulate emergency shutdown
            setTimeout(function() {
                addAlert('warning', 'Emergency stop completed - Manual restart required');
                systemStatus.overall = 'STANDBY';
                updateSystemStatus();
            }, 3000);
        }
    });
    
    // System Reset button
    $('#systemReset').click(function() {
        if (confirm('This will reset all spacecraft systems. Continue?')) {
            addAlert('info', 'System reset initiated - Restarting all subsystems');
            
            // Simulate system reset
            setTimeout(function() {
                systemStatus = {
                    overall: "NOMINAL",
                    propulsion: "ONLINE",
                    lifeSupport: "NOMINAL",
                    communication: "ACTIVE",
                    navigation: "LOCKED",
                    thermal: "NOMINAL"
                };
                updateSystemStatus();
                addAlert('success', 'System reset completed - All systems nominal');
            }, 2000);
        }
    });
    
    // Data Export button
    $('#dataExport').click(function() {
        addAlert('info', 'Telemetry data export initiated');
        
        // Create downloadable data
        const exportData = {
            timestamp: new Date().toISOString(),
            missionTime: $('#missionTime').text(),
            spacecraftData: spacecraftData,
            systemStatus: systemStatus
        };
        
        const dataStr = JSON.stringify(exportData, null, 2);
        const dataBlob = new Blob([dataStr], {type: 'application/json'});
        const url = URL.createObjectURL(dataBlob);
        
        const link = document.createElement('a');
        link.href = url;
        link.download = 'spacecraft_telemetry_' + new Date().getTime() + '.json';
        link.click();
        
        URL.revokeObjectURL(url);
        addAlert('success', 'Telemetry data exported successfully');
    });
}

// Cleanup function when page is unloaded
$(window).on('beforeunload', function() {
    if (missionTimer) {
        clearInterval(missionTimer);
    }
    if (dataUpdateInterval) {
        clearInterval(dataUpdateInterval);
    }
});

// Add keyboard shortcuts for quick actions
$(document).keydown(function(e) {
    // Ctrl+E for Emergency Stop
    if (e.ctrlKey && e.key === 'e') {
        e.preventDefault();
        $('#emergencyStop').click();
    }
    
    // Ctrl+R for System Reset
    if (e.ctrlKey && e.key === 'r') {
        e.preventDefault();
        $('#systemReset').click();
    }
    
    // Ctrl+D for Data Export
    if (e.ctrlKey && e.key === 'd') {
        e.preventDefault();
        $('#dataExport').click();
    }
});

// Console logging for debugging
console.log('Spacecraft Dashboard JavaScript Loaded');
console.log('Available keyboard shortcuts:');
console.log('  Ctrl+E: Emergency Stop');
console.log('  Ctrl+R: System Reset');
console.log('  Ctrl+D: Data Export');