package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.HistoryObserver;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.command.manager.LoggerCommandChangeObserver;

import java.awt.event.ActionListener;

public class CommandsFeature {

    private static ICommandManager commandManager;
    private static Application app;

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

    public static void setupPresetCommands(Application application) {
        app = application;
        app.addComponentMenu(CommandsFeature.class, "Commands");
    }

    public static void addCommand(String name, ActionListener listener) {
        app.addComponentMenuElement(CommandsFeature.class, name, listener);
    }
}
