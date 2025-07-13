<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spacecraft Dashboard - Mission Control</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/custom.css">
    <link rel="stylesheet" href="css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body class="dashboard-body">
    <div class="container-fluid dashboard-container">
        <!-- Header Section -->
        <header class="dashboard-header">
            <div class="row align-items-center">
                <div class="col-md-4">
                    <h1><i class="fas fa-rocket"></i> SPACECRAFT DASHBOARD</h1>
                </div>
                <div class="col-md-4 text-center">
                    <div class="mission-time">
                        <span class="time-label">MISSION TIME:</span>
                        <span id="missionTime">00:00:00</span>
                    </div>
                </div>
                <div class="col-md-4 text-end">
                    <div class="system-status">
                        <span class="status-indicator status-active" id="systemStatus">SYSTEMS NOMINAL</span>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Dashboard Grid -->
        <div class="dashboard-grid">
            <!-- Flight Dynamics Section -->
            <div class="dashboard-card flight-dynamics">
                <div class="card-header">
                    <h3><i class="fas fa-plane"></i> FLIGHT DYNAMICS</h3>
                </div>
                <div class="card-content">
                    <div class="metric-row">
                        <div class="metric">
                            <label>ALTITUDE</label>
                            <div class="value-display">
                                <span id="altitude">423.7</span>
                                <span class="unit">km</span>
                            </div>
                        </div>
                        <div class="metric">
                            <label>VELOCITY</label>
                            <div class="value-display">
                                <span id="velocity">7.66</span>
                                <span class="unit">km/s</span>
                            </div>
                        </div>
                    </div>
                    <div class="metric-row">
                        <div class="metric">
                            <label>ORBITAL PERIOD</label>
                            <div class="value-display">
                                <span id="orbitalPeriod">92.8</span>
                                <span class="unit">min</span>
                            </div>
                        </div>
                        <div class="metric">
                            <label>INCLINATION</label>
                            <div class="value-display">
                                <span id="inclination">51.6</span>
                                <span class="unit">°</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Navigation Section -->
            <div class="dashboard-card navigation">
                <div class="card-header">
                    <h3><i class="fas fa-compass"></i> NAVIGATION</h3>
                </div>
                <div class="card-content">
                    <div class="metric-row">
                        <div class="metric">
                            <label>LATITUDE</label>
                            <div class="value-display">
                                <span id="latitude">-12.45</span>
                                <span class="unit">°</span>
                            </div>
                        </div>
                        <div class="metric">
                            <label>LONGITUDE</label>
                            <div class="value-display">
                                <span id="longitude">167.23</span>
                                <span class="unit">°</span>
                            </div>
                        </div>
                    </div>
                    <div class="metric-row">
                        <div class="metric">
                            <label>HEADING</label>
                            <div class="value-display">
                                <span id="heading">245.7</span>
                                <span class="unit">°</span>
                            </div>
                        </div>
                        <div class="metric">
                            <label>GROUND TRACK</label>
                            <div class="value-display">
                                <span id="groundTrack">NOMINAL</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Systems Status Section -->
            <div class="dashboard-card systems-status">
                <div class="card-header">
                    <h3><i class="fas fa-cogs"></i> SYSTEMS STATUS</h3>
                </div>
                <div class="card-content">
                    <div class="system-item">
                        <span class="system-name">PROPULSION</span>
                        <span class="status-indicator status-active">ONLINE</span>
                    </div>
                    <div class="system-item">
                        <span class="system-name">LIFE SUPPORT</span>
                        <span class="status-indicator status-active">NOMINAL</span>
                    </div>
                    <div class="system-item">
                        <span class="system-name">COMMUNICATION</span>
                        <span class="status-indicator status-active">ACTIVE</span>
                    </div>
                    <div class="system-item">
                        <span class="system-name">NAVIGATION</span>
                        <span class="status-indicator status-active">LOCKED</span>
                    </div>
                    <div class="system-item">
                        <span class="system-name">THERMAL</span>
                        <span class="status-indicator status-warning">MONITOR</span>
                    </div>
                </div>
            </div>

            <!-- Power Management Section -->
            <div class="dashboard-card power-management">
                <div class="card-header">
                    <h3><i class="fas fa-battery-full"></i> POWER MANAGEMENT</h3>
                </div>
                <div class="card-content">
                    <div class="power-gauge">
                        <div class="gauge-label">BATTERY LEVEL</div>
                        <div class="gauge-container">
                            <div class="gauge-bar">
                                <div class="gauge-fill" id="batteryGauge" style="width: 87%"></div>
                            </div>
                            <span class="gauge-value">87%</span>
                        </div>
                    </div>
                    <div class="metric-row">
                        <div class="metric">
                            <label>POWER CONSUMPTION</label>
                            <div class="value-display">
                                <span id="powerConsumption">1.47</span>
                                <span class="unit">kW</span>
                            </div>
                        </div>
                        <div class="metric">
                            <label>SOLAR ARRAY</label>
                            <div class="value-display">
                                <span id="solarArray">2.15</span>
                                <span class="unit">kW</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Environmental Controls Section -->
            <div class="dashboard-card environmental">
                <div class="card-header">
                    <h3><i class="fas fa-thermometer-half"></i> ENVIRONMENTAL</h3>
                </div>
                <div class="card-content">
                    <div class="metric-row">
                        <div class="metric">
                            <label>CABIN TEMP</label>
                            <div class="value-display">
                                <span id="cabinTemp">22.4</span>
                                <span class="unit">°C</span>
                            </div>
                        </div>
                        <div class="metric">
                            <label>CABIN PRESSURE</label>
                            <div class="value-display">
                                <span id="cabinPressure">101.3</span>
                                <span class="unit">kPa</span>
                            </div>
                        </div>
                    </div>
                    <div class="metric-row">
                        <div class="metric">
                            <label>O2 LEVEL</label>
                            <div class="value-display">
                                <span id="o2Level">20.9</span>
                                <span class="unit">%</span>
                            </div>
                        </div>
                        <div class="metric">
                            <label>CO2 LEVEL</label>
                            <div class="value-display">
                                <span id="co2Level">0.04</span>
                                <span class="unit">%</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Communications Section -->
            <div class="dashboard-card communications">
                <div class="card-header">
                    <h3><i class="fas fa-satellite-dish"></i> COMMUNICATIONS</h3>
                </div>
                <div class="card-content">
                    <div class="metric-row">
                        <div class="metric">
                            <label>SIGNAL STRENGTH</label>
                            <div class="value-display">
                                <span id="signalStrength">-78</span>
                                <span class="unit">dBm</span>
                            </div>
                        </div>
                        <div class="metric">
                            <label>DATA RATE</label>
                            <div class="value-display">
                                <span id="dataRate">2.4</span>
                                <span class="unit">Mbps</span>
                            </div>
                        </div>
                    </div>
                    <div class="metric-row">
                        <div class="metric">
                            <label>NEXT PASS</label>
                            <div class="value-display">
                                <span id="nextPass">14:32:15</span>
                            </div>
                        </div>
                        <div class="metric">
                            <label>GROUND STATION</label>
                            <div class="value-display">
                                <span id="groundStation">MADRID</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Alerts Section -->
        <div class="alerts-section">
            <div class="alerts-header">
                <h3><i class="fas fa-exclamation-triangle"></i> MISSION ALERTS</h3>
            </div>
            <div class="alerts-container" id="alertsContainer">
                <div class="alert-item alert-warning">
                    <span class="alert-time">14:23:45</span>
                    <span class="alert-message">Thermal system temperature slightly elevated - monitoring</span>
                </div>
                <div class="alert-item alert-info">
                    <span class="alert-time">14:15:32</span>
                    <span class="alert-message">Ground station pass initiated - Madrid DSN</span>
                </div>
                <div class="alert-item alert-success">
                    <span class="alert-time">14:08:17</span>
                    <span class="alert-message">Solar array deployment successful</span>
                </div>
            </div>
        </div>

        <!-- Control Panel -->
        <div class="control-panel">
            <button class="control-btn" id="emergencyStop">
                <i class="fas fa-stop"></i>
                EMERGENCY STOP
            </button>
            <button class="control-btn" id="systemReset">
                <i class="fas fa-sync"></i>
                SYSTEM RESET
            </button>
            <button class="control-btn" id="dataExport">
                <i class="fas fa-download"></i>
                EXPORT DATA
            </button>
            <button class="control-btn" id="parametersLink" onclick="window.location.href='index.jsp'">
                <i class="fas fa-tools"></i>
                PARAMETERS
            </button>
        </div>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="js/dashboard.js"></script>
</body>
</html>