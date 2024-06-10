package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {

    private static final CompoundCommand history = new CompoundCommand("history");

    public void clear()
    {
        history.clearCommand();
    }

    public void add(DriverCommand command)
    {
        history.addCommand(command);
    }
    public boolean isEmpty()
    {
        return history.getCommands().isEmpty();
    }

    public List<String> getActionsHistory()
    {
        List<String> list = new ArrayList<>();
        for(DriverCommand command: history.getCommands())
            list.add(command.toString());
        return list;
    }
}
