package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.RecordFeature;

public class SelectLoadRecordedCommandOptionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        CommandManager manager = CommandsFeature.getCommandManager();
        manager.setCurrentCommand(RecordFeature.getRecordedCommand());
    }
}
