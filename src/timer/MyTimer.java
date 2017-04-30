package timer;

import sample.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MyTimer {

    public static final String TIME_DATA_FILE_PATH = "timedata.txt";
    public static int initialWorkTimeInMinutes = 25;
    public static int initialBreakTimeInMinutes = 5;
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
            File timeFile = new File(TIME_DATA_FILE_PATH);
            String times = Files.readAllLines(Paths.get(timeFile.getPath())).get(0);
            String[] timeList = times.split(":");
            initialWorkTimeInMinutes = Integer.parseInt(timeList[0]);
            initialBreakTimeInMinutes = Integer.parseInt(timeList[1]);
        } catch (NoSuchFileException e) {
            System.out.println("No timedata file found, creating a new one");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }
}
