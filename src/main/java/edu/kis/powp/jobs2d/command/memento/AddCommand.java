package edu.kis.powp.jobs2d.command.memento;

import edu.kis.powp.jobs2d.command.*;

import java.awt.*;

public class AddCommand implements EditCommand {
    private final CompoundCommand compoundCommand;
    private PointCommand selectedCommand;
    private final Point position;

    public AddCommand(CompoundCommand compoundCommand, PointCommand selectedCommand, Point position) {
        this.compoundCommand = compoundCommand;
        this.selectedCommand = selectedCommand;
        this.position = position;
    }

    @Override
    public void execute() {
        int index;
        if (selectedCommand == null) {
            index = compoundCommand.getCommands().toArray().length;
            SetPositionCommand setPositionCommand = new SetPositionCommand(position.x, position.y);
            compoundCommand.addCommand(setPositionCommand, index);
            index++;
        } else {
            index = compoundCommand.getCommands().indexOf(selectedCommand) + 1;
        }
        OperateToCommand operateToCommand = new OperateToCommand(position.x, position.y);

        selectedCommand = operateToCommand;
        compoundCommand.addCommand(operateToCommand, index);
    }

    public String toString() {
        return "Add (" + position.x + ", " + position.y + ")";
    }
}
