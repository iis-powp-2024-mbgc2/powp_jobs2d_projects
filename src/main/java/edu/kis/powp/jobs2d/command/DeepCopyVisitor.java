package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeepCopyVisitor implements CommandVisitor {

    private DriverCommand copiedCommand;

    public DriverCommand getCopiedCommand() {
        return copiedCommand;
    }

    @Override
    public void visit(OperateToCommand operateToCommand) {
        copiedCommand = new OperateToCommand(operateToCommand.getX(), operateToCommand.getY());
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        copiedCommand = new SetPositionCommand(setPositionCommand.getX(), setPositionCommand.getY());
    }

    @Override
    public void visit(ICompoundCommand compoundCommand) {
        List<DriverCommand> commands = new ArrayList<>();
        for (Iterator<DriverCommand> it = compoundCommand.iterator(); it.hasNext(); ) {
            DriverCommand command = it.next();
            command.accept(this);
            commands.add(this.getCopiedCommand());
        }
        copiedCommand = new CompoundCommand(commands, "Copy of " + compoundCommand);

    }

}
