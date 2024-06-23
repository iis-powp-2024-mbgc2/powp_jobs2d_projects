package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.drivers.DriverManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HistoryComboBox {

    private static JComboBox<DriverCommand> history;
    private static ActionListener listener;
    private ICommandManager commandManager;
    private DriverManager driverManager;

    public HistoryComboBox(){};
    public void comboBoxInit(GridBagConstraints c, Container content, DriverManager driverManager, ICommandManager commandManager)
    {
        history = new JComboBox<>();
        this.commandManager = commandManager;
        this.driverManager = driverManager;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(history, c);
    }

    public void initAction()
    {
        listener = (ActionEvent e) ->
        {
            CompoundCommand command = (CompoundCommand) history.getSelectedItem();
            if (command != null) {
                CommandHistory commandHistory = new CommandHistory();
                CompoundCommand newCommand = new CompoundCommand(commandHistory.getByName(command), command.toString());
                commandManager.setCurrentCommand(newCommand);
                commandManager.runCommand(driverManager.getCurrentDriver());
            }
        };
        history.addActionListener(listener);
    }

    public void actualize()
    {
        history.removeActionListener(listener);
        CommandHistory commandHistory = new CommandHistory();
        history.removeAllItems();
        for(DriverCommand driverCommand: commandHistory.getActionsHistory())
        {
            history.addItem(driverCommand);
            history.setSelectedItem(driverCommand);
        }
        history.repaint();
        history.addActionListener(listener);
    }
}
