package edu.kis.powp.jobs2d.command.factory;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.builder.CompoundCommandBuilder;

import java.util.ArrayList;

public class CompoundCommandFactory {
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

    public static CompoundCommand getRect(String name, int x, int y, int width, int height) {
        CompoundCommandBuilder builder = new CompoundCommandBuilder();
        builder.setName(name)
                .addSetPosition(x, y)
                .addOperateTo(x, y + height)
                .addOperateTo(x + width, y + height)
                .addOperateTo(x + width, y)
                .addOperateTo(x, y);
        return builder.build();
    }

    public static CompoundCommand getGlasses(String name) {

        CompoundCommandBuilder builder = new CompoundCommandBuilder();
        builder.setName(name).addCommand(CompoundCommandFactory.getRect("Left",0,0,50, 50))
                .addCommand(CompoundCommandFactory.getRect("middle",50,0,20, 1))
                        .addCommand(CompoundCommandFactory.getRect("Right",70,0,50, 50));

        return builder.build();
    }


}
