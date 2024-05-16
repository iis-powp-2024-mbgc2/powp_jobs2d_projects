package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.awt.Point;
import java.util.Iterator;

public class CommandStatisticVisitor implements CommandVisitor {
    private double totalLength = 0;

    private double operateToLength = 0;

    private Point previousCoordinates = new Point();
    private Point currentCoordinates = new Point();
    private boolean currentSetPosition = false;
    private boolean previousSetPosition = false;

    public double getOperateToLength() {
        return operateToLength;
    }

    public double getTotalLength() {
        return totalLength;
    }

    @Override
    public void visit(OperateToCommand operateToCommand) {
        previousSetPosition = currentSetPosition;
        currentSetPosition = false;
        previousCoordinates.setLocation(currentCoordinates);
        currentCoordinates.setLocation(operateToCommand.getX(), operateToCommand.getY());
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        previousSetPosition = currentSetPosition;
        currentSetPosition = true;
        previousCoordinates.setLocation(currentCoordinates);
        currentCoordinates.setLocation(setPositionCommand.getX(), setPositionCommand.getY());
    }

    @Override
    public void visit(ICompoundCommand compoundCommand) {
        double totalLength = 0;
        double operateToLength = 0;

        Iterator<DriverCommand> iterator = compoundCommand.iterator();
        while (iterator.hasNext()) {

            DriverCommand command = iterator.next();
            command.accept(this);
            if (!currentSetPosition) {
                totalLength += Math.sqrt(Math.pow(currentCoordinates.getX() - previousCoordinates.getX(), 2) +
                        Math.pow(currentCoordinates.getY() - previousCoordinates.getY(), 2));
                if (!previousSetPosition && !currentSetPosition) {
                    operateToLength += Math.sqrt(Math.pow(currentCoordinates.getX() - previousCoordinates.getX(), 2) +
                            Math.pow(currentCoordinates.getY() - previousCoordinates.getY(), 2));
                }
            }
        }

        this.totalLength += totalLength;
        this.operateToLength += operateToLength;
        this.currentSetPosition = true;
    }
}
