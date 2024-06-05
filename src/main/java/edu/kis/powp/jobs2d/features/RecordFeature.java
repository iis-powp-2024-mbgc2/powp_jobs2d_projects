package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.builder.CompoundCommandBuilder;
import edu.kis.powp.jobs2d.enums.RecordingOption;
import edu.kis.powp.jobs2d.events.SelectRecordingOptionListener;

public class RecordFeature {
    private static Application application;
    private static CompoundCommandBuilder recordCommandBuilder;

    private static boolean isRecording = false;

    public static void setupRecorderPlugin(Application app) {
        recordCommandBuilder = new CompoundCommandBuilder().setName("Record Command");
        application = app;

        SelectRecordingOptionListener startOption = new SelectRecordingOptionListener(RecordingOption.START);
        SelectRecordingOptionListener clearOption = new SelectRecordingOptionListener(RecordingOption.CLEAR);

        application.addComponentMenu(edu.kis.powp.jobs2d.features.RecordFeature.class, "Recorder");
        application.addComponentMenuElement(edu.kis.powp.jobs2d.features.RecordFeature.class, "Clear", clearOption);
        application.addComponentMenuElementWithCheckBox(edu.kis.powp.jobs2d.features.RecordFeature.class, "Start/Stop", startOption, false);

    }
    public static void setCommand(DriverCommand command){
        if(isRecording){
            recordCommandBuilder.addCommand(command);
        }
    }


    public static void start(){
        isRecording = !isRecording;

    }

    public static void clear(){
        recordCommandBuilder = new CompoundCommandBuilder().setName("Record Command");
    }

    public static DriverCommand getRecordedCommand() {
        return recordCommandBuilder.build();
    }

    public static boolean isRecording() { return isRecording; }
}
