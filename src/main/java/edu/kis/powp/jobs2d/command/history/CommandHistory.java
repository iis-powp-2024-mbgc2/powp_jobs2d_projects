package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {

    private static final List<CommandHistoryEntry> history = new ArrayList<>();

    public void clear()
    {
        history.clear();
        HistoryComboBox historyComboBox = new HistoryComboBox();
        historyComboBox.actualize();
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
            CompoundCommand command = new CompoundCommand(entry.toString());
            list.add(command);
        }
        return list;
    }

    public List<DriverCommand> getByName(DriverCommand driverCommand)
    {
        List<DriverCommand> list = new ArrayList<>();
        for(CommandHistoryEntry entry: history)
            if(entry.toString().equals(driverCommand.toString()))
            {
                list.add(entry.getCommand());
                break;
            }

        return list;
    }

}
