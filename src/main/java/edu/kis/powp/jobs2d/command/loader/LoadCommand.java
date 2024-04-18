package edu.kis.powp.jobs2d.command.loader;

import edu.kis.powp.jobs2d.command.DriverCommand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LoadCommand {

    public static void loadCommand(String fileName, List<DriverCommand> commands) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
