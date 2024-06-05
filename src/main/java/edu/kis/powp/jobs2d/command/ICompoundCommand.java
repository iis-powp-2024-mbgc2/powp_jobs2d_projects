package edu.kis.powp.jobs2d.command;

import java.util.Iterator;

/**
 * Interface extending Job2dDriverCommand to execute more than one command.
 */
public interface ICompoundCommand extends DriverCommand {

    Iterator<DriverCommand> iterator();

    void interchangeCommands(int x, int y);

    void modifyCoordinates(int commandIndex, int x, int y);

}
