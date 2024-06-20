package edu.kis.powp.jobs2d.command.memento;

import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.observer.Subscriber;

public class EditHistoryObserver implements Subscriber {


    private CommandManagerWindow commandManagerWindow;

    public EditHistoryObserver(CommandManagerWindow commandManagerWindow) {
        super();
        this.commandManagerWindow = commandManagerWindow;
    }

    public String toString() {
        return "Editing command observer for command manager window";
    }

    @Override
    public void update() {
        commandManagerWindow.updateHistory();
    }

}