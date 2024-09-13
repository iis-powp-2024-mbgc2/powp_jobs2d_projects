package edu.kis.powp.jobs2d.transformations.changedriver;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
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

        RecordCommandsForTransformation.getRecordedCommand().execute(DriverFeature.getDriverManager().getCurrentDriver());
    }
}
