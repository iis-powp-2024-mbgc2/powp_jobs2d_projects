package edu.kis.powp.jobs2d.command;

import java.util.Iterator;

/**
 * Interface extending Job2dDriverCommand to execute more than one command.
 */
public interface ICompoundCommand extends DriverCommand, Cloneable {

    public Iterator<DriverCommand> iterator();

    ICompoundCommand clone() throws CloneNotSupportedException;

}
