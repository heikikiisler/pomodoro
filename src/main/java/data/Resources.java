package data;

import java.net.URL;

public class Resources {

    public static URL getResource(String filePath) {
        return Resources.class.getClassLoader().getResource(filePath);
    }

}
