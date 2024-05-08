package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class CompoundCommand implements ICompoundCommand {
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

    public boolean checkBoundaries()
    {
        for(DriverCommand command : commands)
        {
            if(command instanceof OperateToCommand)
            {
                int x = ((OperateToCommand) command).getX();
                int y = ((OperateToCommand) command).getY();

                if(!CommandFormat.checkY(y) || !CommandFormat.checkX(x))
                {
                    return false;
                }

            }
            if(command instanceof SetPositionCommand)
            {
                int x = ((SetPositionCommand) command).getX();
                int y = ((SetPositionCommand) command).getY();

                if(!CommandFormat.checkY(y) || !CommandFormat.checkX(x))
                {
                    return false;
                }
            }
        }
        return true;
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
}
