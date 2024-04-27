package edu.kis.powp.jobs2d.command.loader;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadCommand {

    public static List<DriverCommand> loadCommandsFromFile(String fileName) {
        List<DriverCommand> commands = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 3) {
                    String commandName = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);

                    switch (commandName) {
                        case "SetPositionTo":
                            commands.add(new SetPositionCommand(x, y));
                            break;
                        case "OperateTo":
                            commands.add(new OperateToCommand(x, y));
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown command: " + commandName);
                    }
                } else {
                    throw new IllegalArgumentException("Invalid command format: " + line);
                }

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return commands;
    }
}
