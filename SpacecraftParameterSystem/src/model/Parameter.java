package model;

import java.util.HashMap;
import java.util.Map;

public class Parameter {
    private int id;
    private String pageId;
    private int pageNo;
    private String spacecraftName;
    private String subsystemName;
    private Map<String, String> parameters;

    public Parameter() {
        this.parameters = new HashMap<>();
    }

    public Parameter(String pageId, String spacecraftName, String subsystemName) {
        this();
        this.pageId = pageId;
        this.spacecraftName = spacecraftName;
        this.subsystemName = subsystemName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getSpacecraftName() {
        return spacecraftName;
    }

    public void setSpacecraftName(String spacecraftName) {
        this.spacecraftName = spacecraftName;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }

    public String getParameter(int index) {
        return parameters.get("param" + index);
    }

    public void setParameter(int index, String value) {
        parameters.put("param" + index, value);
    }
}
