package test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import util.DatabaseUtil;

public class DatabaseConnectionTest {

    public static void main(String[] args) {
        System.out.println("=== Database Connection Test ===");

        // Test 1: Check if SQLite JDBC driver is available
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("✓ SQLite JDBC driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("✗ SQLite JDBC driver NOT found");
            System.out.println("Make sure sqlite-jdbc jar is in the classpath");
            return;
        }

        // Test 2: Test database connection
        try (Connection conn = DatabaseUtil.getConnection()) {
            System.out.println("✓ Database connection established successfully");

            // Get database info
            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println("Database: " + metaData.getDatabaseProductName());
            System.out.println("Version: " + metaData.getDatabaseProductVersion());
            System.out
                    .println("Database file location: " + System.getProperty("user.dir") + "/spacecraft_parameters.db");

        } catch (Exception e) {
            System.out.println("✗ Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Test 3: Initialize database
        try {
            System.out.println("\n=== Initializing Database ===");
            DatabaseUtil.initializeDatabase();
            System.out.println("✓ Database initialization completed");
        } catch (Exception e) {
            System.out.println("✗ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Test 4: Check if tables exist
        try (Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement()) {

            System.out.println("\n=== Checking Tables ===");

            // Check parameters table
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='parameters'");
            if (rs.next()) {
                System.out.println("✓ 'parameters' table exists");
            } else {
                System.out.println("✗ 'parameters' table NOT found");
            }

            // Check spacecraft_names table
            rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='spacecraft_names'");
            if (rs.next()) {
                System.out.println("✓ 'spacecraft_names' table exists");
            } else {
                System.out.println("✗ 'spacecraft_names' table NOT found");
            }

            // Check subsystem_names table
            rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='subsystem_names'");
            if (rs.next()) {
                System.out.println("✓ 'subsystem_names' table exists");
            } else {
                System.out.println("✗ 'subsystem_names' table NOT found");
            }

            // Count records in each table
            System.out.println("\n=== Table Record Counts ===");

            rs = stmt.executeQuery("SELECT COUNT(*) FROM spacecraft_names");
            if (rs.next()) {
                System.out.println("Spacecraft names: " + rs.getInt(1) + " records");
            }

            rs = stmt.executeQuery("SELECT COUNT(*) FROM subsystem_names");
            if (rs.next()) {
                System.out.println("Subsystem names: " + rs.getInt(1) + " records");
            }

            rs = stmt.executeQuery("SELECT COUNT(*) FROM parameters");
            if (rs.next()) {
                System.out.println("Parameters: " + rs.getInt(1) + " records");
            }

        } catch (Exception e) {
            System.out.println("✗ Error checking tables: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== Database Test Completed ===");
    }
}
