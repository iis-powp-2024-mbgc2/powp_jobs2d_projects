package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * Implementation of Job2dDriverCommand for setPosition command functionality.
 */
public class SetPositionCommand implements DriverCommand {

    private final int posX, posY;

    public SetPositionCommand(int posX, int posY) {
        super();
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void execute(Job2dDriver driver) {
        driver.setPosition(posX, posY);
    }

    @Override
    public void accept(CommandVisitor commandVisitor) {
        commandVisitor.visit(this);
    }

    public int getX() {
        return posX;
    }
    public int getY() {
        return posY;
    }
    @Override
    public String toString()
    {
        return "Setting position to (" + posX + "," + posY +")";
    }
}
