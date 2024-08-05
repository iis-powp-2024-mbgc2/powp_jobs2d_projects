package edu.kis.powp.jobs2d.transformations.changedriver;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.events.SelectClearPanelOptionListener;
import edu.kis.powp.jobs2d.features.*;

public class ApplyTransformationToNewDriver {


    public static void setUpTransformationToNewDriver() {
        RecordCommandsForTransformation.setupRecorder();
    }
    public static void clearRecordCommands() {
        RecordCommandsForTransformation.clear();
    }

    public static void applyTransformation(Job2dDriver transformation) {

        DrawerFeature.getDrawerController().clearPanel();
        LinesRecorder.getLinesRecorder().clearLines();

        ICommandManager manager = CommandsFeature.getCommandManager();
        manager.setCurrentCommand(RecordCommandsForTransformation.getRecordedCommand());

        DriverCommand command = CommandsFeature.getCommandManager().getCurrentCommand();
        command.execute(DriverFeature.getDriverManager().getCurrentDriver());

    }
}
