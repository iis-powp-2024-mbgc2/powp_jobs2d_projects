package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kis.powp.jobs2d.command.IDriverCommand;
import edu.kis.powp.jobs2d.command.visitor.CommandCounterVisitor;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

public class SelectRunCurrentCommandOptionListener implements ActionListener {

    private final DriverManager driverManager;
    private final CommandCounterVisitor commandCounterVisitor;

    public SelectRunCurrentCommandOptionListener(DriverManager driverManager, CommandCounterVisitor commandCounterVisitor) {
        this.driverManager = driverManager;
        this.commandCounterVisitor = commandCounterVisitor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IDriverCommand command = CommandsFeature.getCommandManager().getCurrentCommand();
        command.execute(driverManager.getCurrentDriver());
        command.accept(commandCounterVisitor);
    }
}