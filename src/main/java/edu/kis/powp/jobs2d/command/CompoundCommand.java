package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class CompoundCommand implements ICompoundCommand {
    private List<DriverCommand> commands = new ArrayList<>();
    transient private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final String name;

    public CompoundCommand(String name) {
        this.name = name;
    }

    public CompoundCommand(List<DriverCommand> commands, String name) {
        this.commands = commands;
        this.name = name;
    }

    public void addCommand(DriverCommand command) {
        commands.add(command);
    }

    public void addCommand(DriverCommand command, int index) {
        try {
            commands.add(index, command);
        } catch (IndexOutOfBoundsException e) {
            logger.warning("Index out of bounds");
        }
    }

    public void removeCommand(DriverCommand command) {
        commands.remove(command);
    }

    public void removeCommand(int index) {
        try {
            commands.remove(index);
        } catch (IndexOutOfBoundsException e) {
            logger.warning("Index out of bounds");
        }
    }

    public void clearCommand() {
        commands.clear();
    }

    public List<DriverCommand> getCommands() {
        return commands;
    }

    @Override
    public void execute(Job2dDriver driver) {
        iterator().forEachRemaining((c) -> c.execute(driver));
    }

    @Override
    public void accept(CommandVisitor commandVisitor) {
        commandVisitor.visit(this);
    }

    @Override
    public Iterator<DriverCommand> iterator() {
        return commands.iterator();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int getX() {
        return commands.get(0).getX();
    }
    @Override
    public int getY() {
        return commands.get(0).getY();
    }

    @Override
    public void setX(int x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setY(int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Point getPoint() {
        return new Point(getX(), getY());
    }

    public void replace(CompoundCommand compoundCommand) {
        this.commands = compoundCommand.getCommands();
    }
}
