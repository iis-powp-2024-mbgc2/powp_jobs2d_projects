package edu.kis.powp.jobs2d.command;

import java.util.Iterator;
import java.util.Vector;

public class CommandStatisticVisitor implements CommandVisitor {
    private double totalLength = 0;
    private Vector<Integer> previousCoordinates = null;
    private Vector<Integer> currentCoordinates = null;
    private int operateToCount = 0;

    private int setPositionCount = 0;

    public double getTotalLength() {
        return totalLength;
    }

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
        previousCoordinates = currentCoordinates;
        currentCoordinates = new Vector<>();
        currentCoordinates.add(operateToCommand.getX());
        currentCoordinates.add(operateToCommand.getY());
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        if (setPositionCount == 0) {
            setPositionCount = 1;
            operateToCount = 0;
        }
        previousCoordinates = currentCoordinates;
        currentCoordinates = new Vector<>();
        currentCoordinates.add(setPositionCommand.getX());
        currentCoordinates.add(setPositionCommand.getY());
    }

    @Override
    public void visit(ICompoundCommand compoundCommand) {
        operateToCount = 0;
        setPositionCount = 0;

        totalLength = 0;

        int operateToCountTemp = 0;
        int setPositionCountTemp = 0;

        Iterator<DriverCommand> iterator = compoundCommand.iterator();
        while (iterator.hasNext()) {
            DriverCommand command = iterator.next();
            command.accept(this);
            operateToCountTemp += operateToCount;
            setPositionCountTemp += setPositionCount;
            if (currentCoordinates != null && previousCoordinates != null) {
                totalLength += Math.sqrt(Math.pow(currentCoordinates.get(0) - previousCoordinates.get(0), 2) +
                        Math.pow(currentCoordinates.get(1) - previousCoordinates.get(1), 2));
            }
        }

        operateToCount = operateToCountTemp;
        setPositionCount = setPositionCountTemp;
    }
}
