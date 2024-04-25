package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

public class DeepCopyTest {
    public static void testCompoundCommand() {
        CompoundCommand compoundCommand = getCompoundCommand();

        CompoundCommand copiedCompoundCommand;

        try {
            copiedCompoundCommand = compoundCommand.clone();
        } catch(CloneNotSupportedException e) {
            System.out.println("Failed to clone command");
            System.out.println(e);
            
            return;
        }

        if (compoundCommand.getCommands().size() == copiedCompoundCommand.getCommands().size()) {
            System.out.println("Successfully copied commands");
        } else {
            System.out.println("Incorrectly copied commands");
        }
    }

    private static CompoundCommand getCompoundCommand() {
        CompoundCommand compoundCommand = new CompoundCommand("test");

        SetPositionCommand setPositionCommand1 = new SetPositionCommand(10, 10);
        SetPositionCommand setPositionCommand2 = new SetPositionCommand(100, 100);

        OperateToCommand operateToCommand1 = new OperateToCommand(0, 0);
        OperateToCommand operateToCommand2 = new OperateToCommand(-10, -10);

        compoundCommand.addCommand(setPositionCommand1);
        compoundCommand.addCommand(setPositionCommand2);
        compoundCommand.addCommand(operateToCommand1);
        compoundCommand.addCommand(operateToCommand2);
        return compoundCommand;
    }

    public static void main(String[] args) {
        DeepCopyTest.testCompoundCommand();
    }
}
