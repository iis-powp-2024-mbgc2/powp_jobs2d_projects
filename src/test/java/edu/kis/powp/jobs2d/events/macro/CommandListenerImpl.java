package edu.kis.powp.jobs2d.events.macro;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.events.CommandListener;
import edu.kis.powp.jobs2d.events.ExecuteCommandEvent;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.util.ArrayList;

public class CommandListenerImpl implements CommandListener {
    private Boolean isRunning = false;
    private final ArrayList<DriverCommand> commands = new ArrayList<>();
    private final DriverManager driverManager;

    public CommandListenerImpl(DriverManager driverManager) {
        this.driverManager = driverManager;
    }

    public void startRecording() { this.isRunning = true; }
    public void stopRecording() { this.isRunning = false; }

    public void runMacro() {
        this.isRunning = false;
        CommandManager manager = CommandsFeature.getCommandManager();
        manager.setCurrentCommand(commands, "MacroCommand");
        CommandsFeature.getCommandManager().getCurrentCommand().execute(driverManager.getCurrentDriver());
        commands.clear();
    }

    @Override
    public void notify(ExecuteCommandEvent event) {
        if(isRunning) commands.add(event.getCommand());
    }


}
