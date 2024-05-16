package edu.kis.powp.jobs2d.command.factory;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.builder.CompoundCommandBuilder;

public class CompoundCommandRectangleFactory {
    public static CompoundCommand getRectangleAction(String name) {
        CompoundCommandBuilder builder = new CompoundCommandBuilder();
        builder.setName(name)
                .addSetPosition(0, 0)
                .addOperateTo(0, 100)
                .addOperateTo(200, 100)
                .addOperateTo(200, 0)
                .addOperateTo(0, 0);
        return builder.build();
    }
}
