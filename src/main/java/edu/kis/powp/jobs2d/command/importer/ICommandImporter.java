package edu.kis.powp.jobs2d.command.importer;

import edu.kis.powp.jobs2d.command.DriverCommand;

public interface ICommandImporter {
    DriverCommand importCommands(String commands);
}