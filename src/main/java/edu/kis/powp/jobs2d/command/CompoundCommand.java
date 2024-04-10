package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompoundCommand implements DriverCommand, ICompoundCommand {

    private List<DriverCommand> commands = new ArrayList<>();
    private String name;

    public CompoundCommand(String name) {
        this.name = name;
    }

    public void addCommands(List<DriverCommand> commandsList) {
        commands.addAll(commandsList);
    }


    @Override
    public void execute(Job2dDriver driver) {
        for (DriverCommand command : commands) {
            command.execute(driver);
        }
    }

    @Override
    public Iterator<DriverCommand> iterator() {
        return commands.iterator();
    }

    @Override
    public String toString() {
        return name;
    }
}
