package util;

import java.net.URL;

public class Resources {

    public static final URL APP_FXML = getResource("app.fxml");
    public static final URL ICON = getResource("icon.png");

    private static URL getResource(String filePath) {
        return Resources.class.getClassLoader().getResource(filePath);
    }

}
