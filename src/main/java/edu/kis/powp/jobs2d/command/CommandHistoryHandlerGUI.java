package edu.kis.powp.jobs2d.command;

import javax.swing.JTextArea;

public class CommandHistoryHandlerGUI implements CommandHistoryHandler {
    private JTextArea commandHistoryField;

    public CommandHistoryHandlerGUI(JTextArea commandHistoryField) {
        this.commandHistoryField = commandHistoryField;
    }

    @Override
    public void handleHistoryUpdate(String logEntry) {
        if (commandHistoryField != null) {
            commandHistoryField.append(logEntry);
        }
    }
}
