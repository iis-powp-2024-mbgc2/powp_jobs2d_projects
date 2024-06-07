package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.command.canvas.CanvasVisitor;
import edu.kis.powp.observer.Subscriber;

import java.util.logging.Logger;

public class CommandManagerWindowCommandChangeObserver implements Subscriber {

    private CommandManagerWindow commandManagerWindow;
    private CanvasVisitor visitor;
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public CommandManagerWindowCommandChangeObserver(CommandManagerWindow commandManagerWindow, CanvasVisitor visitor) {
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
        if(visitor.getIsOutOfBounds())
            logger.warning("Commands are out of bounds.");
    }

}
