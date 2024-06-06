package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.drivers.IDriver;

/**
 * Implementation of IDriverCommand for operateTo command functionality.
 */
public class OperateToCommand implements DriverCommand {

    private final int posX, posY;

    public OperateToCommand(int posX, int posY) {
        super();
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void execute(IDriver driver) {
        driver.operateTo(posX, posY);
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
