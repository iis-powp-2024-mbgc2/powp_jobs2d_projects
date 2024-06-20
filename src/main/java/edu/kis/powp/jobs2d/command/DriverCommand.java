package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.visitor.CommandVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;

/**
 * DriverCommand interface.
 */
public interface DriverCommand extends Cloneable {

    /**
     * Execute command on driver.
     *
     * @param driver driver.
     */
    public void execute(Job2dDriver driver);

    public void execute(IVisitableDriver driver);
  
    public void accept(CommandVisitor commandVisitor);

    @Override
    boolean equals(Object obj);

    DriverCommand clone() throws CloneNotSupportedException;

}
