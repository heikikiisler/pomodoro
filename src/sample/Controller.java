package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import timer.MyTimer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {

    @FXML
    private Label displayTime;
    @FXML
    private TextField textField;
    @FXML
    private Button startButton;
    @FXML
    private Button breakButton;

    private boolean breakMode = false;
    private boolean isRunning = false;
    private MyTimer myTimer = new MyTimer(this);
    private Timeline timeline = new Timeline(
            new KeyFrame(
                Duration.ZERO,
                ActionEvent -> countDownOneSecond()),
            new KeyFrame(
                    Duration.seconds(1)
            )
    );

    public void initialize() {
        myTimer.resetWorkMode();
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void startButtonClicked() {
        if (myTimer.getTimeInSeconds() == 0) {
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
            myTimer.resetWorkMode();
            breakButton.setText("Break");
            displayTime.setStyle("\t-fx-effect: dropshadow(gaussian, #01fe0e , 5, 0.5, 0, 0);" +
                    "-fx-text-fill: #0D4A00;");
            breakMode = false;

        } else {
            myTimer.resetBreakMode();
            breakButton.setText("Work");
            displayTime.setStyle("-fx-effect: dropshadow(gaussian, #fffa00 , 5, 0.5, 0, 0);" +
                    "-fx-text-fill: #cecb18;");
            breakMode = true;
        }
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
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    textField.setVisible(true);
                }
            }
        });
    }

    public void setDisplayTime(String timeText) {
        displayTime.setText(timeText);
    }

    private void countDownOneSecond() {
        myTimer.countDownOneSecond();
    }

    public void zeroTime() {
        displayTime.setStyle("-fx-effect: dropshadow(gaussian, #ff0000 , 5, 0.5, 0, 0);" +
                "-fx-text-fill: #000000;");
        pauseTimer();
    }

    public void close() {
        Stage stage = (Stage) displayTime.getScene().getWindow();
        stage.close();
    }

    public void minimize() {
        Stage stage = (Stage) displayTime.getScene().getWindow();
        stage.setIconified(true);
    }

    public void submitMinutes(ActionEvent event) {
        TextField source = (TextField) event.getSource();
        String input = source.getText();
        if (input.matches("[0-9]+.[0-9]+")) {
            source.setVisible(false);
            try {
                FileWriter fileWriter = new FileWriter(new File(MyTimer.TIME_DATA_FILE_PATH));
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(input);
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            myTimer.refresh();
            myTimer.setInitialTimesFromFile();
            myTimer.resetWorkMode();
        }
    }
}
