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


    public CommandHistoryLogger(CommandManager commandManager, CommandHistoryHandler historyHandler) {
        this.commandManager = commandManager;
        this.historyHandler = historyHandler;
    }

    public void logCurrentCommand() {
        DriverCommand currentCommand = commandManager.getCurrentCommand();
        if (currentCommand == null) return;
        String currentCommandStr = commandManager.getCurrentCommandString();
        String timestamp = getCurrentTimestamp();
        String logEntry = String.format("%s - %s\n", timestamp, currentCommandStr);
        historyHandler.handleHistoryUpdate(currentCommand,logEntry);
    }


    private String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }


}