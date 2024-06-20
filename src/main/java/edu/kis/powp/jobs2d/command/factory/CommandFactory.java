package edu.kis.powp.jobs2d.command.factory;

import edu.kis.powp.jobs2d.command.DriverCommand;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandFactory implements Serializable {
    private Map<String, DriverCommand> commands = new HashMap<>();

    public void addCommand(DriverCommand command) throws CloneNotSupportedException {
        commands.put(command.toString(), command);
    }

    public void removeCommand(String name) {
        commands.remove(name);
    }

    public DriverCommand getCommand(String name) throws CloneNotSupportedException {
        return commands.get(name);
    }

    public Set<String> getCommandNames() {
        return commands.keySet();
    }
}
