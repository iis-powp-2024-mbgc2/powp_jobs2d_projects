package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;

import java.util.Date;

public class CommandHistoryEntry {
    private final DriverCommand command;
    private final Date date = new Date();

    public CommandHistoryEntry(DriverCommand command)
    {
        this.command = command;
    }

    public DriverCommand getCommand() {
        return command;
    }

    public Date getDate() {
        return date;
    }
}
