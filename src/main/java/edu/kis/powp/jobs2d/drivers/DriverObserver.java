package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.events.CommandCallEvent;

public interface DriverObserver {
    void notifyObserver(CommandCallEvent event);
}
