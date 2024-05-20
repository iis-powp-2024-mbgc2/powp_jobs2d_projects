package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.command.DriverCommand;
//import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class DeepCopyVisitorSaveTest implements ActionListener {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Override
    public void actionPerformed(ActionEvent e) {
        ICommandManager commandManager = CommandsFeature.getCommandManager();
        DriverCommand driverCommand;
        if ((driverCommand = commandManager.getCurrentCommand()) != null)
            driverCommand.accept(DeepCopyVisitorTest.getDeepCopy());
        else
            logger.warning("Cannot save deep copy of empty command");
    }
}
