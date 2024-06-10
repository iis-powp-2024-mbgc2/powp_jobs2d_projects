package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {

    private static final List<CommandHistoryEntry> history = new ArrayList<>();
    private static int entryNo = 1;

    public void clear()
    {
        history.clear();
    }

    public void add(DriverCommand command)
    {
        history.add(new CommandHistoryEntry(command));
    }
    public boolean isEmpty()
    {
        return history.isEmpty();
    }

    public List<String> getActionsHistorySummary()
    {
        List<String> list = new ArrayList<>();
        for(CommandHistoryEntry entry: history)
            list.add(entry.getCommand().toString() + " used at " + entry.getDate());
        return list;
    }

    public List<DriverCommand> getActionsHistory()
    {
        List<DriverCommand> list = new ArrayList<>();
        for(CommandHistoryEntry entry: history)
        {
            CompoundCommand command = new CompoundCommand(entry.getName());
            list.add(entry.getCommand());
        }
        return list;
    }
}
