package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.visitor.CommandVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class CompoundCommand implements ICompoundCommand, Cloneable {
    private List<DriverCommand> commands = new ArrayList<>();
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
    public void execute(IVisitableDriver driver) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompoundCommand that = (CompoundCommand) o;
        boolean equal = true;
        for(int i = 0; i < commands.size(); i++){
            if(!commands.get(i).equals(that.getCommands().get(i))){
                equal = false;
            }
        }
        return equal;
    }

    @Override
    public CompoundCommand clone() {
        List<DriverCommand> clonedCommands = new ArrayList<>();
        for (DriverCommand command : commands) {
            if (command != null) {
                try {
                    clonedCommands.add(command.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return new CompoundCommand(clonedCommands, name);
    }
}
