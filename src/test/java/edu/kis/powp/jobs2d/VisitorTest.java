package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.command.*;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.logging.Logger;

public class VisitorTest implements ActionListener {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Override
    public void actionPerformed(ActionEvent e) {
        CommandManager commandManager = CommandsFeature.getCommandManager();

        CommandCounterVisitor commandCounter = new CommandCounterVisitor();

        commandManager.getCurrentCommand().accept(commandCounter);

        logger.info("OperateTo commands: " + commandCounter.getOperateToCount());
        logger.info("SetPosition commands: " + commandCounter.getSetPositionCount());
    }

}
