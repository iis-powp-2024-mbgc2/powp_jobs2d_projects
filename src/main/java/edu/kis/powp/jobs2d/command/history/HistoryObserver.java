package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.jobs2d.command.history.CommandHistory;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.observer.Subscriber;

public class HistoryObserver implements Subscriber {

    private final ICommandManager commandManagerWindow;
    public HistoryObserver(ICommandManager commandManagerWindow) {
        super();
        this.commandManagerWindow = commandManagerWindow;
    }

    @Override
    public void update()
    {
        updateHistory();
    }

    public void updateHistory()
    {
        CommandHistory history = new CommandHistory();
        history.add(commandManagerWindow.getCurrentCommand());
    }
}
