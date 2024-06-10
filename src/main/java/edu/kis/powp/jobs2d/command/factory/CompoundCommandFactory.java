package edu.kis.powp.jobs2d.command.factory;

import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.jobs2d.command.builder.CompoundCommandBuilder;


public class CompoundCommandFactory {
    public static ImmutableCompoundCommand getRectangleAction(String name) {
        CompoundCommandBuilder builder = new CompoundCommandBuilder();
        builder.setName(name)
                .addSetPosition(0, 0)
                .addOperateTo(0, 100)
                .addOperateTo(200, 100)
                .addOperateTo(200, 0)
                .addOperateTo(0, 0);
        return builder.build();
    }

    public static ImmutableCompoundCommand getTriangleAction(String name) {
        CompoundCommandBuilder builder = new CompoundCommandBuilder();
        builder.setName(name)
                .addSetPosition(0, 100)
                .addOperateTo(200, 50)
                .addOperateTo(0, 0)
                .addOperateTo(0, 100);
        return builder.build();
    }

    public static ImmutableCompoundCommand getSecretAction(String name) {
        CompoundCommandBuilder builder = new CompoundCommandBuilder();
        builder.setName(name)
                .addSetPosition(-20, -50)
                .addOperateTo(-20, -50)
                .addSetPosition(-20, -40)
                .addOperateTo(-20, 50)
                .addSetPosition(0, -50)
                .addOperateTo(0, -50)
                .addSetPosition(0, -40)
                .addOperateTo(0, 50)
                .addSetPosition(70, -50)
                .addOperateTo(20, -50)
                .addOperateTo(20, 0)
                .addOperateTo(70, 0)
                .addOperateTo(70, 50)
                .addOperateTo(20, 50);
        return builder.build();
    }

    public static ImmutableCompoundCommand getDeeplyComplexAction(String name) {
        ImmutableCompoundCommand rectangleCompoundCommand = CompoundCommandFactory.getRectangleAction("Rectangle command");
        ImmutableCompoundCommand triangleCompoundCommand = CompoundCommandFactory.getTriangleAction("Triangle command");

        CompoundCommandBuilder builder = new CompoundCommandBuilder();
        builder.setName(name)
                .addCommand(rectangleCompoundCommand)
                .addCommand(triangleCompoundCommand);
        return builder.build();
    }
}
