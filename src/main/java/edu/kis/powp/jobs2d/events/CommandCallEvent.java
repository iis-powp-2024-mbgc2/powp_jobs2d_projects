package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.DriverCommand;

public class CommandCallEvent {
    private final DriverCommand command;
    public CommandCallEvent(DriverCommand command) {
        this.command = command;
    }

    public DriverCommand getCommand() {
        return command;
    }
}
