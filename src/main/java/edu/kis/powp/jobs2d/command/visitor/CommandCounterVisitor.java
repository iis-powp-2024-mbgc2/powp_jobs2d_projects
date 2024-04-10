package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.IDriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

public class CommandCounterVisitor implements ICommandVisitor {
    private int operateToCommandCounter = 0;
    private int setPositionCommandCounter = 0;
    private int iCompoundCommandCounter = 0;

    @Override
    public void visit(SetPositionCommand command) {
        setPositionCommandCounter++;
    }

    @Override
    public void visit(OperateToCommand command) {
        operateToCommandCounter++;
    }

    @Override
    public void visit(ICompoundCommand command) {
        iCompoundCommandCounter++;
        command.iterator().forEachRemaining((c) -> this.visit(command));
    }

    public int getOperateToCommandCounter() {
        return operateToCommandCounter;
    }

    public int getSetPositionCommandCounter() {
        return setPositionCommandCounter;
    }

    public int getICompoundCommandCounter() {
        return iCompoundCommandCounter;
    }
}
