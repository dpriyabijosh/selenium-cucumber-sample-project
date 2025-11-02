package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Manager to handle properties file reading and environment-specific configurations
 */
public class ConfigManager {
    private static Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config/config.properties";

    static {
        loadProperties();
    }
    
    /**
     * Load properties from config file and environment-specific file
     */
    private static void loadProperties() {
        properties = new Properties();
        
        try {
            // Load main config file
            FileInputStream fis = new FileInputStream(CONFIG_FILE);
            properties.load(fis);
            fis.close();
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config properties: " + e.getMessage());
        }
    }
    
    /**
     * Get property value by key
     * @param key Property key
     * @return Property value or null if not found
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get property value by key with default value
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Get integer property value
     * @param key Property key
     * @param defaultValue Default value if property not found or invalid
     * @return Integer property value
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            System.err.println("Invalid integer value for key '" + key + "': " + value);
            return defaultValue;
        }
    }
    
    /**
     * Get boolean property value
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Boolean property value
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }
    
    /**
     * Get browser name from system property or config
     * @return Browser name
     */
    public static String getBrowser() {
        String systemProperty = System.getProperty("browser");
        String configProperty = getProperty("browser", "firefox");
        
        // Use config property if system property is null or empty
        if (systemProperty == null || systemProperty.trim().isEmpty()) {
            return configProperty;
        } else {
            return systemProperty;
        }
    }
    
    /**
     * Get base URL for the application
     * @return Base URL
     */
    public static String getBaseUrl() {
        return getProperty("base.url", "https://www.saucedemo.com/");
    }
    
    /**
     * Get timeout value in seconds
     * @return Timeout value
     */
    public static int getTimeout() {
        return getIntProperty("timeout", 10);
    }
    
    /**
     * Check if headless mode is enabled
     * @return true if headless mode enabled
     */
    public static boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }
    
    /**
     * Check if screenshot on failure is enabled
     * @return true if screenshot on failure enabled
     */
    public static boolean isScreenshotOnFailure() {
        return getBooleanProperty("screenshot.on.failure", true);
    }
    
    /**
     * Get report path
     * @return Report directory path
     */
    public static String getReportPath() {
        return getProperty("report.path", "target/reports");
    }
}