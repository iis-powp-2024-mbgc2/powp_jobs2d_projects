package edu.kis.powp.jobs2d.command.factory;

import edu.kis.powp.jobs2d.command.DeepCopyVisitor;
import edu.kis.powp.jobs2d.command.DriverCommand;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandFactory {
    private Map<String, DriverCommand> commands = new HashMap<>();
    private static CommandFactory commandFactory = null;

    public static CommandFactory getInstance() {
        if (commandFactory == null) {
            commandFactory = new CommandFactory();
        }
        return commandFactory;
    }

    public DriverCommand getCommand(String commandName) {
        DriverCommand resultCommand = null;
        DeepCopyVisitor deepCopyVisitor = new DeepCopyVisitor();
        if (commands.keySet().contains(commandName)) {
            resultCommand = commands.get(commandName);
            

//            try {
//                resultCommand = commandsMap.get(commandName);
//            } catch (CloneNotSupportedException e) {
//                throw new IllegalStateException("this should never happen: command has to be clonable since its cloned in addCommand", e);
//            }
        } else {
            throw new IllegalArgumentException(commandName + " not found");
        }
        return resultCommand;
    }

    public void addCommand(DriverCommand command) throws CloneNotSupportedException {
        commands.put(command.toString().replace("Command name: ", ""), command);
    }

    public void removeCommand(String commandName) {
        if (commands.keySet().contains(commandName)) {
            commands.remove(commandName);
        } else {
            throw new IllegalArgumentException(commandName + " not found");
        }
    }

    public Set<String> getCommandNames() {
        return commands.keySet();
    }
}
