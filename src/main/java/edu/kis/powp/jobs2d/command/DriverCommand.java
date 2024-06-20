package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;

/**
 * DriverCommand interface.
 */
public interface DriverCommand {

    /**
     * Execute command on driver.
     *
     * @param driver driver.
     */
    public void execute(VisitableJob2dDriver driver);
    public void accept(CommandVisitor commandVisitor);
}
