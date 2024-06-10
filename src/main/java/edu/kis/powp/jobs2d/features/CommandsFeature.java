package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.command.history.HistoryObserver;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.command.manager.LoggerCommandChangeObserver;

public class CommandsFeature {

    private static ICommandManager commandManager;

    public static void setupCommandManager() {
        commandManager = new CommandManager();

        LoggerCommandChangeObserver loggerObserver = new LoggerCommandChangeObserver();
        commandManager.getChangePublisher().addSubscriber(loggerObserver);
        HistoryObserver historyObserver = new HistoryObserver(commandManager);
        CommandsFeature.getCommandManager().getChangePublisher().addSubscriber(historyObserver);
    }
    /**
     * Get manager of application driver command.
     *
     * @return plotterCommandManager.
     */
    public static ICommandManager getCommandManager() {
        return commandManager;
    }
}
