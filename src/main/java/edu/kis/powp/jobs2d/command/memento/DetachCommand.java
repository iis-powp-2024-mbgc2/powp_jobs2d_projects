package edu.kis.powp.jobs2d.command.memento;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.awt.*;

public class DetachCommand implements EditCommand {
    private final CompoundCommand compoundCommand;
    private final DriverCommand selectedCommand;

    public DetachCommand(CompoundCommand compoundCommand, DriverCommand selectedCommand) {
        this.compoundCommand = compoundCommand;
        this.selectedCommand = selectedCommand;
    }

    @Override
    public void execute() {
        int index = compoundCommand.getCommands().indexOf(selectedCommand);
        SetPositionCommand setPositionCommand = new SetPositionCommand(selectedCommand.getX(), selectedCommand.getY());
        compoundCommand.addCommand(setPositionCommand, index);
        compoundCommand.removeCommand(selectedCommand);
    }

    public String toString() {
        Point position = selectedCommand.getPoint();
        return "Detach (" + position.x + ", " + position.y + ")";
    }
}
