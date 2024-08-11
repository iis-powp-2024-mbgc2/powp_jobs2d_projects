package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.manager.LoggerCommandChangeObserver;
import edu.kis.powp.jobs2d.features.RecordFeature;
import edu.kis.powp.observer.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class HistoryFeatureObserver implements Subscriber {
    private CommandManager commandManager;
    public DriverCommand currentCommand;
    public List<DriverCommand> commandList = new ArrayList<>();
    LoggerCommandChangeObserver logger = new LoggerCommandChangeObserver();

    public HistoryFeatureObserver(CommandManager commandManager) {
        super();
        this.commandManager = commandManager;
    }

    public String toString() {
        return "Command history observer for command history window";
    }

    public void addToCommandList(DriverCommand command) {
        commandList.add(command);
        logger.update("Recorded command added to history");
    }

    @Override
    public void update() {
        this.currentCommand = commandManager.getCurrentCommand();
        logger.update("Command set from history to: " + currentCommand.toString());

         this.addToCommandList(RecordFeature.getRecordedCommand());
    }
}
