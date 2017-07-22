package timer;

import data.Config;
import sample.Controller;

public class Timer {

    private static int initialWorkTimeInMinutes;
    private static int initialBreakTimeInMinutes;
    private Controller controller;
    private int timeInSeconds;
    private String timeText;

    public Timer(Controller controller) {
        this.controller = controller;
        setInitialTimesFromFile();
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
        controller.setDisplayTime(timeText);
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
        initialWorkTimeInMinutes = Config.getPropertyInt("workTime");
        initialBreakTimeInMinutes = Config.getPropertyInt("breakTime");
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }
}
