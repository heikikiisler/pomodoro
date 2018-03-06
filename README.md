# pomodoro

Simple timer for using the Pomodoro technique with configurable work and break times.

![Work mode](src/main/resources/screenshot1.png?raw=true "Work mode")

![Break mode paused](src/main/resources/screenshot2.png?raw=true "Break mode paused")

### How to use

The executable JAR is found in the *out/artifacts/Pomodoro_jar* directory.

To set your custom times, double click on the timer and enter the times in minutes in WORK:BREAK format (25:5 for standard Pomodoro).
To change the current running timer time without changing defaults, double click on the timer and enter the time in minutes.

### Features

* Configurable work and break times (in minutes)
* Pausing
* Switching modes
* Timer stays on top of other windows (still minimizable)
* Default position remembering after closing

### Ideas for new features and changes

* Adding options to a right-click menu to reduce confusion
* Make it available for pinning to taskbar (Windows 10)
* Let timer run past 00:00

### Known bugs

* Windows 10: interacting with the taskbar will hide the timer if they're on top of each other.
* Empirical evidence shows timer generally using 80-150Mb of RAM which could probably be lower.
