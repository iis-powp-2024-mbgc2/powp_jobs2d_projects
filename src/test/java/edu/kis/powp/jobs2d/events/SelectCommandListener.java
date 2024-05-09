package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.factory.CompoundCommandFactory;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.enums.Command;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.RecordFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectCommandListener implements ActionListener {
    Command command;
    public SelectCommandListener(Command command) {
        this.command = command;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CommandManager manager = CommandsFeature.getCommandManager();
        switch (command) {
            case RECTANGLE:
                manager.setCurrentCommand(CompoundCommandFactory.getRectangleAction("RectangleCommand"));
                break;
            case DEEPLY_COMPLEX:
                CompoundCommand deeplyCompoundCommand = CompoundCommandFactory.getDeeplyComplexAction("Deeply compound command");
                manager.setCurrentCommand(deeplyCompoundCommand.getCommands(), "Deeply complex command");
                break;
            case RECORDED:
                manager.setCurrentCommand(RecordFeature.getRecordedCommand());
                break;
            case SECRET:
                CompoundCommand secretCommand = CompoundCommandFactory.getSecretAction("TopSecretCommand");
                manager.setCurrentCommand(secretCommand.getCommands(), "Top Secret Command");
                break;
        }
    }
}
