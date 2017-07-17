# pomodoro

Simple timer for using the Pomodoro technique with configurable work and break times.

### How to use

The executable JAR is found in the *out/artifacts/Pomodoro_jar* directory.

To set your custom times, just double click on the timer and enter the times in minutes in WORK:BREAK format (25:5 for standard Pomodoro).

### Ideas for features and changes

* Option to change running time without changing defaults (Right click on the time)
* Different color for when break timer finishes (orange instead of red)
* Remember default position
* Change icon
* Make it available for pinning to taskbar (Windows 10)
* Let timer run past 00:00
* Optionally compensate for overtime with the appropriate ratio

### Code fix ideas

* Config file with times, colors and additional preferences
* Removing circular dependency from Timer and Controller classes

### Bugs

* When time runs out, time defaults are changed and timer is started, the timer numbers will stay red.
* Windows 10: interacting with the taskbar will hide the timer if they're on top of each other.
* Empirical evidence shows timer generally using 80-150Mb of RAM which could probably be lower.
