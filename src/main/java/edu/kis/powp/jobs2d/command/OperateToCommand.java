package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.visitor.CommandCounterVisitor;

/**
 * Implementation of Job2dDriverCommand for operateTo command functionality.
 */
public class OperateToCommand implements IDriverCommand {

    private int posX, posY;

    public OperateToCommand(int posX, int posY) {
        super();
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void execute(Job2dDriver driver) {
        driver.operateTo(posX, posY);
    }

    @Override
    public void accept(CommandCounterVisitor commandCounterVisitor) {
        commandCounterVisitor.visit(this);
    }

}
