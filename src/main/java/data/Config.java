package data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Properties properties = loadAndSetProperties();
    private static final String CONFIG_FILEPATH = "config.properties";

    public static final String WORK_TIME = "workTime";
    public static final String BREAK_TIME = "breakTime";
    public static final String X_DEFAULT = "xDefault";
    public static final String Y_DEFAULT = "yDefault";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String TITLE = "title";

    private static Properties loadAndSetProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(CONFIG_FILEPATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String getPropertyString(String key) {
        return properties.getProperty(key);
    }

    public static int getPropertyInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static double getPropertyDouble(String key) {
        return Double.parseDouble(properties.getProperty(key));
    }

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
        try {
            properties.store(new FileOutputStream(CONFIG_FILEPATH), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
