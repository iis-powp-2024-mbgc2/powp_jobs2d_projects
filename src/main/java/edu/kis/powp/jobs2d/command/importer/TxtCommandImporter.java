package edu.kis.powp.jobs2d.command.importer;

import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TxtCommandImporter implements ICommandImporter {

    @Override
    public DriverCommand importCommands(String commands) {
        String[] lines = commands.split("\n");

        String name = lines[0];

        List<DriverCommand> commandsToReturn = new ArrayList<>();

        for (String line : lines) {
            if (Objects.equals(line, name)) continue;

            commandsToReturn.add(getCommandFromLine(line));
        }

        return new ImmutableCompoundCommand(commandsToReturn, name);
    }

    private static DriverCommand getCommandFromLine(String line) {
        String[] parts = line.split("\\|");

        if (parts.length == 3) {
            String commandName = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);

            switch (commandName) {
                case "SetPositionTo":
                    return new SetPositionCommand(x, y);
                case "OperateTo":
                    return new OperateToCommand(x, y);
                default:
                    throw new IllegalArgumentException("Unknown command: " + commandName);
            }
        } else {
            throw new IllegalArgumentException("Invalid command format: " + line);
        }
    }
}
