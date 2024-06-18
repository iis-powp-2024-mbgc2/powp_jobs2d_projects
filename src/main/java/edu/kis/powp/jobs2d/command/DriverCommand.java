package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.awt.*;
import java.io.Serializable;

/**
 * DriverCommand interface.
 */
public interface DriverCommand extends Serializable {

    /**
     * Execute command on driver.
     *
     * @param driver driver.
     */
    public void execute(Job2dDriver driver);
    public void accept(CommandVisitor commandVisitor);
    public int getX();
    public int getY();
    public void setX(int x);
    public void setY(int y);
    public Point getPoint();
}
