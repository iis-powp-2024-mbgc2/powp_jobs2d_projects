package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.util.logging.Logger;

public class HistoryFeature {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final CommandHistory history = new CommandHistory();

    public static void setupHistory(Application application) {
        application.addComponentMenu(HistoryFeature.class, "History");
        application.addComponentMenuElement(HistoryFeature.class, "Show history", e -> {
            if (history.isEmpty())
                logger.info("History is empty.");
            else
                for(String log: history.getActionsHistorySummary())
                    logger.info(log);
        });
        application.addComponentMenuElement(HistoryFeature.class, "Clear history", e -> {
            history.clear();
        });
        application.addComponentMenuElement(HistoryFeature.class, "Load history", e -> {
            ICommandManager commandManager = CommandsFeature.getCommandManager();
            DriverManager driverManager = new DriverManager();
            for(DriverCommand command: history.getActionsHistory())
            {
                commandManager.setCurrentCommand(command);
                commandManager.runCommand(driverManager.getCurrentDriver());
            }
        });

    }
}
