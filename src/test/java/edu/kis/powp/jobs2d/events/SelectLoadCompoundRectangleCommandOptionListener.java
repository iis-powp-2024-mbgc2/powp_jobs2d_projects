package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.factory.CompoundCommandRectangleFactory;
//import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectLoadCompoundRectangleCommandOptionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ICommandManager manager = CommandsFeature.getCommandManager();
        manager.setCurrentCommand(CompoundCommandRectangleFactory.getRectangleAction("RectangleCommand"));
    }
}