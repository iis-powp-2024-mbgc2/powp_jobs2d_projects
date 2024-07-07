package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandHistoryLogger {
    private final CommandManager commandManager;

    public CommandHistoryLogger(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void logCurrentCommand() {
        String currentCommand = commandManager.getCurrentCommandString();
        String timestamp = getCurrentTimestamp();
        String logEntry = String.format("%s - %s\n", timestamp, currentCommand);
        updateHistoryField(logEntry);
    }

    private String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    private void updateHistoryField(String logEntry) {
        CommandManagerWindow commandManagerWindow = CommandManagerWindow.getInstance();
        if (commandManagerWindow != null) {
            commandManagerWindow.getCommandHistoryField().append(logEntry);
        }
    }
}
