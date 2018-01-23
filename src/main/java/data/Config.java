package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class Config {

    private static final Properties PROPERTIES = loadAndSetProperties();
    private static final String CONFIG_FILEPATH = "config.properties";
    private static final String CONFIG_SAMPLE_FILEPATH = "config.properties.sample";

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
            if (!new File(CONFIG_FILEPATH).isFile()) {
                System.out.println("here");
                Files.copy(
                        new File(CONFIG_SAMPLE_FILEPATH).toPath(),
                        new File(CONFIG_FILEPATH).toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
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
