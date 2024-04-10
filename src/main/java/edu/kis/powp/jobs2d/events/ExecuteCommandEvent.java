package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.DriverCommand;

public class ExecuteCommandEvent {
    private final DriverCommand command;
    public ExecuteCommandEvent(DriverCommand command) {
        this.command = command;
    }

    public DriverCommand getCommand() {
        return command;
    }
}
