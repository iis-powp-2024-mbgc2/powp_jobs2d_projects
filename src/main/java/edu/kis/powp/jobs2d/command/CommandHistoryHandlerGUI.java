package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.manager.CommandManager;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class CommandHistoryHandlerGUI implements CommandHistoryHandler {
    private JPanel commandHistoryField;

    List<DriverCommand> commands = new ArrayList<>();
    CommandManager commandManager;

    public CommandHistoryHandlerGUI(JPanel commandHistoryField, CommandManager manager) {
        this.commandHistoryField = commandHistoryField;
        this.commandHistoryField.setLayout(new BoxLayout(commandHistoryField, BoxLayout.Y_AXIS));
        this.commandManager = manager;
    }

    @Override
    public void handleHistoryUpdate(DriverCommand command,String log) {
        commands.add(command);
        if (commandHistoryField != null) {
            JPanel entryPanel = new JPanel(new BorderLayout());

            JLabel logLabel = new JLabel(log);
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

    public void redoCommand(int commandIndex) {
        commandManager.setCurrentCommand(commands.get(commandIndex));
    }
}
