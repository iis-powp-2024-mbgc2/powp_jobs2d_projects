package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.command.DeepCopyVisitor;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeepCopyVisitorTest implements ActionListener {
    private static final DeepCopyVisitor deepCopy = new DeepCopyVisitor();
    @Override
    public void actionPerformed(ActionEvent e) {
        CommandManager manager = CommandsFeature.getCommandManager();
        manager.setCurrentCommand(deepCopy.getCopiedCommand());
    }

    public static DeepCopyVisitor getDeepCopy() {
        return deepCopy;
    }
}
