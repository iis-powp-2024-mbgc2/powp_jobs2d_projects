package edu.kis.powp.jobs2d.command.manager;

import java.util.Iterator;
import java.util.List;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.CommandVisitor;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.editor.CommandCoordinatesModifierVisitor;
import edu.kis.powp.observer.Publisher;

/**
 * Command Manager.
 */
public class CommandManager implements ICommandManager {
    private DriverCommand currentCommand = null;

    private Publisher changePublisher = new Publisher();

    /**
     * Set current command.
     *
     * @param commandList Set the command as current.
     */
    public synchronized void setCurrentCommand(DriverCommand commandList) {
        this.currentCommand = commandList;
        changePublisher.notifyObservers();
    }

    /**
     * Set current command.
     *
     * @param commandList list of commands representing a compound command.
     * @param name        name of the command.
     */
    public synchronized void setCurrentCommand(List<DriverCommand> commandList, String name) {
        setCurrentCommand(new ICompoundCommand() {

            List<DriverCommand> driverCommands = commandList;

            @Override
            public void execute(Job2dDriver driver) {
                driverCommands.forEach((c) -> c.execute(driver));
            }

            @Override
            public void accept(CommandVisitor commandVisitor) {
                commandVisitor.visit(this);
            }

            @Override
            public Iterator<DriverCommand> iterator() {
                return driverCommands.iterator();
            }

            @Override
            public void interchangeCommands(int x, int y) {
                if (commandList.isEmpty()) {
                    throw new IndexOutOfBoundsException("Compound command is empty");
                }

                if (x >= commandList.size() || x < 0 || y >= commandList.size() || y < 0) {
                    throw new IndexOutOfBoundsException("Index out of bounds");
                }

                DriverCommand temp = commandList.get(x);

                commandList.set(x, commandList.get(y));
                commandList.set(y, temp);
            }

            @Override
            public void modifyCoordinates(int commandIndex, int x, int y) {
                if (commandList.isEmpty()) {
                    throw new IndexOutOfBoundsException("Compound command is empty");
                }

                if (commandIndex >= commandList.size() || commandIndex < 0) {
                    throw new IndexOutOfBoundsException("Index out of bounds");
                }

                DriverCommand command = commandList.get(commandIndex);

                CommandCoordinatesModifierVisitor commandCoordinatesModifierVisitor = new CommandCoordinatesModifierVisitor(x, y);

                command.accept(commandCoordinatesModifierVisitor);

                commandList.set(commandIndex, commandCoordinatesModifierVisitor.getCommand());
            }

            @Override
            public String toString() {
                return name;
            }
        });

    }

    @Override
    public synchronized void runCommand(Job2dDriver driver) {
        this.currentCommand.execute(driver);
    }

    /**
     * Return current command.
     *
     * @return Current command.
     */
    public synchronized DriverCommand getCurrentCommand() {
        return currentCommand;
    }

    public synchronized void clearCurrentCommand() {
        currentCommand = null;
    }

    public synchronized String getCurrentCommandString() {
        if (getCurrentCommand() == null) {
            return "No command loaded";
        } else
            return getCurrentCommand().toString();
    }

    public Publisher getChangePublisher() {
        return changePublisher;
    }


}
