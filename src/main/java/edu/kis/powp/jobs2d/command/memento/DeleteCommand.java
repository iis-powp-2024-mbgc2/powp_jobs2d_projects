package edu.kis.powp.jobs2d.command.memento;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;

import java.awt.*;

public class DeleteCommand implements EditCommand {
    private final CompoundCommand compoundCommand;
    private final DriverCommand selectedCommand;

    public DeleteCommand(CompoundCommand compoundCommand, DriverCommand selectedCommand) {
        this.compoundCommand = compoundCommand;
        this.selectedCommand = selectedCommand;
    }

    @Override
    public void execute() {
        compoundCommand.removeCommand(selectedCommand);
    }

    public String toString() {
        Point position = selectedCommand.getPoint();
        return "Delete (" + position.x + ", " + position.y + ")";
    }
}
