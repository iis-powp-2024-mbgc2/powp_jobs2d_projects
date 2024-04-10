package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.enums.RecordingOption;
import edu.kis.powp.jobs2d.events.SelectRecordingOptionListener;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.DriverCommand;

import java.util.List;

public class RecordFeature {
    private static Application application;
    private static CompoundCommand recordCommand;

    private static boolean isRecording = false;

    public static void setupRecorderPlugin(Application app) {
        recordCommand = new CompoundCommand("Record Command");
        application = app;

        SelectRecordingOptionListener startOption = new SelectRecordingOptionListener(RecordingOption.START);
        SelectRecordingOptionListener clearOption = new SelectRecordingOptionListener(RecordingOption.CLEAR);

        application.addComponentMenu(edu.kis.powp.jobs2d.features.RecordFeature.class, "Recorder");
        application.addComponentMenuElement(edu.kis.powp.jobs2d.features.RecordFeature.class, "Clear", clearOption);
        application.addComponentMenuElementWithCheckBox(edu.kis.powp.jobs2d.features.RecordFeature.class, "Start/Stop", startOption, false);

    }
    public static void setCommand(DriverCommand command){
        if(isRecording){
            recordCommand.addCommand(command);
        }
    }

    public static void start(){
        isRecording = !isRecording;

    }

    public static void clear(){
        recordCommand.clearCommand();
    }

    public static List<DriverCommand> getCommands(){
        return recordCommand.getCommands();
    }

    public static DriverCommand getRecordedCommand() {
        return recordCommand;
    }
}
