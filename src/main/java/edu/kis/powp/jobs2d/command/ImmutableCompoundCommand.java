package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import java.util.*;

public final class ImmutableCompoundCommand implements ICompoundCommand {
    private final List<DriverCommand> commands;
    private final String name;

    public ImmutableCompoundCommand(List<DriverCommand> commands, String name) {
        this.commands = deepCopyCommands(commands);
        this.name = name;
    }

    public List<DriverCommand> getCommands() {
        return deepCopyCommands(commands);
    }

    private List<DriverCommand> deepCopyCommands(List<DriverCommand> originalCommands) {
        DeepCopyVisitor deepCopy = new DeepCopyVisitor();
        List<DriverCommand> copiedCommands = new ArrayList<>();
        for (DriverCommand command : originalCommands) {
            command.accept(deepCopy);
            copiedCommands.add(deepCopy.getCopiedCommand());
        }
        return copiedCommands;
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