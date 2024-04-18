package edu.kis.powp.jobs2d.command.loader;

import edu.kis.powp.jobs2d.command.DriverCommand;

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
                //Logic to add command from file

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return commands;
    }
}
