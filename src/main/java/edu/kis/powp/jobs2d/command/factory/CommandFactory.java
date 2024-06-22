package edu.kis.powp.jobs2d.command.factory;

import edu.kis.powp.jobs2d.command.DeepCopyVisitor;
import edu.kis.powp.jobs2d.command.DriverCommand;
import java.util.HashMap;
import java.util.Map;

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
            commands.get(commandName).accept(deepCopyVisitor);
            resultCommand = deepCopyVisitor.getCopiedCommand();
        } else {
            throw new IllegalArgumentException(commandName + " not found");
        }
        return resultCommand;
    }

    public void addCommand(DriverCommand command) throws CloneNotSupportedException {
        String newName = command.toString();
        DeepCopyVisitor deepCopyVisitor = new DeepCopyVisitor();
        command.accept(deepCopyVisitor);

        DriverCommand newCommand = deepCopyVisitor.getCopiedCommand();
        commands.put(newName.replace("Command name: ", ""), newCommand);
    }

    public void removeCommand(String commandName) {
        if (commands.keySet().contains(commandName)) {
            commands.remove(commandName);
        } else {
            throw new IllegalArgumentException(commandName + " not found");
        }
    }

}
