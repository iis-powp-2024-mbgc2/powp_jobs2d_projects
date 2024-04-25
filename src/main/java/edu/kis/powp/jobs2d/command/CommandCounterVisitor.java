package edu.kis.powp.jobs2d.command;

import java.util.Iterator;

public class CommandCounterVisitor implements CommandVisitor {
    private int operateToCount = 0;

    private int setPositionCount = 0;

    public int getOperateToCount() {
        return operateToCount;
    }

    public int getSetPositionCount() {
        return setPositionCount;
    }

    @Override
    public void visit(OperateToCommand operateToCommand) {
        if (operateToCount == 0) {
            operateToCount = 1;
            setPositionCount = 0;
        }
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        if (setPositionCount == 0) {
            setPositionCount = 1;
            operateToCount = 0;
        }
    }

    @Override
    public void visit(ICompoundCommand compoundCommand) {
        operateToCount = 0;
        setPositionCount = 0;

        int operateToCountTemp = 0;
        int setPositionCountTemp = 0;

        Iterator<DriverCommand> iterator = compoundCommand.iterator();
        while (iterator.hasNext()) {
            DriverCommand command = iterator.next();
            command.accept(this);
            operateToCountTemp += operateToCount;
            setPositionCountTemp += setPositionCount;
        }

        operateToCount = operateToCountTemp;
        setPositionCount = setPositionCountTemp;
    }
}
