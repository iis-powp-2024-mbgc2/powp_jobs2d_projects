package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.visitor.CommandVisitor;
import edu.kis.powp.jobs2d.command.visitor.DeepCopyVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public final class ImmutableCompoundCommand implements ICompoundCommand {
    private final List<DriverCommand> commands;
    private final String name;

    public ImmutableCompoundCommand(List<DriverCommand> commands, String name) {
        this.commands = deepCopyCommands(commands);
        this.name = name;
    }

    @Override
    public void execute(IVisitableDriver driver) {
        for (DriverCommand command : commands) {
            command.execute(driver);
        }
    }

    @Override
    public void accept(CommandVisitor commandVisitor) {
        commandVisitor.visit(this);
    }

    @Override
    public Iterator<DriverCommand> iterator() {
        return deepCopyCommands(this.commands).iterator();
    }

    @Override
    public String toString() {
        return name;
    }

    public List<DriverCommand> getCommands() {
        return deepCopyCommands(this.commands);
    }

    private List<DriverCommand> deepCopyCommands(List<DriverCommand> commands) {
        List<DriverCommand> deepCopiedCommands = new ArrayList<>();
        DeepCopyVisitor deepCopyVisitor = new DeepCopyVisitor();
        for (DriverCommand command : commands) {
            command.accept(deepCopyVisitor);
            deepCopiedCommands.add(deepCopyVisitor.getCopiedCommand());
        }
        return Collections.unmodifiableList(deepCopiedCommands);
    }
}
