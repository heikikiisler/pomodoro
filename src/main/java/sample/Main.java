package sample;

import data.Config;
import data.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Resources.getResource("sample.fxml"));

        primaryStage.setTitle(Config.getPropertyString(Config.TITLE));
        primaryStage.setScene(
            new Scene(root, Config.getPropertyInt(Config.WIDTH), Config.getPropertyInt(Config.HEIGHT))
        );

        setDefaultLocation(primaryStage);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        primaryStage.getIcons().add(new Image(String.valueOf(Resources.getResource("icon.png"))));

    }

    private void setDefaultLocation(Stage primaryStage) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenX = primaryScreenBounds.getMaxX();
        double screenY = primaryScreenBounds.getMaxY();

        int width = Config.getPropertyInt(Config.WIDTH);
        int height = Config.getPropertyInt(Config.HEIGHT);

        double defaultX = Config.getPropertyDouble(Config.X_DEFAULT);
        double defaultY = Config.getPropertyDouble(Config.Y_DEFAULT);

        if (defaultX + width / 2 < screenX && defaultY + height / 2 < screenY) {
            primaryStage.setX(Config.getPropertyDouble(Config.X_DEFAULT));
            primaryStage.setY(Config.getPropertyDouble(Config.Y_DEFAULT));
        }
    }

}
