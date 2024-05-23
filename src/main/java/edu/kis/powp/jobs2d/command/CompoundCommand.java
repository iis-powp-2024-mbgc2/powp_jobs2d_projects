package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import java.util.*;

public final class CompoundCommand implements ICompoundCommand {
    private final List<DriverCommand> commands;
    private final String name;

    public CompoundCommand(List<DriverCommand> commands, String name) {
        this.commands = new ArrayList<>(commands);
        this.name = name;
    }

    public List<DriverCommand> getCommands() {
        return Collections.unmodifiableList(commands);
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
