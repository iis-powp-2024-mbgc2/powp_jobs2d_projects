package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {

    private static ImmutableCompoundCommand history = new ImmutableCompoundCommand(new ArrayList<>(), "history");

    public void clear()
    {
        history = new ImmutableCompoundCommand(new ArrayList<>(), "history");
    }

    public void add(DriverCommand command)
    {
        List<DriverCommand> newCommands = history.getCommands();
        newCommands.add(command);
        history = new ImmutableCompoundCommand(newCommands, "history");
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
