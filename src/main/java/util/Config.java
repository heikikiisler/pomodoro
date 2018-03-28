package util;

import java.io.*;
import java.util.Properties;

public class Config {

    private static final String DEFAULT_CONFIG = String.join("\n",
            "workTime=25",
            "breakTime=5",
            "width=200",
            "height=60",
            "title=Pomodoro",
            "yDefault=300.0",
            "xDefault=300.0"
    );

    private static final String CONFIG_FILEPATH = "pomodoro.properties";
    private static final Properties PROPERTIES = getAndSetProperties();

    public static final String WORK_TIME = "workTime";
    public static final String BREAK_TIME = "breakTime";
    public static final String X_DEFAULT = "xDefault";
    public static final String Y_DEFAULT = "yDefault";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String TITLE = "title";

    private static Properties getAndSetProperties() {
        Properties properties = new Properties();
        File configFile = new File(CONFIG_FILEPATH);
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
                FileWriter writer = new FileWriter(configFile);
                writer.write(DEFAULT_CONFIG);
                writer.close();
            }
            properties.load(new FileInputStream(CONFIG_FILEPATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String getPropertyString(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static int getPropertyInt(String key) {
        return Integer.parseInt(PROPERTIES.getProperty(key));
    }

    public static double getPropertyDouble(String key) {
        return Double.parseDouble(PROPERTIES.getProperty(key));
    }

    public static void setProperty(String key, String value) {
        PROPERTIES.setProperty(key, value);
        try {
            PROPERTIES.store(new FileOutputStream(CONFIG_FILEPATH), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
