package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.visitor.CommandVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;

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
    public void execute(IVisitableDriver driver) {
        driver.setPosition(posX, posY);
    }

    @Override
    public SetPositionCommand clone() throws CloneNotSupportedException {
        return (SetPositionCommand) super.clone();
    }

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
