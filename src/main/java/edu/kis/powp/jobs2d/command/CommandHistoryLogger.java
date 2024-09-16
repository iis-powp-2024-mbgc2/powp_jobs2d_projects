package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.manager.CommandManager;

import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommandHistoryLogger {
    private final CommandManager commandManager;
    private final CommandHistoryHandler historyHandler;

    List<DriverCommand> commands = new ArrayList<>();



    public CommandHistoryLogger(CommandManager commandManager, CommandHistoryHandler historyHandler) {
        this.commandManager = commandManager;
        this.historyHandler = historyHandler;
    }

    public void logCurrentCommand() {
        String currentCommand = commandManager.getCurrentCommandString();
        String timestamp = getCurrentTimestamp();
        String logEntry = String.format("%s - %s\n", timestamp, currentCommand);
        historyHandler.handleHistoryUpdate(logEntry);
        commands.add(commandManager.getCurrentCommand());
    }

    public void redoCommand(int positionFromTop)
    {
        commandManager.setCurrentCommand(commands.get(positionFromTop));
    }

    private String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}