package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.history.CommandHistory;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.observer.Subscriber;

import javax.swing.*;

public class HistoryObserver implements Subscriber {

    private final ICommandManager commandManagerWindow;
    public HistoryObserver(ICommandManager commandManagerWindow) {
        super();
        this.commandManagerWindow = commandManagerWindow;
    }

    @Override
    public void update()
    {
        CommandHistory history = new CommandHistory();
        history.add(commandManagerWindow.getCurrentCommand());
        HistoryComboBox historyComboBox = new HistoryComboBox();
        historyComboBox.actualize();
    }

}
