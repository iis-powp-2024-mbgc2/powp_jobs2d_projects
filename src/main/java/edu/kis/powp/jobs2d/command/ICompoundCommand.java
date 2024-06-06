package edu.kis.powp.jobs2d.command;

import java.util.Iterator;

/**
 * Interface extending IDriverCommand to execute more than one command.
 */
public interface ICompoundCommand extends DriverCommand {

    public Iterator<DriverCommand> iterator();

}
