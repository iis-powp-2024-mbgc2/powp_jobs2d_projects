package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.drivers.IDriver;

/**
 * Implementation of IDriverCommand for setPosition command functionality.
 */
public class SetPositionCommand implements DriverCommand {

    private final int posX, posY;

    public SetPositionCommand(int posX, int posY) {
        super();
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void execute(IDriver driver) {
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
}
