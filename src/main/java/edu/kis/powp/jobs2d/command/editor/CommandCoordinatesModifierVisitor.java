package edu.kis.powp.jobs2d.command.editor;

import edu.kis.powp.jobs2d.command.*;

public class CommandCoordinatesModifierVisitor implements CommandVisitor {
    private DriverCommand command;
    private final int x, y;

    public CommandCoordinatesModifierVisitor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public DriverCommand getCommand() {
        return command;
    }

    @Override
    public void visit(OperateToCommand operateToCommand) {
        command = new OperateToCommand(this.x, this.y);
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        command = new SetPositionCommand(this.x, this.y);
    }

    @Override
    public void visit(ICompoundCommand compoundCommand) {

    }
}
