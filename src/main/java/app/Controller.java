package app;

import util.Colors;
import util.Config;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import timer.Timer;

public class Controller implements TimerController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Label displayTime;
    @FXML
    private TextField textField;
    @FXML
    private Button startButton;
    @FXML
    private Button breakButton;

    private double xOffset = 0;
    private double yOffset = 0;

    private boolean breakMode = false;
    private boolean isRunning = false;

    private Timer timer = new Timer(this);
    private Timeline timeline = new Timeline(
            new KeyFrame(
                    Duration.ZERO,
                    ActionEvent -> timer.countDownOneSecond()
            ),
            new KeyFrame(
                    Duration.seconds(1)
            )
    );

    public void initialize() {
        setUpEvents();
        timer.resetWorkMode();
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void startButtonClicked() {
        if (timer.getTimeInSeconds() == 0) {
            switchMode();
        }
        if (!isRunning) {
            startTimer();
        } else {
            pauseTimer();
        }
    }

    public void breakButtonClicked() {
        switchMode();
    }

    public void switchMode() {
        if (breakMode) {
            resetWorkMode();
        } else {
            resetBreakMode();
        }
    }

    private void resetBreakMode() {
        timer.resetBreakMode();
        pauseTimer();
        breakButton.setText("Work ");
        displayTime.setStyle(Colors.DISPLAY_TIME_BREAK);
        breakMode = true;
    }

    private void resetWorkMode() {
        timer.resetWorkMode();
        pauseTimer();
        breakButton.setText("Break");
        displayTime.setStyle(Colors.DISPLAY_TIME_WORK);
        breakMode = false;
    }

    public void pauseTimer() {
        startButton.setText("Start");
        timeline.pause();
        isRunning = false;
    }

    public void startTimer() {
        startButton.setText("Pause");
        timeline.play();
        isRunning = true;
    }


    public void displayTimeClicked() {
        displayTime.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                pauseTimer();
                textField.setVisible(true);
                textField.requestFocus();
            }
        });
    }

    public void setDisplayTime(String timeText) {
        displayTime.setText(timeText);
    }

    public void zeroTime() {
        if (breakMode) {
            displayTime.setStyle(Colors.ZERO_TIME_BREAK);
        } else {
            displayTime.setStyle(Colors.ZERO_TIME_WORK);
        }
        pauseTimer();
    }

    private Stage getStage() {
        return (Stage) displayTime.getScene().getWindow();
    }

    public void close() {
        getStage().close();
    }

    public void minimize() {
        getStage().setIconified(true);
    }

    public void saveCoordinates() {
        Bounds bounds = borderPane.localToScreen(borderPane.getBoundsInLocal());
        Config.setProperty(Config.X_DEFAULT, String.valueOf(bounds.getMinX()));
        Config.setProperty(Config.Y_DEFAULT, String.valueOf(bounds.getMinY()));
    }

    public void setUpEvents() {
        borderPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        borderPane.setOnMouseDragged(event -> {
            setXY(event.getScreenX() - xOffset, event.getScreenY() - yOffset);
            saveCoordinates();
        });
        disableContextMenu();
    }

    private void disableContextMenu() {
        textField.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
    }

    private void setXY(double x, double y) {
        getStage().setX(x);
        getStage().setY(y);
    }

    public void submitMinutes(ActionEvent event) {
        TextField source = (TextField) event.getSource();
        String input = source.getText();
        source.setVisible(false);
        // Change default times.
        if (input.matches("[0-9]+.[0-9]+")) {
            String[] splitInput = input.split("\\D+");
            Config.setProperty(Config.WORK_TIME, splitInput[0]);
            Config.setProperty(Config.BREAK_TIME, splitInput[1]);
            timer.refresh();
            timer.setInitialTimesFromFile();
        // Temporary time change.
        } else if (input.matches("[0-9]+")) {
            timer.setTimeInMinutes(Integer.valueOf(input));
            timer.refresh();
            return;
        }
        resetWorkMode();
    }

}
