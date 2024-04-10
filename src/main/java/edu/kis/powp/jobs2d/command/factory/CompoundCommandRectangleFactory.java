package edu.kis.powp.jobs2d.command.factory;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

public class CompoundCommandRectangleFactory {
    public static CompoundCommand getRectangleAction(String name) {
        CompoundCommand compoundCommand = new CompoundCommand(name);
        compoundCommand.addCommand(new SetPositionCommand(0, 0));
        compoundCommand.addCommand(new OperateToCommand(0, 100));
        compoundCommand.addCommand(new OperateToCommand(200, 100));
        compoundCommand.addCommand(new OperateToCommand(200, 0));
        compoundCommand.addCommand(new OperateToCommand(0, 0));
        return compoundCommand;
    }
}
