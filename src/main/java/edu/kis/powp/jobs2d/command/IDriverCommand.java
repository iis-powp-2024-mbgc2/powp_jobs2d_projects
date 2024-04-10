package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.visitor.CommandCounterVisitor;
import edu.kis.powp.jobs2d.command.visitor.ICommandVisitor;

/**
 * DriverCommand interface.
 */
public interface IDriverCommand {

    /**
     * Execute command on driver.
     *
     * @param driver driver.
     */
    public void execute(Job2dDriver driver);

    public void accept(ICommandVisitor commandCounterVisitor);
}
