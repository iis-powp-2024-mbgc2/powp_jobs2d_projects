package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.drivers.DriverObserver;
import edu.kis.powp.jobs2d.events.CommandCallEvent;

import java.util.ArrayList;

public class DriverRecorder implements DriverObserver {
    private Boolean isRunning = false;
    private final ArrayList<DriverCommand> commands = new ArrayList<>();

    public boolean isRecording() { return this.isRunning; }
    public void startRecording() {
        commands.clear();
        this.isRunning = true;
    }

    public void stopRecording() { this.isRunning = false; }

    public void runMacro() {
        this.isRunning = false;
        CommandManager manager = CommandsFeature.getCommandManager();
        manager.setCurrentCommand(new ArrayList<>(commands), "MacroCommand");
        CommandsFeature.getCommandManager()
                .getCurrentCommand()
                .execute(DriverFeature.getDriverManager().getCurrentDriver());
    }

    @Override
    public void notifyObserver(CommandCallEvent event) {
        if(isRunning) commands.add(event.getCommand());
    }
}
