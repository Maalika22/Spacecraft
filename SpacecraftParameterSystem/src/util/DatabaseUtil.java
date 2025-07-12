package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    // Use absolute path to avoid issues with web application context
    private static final String DB_URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "/spacecraft_parameters.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            // Enable foreign key support
            conn.createStatement().execute("PRAGMA foreign_keys = ON");
            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        }
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            String createTableSQL = """
                        CREATE TABLE IF NOT EXISTS parameters (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            page_id TEXT NOT NULL,
                            page_no INTEGER,
                            spacecraft_name TEXT NOT NULL,
                            subsystem_name TEXT NOT NULL,
                            param1 TEXT,
                            param2 TEXT,
                            param3 TEXT,
                            param4 TEXT,
                            param5 TEXT,
                            param6 TEXT,
                            param7 TEXT,
                            param8 TEXT,
                            param9 TEXT,
                            param10 TEXT,
                            param11 TEXT,
                            param12 TEXT,
                            param13 TEXT,
                            param14 TEXT,
                            param15 TEXT,
                            param16 TEXT,
                            param17 TEXT,
                            param18 TEXT,
                            param19 TEXT,
                            param20 TEXT,
                            param21 TEXT,
                            param22 TEXT,
                            param23 TEXT,
                            param24 TEXT,
                            param25 TEXT,
                            param26 TEXT,
                            param27 TEXT,
                            param28 TEXT,
                            param29 TEXT,
                            param30 TEXT,
                            param31 TEXT,
                            param32 TEXT,
                            param33 TEXT,
                            param34 TEXT,
                            param35 TEXT,
                            param36 TEXT,
                            param37 TEXT,
                            param38 TEXT,
                            UNIQUE(page_no, spacecraft_name, subsystem_name)
                        )
                    """;

            stmt.execute(createTableSQL);

            // Create a table for storing default spacecraft names if it doesn't exist
            String createSpacecraftTableSQL = """
                        CREATE TABLE IF NOT EXISTS spacecraft_names (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL UNIQUE
                        )
                    """;
            stmt.execute(createSpacecraftTableSQL);

            // Create a table for storing default subsystem names if it doesn't exist
            String createSubsystemTableSQL = """
                        CREATE TABLE IF NOT EXISTS subsystem_names (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL UNIQUE
                        )
                    """;
            stmt.execute(createSubsystemTableSQL);

            // Insert default spacecraft names if the table is empty
            String countSpacecraftSQL = "SELECT COUNT(*) FROM spacecraft_names";
            ResultSet rsSpacecraft = stmt.executeQuery(countSpacecraftSQL);
            if (rsSpacecraft.next() && rsSpacecraft.getInt(1) == 0) {
                String[] spacecraftNames = {
                        "Aryabhata", "Rohini", "INSAT series", "Chandrayaan series",
                        "Mars Orbiter Mission (MOM)", "AstroSat", "RISAT series",
                        "Oceansat series", "GSAT series", "Aditya-L1"
                };

                for (String name : spacecraftNames) {
                    String insertSQL = "INSERT INTO spacecraft_names (name) VALUES (?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                        pstmt.setString(1, name);
                        pstmt.executeUpdate();
                    }
                }
                System.out.println("Default spacecraft names added successfully!");
            }

            // Insert default subsystem names if the table is empty
            String countSubsystemSQL = "SELECT COUNT(*) FROM subsystem_names";
            ResultSet rsSubsystem = stmt.executeQuery(countSubsystemSQL);
            if (rsSubsystem.next() && rsSubsystem.getInt(1) == 0) {
                String[] subsystemNames = {
                        "power", "thermal", "attitude and orbit control", "propulsion",
                        "communication", "data handling", "payload subsystems"
                };

                for (String name : subsystemNames) {
                    String insertSQL = "INSERT INTO subsystem_names (name) VALUES (?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                        pstmt.setString(1, name);
                        pstmt.executeUpdate();
                    }
                }
                System.out.println("Default subsystem names added successfully!");
            }

            System.out.println("Database initialized successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
