package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.factory.CompoundCommandFactory;

import java.util.ArrayList;
import java.util.List;

public class DeepCopyTest {
    public static void testCompoundCommand() {
        CompoundCommand compoundCommand = CompoundCommandFactory.getGlasses("Glass");

        CompoundCommand copiedCommand = compoundCommand.clone();

        if (compoundCommand.equals(copiedCommand)) {
            System.out.println("Successfully copied commands");
        } else {
            System.out.println("Incorrectly copied commands");
        }
    }

    public static void main(String[] args) {
        DeepCopyTest.testCompoundCommand();
    }
}
