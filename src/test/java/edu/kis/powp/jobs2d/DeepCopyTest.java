package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.util.List;

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

    private static CompoundCommand getCompoundCommand() {
        CompoundCommand compoundCommand = new CompoundCommand("glassesTest");

        // rysowanie okularów
        SetPositionCommand leftTop = new SetPositionCommand(-50, 0);
        OperateToCommand leftBottom = new OperateToCommand(-50, -100);
        OperateToCommand leftBridge = new OperateToCommand(-20, -100);

        SetPositionCommand rightTop = new SetPositionCommand(50, 0);
        OperateToCommand rightBottom = new OperateToCommand(50, -100);
        OperateToCommand rightBridge = new OperateToCommand(20, -100);

        OperateToCommand topBridge = new OperateToCommand(20, 0);
        OperateToCommand bottomBridge = new OperateToCommand(-20, 0);

        compoundCommand.addCommand(leftTop);
        compoundCommand.addCommand(leftBottom);
        compoundCommand.addCommand(leftBridge);
        compoundCommand.addCommand(rightTop);
        compoundCommand.addCommand(rightBottom);
        compoundCommand.addCommand(rightBridge);
        compoundCommand.addCommand(topBridge);
        compoundCommand.addCommand(bottomBridge);

        return compoundCommand;
    }

    public static void main(String[] args) {
        DeepCopyTest.testCompoundCommand();
    }
}
