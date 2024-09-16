package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.manager.CommandManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandHistoryLogger {
    private final CommandManager commandManager;
    private final CommandHistoryHandler historyHandler;

    public CommandHistoryLogger(CommandManager commandManager, CommandHistoryHandler historyHandler) {
        this.commandManager = commandManager;
        this.historyHandler = historyHandler;
    }

    public void logCurrentCommand() {
        String currentCommand = commandManager.getCurrentCommandString();
        String timestamp = getCurrentTimestamp();
        String logEntry = String.format("%s - %s\n", timestamp, currentCommand);
        historyHandler.handleHistoryUpdate(logEntry);
    }

    private String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
