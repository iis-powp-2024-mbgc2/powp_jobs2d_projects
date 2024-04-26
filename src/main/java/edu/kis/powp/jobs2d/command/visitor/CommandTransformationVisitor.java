package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.*;
import edu.kis.powp.jobs2d.transformations.Transformation;

import java.awt.Point;

public class CommandTransformationVisitor implements CommandVisitor {

    private final CompoundCommand transformedCommands;
    private final Transformation transformation;

    public CompoundCommand getTransformedCommand() {
        return transformedCommands;
    }

    public CommandTransformationVisitor(String commandName, Transformation transformation) {
        String newName = commandName + "_" + transformation.getName();
        this.transformedCommands = new CompoundCommand(newName);
        this.transformation = transformation;
    }

    @Override
    public void visit(OperateToCommand operateToCommand){
        Point point = new Point(operateToCommand.getX(), operateToCommand.getY());
        applyTransformation(point, OperateToCommand::new);
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand){
        Point point = new Point(setPositionCommand.getX(), setPositionCommand.getY());
        applyTransformation(point, SetPositionCommand::new);
    }

    @Override
    public void visit(ICompoundCommand compoundCommand) {
        compoundCommand.iterator().forEachRemaining(command -> command.accept(this));
    }

    private void applyTransformation(Point point, CommandCreator commandCreator) {
        Point transformedPoint = transformation.transform(point);
        add(commandCreator.create(transformedPoint.x, transformedPoint.y));
    }

    private void add(DriverCommand command) {
        transformedCommands.addCommand(command);
    }

    @Override
    public String toString() {
        return transformedCommands.toString();
    }

    /**
     * Functional interface for OperationToCommand and SetPositionCommand constructors.
     */
    @FunctionalInterface
    private interface CommandCreator {
        DriverCommand create(int x, int y);
    }
}
