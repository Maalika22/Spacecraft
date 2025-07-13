package dao;

import model.Parameter;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParameterDAO {

    public boolean save(Parameter parameter) {
        String sql = """
                INSERT OR REPLACE INTO parameters (page_id, page_no, spacecraft_name, subsystem_name, 
                param1, param2, param3, param4, param5, param6, param7, param8, param9, param10,
                param11, param12, param13, param14, param15, param16, param17, param18, param19, param20,
                param21, param22, param23, param24, param25, param26, param27, param28, param29, param30,
                param31, param32, param33, param34, param35, param36, param37, param38)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, parameter.getPageId());
            pstmt.setInt(2, parameter.getPageNo());
            pstmt.setString(3, parameter.getSpacecraftName());
            pstmt.setString(4, parameter.getSubsystemName());

            // Set all 38 parameters
            for (int i = 1; i <= 38; i++) {
                String value = parameter.getParameter(i);
                pstmt.setString(i + 4, value != null ? value : "");
            }

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Parameter> searchByPageId(String pageId) {
        String sql = "SELECT * FROM parameters WHERE page_id LIKE ?";
        return searchParameters(sql, "%" + pageId + "%");
    }

    public List<Parameter> searchBySpacecraftName(String spacecraftName) {
        String sql = "SELECT * FROM parameters WHERE spacecraft_name LIKE ?";
        return searchParameters(sql, "%" + spacecraftName + "%");
    }

    public List<Parameter> searchBySubsystemName(String subsystemName) {
        String sql = "SELECT * FROM parameters WHERE subsystem_name LIKE ?";
        return searchParameters(sql, "%" + subsystemName + "%");
    }

    public Parameter getParameter(String pageId, String spacecraftName, String subsystemName) {
        String sql = "SELECT * FROM parameters WHERE page_id = ? AND spacecraft_name = ? AND subsystem_name = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pageId);
            pstmt.setString(2, spacecraftName);
            pstmt.setString(3, subsystemName);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToParameter(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Alias for getParameter - used by LoadParameterServlet
    public Parameter findByKeys(String pageId, String spacecraftName, String subsystemName) {
        return getParameter(pageId, spacecraftName, subsystemName);
    }

    public boolean delete(String pageId, String spacecraftName, String subsystemName) {
        String sql = "DELETE FROM parameters WHERE page_id = ? AND spacecraft_name = ? AND subsystem_name = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pageId);
            pstmt.setString(2, spacecraftName);
            pstmt.setString(3, subsystemName);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getSpacecraftNames() {
        String sql = "SELECT name FROM spacecraft_names ORDER BY name";
        List<String> names = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    public List<String> getSubsystemNames() {
        String sql = "SELECT name FROM subsystem_names ORDER BY name";
        List<String> names = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    // Methods expected by AutocompleteServlet
    public List<String> getDistinctSpacecraftNames() {
        String sql = "SELECT DISTINCT spacecraft_name FROM parameters ORDER BY spacecraft_name";
        List<String> names = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("spacecraft_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    public List<String> getDistinctSubsystemNames() {
        String sql = "SELECT DISTINCT subsystem_name FROM parameters ORDER BY subsystem_name";
        List<String> names = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("subsystem_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    private List<Parameter> searchParameters(String sql, String searchValue) {
        List<Parameter> parameters = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, searchValue);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                parameters.add(mapResultSetToParameter(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parameters;
    }

    private Parameter mapResultSetToParameter(ResultSet rs) throws SQLException {
        Parameter parameter = new Parameter();
        parameter.setId(rs.getInt("id"));
        parameter.setPageId(rs.getString("page_id"));
        parameter.setPageNo(rs.getInt("page_no"));
        parameter.setSpacecraftName(rs.getString("spacecraft_name"));
        parameter.setSubsystemName(rs.getString("subsystem_name"));
        
        // Set all 38 parameters
        for (int i = 1; i <= 38; i++) {
            String value = rs.getString("param" + i);
            parameter.setParameter(i, value != null ? value : "");
        }
        
        return parameter;
    }
}