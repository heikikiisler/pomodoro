package timer;

import sample.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyTimer {

    public static final String TIME_DATA_FILE_PATH = "src/resources/timedata.txt";
    public static int initialWorkTimeInMinutes = 30;
    public static int initialBreakTimeInMinutes = 30;
    private Controller controller;
    private int timeInSeconds;
    private String timeText;

    public MyTimer(Controller controller) {
        this.controller = controller;
        setInitialTimesFromFile();
    }

    public void setDisplayTime() {
        controller.setDisplayTime(timeText);
    }

    private void generateTimeText() {
        int minutes = (int) Math.floor(timeInSeconds / 60);
        int seconds = timeInSeconds - 60 * minutes;
        timeText = String.format("%02d:%02d", minutes, seconds);
    }

    public void countDownOneSecond() {
        if (timeInSeconds > 0) {
            timeInSeconds -= 1;
            refresh();
        }
        else {
            controller.zeroTime();
        }
    }

    public void refresh() {
        generateTimeText();
        setDisplayTime();
    }

    public void resetWorkMode() {
        controller.pauseTimer();
        setTimeInMinutes(initialWorkTimeInMinutes);
        refresh();
    }

    private void setTimeInMinutes(int minutes) {
        timeInSeconds = 60 * minutes;
        refresh();
    }

    public void resetBreakMode() {
        controller.pauseTimer();
        setTimeInMinutes(initialBreakTimeInMinutes);
        refresh();
    }

    public void setInitialTimesFromFile() {
        try {
            String times = Files.readAllLines(Paths.get(TIME_DATA_FILE_PATH)).get(0);
            initialWorkTimeInMinutes = Integer.parseInt(times.substring(0, 2));
            initialBreakTimeInMinutes = Integer.parseInt(times.substring(3, 5));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }
}
