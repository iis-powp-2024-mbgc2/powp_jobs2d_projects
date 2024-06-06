package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.drivers.IDriver;

/**
 * DriverCommand interface.
 */
public interface DriverCommand {

    /**
     * Execute command on driver.
     *
     * @param driver driver.
     */
    public void execute(IDriver driver);
    public void accept(CommandVisitor commandVisitor);
}
