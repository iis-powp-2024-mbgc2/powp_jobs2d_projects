package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.command.ExceedingCanvasCommandVisitor;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class ExceedingCanvasCommandVisitorTest implements ActionListener {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Override
    public void actionPerformed(ActionEvent e) {
        CommandManager commandManager = CommandsFeature.getCommandManager();

        ExceedingCanvasCommandVisitor visitor = new ExceedingCanvasCommandVisitor();
        commandManager.getCurrentCommand().accept(visitor);
    }

}

