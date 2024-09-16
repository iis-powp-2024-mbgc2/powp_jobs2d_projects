package edu.kis.powp.jobs2d.command;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

public class CommandHistoryHandlerGUI implements CommandHistoryHandler {
    private JPanel commandHistoryField;

    public CommandHistoryHandlerGUI(JPanel commandHistoryField) {
        this.commandHistoryField = commandHistoryField;
        this.commandHistoryField.setLayout(new BoxLayout(commandHistoryField, BoxLayout.Y_AXIS));
    }

    @Override
    public void handleHistoryUpdate(String logEntry) {
        if (commandHistoryField != null) {
            JPanel entryPanel = new JPanel(new BorderLayout());

            JLabel logLabel = new JLabel(logEntry);
            entryPanel.add(logLabel, BorderLayout.CENTER);

            JButton btnR = new JButton("R");
            int commandIndex = commandHistoryField.getComponentCount();
            btnR.addActionListener((ActionEvent e) -> redoCommand(commandIndex));
            entryPanel.add(btnR, BorderLayout.EAST);

            commandHistoryField.add(entryPanel);

            commandHistoryField.revalidate();
            commandHistoryField.repaint();
        }
    }

    private void redoCommand(int commandIndex) {
        System.out.println("Redo command with ID: " + commandIndex);
    }
}
