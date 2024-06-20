package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.factory.CompoundCommandFactory;

import java.util.List;

public class DeepCopyTest {
    public static void testCompoundCommand() {
        CompoundCommand compoundCommand = CompoundCommandFactory.getGlasses("Glasses");

        CompoundCommand copiedCompoundCommand;

        copiedCompoundCommand = compoundCommand.clone();

        List<DriverCommand> originalCommands = compoundCommand.getCommands();
        List<DriverCommand> copiedCommands = copiedCompoundCommand.getCommands();

        boolean equal = true;
        if (originalCommands.size() == copiedCommands.size()) {
            for (int i = 0; i < originalCommands.size(); i++) {
                if (!originalCommands.get(i).equals(copiedCommands.get(i))) {
                    equal = false;
                    break;
                }
            }
        } else {
            equal = false;
        }

        if (equal) {
            System.out.println("Successfully copied commands");
        } else {
            System.out.println("Incorrectly copied commands");
        }
    }

    public static void main(String[] args) {
        DeepCopyTest.testCompoundCommand();
    }
}
