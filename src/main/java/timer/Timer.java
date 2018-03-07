package timer;

import app.TimerController;
import util.Config;

public class Timer {

    private TimerController controller;
    private int initialWorkTimeInMinutes;
    private int initialBreakTimeInMinutes;
    private int timeInSeconds;
    private String timeText;

    public Timer(TimerController controller) {
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
            timeInSeconds--;
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
        setTimeInMinutes(initialWorkTimeInMinutes);
        refresh();
    }

    public void setTimeInMinutes(int minutes) {
        timeInSeconds = 60 * minutes;
        refresh();
    }

    public void resetBreakMode() {
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
