package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.command.ExceedingCanvasCommandVisitor;
import edu.kis.powp.observer.Subscriber;
import java.util.logging.Logger;

public class CommandManagerWindowCommandChangeObserver implements Subscriber {

    private CommandManagerWindow commandManagerWindow;
    private ExceedingCanvasCommandVisitor visitor;

    public CommandManagerWindowCommandChangeObserver(CommandManagerWindow commandManagerWindow,ExceedingCanvasCommandVisitor visitor) {
        super();
        this.commandManagerWindow = commandManagerWindow;
        this.visitor = visitor;
    }

    public String toString() {
        return "Current command change observer for command manager window";
    }

    @Override
    public void update() {
        commandManagerWindow.updateCurrentCommandField();
        commandManagerWindow.getCommandManager().getCurrentCommand().accept(visitor);
        if(visitor.isExceedingCanvas()){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).warning("Command exceeds canvas");
        }

    }

}
